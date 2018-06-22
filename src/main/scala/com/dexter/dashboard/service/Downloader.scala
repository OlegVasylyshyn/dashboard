package com.dexter.dashboard.service

import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import akka.event.Logging
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.stream.ActorMaterializer
import com.dexter.dashboard.json.GsonParser
import com.dexter.dashboard.model.ActorMessage.{Parse, Update}

import scala.concurrent.{ExecutionContextExecutor, Future}

object Downloader {

  implicit val system: ActorSystem = ActorSystem()

  def update() = {

    val parser = system.actorOf(Props(new GsonParser))
    val downloader = system.actorOf(Props(new DownloaderActor(parser)))

    downloader ! Update

  }

  class DownloaderActor(parser: ActorRef) extends Actor {

    val log = Logging(system, classOf[DownloaderActor])

    def receive = {
      case Update =>
//        implicit val system: ActorSystem = ActorSystem()
        implicit val executionContext: ExecutionContextExecutor = system.dispatcher
        implicit val materializer: ActorMaterializer = ActorMaterializer()(system)
        val response: Future[HttpResponse] = Http()(system).singleRequest(HttpRequest(uri = "https://public-api.adsbexchange.com/VirtualRadar/AircraftList.json"))

        response
          .map(res => res.entity.dataBytes.map(b => b.utf8String))
          .map(source => parser ! Parse(source, this.self))
          .onComplete(e => log.info("Was downloaded new updates for aircrafts"))

        response.failed.foreach(e => log.error(e.toString))
    }

  }
}

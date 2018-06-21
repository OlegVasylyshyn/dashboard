package com.dexter.dashboard.service

import akka.actor.ActorSystem
import akka.event.Logging
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.stream.ActorMaterializer
import com.dexter.dashboard.json.GsonParser
import com.dexter.dashboard.model.Aircraft

import scala.collection.mutable.ArrayBuffer
import scala.concurrent.{ExecutionContextExecutor, Future}

class Downloader {

  val system = ActorSystem("LifeCycleSystem")
  val log = Logging(system, classOf[Downloader])

  def update(aircrafts: ArrayBuffer[Aircraft]) = {

    implicit val system: ActorSystem = ActorSystem()
    implicit val executionContext: ExecutionContextExecutor = system.dispatcher
    implicit val materializer: ActorMaterializer = ActorMaterializer()

    val response: Future[HttpResponse] = Http().singleRequest(HttpRequest(uri = "https://public-api.adsbexchange.com/VirtualRadar/AircraftList.json"))

    response
        .map(res => res.entity.dataBytes.map(b => b.utf8String))
        .map(source => GsonParser.update(source, aircrafts))
        .onComplete(e => log.info("Was downloaded new aircrafts - {}", e.get))

    response.failed.foreach(e => log.error(e.toString))

  }

}

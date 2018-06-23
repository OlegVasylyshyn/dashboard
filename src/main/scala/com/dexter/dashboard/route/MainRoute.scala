package com.dexter.dashboard.route

import akka.NotUsed
import akka.actor.ActorSystem
import akka.event.Logging
import akka.http.scaladsl.model.sse.ServerSentEvent
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.stream.scaladsl.Source
import ch.megard.akka.http.cors.scaladsl.CorsDirectives._
import com.dexter.dashboard.model.Aircraft
import com.dexter.dashboard.service.AircraftService
import io.circe._
import io.circe.generic.semiauto._
import io.circe.syntax._

import scala.concurrent.duration._

object MainRoute {

  implicit val system: ActorSystem = ActorSystem("routing")
  val log = Logging(system, classOf[AircraftService])

  private val service: AircraftService = AircraftService.apply

  def route: Route = cors() {
    import akka.http.scaladsl.marshalling.sse.EventStreamMarshalling._
    implicit val decodeAircraft: Encoder[Aircraft] = deriveEncoder[Aircraft]

    path("events") {
      get {
        complete {
          Source
            .tick(1.seconds, 5.seconds, NotUsed)
            .map(_ => service.getAircrafts().asJson.toString())
            .map(d => ServerSentEvent(d))
            .keepAlive(5.second, () => ServerSentEvent.heartbeat)
        }
      }
    }

    //    path("hello") {
    //      get {
    //        complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1>Say hello to akka-http</h1>"))
    //      }
    //    }
  }

}

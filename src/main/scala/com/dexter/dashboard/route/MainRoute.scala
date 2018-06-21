package com.dexter.dashboard.route

import java.time.LocalTime

import akka.NotUsed
import akka.http.scaladsl.model.sse.ServerSentEvent
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.stream.scaladsl.Source
import ch.megard.akka.http.cors.scaladsl.CorsDirectives._
import com.dexter.dashboard.model.Aircraft
import com.dexter.dashboard.service.AircraftService
import io.circe._, io.circe.generic.semiauto._,io.circe.syntax._

import scala.collection.mutable.ArrayBuffer
import scala.concurrent.duration._

object MainRoute {

  private val service: AircraftService = AircraftService.apply

  def route: Route = cors() {
    import akka.http.scaladsl.marshalling.sse.EventStreamMarshalling._

    implicit val decodeAircraft: Encoder[Aircraft] = deriveEncoder[Aircraft]
//    implicit val decodeArrayAircraft: Encoder[ArrayBuffer[Aircraft]] = deriveEncoder[ArrayBuffer[Aircraft]]

    path("events") {
      get {
        complete {
          Source
            .tick(5.seconds, 5.seconds, NotUsed)
            .map(_ => service.getAircrafts())
            .map(ac => (ac.asJson, LocalTime.now()))
            .map(t => ServerSentEvent(t.toString()))
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

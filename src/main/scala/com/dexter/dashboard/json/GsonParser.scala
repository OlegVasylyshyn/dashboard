package com.dexter.dashboard.json

import java.io.StringReader

import akka.actor.{Actor, ActorSystem}
import akka.event.Logging
import akka.stream.ActorMaterializer
import com.dexter.dashboard.model.ActorMessage.{Parse, Update}
import com.dexter.dashboard.model.Aircraft
import com.dexter.dashboard.service.AircraftService
import com.google.gson.JsonParser
import com.google.gson.stream.JsonReader

import scala.concurrent.ExecutionContextExecutor

class GsonParser extends Actor  {

  private val service: AircraftService = AircraftService.apply

  val system = ActorSystem("LifeCycleSystem")
  val log = Logging(system, classOf[GsonParser])

  def receive = {
    case Parse(source, downloader) => {
//      implicit val system: ActorSystem = ActorSystem()
      implicit val executionContext: ExecutionContextExecutor = system.dispatcher
      implicit val materializer: ActorMaterializer = ActorMaterializer()

      val sb = new StringBuilder
      source.runForeach(str => sb.append(str))
        .onComplete(emptySuccess => {

          service.getAircrafts().clear
          val jsonParser = new JsonParser

          val reader = new JsonReader(new StringReader(sb.toString))
          reader.setLenient(true)
          val element = jsonParser.parse(reader)
          val jsonObject = element.getAsJsonObject
          val jsonArray = jsonObject.getAsJsonArray("acList")
          jsonArray.forEach(ac => {
            val aircraftJson = ac.getAsJsonObject
            val icao = aircraftJson.getAsJsonPrimitive("Icao").getAsString
            val alt = if(aircraftJson.getAsJsonPrimitive("Alt") == null) 0 else aircraftJson.getAsJsonPrimitive("Alt").getAsInt
            val long = if(aircraftJson.getAsJsonPrimitive("Long") == null) 0 else aircraftJson.getAsJsonPrimitive("Long").getAsFloat
            val lat = if(aircraftJson.getAsJsonPrimitive("Lat") == null) 0 else aircraftJson.getAsJsonPrimitive("Lat").getAsFloat
            val aircraft = Aircraft(icao, alt, long, lat)
            service.getAircrafts += aircraft
          })

          downloader ! Update
          log.info("Was parsed next amount of aircrafts - {}", service.getAircrafts().size)
        })
    }
  }

}

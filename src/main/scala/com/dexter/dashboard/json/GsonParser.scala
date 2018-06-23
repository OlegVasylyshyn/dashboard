package com.dexter.dashboard.json

import java.io.StringReader

import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import akka.event.Logging
import akka.stream.ActorMaterializer
import com.dexter.dashboard.model.ActorMessage.{AircraftLocation, Parse}
import com.dexter.dashboard.model.Aircraft
import com.dexter.dashboard.service.AircraftLocationService
import com.google.gson.JsonParser
import com.google.gson.stream.JsonReader

import scala.collection.mutable.ArrayBuffer
import scala.concurrent.ExecutionContextExecutor

class GsonParser extends Actor  {

  val system = ActorSystem("parser")
  val log = Logging(system, classOf[GsonParser])

  val aircraftLocationService: ActorRef = system.actorOf(Props(new AircraftLocationService))

  def receive: PartialFunction[Any, Unit] = {
    case Parse(source, downloader) => {

      implicit val executionContext: ExecutionContextExecutor = system.dispatcher
      implicit val materializer: ActorMaterializer = ActorMaterializer()

      val sb = new StringBuilder
      source.runForeach(str => sb.append(str))
        .onComplete(emptySuccess => {

          val aircrafts = new ArrayBuffer[Aircraft]
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
            aircrafts += aircraft
          })

          aircraftLocationService ! AircraftLocation(aircrafts, downloader)
          log.info("Was parsed next amount of aircrafts - {}", aircrafts.size)
        })
    }
  }

}

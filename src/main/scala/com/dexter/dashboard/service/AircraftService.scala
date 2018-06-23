package com.dexter.dashboard.service

import akka.actor.{Actor, ActorRef, ActorSystem}
import akka.event.Logging
import com.dexter.dashboard.model.ActorMessage.{AircraftLocation, Update}
import com.dexter.dashboard.model.Aircraft
import com.dexter.dashboard.util.AircraftUtil
import com.dexter.dashboard.util.AircraftUtil._

import scala.collection.mutable._

trait AircraftService {
  // I just wanted keep everything sorted
  def getAircrafts() : TreeMap[String, LinkedHashSet[Aircraft]]
  def putAircraft(city: String, aircraft: Aircraft): Unit
  def clear():Unit
}

private class Engine extends AircraftService {
  val aircrafts = new TreeMap[String, LinkedHashSet[Aircraft]]
  def getAircrafts() : TreeMap[String, LinkedHashSet[Aircraft]] = aircrafts

  def putAircraft(city: String, aircraft: Aircraft): Unit = {
    val aircraftByCity = aircrafts.getOrElseUpdate(city, new LinkedHashSet[Aircraft])
    aircraftByCity += aircraft
  }

  def clear = {
    aircrafts.foreach(tuple => tuple._2.clear())
  }

}

// I just tried implement Singleton
object AircraftService {

  var service: AircraftService = null

  // lazy initialisation .. thread safe
  def apply: AircraftService = this.synchronized {
    if(service == null) {
      service = new Engine
      service
    } else service
  }

}

class AircraftLocationService  extends Actor  {

  val service: AircraftService = AircraftService.apply

  implicit val system: ActorSystem = ActorSystem("aircraft-location-service")
  val log = Logging(system, classOf[AircraftService])

  def receive: PartialFunction[Any, Unit] = {
    case AircraftLocation(aircrafts: ArrayBuffer[Aircraft], downloader: ActorRef) => {

      service.clear()

      aircrafts.foreach(ac => {

        if(AircraftUtil.inIstanbul(ac.long, ac.lat) && ac.alt < 30000) service.putAircraft(ISTANBUL, ac)
        else if(AircraftUtil.inLondon(ac.long, ac.lat) && ac.alt < 30000) service.putAircraft(LONDON, ac)
        else if(AircraftUtil.inFrankfurt(ac.long, ac.lat) && ac.alt < 30000) service.putAircraft(FRANKFURT, ac)
        else if(AircraftUtil.inAtlanta(ac.long, ac.lat) && ac.alt < 30000) service.putAircraft(ATLANTA, ac)
        else if(AircraftUtil.inParis(ac.long, ac.lat) && ac.alt < 30000) service.putAircraft(PARIS, ac)
        else if(AircraftUtil.inTokyo(ac.long, ac.lat) && ac.alt < 30000) service.putAircraft(TOKYO, ac)
        else if(AircraftUtil.inNYC(ac.long, ac.lat) && ac.alt < 30000) service.putAircraft(NEY_YORK, ac)

      })

      log.info("Was processed all new aircrafts")
      log.info("In Istanbul there are " + service.getAircrafts.get(ISTANBUL).map(l => l.size).getOrElse(0) + " aircraft")
      log.info("In London there are " + service.getAircrafts.get(LONDON).map(l => l.size).getOrElse(0) + " aircraft")
      log.info("In Frankfurt there are " + service.getAircrafts.get(FRANKFURT).map(l => l.size).getOrElse(0) + " aircraft")
      log.info("In Atlanta there are " + service.getAircrafts.get(ATLANTA).map(l => l.size).getOrElse(0) + " aircraft")
      log.info("In Paris there are " + service.getAircrafts.get(PARIS).map(l => l.size).getOrElse(0) + " aircraft")
      log.info("In Tokyo there are " + service.getAircrafts.get(TOKYO).map(l => l.size).getOrElse(0) + " aircraft")
      log.info("In New York there are " + service.getAircrafts.get(NEY_YORK).map(l => l.size).getOrElse(0) + " aircraft")

      downloader ! Update
    }
  }



}
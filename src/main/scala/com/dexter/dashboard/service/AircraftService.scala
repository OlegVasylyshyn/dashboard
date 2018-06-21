package com.dexter.dashboard.service

import com.dexter.dashboard.model.Aircraft

import scala.collection.mutable.ArrayBuffer

trait AircraftService {

  def update()
  def getAircrafts() : ArrayBuffer[Aircraft]

}

private class Engine extends AircraftService {
  private val downloader: Downloader = new Downloader
  private var aircrafts = new ArrayBuffer[Aircraft](10000)

  def update() = {
    downloader.update()
  }

  def getAircrafts() : ArrayBuffer[Aircraft] = aircrafts
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

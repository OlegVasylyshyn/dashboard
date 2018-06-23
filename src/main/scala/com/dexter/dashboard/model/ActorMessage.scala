package com.dexter.dashboard.model

import akka.actor.ActorRef
import akka.stream.scaladsl.Source

import scala.collection.mutable.ArrayBuffer

object ActorMessage {

  case object Update
  final case class Parse(source: Source[String, Any], downloader: ActorRef)
  final case class AircraftLocation(aircraft: ArrayBuffer[Aircraft], downloader: ActorRef)

}

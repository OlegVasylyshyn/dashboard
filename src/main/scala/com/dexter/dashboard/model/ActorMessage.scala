package com.dexter.dashboard.model

import akka.actor.ActorRef
import akka.stream.scaladsl.Source

object ActorMessage {

  final case class Parse(source: Source[String, Any], downloader: ActorRef)
  case object Update

}

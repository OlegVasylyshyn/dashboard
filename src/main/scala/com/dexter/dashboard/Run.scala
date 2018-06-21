package com.dexter.dashboard

import akka.actor.ActorSystem
import akka.event.Logging
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import com.dexter.dashboard.route.MainRoute
import com.dexter.dashboard.service.Downloader

import scala.concurrent.ExecutionContextExecutor
import scala.io.StdIn

object Run extends App {

  implicit val system = ActorSystem("my-system")
  implicit val materializer: ActorMaterializer = ActorMaterializer()
  implicit val executionContext: ExecutionContextExecutor = system.dispatcher

  val log = Logging(system, classOf[Downloader])
  val bindingFuture = Http().bindAndHandle(MainRoute.route, "localhost", 8080)

  log.info(s"Just enter http://localhost:8080/events \t.Press RETURN to stop...")
  StdIn.readLine() // let it run until user presses return
  bindingFuture
    .flatMap(_.unbind()) // trigger unbinding from the port
    .onComplete(_ => system.terminate()) // and shutdown when done

}

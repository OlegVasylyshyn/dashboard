package com.dexter.dashboard.json
//import jawn.AsyncParser

// TODO: implement parcing in a stream
object CirceParser {

  //    implicit val decodeAircraft: Decoder[List[Aircraft]] =
  //      Decoder[List[Aircraft]].prepare(_.downField("acList"))



  //    implicit val decodeResponse: Decoder[Array[Aircraft]] =
  //      Decoder.decodeArray.prepare(_.downField("acList"))

  //    implicit val decodeAircraft: Decoder[Aircraft] =
  //      Decoder.forProduct4("Icao", "Alt", "Long", "Lat")(Aircraft.apply)

  //    implicit val decodeClipsParam = Decoder[List[Aircraft]].prepare(
  //      _.downField("acList")
  //    )

  //    implicit val decodeAircraft: Decoder[Aircraft] = Decoder.instance(a =>
  //      for {
  //        icao <- a.downField("Icao").as[String]
  //        alt <- a.downField("Alt").as[Int]
  //        long <- a.downField("Long").as[Float]
  //        lat <- a.downField("Lat").as[Float]
  //      } yield Aircraft(icao, alt, long, lat)
  //    )
  //
  //    implicit val decodeResp: Decoder[Response] = Decoder.instance(c =>
  //      for {
  //        t <- c.downField("acList").as[List[Aircraft]]
  //      } yield Response(t)
  //    )


  //    implicit val decodeLot: Decoder[Aircraft] = Decoder.instance(c =>
  //      for {
  //        icao <- c.downField("acList").downField("Icao").as[String]
  //        alt <- c.downField("acList").downField("Alt").as[Int]
  //        long <- c.downField("acList").downField("Long").as[Float]
  //        lat <- c.downField("acList").downField("Lat").as[Float]
  //      } yield Aircraft(icao, alt, long, lat)
  //    )

  //    def decodeNew = JsonStreamParser.flow[Json].map(json => println(json.toString))


  //    val result = for {
  //      resp <- Http().singleRequest(HttpRequest(uri = "https://public-api.adsbexchange.com/VirtualRadar/AircraftList.json"))
  //      source <- resp.entity.dataBytes.map(b => b.utf8String)
  //      decoded <- decode[Response](source)
  //    } yield println(decoded)


  //    responseFuture
  //      .onComplete {
  //        case Success(res) => res.entity.dataBytes.map(b => b.utf8String).reduce(_ + _).runForeach(println)
  //        case Success(res) => res.entity.dataBytes.map(b => b.utf8String).reduce(_ + _).runForeach(s => decode[List[Aircraft]](s).foreach(println))
  //        case Success(res) => res.entity.dataBytes.map(b => b.utf8String).reduce(_ + _).runForeach(s => println(decode[Response](s)))
  //        case Success(res) => println(decode[Response](res.entity.dataBytes.map(b => b.utf8String).reduce(_ + _).+("")))
  //        case Success(res) => println(decode[Response](res.entity.dataBytes.map(b => b.utf8String).reduce(_ + _) + ""))
  //        case Success(res) => println(decode[Response](res.entity.dataBytes.map(b => b.utf8String).reduce(_ + _).toString()))
  //        case Failure(_)   => sys.error("something wrong")
  //      }

}

package com.dexter.dashboard.model

case class Response(
                     src : Int,
                     feeds : Array[Feeds],
                     srcFeed : Int,
                     showSil : Boolean,
                     showFlg : Boolean,
                     showPic : Boolean,
                     flgH : Float,
                     flgW :Float,
                     acList: List[Aircraft],
                     totalAc : Long,
                     lastDv : String,
                     shtTrlSec : Long,
                     stm : Long
                   )
case class Aircraft(
                     icao : String,
                     alt : Int,
                     long : Float,
                     lat : Float
                   )
case class Feeds(id : Int, name : String, polarPlot : Boolean)
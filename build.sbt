name := "dashboard"

version := "0.1"

scalaVersion := "2.12.6"

libraryDependencies ++= {
  Seq(
    "com.typesafe.akka" %% "akka-http"   % "10.1.3",
    "com.typesafe.akka" %% "akka-stream" % "2.5.12",
    "ch.megard" %% "akka-http-cors" % "0.3.0",
    "com.typesafe.akka" %% "akka-actor" % "2.5.13",
    "io.circe" %% "circe-parser" % "0.9.3",
    "io.circe" %% "circe-generic" % "0.9.3",
    "co.fs2" %% "fs2-core" % "0.9.3",
    "io.circe" %% "circe-fs2" % "0.9.0",
    "de.knutwalker" %% "akka-stream-json" % "3.3.0",
    "org.spire-math"    %% "jawn-parser" % "0.11.0",
    "com.google.code.gson" % "gson" % "2.8.5"
  )
}
package com.dexter.dashboard.util

object AircraftUtil {

  val IST_LAT: Float = 40.982555f
  val IST_LON: Float = 28.820829f
  val LHR_LAT: Float = 51.470020f
  val LHR_LON: Float = -0.454295f
  val FRA_LAT: Float = 50.110924f
  val FRA_LON: Float = 8.682127f
  val ATL_LAT: Float = 33.640411f
  val ATL_LON: Float = -84.419853f
  val PAR_LAT: Float = 49.0096906f
  val PAR_LON: Float = 2.54792450f
  val TOK_LAT: Float = 35.765786f
  val TOK_LON: Float = 140.386347f
  val JFK_LAT: Float = 40.6441666667f
  val JFK_LON: Float = -73.7822222222f

  def inIstanbul(lon: Float, lat: Float): Boolean = inBoundariesOf(lon, lat, boundingBox(IST_LON, IST_LAT, 80f))
  def inLondon(lon: Float, lat: Float): Boolean = inBoundariesOf(lon, lat, boundingBox(LHR_LON, LHR_LAT, 80f))
  def inFrankfurt(lon: Float, lat: Float): Boolean = inBoundariesOf(lon, lat, boundingBox(FRA_LON, FRA_LAT, 80f))
  def inAtlanta(lon: Float, lat: Float): Boolean = inBoundariesOf(lon, lat, boundingBox(ATL_LON, ATL_LAT, 80f))
  def inParis(lon: Float, lat: Float): Boolean = inBoundariesOf(lon, lat, boundingBox(PAR_LON, PAR_LAT, 80f))
  def inTokyo(lon: Float, lat: Float): Boolean = inBoundariesOf(lon, lat, boundingBox(TOK_LON, TOK_LAT, 80f))
  def inNYC(lon: Float, lat: Float): Boolean = inBoundariesOf(lon, lat, boundingBox(JFK_LON, JFK_LAT, 80f))

  def boundingBox(lon: Float, lat: Float, radius: Float): Array[Double] = {
    val boundingLon1 = lon + radius / Math.abs(Math.cos(Math.toRadians(lat)) * 69)
    val boundingLon2 = lon - radius / Math.abs(Math.cos(Math.toRadians(lat)) * 69)
    val boundingLat1 = lat + (radius / 69)
    val boundingLat2 = lat - (radius / 69)
    Array[Double](boundingLon1, boundingLat1, boundingLon2, boundingLat2)
  }

  def inBoundariesOf(lon: Float, lat: Float, boundaries: Array[Double]): Boolean =
    !(lon > boundaries(0) || lon < boundaries(2)) && !(lat > boundaries(1) || lat < boundaries(3))

  val ISTANBUL = "Istanbul"
  val LONDON = "London"
  val FRANKFURT = "Frankfurt"
  val ATLANTA = "Atlanta"
  val PARIS = "Paris"
  val TOKYO = "Tokyo"
  val NEY_YORK = "New York"

}


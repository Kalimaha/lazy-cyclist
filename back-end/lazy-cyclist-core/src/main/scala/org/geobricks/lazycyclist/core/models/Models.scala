package org.geobricks.lazycyclist.core.models


object Models {

  case class Route(steps: List[Step])
  case class LatLon(lat: Double, lon: Double)
  case class Step(distance: BigInt, start: LatLon, end: LatLon)

  case class XY(x: Double, y: Double)
  case class ElevationProfile(points: List[XY])

  object Field {
    val LAT       = "lat"
    val LON       = "lng"
    val LEGS      = "legs"
    val STEPS     = "steps"
    val VALUE     = "value"
    val ROUTES    = "routes"
    val RESULTS   = "results"
    val DISTANCE  = "distance"
    val LOCATION  = "location"
    val ELEVATION = "elevation"
    val END       = "end_location"
    val START     = "start_location"
  }
}
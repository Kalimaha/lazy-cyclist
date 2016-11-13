package org.geobricks.lazycyclist.core.models


object Models {

  case class Route(steps: List[Step])
  case class LatLon(lat: Double, lon: Double)
  case class Step(distance: BigInt, start: LatLon, end: LatLon)

  case class Segment(start: SegmentEnd, end: SegmentEnd)
  case class SegmentEnd(x: Option[BigInt], y: Option[Double], lat: Option[Double], lon: Option[Double])
  case class ElevationProfile(segments: List[Segment])

  object Field {
    val LAT       = "lat"
    val LON       = "lng"
    val LEGS      = "legs"
    val STEPS     = "steps"
    val VALUE     = "value"
    val ROUTES    = "routes"
    val END       = "end_location"
    val DISTANCE  = "distance"
    val START     = "start_location"
  }
}
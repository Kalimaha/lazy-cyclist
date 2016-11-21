package org.geobricks.lazycyclist.core.models

import org.geobricks.lazycyclist.core.parsers.ElevationParser
import org.geobricks.lazycyclist.core.utils.MathUtils


object Models {

  case class Route(steps: List[Step])
  case class XY(x: Double, y: Double)
  case class LatLon(lat: Double, lon: Double)
  case class Segment(start: XY, end: XY, climb: Boolean, distance: Double)
  case class Step(distance: BigInt, start: LatLon, end: LatLon)

  class ElevationProfile(private var _points: List[XY]) {
    def points = _points

    def segments = points.sliding(2).toList.map((s: List[XY]) => {
      Segment(s.head, s.last, s.last.y > s.head.y, Math.abs(s.last.x - s.head.x))
    })

    def climbs = segments.filter(_.climb)

    def distance = segments.foldRight(0.0)(_.distance + _)

    println(segments)
  }

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
package org.geobricks.lazycyclist.core.models

import org.geobricks.lazycyclist.core.utils.MathUtils


object Models {

  case class Route(steps: List[Step])
  case class XY(x: Double, y: Double)
  case class LatLon(lat: Double, lon: Double)
  case class Segment(start: XY, end: XY, climb: Boolean, distance: Double, slope: Double)
  case class Step(distance: BigInt, start: LatLon, end: LatLon)

  class ElevationProfile(points: List[XY]) {
    def toEnhancedElevationProfile: EnhancedElevationProfile = {
      EnhancedElevationProfile(points, climbs, distance, slope)
    }

    private def segments = points.sliding(2).toList.map((s: List[XY]) => {
      Segment(s.head, s.last, s.last.y > s.head.y, MathUtils.distance(s.head, s.last), MathUtils.slope(s.head, s.last))
    })

    private def climbs = segments.filter(_.climb)

    private def distance = segments.foldRight(0.0)(_.distance + _)

    private def slope = climbs.foldRight(0.0)(_.slope + _) / climbs.length
  }

  case class EnhancedElevationProfile(points: List[XY], climbs: List[Segment], totalDistance: Double, averageSlope: Double)

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
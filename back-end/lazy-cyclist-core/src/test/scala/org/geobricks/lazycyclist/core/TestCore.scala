package org.geobricks.lazycyclist.core

import org.geobricks.lazycyclist.core.models.Models._
import org.geobricks.lazycyclist.core.Core._
import org.geobricks.lazycyclist.core.clients.{DirectionsClient, ElevationClient}
import org.scalatest.FunSpec

class TestCore extends FunSpec {
  describe(".route2XYs") {
    val start             = LatLon(-37.8168987, 144.9955671)
    val end_1             = LatLon(-37.816969 , 144.9961949)
    val end_2             = LatLon(-37.8164005, 144.9962741)
    val step_1            = Step(56, start, end_1)
    val step_2            = Step(56, end_1, end_2)
    val route             = Route(List(step_1, step_2))
    val lleMap            = Map(start -> 100.toDouble, end_1 -> 200.toDouble, end_2 -> 175.toDouble)
    val xy_1              = XY(0, 100)
    val xy_2              = XY(56, 200)
    val xy_3              = XY(120, 175)
    val elevationProfile  = ElevationProfile(List(xy_1, xy_2, xy_3))

    it("converts a Route into a list of XY points") {
      assert(route2XYs(route, lleMap) == elevationProfile)
    }
  }

  describe(".elevationProfile") {
    val from        = "2 McGoun St, Melbourne, Australia"
    val to          = "511 Church St, Melbourne, Australia"
    val directions  = DirectionsClient("")
    val elevation   = ElevationClient("")

    it("calculates the elevation profile of the routes") {
      elevationProfile(from, to, directions, elevation)
    }
  }
}

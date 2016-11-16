package org.geobricks.lazycyclist.core

import org.geobricks.lazycyclist.core.models.Models._
import org.geobricks.lazycyclist.core.Core._
import org.geobricks.lazycyclist.core.clients.{DirectionsClient, ElevationClient}
import org.scalatest.FunSpec

class TestCore extends FunSpec {
  describe(".distance") {
    val from  = LatLon(-37.8168987, 144.9955671)
    val to    = LatLon(-37.816969 , 144.9961949)

    it("calculates th distance between two points") {
      assert(distance(from, to) == 56)
    }
  }


//  describe(".route2profile") {
//    val start   = LatLon(-37.8168987, 144.9955671)
//    val end     = LatLon(-37.816969 , 144.9961949)
//    val step    = Step(56, start, end)
//    val route   = Route(List(Step))
//    val lleMap  = Map(start -> 100, end -> 200)
//    val segment = Segment(0, 100, 56, 200)
//    val profile = ElevationProfile(List(segment))
//
//    it("converts a Route into an ElevationProfile") {
//
//    }
//  }

  describe(".elevationProfile") {
    val from        = "2 McGoun St, Melbourne, Australia"
    val to          = "511 Church St, Melbourne, Australia"
    val directions  = DirectionsClient("")
    val elevation   = ElevationClient("")

    it("calculates the elevation profile of the routes") {
      elevationProfile(from, to, directions, elevation)
    }
  }

  describe(".routes2coordinates") {
    val start   = LatLon(-37.8168987, 144.9955671)
    val end     = LatLon(-37.816969 , 144.9961949)
    val step    = Step(56, start, end)
    val routes  = List(Route(List(step)))

    it(".converts a list of routes into a list of coordinates") {
      assert(routes2coordinates(routes) == Right(List(LatLon(-37.8168987, 144.9955671), LatLon(-37.816969, 144.9961949))))
    }
  }
}

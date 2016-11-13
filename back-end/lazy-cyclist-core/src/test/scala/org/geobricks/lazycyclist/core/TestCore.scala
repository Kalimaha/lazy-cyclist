package org.geobricks.lazycyclist.core

import org.geobricks.lazycyclist.core.models.Models._
import org.geobricks.lazycyclist.core.Core._
import org.geobricks.lazycyclist.core.clients.{DirectionsClient, ElevationClient}
import org.scalatest.FunSpec

class TestCore extends FunSpec {
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

  describe(".steps2segments") {
    val step_1          = Step(56, LatLon(0, 0), LatLon(0, 0))
    val step_2          = Step(130, LatLon(0, 0), LatLon(0, 0))

    val segmentStart_1  = SegmentEnd(Some(0), None, Some(step_1.start.lat), Some(step_1.start.lon))
    val segmentEnd_1    = SegmentEnd(Some(56), None, Some(step_1.end.lat), Some(step_1.end.lon))
    val segment_1       = Segment(segmentStart_1, segmentEnd_1)

    val segmentStart_2  = SegmentEnd(Some(56), None, Some(step_2.start.lat), Some(step_2.start.lon))
    val segmentEnd_2    = SegmentEnd(Some(186), None, Some(step_2.end.lat), Some(step_2.end.lon))
    val segment_2       = Segment(segmentStart_2, segmentEnd_2)

    val steps           = List(step_1, step_2)
    val segments        = List(segment_1, segment_2)

    it("converts a list of Step into a list of Segment") { assert(steps2segments(steps) == segments) }
  }

  describe(".step2segment") {
    val start         = LatLon(-37.8168987, 144.9955671)
    val end           = LatLon(-37.816969 , 144.9961949)
    val step          = Step(56, start, end)
    val segmentStart  = SegmentEnd(Some(0), None, Some(step.start.lat), Some(step.start.lon))
    val segmentEnd    = SegmentEnd(Some(56), None, Some(step.end.lat), Some(step.end.lon))
    val segment       = Segment(segmentStart, segmentEnd)

    it("converts a Step into a Segment") { assert(step2segment(step, 0) == segment) }
  }
}

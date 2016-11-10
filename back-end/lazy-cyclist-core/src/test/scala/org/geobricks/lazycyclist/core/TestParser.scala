package org.geobricks.lazycyclist.core

import org.geobricks.lazycyclist.core.Parser._
import org.scalatest.FunSpec

class TestParser extends FunSpec {
  describe(".toStep") {
    val json  = "{\"distance\":{\"text\":\"56 m\",\"value\":56},\"duration\":{\"text\":\"1 min\",\"value\":7},\"end_location\":{\"lat\":-37.816969,\"lng\":144.9961949},\"html_instructions\":\"Head \\u003cb\\u003eeast\\u003c/b\\u003e on \\u003cb\\u003eMcgoun St\\u003c/b\\u003e toward \\u003cb\\u003eThomas St\\u003c/b\\u003e\",\"polyline\":{\"points\":\"rbyeFim~sZL{B\"},\"start_location\":{\"lat\":-37.8168987,\"lng\":144.9955671},\"travel_mode\":\"BICYCLING\"}"
    val start = LatLon(-37.8168987, 144.9955671)
    val end   = LatLon(-37.816969, 144.9961949)
    val step  = Step(56, start, end)

    it("converts JSON into a Step object") {
      assert(toStep(json) == Right(step))
    }
  }
}
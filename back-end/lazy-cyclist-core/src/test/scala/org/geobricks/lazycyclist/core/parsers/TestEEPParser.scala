package org.geobricks.lazycyclist.core.parsers

import org.geobricks.lazycyclist.core.models.Models.{EnhancedElevationProfile, Segment, XY}
import org.scalatest.FunSpec
import org.geobricks.lazycyclist.core.parsers.EEPParser._

class TestEEPParser extends FunSpec {
  describe(".toJSON") {
    val points  = List(XY(0, 0), XY(56, 74), XY(120, 25))
    val climbs  = List(Segment(XY(56, 74), XY(120, 25), true, 64.0, 12.34))
    val eep     = EnhancedElevationProfile(points, climbs, 120, 12.34)
    val json = """{"points":[{"x":0.0,"y":0.0},{"x":56.0,"y":74.0},{"x":120.0,"y":25.0}],"climbs":[{"start":{"x":56.0,"y":74.0},"end":{"x":120.0,"y":25.0},"slope":12.34}],"totalDistance":120.0,"averageSlope":12.34}"""

    it("converts an Enhanced Elevation Profile into a JSON string") { assert(toJSON(eep) == json) }
  }

  describe("List toJSON") {
    val points  = List(XY(0, 0), XY(56, 74), XY(120, 25))
    val climbs  = List(Segment(XY(56, 74), XY(120, 25), true, 64.0, 12.34))
    val eeps    = List(EnhancedElevationProfile(points, climbs, 120, 12.34))
    val json = """[{"points":[{"x":0.0,"y":0.0},{"x":56.0,"y":74.0},{"x":120.0,"y":25.0}],"climbs":[{"start":{"x":56.0,"y":74.0},"end":{"x":120.0,"y":25.0},"slope":12.34}],"totalDistance":120.0,"averageSlope":12.34}]"""

    it("converts an Enhanced Elevation Profile into a JSON string") { assert(toJSON(eeps) == json) }
  }
}
package org.geobricks.lazycyclist.core.models

import org.scalatest.FunSpec
import org.geobricks.lazycyclist.core.models.Models._

class TestModels extends FunSpec {
  val xy_1 = XY(0, 100)
  val xy_2 = XY(56, 200)
  val xy_3 = XY(120, 175)

  describe("ElevationProfile") {
    val ep = new ElevationProfile(List(xy_1, xy_2, xy_3))

    it("stores the points") {
      assert(ep.points.length == 3)
    }

    it("organizes the coordinates into segments") {
      assert(ep.segments.length == 2)
    }

    it("computes the number of climbs") {
      assert(ep.climbs.length == 1)
    }

    it("computes the total distance") {
      assert(ep.distance == 120)
    }
  }
}
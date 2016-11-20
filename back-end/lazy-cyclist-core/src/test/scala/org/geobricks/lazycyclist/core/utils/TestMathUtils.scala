package org.geobricks.lazycyclist.core.utils

import org.geobricks.lazycyclist.core.models.Models.LatLon
import org.scalatest.FunSpec
import MathUtils._

class TestMathUtils extends FunSpec {
  describe(".distance") {
    val from  = LatLon(-37.8168987, 144.9955671)
    val to    = LatLon(-37.816969 , 144.9961949)

    it("calculates th distance between two points") {
      assert(distance(from, to) == 56)
    }
  }
}
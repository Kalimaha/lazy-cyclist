package org.geobricks.lazycyclist.core

import org.geobricks.lazycyclist.core.Core._
import org.scalatest.FunSpec

class TestCore extends FunSpec {
  describe(".validate") {
    describe("when inputs are valid") {
      it("returns true") {
        assert(validate("Home", "Work").contains(true))
      }
    }
  }
}

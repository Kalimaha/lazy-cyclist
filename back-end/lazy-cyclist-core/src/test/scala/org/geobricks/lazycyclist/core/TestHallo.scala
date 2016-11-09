package org.geobricks.lazycyclist.core

import org.scalatest.FunSpec
import org.geobricks.lazycyclist.core.Hallo._

class TestHallo extends FunSpec {
  describe(".greet") {
    it("says hallo") {
      assert(greet("Guido") == "Hallo, Guido!")
    }
  }
}

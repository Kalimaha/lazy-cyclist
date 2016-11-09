package org.geobricks.lazycyclist.core

import org.geobricks.lazycyclist.core.Core._
import org.scalatest.FunSpec

class TestCore extends FunSpec {
  describe(".encode") {
    it("encodes a string for the API request") {
      assert(encode("75 9th Ave, New York, NY") == "75+9th+Ave,+New+York,+NY")
    }
  }

  describe(".validate") {
    describe("when inputs are valid") {
      it("returns true") {
        assert(validate("Home", "Work") == Right(true))
      }
    }

    describe("when 'from' is empty") {
      it("returns an error") {
        assert(validate("", "Work") == Left("Parameter 'from' can't be empty."))
      }
    }

    describe("when 'from' is null") {
      it("returns an error") {
        assert(validate(null, "Work") == Left("Parameter 'from' can't be null."))
      }
    }

    describe("when 'to' is empty") {
      it("returns an error") {
        assert(validate("Home", "") == Left("Parameter 'to' can't be empty."))
      }
    }

    describe("when 'to' is null") {
      it("returns an error") {
        assert(validate("Home", null) == Left("Parameter 'to' can't be null."))
      }
    }
  }
}

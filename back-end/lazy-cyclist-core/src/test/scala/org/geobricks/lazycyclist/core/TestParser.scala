package org.geobricks.lazycyclist.core

import org.geobricks.lazycyclist.core.Parser._
import org.scalatest.FunSpec

class TestParser extends FunSpec {
  describe(".toSteps") {
    val json = """{"steps":[{"distance":{"text":"56 m","value":56},"duration":{"text":"1 min","value":7},"end_location":{"lat":-37.816969,"lng":144.9961949},"html_instructions":"Head \u003cb\u003eeast\u003c/b\u003e on \u003cb\u003eMcgoun St\u003c/b\u003e toward \u003cb\u003eThomas St\u003c/b\u003e","polyline":{"points":"rbyeFim~sZL{B"},"start_location":{"lat":-37.8168987,"lng":144.9955671},"travel_mode":"BICYCLING"},{"distance":{"text":"64 m","value":64},"duration":{"text":"1 min","value":8},"end_location":{"lat":-37.8164005,"lng":144.9962741},"html_instructions":"Turn \u003cb\u003eleft\u003c/b\u003e onto \u003cb\u003eThomas St\u003c/b\u003e","maneuver":"turn-left","polyline":{"points":"`cyeFeq~sZqBO"},"start_location":{"lat":-37.816969,"lng":144.9961949},"travel_mode":"BICYCLING"}]}"""

    val start_1 = LatLon(-37.8168987, 144.9955671)
    val start_2 = LatLon(-37.816969, 144.9961949)
    val end_1   = LatLon(-37.816969, 144.9961949)
    val end_2   = LatLon(-37.8164005, 144.9962741)
    val step_1  = Step(56, start_1, end_1)
    val step_2  = Step(64, start_2, end_2)

    it("returns a list of Steps") {
      assert(toSteps(json) == Right(List(step_1, step_2)))
    }

    describe("something wrong?") {
      val json = """{"steps":[{"end_location":{"lat":-37.816969,"lng":144.9961949},"html_instructions":"Head \u003cb\u003eeast\u003c/b\u003e on \u003cb\u003eMcgoun St\u003c/b\u003e toward \u003cb\u003eThomas St\u003c/b\u003e","polyline":{"points":"rbyeFim~sZL{B"},"start_location":{"lat":-37.8168987,"lng":144.9955671},"travel_mode":"BICYCLING"},{"distance":{"text":"64 m","value":64},"duration":{"text":"1 min","value":8},"end_location":{"lat":-37.8164005,"lng":144.9962741},"html_instructions":"Turn \u003cb\u003eleft\u003c/b\u003e onto \u003cb\u003eThomas St\u003c/b\u003e","maneuver":"turn-left","polyline":{"points":"`cyeFeq~sZqBO"},"start_location":{"lat":-37.816969,"lng":144.9961949},"travel_mode":"BICYCLING"}]}"""

      it("returns a list of Steps") {
        assert(toSteps(json) == Right(List(null, step_2)))
      }
    }
  }
  describe(".toStep") {
    val start = LatLon(-37.8168987, 144.9955671)
    val end   = LatLon(-37.816969, 144.9961949)
    val step  = Step(56, start, end)

    describe("when the JSON is well formatted") {
      val json  = """{"distance":{"text":"56 m","value":56},"duration":{"text":"1 min","value":7},"end_location":{"lat":-37.816969,"lng":144.9961949},"html_instructions":"Head \u003cb\u003eeast\u003c/b\u003e on \u003cb\u003eMcgoun St\u003c/b\u003e toward \u003cb\u003eThomas St\u003c/b\u003e","polyline":{"points":"rbyeFim~sZL{B"},"start_location":{"lat":-37.8168987,"lng":144.9955671},"travel_mode":"BICYCLING"}"""

      it("converts JSON into a Step object") {
        assert(toStep(json) == Right(step))
      }
    }

    describe("when the JSON is NOT well formatted") {
      describe("when the distance is missing") {
        val json  = """{"duration":{"text":"1 min","value":7},"end_location":{"lat":-37.816969,"lng":144.9961949},"html_instructions":"Head \u003cb\u003eeast\u003c/b\u003e on \u003cb\u003eMcgoun St\u003c/b\u003e toward \u003cb\u003eThomas St\u003c/b\u003e","polyline":{"points":"rbyeFim~sZL{B"},"start_location":{"lat":-37.8168987,"lng":144.9955671},"travel_mode":"BICYCLING"}"""

        it("returns an error") {
          assert(toStep(json) == Left("Distance is not available for this step."))
        }
      }

      describe("when the start latitude is missing") {
        val json  = """{"distance":{"text":"56 m","value":56},"duration":{"text":"1 min","value":7},"end_location":{"lat":-37.816969,"lng":144.9961949},"html_instructions":"Head \u003cb\u003eeast\u003c/b\u003e on \u003cb\u003eMcgoun St\u003c/b\u003e toward \u003cb\u003eThomas St\u003c/b\u003e","polyline":{"points":"rbyeFim~sZL{B"},"start_location":{"lng":144.9955671},"travel_mode":"BICYCLING"}"""

        it("returns an error") {
          assert(toStep(json) == Left("Start location is not available for this step."))
        }
      }

      describe("when the start longitude is missing") {
        val json  = """{"distance":{"text":"56 m","value":56},"duration":{"text":"1 min","value":7},"end_location":{"lat":-37.816969,"lng":144.9961949},"html_instructions":"Head \u003cb\u003eeast\u003c/b\u003e on \u003cb\u003eMcgoun St\u003c/b\u003e toward \u003cb\u003eThomas St\u003c/b\u003e","polyline":{"points":"rbyeFim~sZL{B"},"start_location":{"lat":-37.8168987},"travel_mode":"BICYCLING"}"""

        it("returns an error") {
          assert(toStep(json) == Left("Start location is not available for this step."))
        }
      }

      describe("when the end latitude is missing") {
        val json  = """{"distance":{"text":"56 m","value":56},"duration":{"text":"1 min","value":7},"end_location":{"lng":144.9961949},"html_instructions":"Head \u003cb\u003eeast\u003c/b\u003e on \u003cb\u003eMcgoun St\u003c/b\u003e toward \u003cb\u003eThomas St\u003c/b\u003e","polyline":{"points":"rbyeFim~sZL{B"},"start_location":{"lat":-37.8168987,"lng":144.9955671},"travel_mode":"BICYCLING"}"""

        it("returns an error") {
          assert(toStep(json) == Left("End location is not available for this step."))
        }
      }

      describe("when the end longitude is missing") {
        val json  = """{"distance":{"text":"56 m","value":56},"duration":{"text":"1 min","value":7},"end_location":{"lat":-37.816969},"html_instructions":"Head \u003cb\u003eeast\u003c/b\u003e on \u003cb\u003eMcgoun St\u003c/b\u003e toward \u003cb\u003eThomas St\u003c/b\u003e","polyline":{"points":"rbyeFim~sZL{B"},"start_location":{"lat":-37.8168987,"lng":144.9955671},"travel_mode":"BICYCLING"}"""

        it("returns an error") {
          assert(toStep(json) == Left("End location is not available for this step."))
        }
      }

      describe("when distance, start and end are missing") {
        val json  = """{"duration":{"text":"1 min","value":7},"html_instructions":"Head \u003cb\u003eeast\u003c/b\u003e on \u003cb\u003eMcgoun St\u003c/b\u003e toward \u003cb\u003eThomas St\u003c/b\u003e","polyline":{"points":"rbyeFim~sZL{B"},"travel_mode":"BICYCLING"}"""

        it("returns an error") {
          assert(toStep(json) == Left("Distance, start and end locations missing."))
        }
      }

      describe("when distance and start are missing") {
        val json  = """{"duration":{"text":"1 min","value":7},"end_location":{"lat":-37.816969,"lng":144.9961949},"html_instructions":"Head \u003cb\u003eeast\u003c/b\u003e on \u003cb\u003eMcgoun St\u003c/b\u003e toward \u003cb\u003eThomas St\u003c/b\u003e","polyline":{"points":"rbyeFim~sZL{B"},"travel_mode":"BICYCLING"}"""

        it("returns an error") {
          assert(toStep(json) == Left("Distance and start location missing."))
        }
      }

      describe("when distance and end are missing") {
        val json  = """{"duration":{"text":"1 min","value":7},"html_instructions":"Head \u003cb\u003eeast\u003c/b\u003e on \u003cb\u003eMcgoun St\u003c/b\u003e toward \u003cb\u003eThomas St\u003c/b\u003e","polyline":{"points":"rbyeFim~sZL{B"},"start_location":{"lat":-37.8168987,"lng":144.9955671},"travel_mode":"BICYCLING"}"""

        it("returns an error") {
          assert(toStep(json) == Left("Distance and end location missing."))
        }
      }

      describe("when start and end are missing") {
        val json  = """{"distance":{"text":"56 m","value":56},"duration":{"text":"1 min","value":7},"html_instructions":"Head \u003cb\u003eeast\u003c/b\u003e on \u003cb\u003eMcgoun St\u003c/b\u003e toward \u003cb\u003eThomas St\u003c/b\u003e","polyline":{"points":"rbyeFim~sZL{B"},"travel_mode":"BICYCLING"}"""

        it("returns an error") {
          assert(toStep(json) == Left("Start and end locations missing."))
        }
      }
    }
  }
}
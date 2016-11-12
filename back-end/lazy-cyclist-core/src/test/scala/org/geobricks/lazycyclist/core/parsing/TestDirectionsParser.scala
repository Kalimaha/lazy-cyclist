package org.geobricks.lazycyclist.core.parsing

import org.geobricks.lazycyclist.core.parsing.DirectionsParser._
import org.json4s.{JValue, _}
import org.json4s.native.JsonMethods._
import org.scalatest.FunSpec

class TestDirectionsParser extends FunSpec {
  def toJValue(rawJSON: String): JValue = parse(rawJSON)

  describe(".toRoutes") {
    describe("when the JSON is valid") {
      val json = """{"routes":[{"bounds":{"northeast":{"lat":-37.8164005,"lng":144.9994843},"southwest":{"lat":-37.8287644,"lng":144.9955671}},"copyrights":"Map data ©2016 Google","legs":[{"distance":{"text":"1.8 km","value":1797},"duration":{"text":"7 mins","value":442},"end_address":"511 Church St, Cremorne VIC 3121, Australia","end_location":{"lat":-37.8287644,"lng":144.9980297},"start_address":"2 Mcgoun St, Richmond VIC 3121, Australia","start_location":{"lat":-37.8168987,"lng":144.9955671},"steps":[{"distance":{"text":"56 m","value":56},"duration":{"text":"1 min","value":7},"end_location":{"lat":-37.816969,"lng":144.9961949},"html_instructions":"Head \u003cb\u003eeast\u003c/b\u003e on \u003cb\u003eMcgoun St\u003c/b\u003e toward \u003cb\u003eThomas St\u003c/b\u003e","polyline":{"points":"rbyeFim~sZL{B"},"start_location":{"lat":-37.8168987,"lng":144.9955671},"travel_mode":"BICYCLING"}],"traffic_speed_entry":[],"via_waypoint":[]}],"overview_polyline":{"points":"rbyeFim~sZL{BqBORyDX{DHaAXiEbHr@tLhAjP~ApWjCxFl@t@HHsABk@"},"summary":"Church St","warnings":["Bicycling directions are in beta. Use caution – This route may contain streets that aren't suited for bicycling."],"waypoint_order":[]}]}"""

      val start = LatLon(-37.8168987, 144.9955671)
      val end   = LatLon(-37.816969, 144.9961949)
      val step  = Step(56, start, end)
      val route = Route(List(step))

      it("converts a JSON into a Route") { assert(toRoutes(json) == List(route)) }
    }

    describe("when the JSON is NOT valid") {
      val json = """{"routes":[{"bounds":{"northeast":{"lat":-37.8164005,"lng":144.9994843},"southwest":{"lat":-37.8287644,"lng":144.9955671}},"copyrights":"Map data ©2016 Google","legs":[{"distance":{"text":"1.8 km","value":1797},"duration":{"text":"7 mins","value":442},"end_address":"511 Church St, Cremorne VIC 3121, Australia","end_location":{"lat":-37.8287644,"lng":144.9980297},"start_address":"2 Mcgoun St, Richmond VIC 3121, Australia","start_location":{"lat":-37.8168987,"lng":144.9955671},"steps":[{"duration":{"text":"1 min","value":7},"end_location":{"lat":-37.816969,"lng":144.9961949},"html_instructions":"Head \u003cb\u003eeast\u003c/b\u003e on \u003cb\u003eMcgoun St\u003c/b\u003e toward \u003cb\u003eThomas St\u003c/b\u003e","polyline":{"points":"rbyeFim~sZL{B"},"start_location":{"lat":-37.8168987,"lng":144.9955671},"travel_mode":"BICYCLING"}],"traffic_speed_entry":[],"via_waypoint":[]}],"overview_polyline":{"points":"rbyeFim~sZL{BqBORyDX{DHaAXiEbHr@tLhAjP~ApWjCxFl@t@HHsABk@"},"summary":"Church St","warnings":["Bicycling directions are in beta. Use caution – This route may contain streets that aren't suited for bicycling."],"waypoint_order":[]}]}"""

      it("returns an empty list") { assert(toRoutes(json) == List()) }
    }
  }

  describe(".toRoute") {
    describe("when the JSON is valid") {
      val rawJSON = """{"bounds":{"northeast":{"lat":-37.8164005,"lng":144.9994843},"southwest":{"lat":-37.8287644,"lng":144.9955671}},"copyrights":"Map data ©2016 Google","legs":[{"distance":{"text":"1.8 km","value":1797},"duration":{"text":"7 mins","value":442},"end_address":"511 Church St, Cremorne VIC 3121, Australia","end_location":{"lat":-37.8287644,"lng":144.9980297},"start_address":"2 Mcgoun St, Richmond VIC 3121, Australia","start_location":{"lat":-37.8168987,"lng":144.9955671},"steps":[{"distance":{"text":"56 m","value":56},"duration":{"text":"1 min","value":7},"end_location":{"lat":-37.816969,"lng":144.9961949},"html_instructions":"Head \u003cb\u003eeast\u003c/b\u003e on \u003cb\u003eMcgoun St\u003c/b\u003e toward \u003cb\u003eThomas St\u003c/b\u003e","polyline":{"points":"rbyeFim~sZL{B"},"start_location":{"lat":-37.8168987,"lng":144.9955671},"travel_mode":"BICYCLING"}],"traffic_speed_entry":[],"via_waypoint":[]}],"overview_polyline":{"points":"rbyeFim~sZL{BqBORyDX{DHaAXiEbHr@tLhAjP~ApWjCxFl@t@HHsABk@"},"summary":"Church St","warnings":["Bicycling directions are in beta. Use caution – This route may contain streets that aren't suited for bicycling."],"waypoint_order":[]}"""
      val json    = toJValue(rawJSON)

      val start   = LatLon(-37.8168987, 144.9955671)
      val end     = LatLon(-37.816969, 144.9961949)
      val step    = Step(56, start, end)
      val route   = Route(List(step))

      it("converts a JSON into a Route") { assert(toRoute(json).contains(route)) }
    }
  }

  describe(".toSteps") {
    val start_1 = LatLon(-37.8168987, 144.9955671)
    val start_2 = LatLon(-37.816969, 144.9961949)
    val end_1   = LatLon(-37.816969, 144.9961949)
    val end_2   = LatLon(-37.8164005, 144.9962741)
    val step_1  = Step(56, start_1, end_1)
    val step_2  = Step(64, start_2, end_2)

    describe("when the JSON is valid") {
      val rawJSON = """{"steps":[{"distance":{"text":"56 m","value":56},"duration":{"text":"1 min","value":7},"end_location":{"lat":-37.816969,"lng":144.9961949},"html_instructions":"Head \u003cb\u003eeast\u003c/b\u003e on \u003cb\u003eMcgoun St\u003c/b\u003e toward \u003cb\u003eThomas St\u003c/b\u003e","polyline":{"points":"rbyeFim~sZL{B"},"start_location":{"lat":-37.8168987,"lng":144.9955671},"travel_mode":"BICYCLING"},{"distance":{"text":"64 m","value":64},"duration":{"text":"1 min","value":8},"end_location":{"lat":-37.8164005,"lng":144.9962741},"html_instructions":"Turn \u003cb\u003eleft\u003c/b\u003e onto \u003cb\u003eThomas St\u003c/b\u003e","maneuver":"turn-left","polyline":{"points":"`cyeFeq~sZqBO"},"start_location":{"lat":-37.816969,"lng":144.9961949},"travel_mode":"BICYCLING"}]}"""
      val json    = toJValue(rawJSON)

      it("returns a list of Steps") { assert(toSteps(json) == List(step_1, step_2)) }
    }

    describe("when the JSON file is not valid") {
      val rawJSON = """{"steps":[{"end_location":{"lat":-37.816969,"lng":144.9961949},"html_instructions":"Head \u003cb\u003eeast\u003c/b\u003e on \u003cb\u003eMcgoun St\u003c/b\u003e toward \u003cb\u003eThomas St\u003c/b\u003e","polyline":{"points":"rbyeFim~sZL{B"},"start_location":{"lat":-37.8168987,"lng":144.9955671},"travel_mode":"BICYCLING"},{"distance":{"text":"64 m","value":64},"duration":{"text":"1 min","value":8},"end_location":{"lat":-37.8164005,"lng":144.9962741},"html_instructions":"Turn \u003cb\u003eleft\u003c/b\u003e onto \u003cb\u003eThomas St\u003c/b\u003e","maneuver":"turn-left","polyline":{"points":"`cyeFeq~sZqBO"},"start_location":{"lat":-37.816969,"lng":144.9961949},"travel_mode":"BICYCLING"}]}"""
      val json    = toJValue(rawJSON)

      it("returns a list of Steps") { assert(toSteps(json) == List(step_2)) }
    }
  }

  describe(".toStep") {
    val start = LatLon(-37.8168987, 144.9955671)
    val end   = LatLon(-37.816969, 144.9961949)
    val step  = Step(56, start, end)

    describe("when the JSON is well formatted") {
      val rawJSON = """{"distance":{"text":"56 m","value":56},"duration":{"text":"1 min","value":7},"end_location":{"lat":-37.816969,"lng":144.9961949},"html_instructions":"Head \u003cb\u003eeast\u003c/b\u003e on \u003cb\u003eMcgoun St\u003c/b\u003e toward \u003cb\u003eThomas St\u003c/b\u003e","polyline":{"points":"rbyeFim~sZL{B"},"start_location":{"lat":-37.8168987,"lng":144.9955671},"travel_mode":"BICYCLING"}"""
      val json    = toJValue(rawJSON)

      it("converts JSON into a Step object") { assert(toStep(json).contains(step)) }
    }

    describe("when the JSON is NOT well formatted") {
      describe("when the distance is missing") {
        val rawJSON = """{"duration":{"text":"1 min","value":7},"end_location":{"lat":-37.816969,"lng":144.9961949},"html_instructions":"Head \u003cb\u003eeast\u003c/b\u003e on \u003cb\u003eMcgoun St\u003c/b\u003e toward \u003cb\u003eThomas St\u003c/b\u003e","polyline":{"points":"rbyeFim~sZL{B"},"start_location":{"lat":-37.8168987,"lng":144.9955671},"travel_mode":"BICYCLING"}"""
        val json    = toJValue(rawJSON)

        it("returns None") { assert(toStep(json).isEmpty) }
      }

      describe("when the start latitude is missing") {
        val rawJSON = """{"distance":{"text":"56 m","value":56},"duration":{"text":"1 min","value":7},"end_location":{"lat":-37.816969,"lng":144.9961949},"html_instructions":"Head \u003cb\u003eeast\u003c/b\u003e on \u003cb\u003eMcgoun St\u003c/b\u003e toward \u003cb\u003eThomas St\u003c/b\u003e","polyline":{"points":"rbyeFim~sZL{B"},"start_location":{"lng":144.9955671},"travel_mode":"BICYCLING"}"""
        val json    = toJValue(rawJSON)

        it("returns None") {
          assert(toStep(json).isEmpty)
        }
      }

      describe("when the start longitude is missing") {
        val rawJSON = """{"distance":{"text":"56 m","value":56},"duration":{"text":"1 min","value":7},"end_location":{"lat":-37.816969,"lng":144.9961949},"html_instructions":"Head \u003cb\u003eeast\u003c/b\u003e on \u003cb\u003eMcgoun St\u003c/b\u003e toward \u003cb\u003eThomas St\u003c/b\u003e","polyline":{"points":"rbyeFim~sZL{B"},"start_location":{"lat":-37.8168987},"travel_mode":"BICYCLING"}"""
        val json    = toJValue(rawJSON)

        it("returns None") { assert(toStep(json).isEmpty) }
      }

      describe("when the end latitude is missing") {
        val rawJSON = """{"distance":{"text":"56 m","value":56},"duration":{"text":"1 min","value":7},"end_location":{"lng":144.9961949},"html_instructions":"Head \u003cb\u003eeast\u003c/b\u003e on \u003cb\u003eMcgoun St\u003c/b\u003e toward \u003cb\u003eThomas St\u003c/b\u003e","polyline":{"points":"rbyeFim~sZL{B"},"start_location":{"lat":-37.8168987,"lng":144.9955671},"travel_mode":"BICYCLING"}"""
        val json    = toJValue(rawJSON)

        it("returns None") { assert(toStep(json).isEmpty) }
      }

      describe("when the end longitude is missing") {
        val rawJSON = """{"distance":{"text":"56 m","value":56},"duration":{"text":"1 min","value":7},"end_location":{"lat":-37.816969},"html_instructions":"Head \u003cb\u003eeast\u003c/b\u003e on \u003cb\u003eMcgoun St\u003c/b\u003e toward \u003cb\u003eThomas St\u003c/b\u003e","polyline":{"points":"rbyeFim~sZL{B"},"start_location":{"lat":-37.8168987,"lng":144.9955671},"travel_mode":"BICYCLING"}"""
        val json    = toJValue(rawJSON)

        it("returns None") { assert(toStep(json).isEmpty) }
      }

      describe("when distance, start and end are missing") {
        val rawJSON = """{"duration":{"text":"1 min","value":7},"html_instructions":"Head \u003cb\u003eeast\u003c/b\u003e on \u003cb\u003eMcgoun St\u003c/b\u003e toward \u003cb\u003eThomas St\u003c/b\u003e","polyline":{"points":"rbyeFim~sZL{B"},"travel_mode":"BICYCLING"}"""
        val json    = toJValue(rawJSON)

        it("returns None") { assert(toStep(json).isEmpty) }
      }

      describe("when distance and start are missing") {
        val rawJSON = """{"duration":{"text":"1 min","value":7},"end_location":{"lat":-37.816969,"lng":144.9961949},"html_instructions":"Head \u003cb\u003eeast\u003c/b\u003e on \u003cb\u003eMcgoun St\u003c/b\u003e toward \u003cb\u003eThomas St\u003c/b\u003e","polyline":{"points":"rbyeFim~sZL{B"},"travel_mode":"BICYCLING"}"""
        val json    = toJValue(rawJSON)

        it("returns None") { assert(toStep(json).isEmpty) }
      }

      describe("when distance and end are missing") {
        val rawJSON = """{"duration":{"text":"1 min","value":7},"html_instructions":"Head \u003cb\u003eeast\u003c/b\u003e on \u003cb\u003eMcgoun St\u003c/b\u003e toward \u003cb\u003eThomas St\u003c/b\u003e","polyline":{"points":"rbyeFim~sZL{B"},"start_location":{"lat":-37.8168987,"lng":144.9955671},"travel_mode":"BICYCLING"}"""
        val json    = toJValue(rawJSON)

        it("returns None") { assert(toStep(json).isEmpty) }
      }

      describe("when start and end are missing") {
        val rawJSON = """{"distance":{"text":"56 m","value":56},"duration":{"text":"1 min","value":7},"html_instructions":"Head \u003cb\u003eeast\u003c/b\u003e on \u003cb\u003eMcgoun St\u003c/b\u003e toward \u003cb\u003eThomas St\u003c/b\u003e","polyline":{"points":"rbyeFim~sZL{B"},"travel_mode":"BICYCLING"}"""
        val json    = toJValue(rawJSON)

        it("returns None") { assert(toStep(json).isEmpty) }
      }
    }
  }
}
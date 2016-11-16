package org.geobricks.lazycyclist.core.parsers

import org.geobricks.lazycyclist.core.models.Models.LatLon
import org.json4s._
import org.json4s.native.JsonMethods._
import org.scalatest.FunSpec
import org.geobricks.lazycyclist.core.parsers.ElevationParser._

class TestElevationParser extends FunSpec {
  def toJValue(rawJSON: String): JValue = parse(rawJSON)

  describe(".latLonElevMap") {
    val json  = """{"results":[{"elevation":8.757536888122559,"location":{"lat":40.714728,"lng":-73.998672},"resolution":76.35161590576172}],"status":"OK"}"""
    val map   = Map(LatLon(40.714728, -73.998672) -> 8.757536888122559)

    it("maps the LatLon to their elevations") { assert(latLonElevMap(json) == Right(map)) }
  }

  describe(".latLon2elev") {
    val json  = """{"elevation":8.757536888122559,"location":{"lat":40.714728,"lng":-73.998672},"resolution":76.35161590576172}"""
    val map   = (LatLon(40.714728, -73.998672), 8.757536888122559)

    it("maps a single LatLon to its elevation") { assert(latLon2elev(toJValue(json)).contains(map)) }
  }
}
package org.geobricks.lazycyclist.core.parsers

import org.geobricks.lazycyclist.core.models.Models._
import org.json4s._
import org.json4s.native.JsonMethods._

object ElevationParser {
  def latLonElevMap(rawJSON: String): Either[String, collection.mutable.Map[LatLon, Double]] = {
    try {
      val json = parse(rawJSON)
      val results = (json \ Field.RESULTS).asInstanceOf[JArray].arr
      val map = collection.mutable.Map[LatLon, Double]()

      results.flatMap((result: JValue) => latLon2elev(result)).map(m => map += m)

      Right(map)
    } catch {
      case e: Exception => Left(e.getMessage)
    }
  }

  def latLon2elev(json: JValue): Option[(LatLon, Double)] = {
    try {
      val elev  = (json \ Field.ELEVATION).values.asInstanceOf[Double]
      val lat   = (json \ Field.LOCATION \ Field.LAT).values.asInstanceOf[Double]
      val lon   = (json \ Field.LOCATION \ Field.LON).values.asInstanceOf[Double]

      Some(LatLon(lat, lon), elev)
    } catch {
      case e: Exception => None
    }
  }
}
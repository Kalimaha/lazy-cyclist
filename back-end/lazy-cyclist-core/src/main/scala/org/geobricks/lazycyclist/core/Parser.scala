package org.geobricks.lazycyclist.core

import org.json4s._
import org.json4s.native.JsonMethods._

case class LatLon(lat: Double, lon: Double)
case class Step(distance: BigInt, start: LatLon, end: LatLon)

object Parser {
  def toStep(rawJSON: String): Either[String, Step] = {
    val json              = parse(rawJSON)

    val distance          = (json \ "distance" \ "value").values.asInstanceOf[BigInt]

    val start_lat         = (json \ "start_location" \ "lat").values.asInstanceOf[Double]
    val start_lon         = (json \ "start_location" \ "lng").values.asInstanceOf[Double]
    val start             = LatLon(start_lat, start_lon)

    val end_lat           = (json \ "end_location" \ "lat").values.asInstanceOf[Double]
    val end_lon           = (json \ "end_location" \ "lng").values.asInstanceOf[Double]
    val end               = LatLon(end_lat, end_lon)

    Right(Step(distance, start, end))
  }
}
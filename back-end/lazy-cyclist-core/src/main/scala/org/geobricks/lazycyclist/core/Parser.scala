package org.geobricks.lazycyclist.core

import org.json4s._
import org.json4s.native.JsonMethods._


object Parser {

  case class Route(steps: List[Step])
  case class LatLon(lat: Double, lon: Double)
  case class Step(distance: BigInt, start: LatLon, end: LatLon)

  def toSteps(rawJSON: String): List[Step] = {
    val json  = parse(rawJSON)
    val steps = (json \ "steps").asInstanceOf[JArray].arr

    steps.flatMap((step: JValue) => toStep(toJSON(step)))
  }

  def toJSON(value: JValue): String = compact(render(value))

  def toStep(rawJSON: String): Option[Step] = {
    val json  = parse(rawJSON)
    val dist  = distance(json)
    val end   = latLon("end_location", json)
    val start = latLon("start_location", json)

    (dist, start, end) match {
      case (None    , None    , None)     => None
      case (Some(_) , None    , None)     => None
      case (Some(_) , Some(_) , None)     => None
      case (None    , Some(_) , None)     => None
      case (None    , None    , Some(_))  => None
      case (None    , Some(_) , Some(_))  => None
      case (Some(_) , None    , Some(_))  => None

      case (Some(_) , Some(_) , Some(_))  => Some(Step(dist.get, start.get, end.get))
    }
  }

  def distance(json: JValue): Option[BigInt] = {
    try   { Some((json \ "distance" \ "value").values.asInstanceOf[BigInt]) }
    catch { case e: Exception => None }
  }

  def latLon(jsonKey: String, json: JValue): Option[LatLon] = {
    var lat: Option[Double] = None
    var lon: Option[Double] = None

    try {
      lat = Some((json \ jsonKey \ "lat").values.asInstanceOf[Double])
      lon = Some((json \ jsonKey \ "lng").values.asInstanceOf[Double])

      Some(LatLon(lat.get, lon.get))
    } catch {
      case e: Exception => None
    }
  }
}
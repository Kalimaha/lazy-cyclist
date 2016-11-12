package org.geobricks.lazycyclist.core.parsing

import org.json4s._
import org.json4s.native.JsonMethods._


object DirectionsParser {

  case class Route(steps: List[Step])
  case class LatLon(lat: Double, lon: Double)
  case class Step(distance: BigInt, start: LatLon, end: LatLon)

  object Field {
    val LAT       = "lat"
    val LON       = "lng"
    val LEGS      = "legs"
    val STEPS     = "steps"
    val VALUE     = "value"
    val ROUTES    = "routes"
    val END       = "end_location"
    val DISTANCE  = "distance"
    val START     = "start_location"
  }

  def toRoutes(rawJSON: String): List[Route] = {
    val json  = parse(rawJSON)
    val routes = (json \ Field.ROUTES).asInstanceOf[JArray].arr

    routes.flatMap((step: JValue) => toRoute(step))
  }

  def toRoute(json: JValue): Option[Route] = {
    val stepsJSON = (json \ Field.LEGS).asInstanceOf[JArray].arr.head
    val steps     = toSteps(stepsJSON)

    if (steps.isEmpty)  { None }
    else                { Some(Route(steps)) }
  }

  def toSteps(json: JValue): List[Step] = {
    val steps = (json \ Field.STEPS).asInstanceOf[JArray].arr

    steps.flatMap((step: JValue) => toStep(step))
  }

  def toStep(json: JValue): Option[Step] = {
    val dist  = distance(json)
    val end   = latLon(Field.END, json)
    val start = latLon(Field.START, json)

    (dist, start, end) match {
      case (Some(_), Some(_), Some(_))  => Some(Step(dist.get, start.get, end.get))
      case default                      => None
    }
  }

  def distance(json: JValue): Option[BigInt] =
    try   { Some((json \ Field.DISTANCE \ Field.VALUE).values.asInstanceOf[BigInt]) }
    catch { case e: Exception => None }

  def latLon(jsonKey: String, json: JValue): Option[LatLon] = {
    val latValue = read(jsonKey, Field.LAT, json)
    val lonValue = read(jsonKey, Field.LON, json)

    (latValue, lonValue) match {
      case (Some(_), Some(_)) => Some(LatLon(latValue.get, lonValue.get))
      case default            => None
    }
  }

  def read(jsonKey: String, value: String, json: JValue): Option[Double] =
    try   { Some((json \ jsonKey \ value).values.asInstanceOf[Double])}
    catch { case e: Exception => None }
}
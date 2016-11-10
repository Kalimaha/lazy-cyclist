package org.geobricks.lazycyclist.core

import org.json4s._
import org.json4s.native.JsonMethods._

case class LatLon(lat: Double, lon: Double)
case class Step(distance: BigInt, start: LatLon, end: LatLon)

object Parser {
  def toSteps(rawJSON: String): Either[String, List[Step]] = {
    val json  = parse(rawJSON)
    val steps = (json \ "steps").asInstanceOf[JArray].arr

    Right(steps.map((step: JValue) => toStep(toJSON(step))).map(_.right.getOrElse(null)))
  }

  def toJSON(value: JValue): String = compact(render(value))

  def toStep(rawJSON: String): Either[String, Step] = {
    val json  = parse(rawJSON)
    val dist  = distance(json)
    val end   = latLon("end_location", json)
    val start = latLon("start_location", json)

    (dist, start, end) match {
      case (Right(_), Left(_), Left(_))   => Left("Start and end locations missing.")
      case (Left(_), Right(_), Left(_))   => Left("Distance and end location missing.")
      case (Left(_), Left(_), Right(_))   => Left("Distance and start location missing.")
      case (Left(_), Left(_), Left(_))    => Left("Distance, start and end locations missing.")
      case (Left(_), Right(_), Right(_))  => Left("Distance is not available for this step.")
      case (Right(_), Right(_), Left(_))  => Left("End location is not available for this step.")
      case (Right(_), Left(_), Right(_))  => Left("Start location is not available for this step.")

      case (Right(_), Right(_), Right(_)) => Right(Step(dist.right.get, start.right.get, end.right.get))
    }
  }

  def distance(json: JValue): Either[String, BigInt] = {
    try   { Right((json \ "distance" \ "value").values.asInstanceOf[BigInt]) }
    catch { case e: Exception => Left(e.getMessage) }
  }

  def latLon(jsonKey: String, json: JValue): Either[String, LatLon] = {
    var lat: Option[Double] = None
    var lon: Option[Double] = None

    try {
      lat = Some((json \ jsonKey \ "lat").values.asInstanceOf[Double])
      lon = Some((json \ jsonKey \ "lng").values.asInstanceOf[Double])
      Right(LatLon(lat.get, lon.get))
    } catch {
      case e: Exception => Left(e.getMessage)
    }
  }
}
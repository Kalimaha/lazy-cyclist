package org.geobricks.lazycyclist.core

import org.geobricks.lazycyclist.core.models.Models._
import org.geobricks.lazycyclist.core.clients.DirectionsClient._
import org.geobricks.lazycyclist.core.parsers.DirectionsParser._
import org.geobricks.lazycyclist.core.parsers.ElevationParser._
import org.geobricks.lazycyclist.core.clients.{DirectionsClient, ElevationClient}

import scala.annotation.tailrec

object Core {

  def elevationProfile(from: String, to: String, directions: DirectionsClient, elevation: ElevationClient): Unit = {
    val out = for {
      valid           <- validate(from, to).right
      directionsURL   <- directions.directionsURL(directions.encode(from), directions.encode(to)).right
      directionsJSON  <- directions.request(directionsURL).right
      routes          <- toRoutes(directionsJSON).right
      coordinates     <- routes2coordinates(routes).right
      elevationURL    <- elevation.elevationURL(coordinates).right
      elevationJSON   <- elevation.request(elevationURL).right
      latLonElevMap   <- latLonElevMap(elevationJSON).right
    } yield latLonElevMap

    println(out)

    out match {
      case Left(_)  => println(s"Error: ${out.left.get}")
      case Right(_) => println(s"Error: ${out.right.get}")
    }

//    val routes = DirectionsParser.toRoutes(rawJSON.right.get)

//    val coordinates = routes2coordinates(routes)
//    println(coordinates)
//    val elevationURL = elevation.elevationURL(coordinates)
//    println(elevationURL)
//    val elevationJSON = elevation.request(elevationURL.right.get)
//    println(elevationJSON)
  }

  def routes2coordinates(routes: List[Route]): Either[String, List[LatLon]] = {
    Right(routes.flatMap(_.steps.flatMap((s: Step) => List(s.start, s.end))))
  }

  def steps2segments(steps: List[Step]): List[Segment] = {
    @tailrec
    def loop(steps: List[Step], offset: BigInt, acc: List[Segment]): List[Segment] = steps match {
      case Nil  => acc
      case h::t =>
        val segment = step2segment(h, offset)
        loop(t, segment.end.x.get, segment :: acc)
    }

    loop(steps, 0, List()).reverse
  }

  def step2segment(step: Step, offset: BigInt): Segment = {
    val start = SegmentEnd(Some(offset), None, Some(step.start.lat), Some(step.start.lon))
    val end   = SegmentEnd(Some(offset + step.distance), None, Some(step.end.lat), Some(step.end.lon))

    Segment(start, end)
  }
}
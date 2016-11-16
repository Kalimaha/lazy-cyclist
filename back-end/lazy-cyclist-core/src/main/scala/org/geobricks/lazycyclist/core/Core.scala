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
  }

  def route2profile(route: Route, lleMap: Map[LatLon, Double]): Either[String, ElevationProfile] = ???

  def distance(from: LatLon, to: LatLon): Double = {
    val dlon = deg2rad(to.lon - from.lon)
    val dlat = deg2rad(to.lat - from.lat)
    val a = Math.pow(Math.sin(dlat / 2), 2) + Math.cos(deg2rad(from.lat)) * Math.cos(deg2rad(to.lat)) * Math.pow(Math.sin(dlon / 2), 2)
    val c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))

    Math.round(6373000 * c)
  }

  def deg2rad(deg: Double): Double = deg * Math.PI / 180

  def routes2coordinates(routes: List[Route]): Either[String, List[LatLon]] = {
    Right(routes.flatMap(_.steps.flatMap((s: Step) => List(s.start, s.end))))
  }
}
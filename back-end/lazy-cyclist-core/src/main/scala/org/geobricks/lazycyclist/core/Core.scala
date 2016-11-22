package org.geobricks.lazycyclist.core

import org.geobricks.lazycyclist.core.models.Models._
import org.geobricks.lazycyclist.core.clients.DirectionsClient._
import org.geobricks.lazycyclist.core.parsers.DirectionsParser._
import org.geobricks.lazycyclist.core.parsers.ElevationParser._
import org.geobricks.lazycyclist.core.clients.{DirectionsClient, ElevationClient}
import org.geobricks.lazycyclist.core.utils.MathUtils._

object Core {

  def elevationProfile(from: String, to: String, dc: DirectionsClient, ec: ElevationClient): Either[String, List[EnhancedElevationProfile]] = {
    for {
      valid             <- validate(from, to).right
      directionsURL     <- dc.directionsURL(dc.encode(from), dc.encode(to)).right
      directionsJSON    <- dc.request(directionsURL).right
      routes            <- toRoutes(directionsJSON).right
      coordinates       <- routes2coordinates(routes).right
      elevationURL      <- ec.elevationURL(coordinates).right
      elevationJSON     <- ec.request(elevationURL).right
      latLonElevMap     <- latLonElevMap(elevationJSON).right
      elevationProfiles <- routes2XYs(routes, latLonElevMap).right
    } yield elevationProfiles.map(_.toEnhancedElevationProfile)
  }

  def routes2XYs(routes: List[Route], lleMap: Map[LatLon, Double]): Either[String, List[ElevationProfile]] = {
    Right(routes.map((r: Route) => route2XYs(r, lleMap)))
  }

  def route2XYs(route: Route, lleMap: Map[LatLon, Double]): ElevationProfile = {
    val latLons: List[LatLon]   = route.steps.flatMap((s: Step) => List(s.start, s.end)).distinct
    val distances: List[Double] = latLons.sliding(2).toList.map((l: List[LatLon]) => distance(l.head, l.last))
    val cumulates               = accumulate(0 :: distances)

    val xys = (latLons zip cumulates).map(p => XY(p._2.toString.toDouble, lleMap(p._1)))
    new ElevationProfile(xys)
  }
}
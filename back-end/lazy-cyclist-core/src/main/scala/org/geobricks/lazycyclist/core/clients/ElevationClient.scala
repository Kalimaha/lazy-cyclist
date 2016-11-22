package org.geobricks.lazycyclist.core.clients

import org.geobricks.lazycyclist.core.models.Models.LatLon

case class ElevationClient(apiKey: String) extends GoogleAPIClient {
  override val BASE_URL = "https://maps.googleapis.com/maps/api/elevation/json?"

  def elevationURL(coordinates: List[LatLon]): Either[String, String] =
    if (coordinates.isEmpty) {
      Left("The list of coordinates is empty.")
    } else {
      val locations = coordinates.map(ElevationClient.encode).mkString("|")

      Right(s"${BASE_URL}key=$apiKey&locations=$locations")
    }
}

object ElevationClient {
  def encode(ll: LatLon): String = s"${ll.lat},${ll.lon}"
}
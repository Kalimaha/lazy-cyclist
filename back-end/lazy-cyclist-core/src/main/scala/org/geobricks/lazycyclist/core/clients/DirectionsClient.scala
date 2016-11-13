package org.geobricks.lazycyclist.core.clients

import scalaj.http._

case class DirectionsClient(apiKey: String) extends GoogleAPIClient {
  override val BASE_URL = "https://maps.googleapis.com/maps/api/directions/json?"

  def request(url: String): Either[String, String] = {
    val response = Http(url).asString

    response.code match {
      case x if x > 299 => Left(response.body.trim)
      case _            => Right(response.body.trim)
    }
  }

  def directionsURL(from: String, to: String, directionsAPIKey: String): Either[String, String] = directionsAPIKey match {
    case null => Left("Parameter 'directionsAPIKey' can't be null.")
    case ""   => Left("Parameter 'directionsAPIKey' can't be empty.")
    case key  => Right(s"${BASE_URL}origin=$from&destination=$to&key=$key")
  }

  def validate(from: String, to: String): Either[String, Boolean] = (from, to) match {
    case ("", _)    => Left("Parameter 'from' can't be empty.")
    case (_, "")    => Left("Parameter 'to' can't be empty.")
    case (null, _)  => Left("Parameter 'from' can't be null.")
    case (_, null)  => Left("Parameter 'to' can't be null.")
    case (_, _)     => Right(true)
  }
}
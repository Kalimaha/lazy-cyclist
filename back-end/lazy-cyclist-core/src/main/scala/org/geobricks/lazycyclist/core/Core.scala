package org.geobricks.lazycyclist.core

object Core {
  val BASE_URL_DIRECTIONS = "https://maps.googleapis.com/maps/api/directions/json?"

  def directionsURL(from: String, to: String, directionsAPIKey: String): Either[String, String] = directionsAPIKey match {
    case null => Left("Parameter 'directionsAPIKey' can't be null.")
    case ""   => Left("Parameter 'directionsAPIKey' can't be empty.")
    case key  => Right(s"${BASE_URL_DIRECTIONS}origin=$from&destination=$to&key=$key")
  }

  def encode(address: String): String = address.replaceAll(" ", "+")

  def validate(from: String, to: String): Either[String, Boolean] = (from, to) match {
    case ("", _)    => Left("Parameter 'from' can't be empty.")
    case (_, "")    => Left("Parameter 'to' can't be empty.")
    case (null, _)  => Left("Parameter 'from' can't be null.")
    case (_, null)  => Left("Parameter 'to' can't be null.")
    case (_, _)     => Right(true)
  }
}
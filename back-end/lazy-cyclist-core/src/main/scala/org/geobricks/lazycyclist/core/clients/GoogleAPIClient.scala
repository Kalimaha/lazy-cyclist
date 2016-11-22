package org.geobricks.lazycyclist.core.clients

import scalaj.http._

trait GoogleAPIClient {
  val BASE_URL = ""

  def encode(address: String): String = address.replaceAll(" ", "+")

  def request(url: String): Either[String, String] = {
    val response = Http(url).asString

    println(response.body.trim)

    response.code match {
      case x if x > 299 => Left(response.body.trim)
      case _            => Right(response.body.trim)
    }
  }
}
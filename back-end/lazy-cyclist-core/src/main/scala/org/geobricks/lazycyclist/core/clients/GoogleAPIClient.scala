package org.geobricks.lazycyclist.core.clients

trait GoogleAPIClient {
  val BASE_URL = ""

  def encode(address: String): String = address.replaceAll(" ", "+")

  def request(url: String): Either[String, String]
}
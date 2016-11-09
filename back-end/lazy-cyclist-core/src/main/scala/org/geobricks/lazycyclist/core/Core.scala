package org.geobricks.lazycyclist.core

object Core {
  val BASE_URL_DIRECTIONS = ""
  val API_KEY_DIRECTIONS = ""

  def validate(from: String, to: String): Option[Boolean] = {
    if (from != null && from.length > 0 && to != null && to.length > 0) Some(true)
    else None
  }
}
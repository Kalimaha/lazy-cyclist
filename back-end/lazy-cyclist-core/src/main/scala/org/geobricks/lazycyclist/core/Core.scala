package org.geobricks.lazycyclist.core

object Core {
  def validate(from: String, to: String): Either[String, Boolean] = (from, to) match {
    case ("", _)    => Left("Parameter 'from' can't be empty.")
    case (_, "")    => Left("Parameter 'to' can't be empty.")
    case (null, _)  => Left("Parameter 'from' can't be null.")
    case (_, null)  => Left("Parameter 'to' can't be null.")
    case (_, _)     => Right(true)
  }
}
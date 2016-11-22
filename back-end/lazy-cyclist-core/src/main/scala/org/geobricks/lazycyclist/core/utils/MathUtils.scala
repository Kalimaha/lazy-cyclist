package org.geobricks.lazycyclist.core.utils

import org.geobricks.lazycyclist.core.models.Models.LatLon
import org.geobricks.lazycyclist.core.models.Models.XY

import scala.annotation.tailrec

object MathUtils {
  def distance(from: LatLon, to: LatLon): Double = {
    val dlon  = deg2rad(to.lon - from.lon)
    val dlat  = deg2rad(to.lat - from.lat)
    val a     = Math.pow(Math.sin(dlat / 2), 2) + Math.cos(deg2rad(from.lat)) * Math.cos(deg2rad(to.lat)) * Math.pow(Math.sin(dlon / 2), 2)
    val c     = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))

    Math.round(6373000 * c)
  }

  def distance(from: XY, to: XY): Double = Math.abs(to.x - from.x)

  def slope(from: XY, to: XY): Double = 100 * (to.y - from.y) / (to.x - from.x)

  def deg2rad(deg: Double): Double = deg * Math.PI / 180

  def accumulate(values: List[Double]): List[Double] = {
    @tailrec
    def loop(values: List[Double], offset: Double, acc: List[Double]): List[Double] = values match {
      case Nil => acc
      case h :: t => loop(t, h + offset, (h + offset) :: acc)
    }

    loop(values, 0, List()).reverse
  }
}
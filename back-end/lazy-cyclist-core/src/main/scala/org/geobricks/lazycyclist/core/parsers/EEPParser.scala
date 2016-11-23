package org.geobricks.lazycyclist.core.parsers

import org.geobricks.lazycyclist.core.models.Models.{EnhancedElevationProfile, Segment, XY}

object EEPParser {
  def toJSON(eeps: List[EnhancedElevationProfile]): String = {
    val sb = new StringBuilder().append("[")
    sb.append(eeps.map(toJSON(_)).mkString(","))
    sb.append("]")
    sb.toString()
  }

  def toJSON(eep: EnhancedElevationProfile): String = {
    val sb = new StringBuilder()
    sb.append("{")

    sb.append("\"points\":[")
    sb.append(eep.points.map(toJSON(_)).mkString(","))
    sb.append("],")

    sb.append("\"climbs\":[")
    sb.append(eep.climbs.map(toJSON(_)).mkString(","))
    sb.append("],")

    sb.append("\"totalDistance\":").append(eep.totalDistance).append(",")
    sb.append("\"averageSlope\":").append(eep.averageSlope)
    sb.append("}")

    sb.toString()
  }

  def toJSON(s: Segment): String = {
    val sb = new StringBuilder()
    sb.append("{")
    sb.append("\"start\":").append(toJSON(s.start)).append(",")
    sb.append("\"end\":").append(toJSON(s.end)).append(",")
    sb.append("\"slope\":").append(s.slope)
    sb.append("}")
    sb.toString()
  }

  def toJSON(xy: XY): String = {
    val sb = new StringBuilder()
    sb.append("{")
    sb.append("\"x\":").append(xy.x).append(",")
    sb.append("\"y\":").append(xy.y)
    sb.append("}")
    sb.toString()
  }
}
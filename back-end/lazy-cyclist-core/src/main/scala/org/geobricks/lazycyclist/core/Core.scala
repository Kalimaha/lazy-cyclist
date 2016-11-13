package org.geobricks.lazycyclist.core

import org.geobricks.lazycyclist.core.models.Models.{Segment, SegmentEnd, Step}

import scala.annotation.tailrec

object Core {

  def steps2segments(steps: List[Step]): List[Segment] = {
    @tailrec
    def loop(steps: List[Step], offset: BigInt, acc: List[Segment]): List[Segment] = steps match {
      case Nil  => acc
      case h::t =>
        val segment = step2segment(h, offset)
        loop(t, segment.end.x.get, segment :: acc)
    }

    loop(steps, 0, List()).reverse
  }

  def step2segment(step: Step, offset: BigInt): Segment = {
    val start = SegmentEnd(Some(offset), None, Some(step.start.lat), Some(step.start.lon))
    val end   = SegmentEnd(Some(offset + step.distance), None, Some(step.end.lat), Some(step.end.lon))

    Segment(start, end)
  }
}
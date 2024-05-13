package plotly

import plotly.element.LocalDateTime

import scala.language.implicitConversions

sealed abstract class Range extends Product with Serializable

object Range {
  final case class Doubles(range: (Double, Double))                 extends Range
  final case class DateTimes(range: (LocalDateTime, LocalDateTime)) extends Range

  implicit def fromDoubleTuple(t: (Double, Double)): Range =
    Doubles(t)
  implicit def fromDateTimes(t: (LocalDateTime, LocalDateTime)): Range =
    DateTimes(t)
}

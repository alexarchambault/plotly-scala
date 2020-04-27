package plotly

import plotly.element.LocalDateTime

import scala.language.implicitConversions

sealed abstract class Sequence extends Product with Serializable

object Sequence extends MutableSequenceImplicitConversions {
  final case class Doubles(seq: Seq[Double]) extends Sequence
  final case class NestedDoubles(seq: Seq[Seq[Double]]) extends Sequence
  final case class NestedInts(seq: Seq[Seq[Int]]) extends Sequence
  final case class Strings(seq: Seq[String]) extends Sequence
  final case class DateTimes(seq: Seq[LocalDateTime]) extends Sequence

  implicit def fromDoubleSeq(s: Seq[Double]): Sequence =
    Doubles(s)
  implicit def fromFloatSeq(s: Seq[Float]): Sequence =
    Doubles(s.map(_.toDouble))
  implicit def fromIntSeq(s: Seq[Int]): Sequence =
    Doubles(s.map(_.toDouble))
  implicit def fromLongSeq(s: Seq[Long]): Sequence =
    Doubles(s.map(_.toDouble))
  implicit def fromNestedDoubleSeq(s: Seq[Seq[Double]]): Sequence =
    NestedDoubles(s)
  implicit def fromNestedIntSeq(s: Seq[Seq[Int]]): Sequence =
    NestedInts(s)
  implicit def fromStringSeq(s: Seq[String]): Sequence =
    Strings(s)
  implicit def fromDateTimes(seq: Seq[LocalDateTime]): Sequence =
    DateTimes(seq)
}

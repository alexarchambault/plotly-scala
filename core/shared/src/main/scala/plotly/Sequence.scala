package plotly

import java.time.LocalDateTime

sealed abstract class Sequence extends Product with Serializable

object Sequence {
  final case class Doubles(seq: Seq[Double]) extends Sequence
  final case class Strings(seq: Seq[String]) extends Sequence
  final case class DateTimes(seq: Seq[LocalDateTime]) extends Sequence

  implicit def fromDoubleSeq(s: Seq[Double]): Sequence =
    Doubles(s)
  implicit def fromBigDecimalSeq(s: Seq[BigDecimal]): Sequence =
    Doubles(s.map(_.toDouble))
  implicit def fromFloatSeq(s: Seq[Float]): Sequence =
    Doubles(s.map(_.toDouble))
  implicit def fromIntSeq(s: Seq[Int]): Sequence =
    Doubles(s.map(_.toDouble))
  implicit def fromLongSeq(s: Seq[Long]): Sequence =
    Doubles(s.map(_.toDouble))
  implicit def fromStringSeq(s: Seq[String]): Sequence =
    Strings(s)
  implicit def fromDateTimes(seq: Seq[LocalDateTime]): Sequence =
    DateTimes(seq)
}

package plotly

import plotly.element.LocalDateTime

import scala.collection.{Seq => BaseScalaSeq}
import scala.collection.immutable.Seq

sealed abstract class Sequence extends Product with Serializable

object Sequence {
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

  implicit def fromMutableDoubleSeq(s: BaseScalaSeq[Double]): Sequence =
    Doubles(makeImmutableSeq(s))
  implicit def fromMutableFloatSeq(s: BaseScalaSeq[Float]): Sequence =
    Doubles(makeImmutableSeq(s.map(_.toDouble)))
  implicit def fromMutableIntSeq(s: BaseScalaSeq[Int]): Sequence =
    Doubles(makeImmutableSeq(s.map(_.toDouble)))
  implicit def fromMutableLongSeq(s: BaseScalaSeq[Long]): Sequence =
    Doubles(makeImmutableSeq(s.map(_.toDouble)))
  implicit def fromMutableNestedDoubleSeq(s: BaseScalaSeq[BaseScalaSeq[Double]]): Sequence =
    NestedDoubles(makeImmutableSeq(s.map(makeImmutableSeq)))
  implicit def fromMutableNestedIntSeq(s: BaseScalaSeq[BaseScalaSeq[Int]]): Sequence =
    NestedInts(makeImmutableSeq(s.map(makeImmutableSeq)))
  implicit def fromMutableStringSeq(s: BaseScalaSeq[String]): Sequence =
    Strings(makeImmutableSeq(s))
  implicit def fromMutableDateTimes(seq: BaseScalaSeq[LocalDateTime]): Sequence =
    DateTimes(makeImmutableSeq(seq))

  private def makeImmutableSeq[A](mutableSeq: BaseScalaSeq[A]): Seq[A] =
    mutableSeq match {
      case asImmutableSeq: Seq[A] => asImmutableSeq
      case _ => mutableSeq.toVector
    }

}

package plotly

import plotly.Sequence.{DateTimes, Doubles, NestedDoubles, NestedInts, Strings}
import plotly.element.LocalDateTime

import scala.collection.immutable

/**
  * Provides implicit conversions from the possibly-mutable `scala.collection.Seq` to `plotly.Sequence`.
  *
  * This implementation is written for Scala 2.12 and does not leverage the efficient `.toSeq` conversion available
  * since Scala 2.13. An equivalent implementation is provided for Scala 2.13 in the corresponding source root.
  */
trait MutableSequenceConversions {

  private def makeImmutableSeq[A](mutableSeq: scala.collection.Seq[A]): immutable.Seq[A] =
    mutableSeq match {
      case asImmutableSeq: immutable.Seq[A] => asImmutableSeq
      case _ => immutable.Seq[A](mutableSeq: _*)
    }

  implicit def fromMutableDoubleSeq(s: scala.collection.Seq[Double]): Sequence =
    Doubles(makeImmutableSeq(s))
  implicit def fromMutableFloatSeq(s: scala.collection.Seq[Float]): Sequence =
    Doubles(makeImmutableSeq(s.map(_.toDouble)))
  implicit def fromMutableIntSeq(s: scala.collection.Seq[Int]): Sequence =
    Doubles(makeImmutableSeq(s.map(_.toDouble)))
  implicit def fromMutableLongSeq(s: scala.collection.Seq[Long]): Sequence =
    Doubles(makeImmutableSeq(s.map(_.toDouble)))
  implicit def fromMutableNestedDoubleSeq(s: scala.collection.Seq[scala.collection.Seq[Double]]): Sequence =
    NestedDoubles(makeImmutableSeq(s.map(makeImmutableSeq)))
  implicit def fromMutableNestedIntSeq(s: scala.collection.Seq[scala.collection.Seq[Int]]): Sequence =
    NestedInts(makeImmutableSeq(s.map(makeImmutableSeq)))
  implicit def fromMutableStringSeq(s: scala.collection.Seq[String]): Sequence =
    Strings(makeImmutableSeq(s))
  implicit def fromMutableDateTimes(seq: scala.collection.Seq[LocalDateTime]): Sequence =
    DateTimes(makeImmutableSeq(seq))

}

package plotly

import plotly.Sequence.{DateTimes, Doubles, NestedDoubles, NestedInts, Strings}
import plotly.element.LocalDateTime

import scala.collection.immutable

/**
  * Provides implicit conversions from the possibly-mutable `scala.collection.Seq` to `plotly.Sequence`.
  *
  * This implementation leverages the efficient `.toSeq` conversion available since Scala 2.13. An equivalent
  * implementation is provided for Scala 2.12 in the corresponding source root.
  */
trait MutableSequenceConversions {

  implicit def fromMutableDoubleSeq(s: scala.collection.Seq[Double]): Sequence =
    Doubles(s.toSeq)
  implicit def fromMutableFloatSeq(s: scala.collection.Seq[Float]): Sequence =
    Doubles(s.map(_.toDouble).toSeq)
  implicit def fromMutableIntSeq(s: scala.collection.Seq[Int]): Sequence =
    Doubles(s.map(_.toDouble).toSeq)
  implicit def fromMutableLongSeq(s: scala.collection.Seq[Long]): Sequence =
    Doubles(s.map(_.toDouble).toSeq)
  implicit def fromMutableNestedDoubleSeq(s: scala.collection.Seq[scala.collection.Seq[Double]]): Sequence =
    NestedDoubles(s.map(immutable.Seq.from).toSeq)
  implicit def fromMutableNestedIntSeq(s: scala.collection.Seq[scala.collection.Seq[Int]]): Sequence =
    NestedInts(s.map(immutable.Seq.from).toSeq)
  implicit def fromMutableStringSeq(s: scala.collection.Seq[String]): Sequence =
    Strings(s.toSeq)
  implicit def fromMutableDateTimes(seq: scala.collection.Seq[LocalDateTime]): Sequence =
    DateTimes(seq.toSeq)

}

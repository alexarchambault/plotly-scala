package plotly

import plotly.Sequence.{DateTimes, Doubles, NestedDoubles, NestedInts, Strings}
import plotly.element.LocalDateTime
import scala.collection.{Seq => BaseScalaSeq}

trait MutableSequenceImplicitConversions {

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

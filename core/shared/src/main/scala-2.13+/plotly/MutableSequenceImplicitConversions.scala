package plotly

import plotly.Sequence.{DateTimes, Doubles, NestedDoubles, NestedInts, Strings}
import plotly.element.LocalDateTime
import scala.collection.{Seq => BaseScalaSeq}

trait MutableSequenceImplicitConversions {

  implicit def fromMutableDoubleSeq(s: BaseScalaSeq[Double]): Sequence =
    Doubles(s.toSeq)
  implicit def fromMutableFloatSeq(s: BaseScalaSeq[Float]): Sequence =
    Doubles(s.map(_.toDouble).toSeq)
  implicit def fromMutableIntSeq(s: BaseScalaSeq[Int]): Sequence =
    Doubles(s.map(_.toDouble).toSeq)
  implicit def fromMutableLongSeq(s: BaseScalaSeq[Long]): Sequence =
    Doubles(s.map(_.toDouble).toSeq)
  implicit def fromMutableNestedDoubleSeq(s: BaseScalaSeq[BaseScalaSeq[Double]]): Sequence =
    NestedDoubles(s.map(_.toSeq).toSeq)
  implicit def fromMutableNestedIntSeq(s: BaseScalaSeq[BaseScalaSeq[Int]]): Sequence =
    NestedInts(s.map(_.toSeq).toSeq)
  implicit def fromMutableStringSeq(s: BaseScalaSeq[String]): Sequence =
    Strings(s.toSeq)
  implicit def fromMutableDateTimes(seq: BaseScalaSeq[LocalDateTime]): Sequence =
    DateTimes(seq.toSeq)

}

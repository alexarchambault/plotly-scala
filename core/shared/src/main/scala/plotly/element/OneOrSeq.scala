package plotly.element

import scala.language.implicitConversions

sealed abstract class OneOrSeq[T] extends Product with Serializable

object OneOrSeq {
  case class One[T](value: T) extends OneOrSeq[T]
  case class Sequence[T](seq: Seq[T]) extends OneOrSeq[T]

  implicit def fromOne[T](value: T): OneOrSeq[T] =
    One(value)
  implicit def fromSeq[T](seq: Seq[T]): OneOrSeq[T] =
    Sequence(seq)
}

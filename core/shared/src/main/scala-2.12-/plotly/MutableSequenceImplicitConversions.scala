package plotly

import plotly.Sequence.{DateTimes, Doubles, NestedDoubles, NestedInts, Strings}
import plotly.element.LocalDateTime
import scala.collection.{Seq => BaseScalaSeq}

trait MutableSequenceImplicitConversions {
  // Unneccessary in Scala 2.12, since the `Seq` alias refers to the supertype for mutable and immutable sequences
}

package plotly
package layout

import java.lang.{ Integer => JInt, Boolean => JBoolean }

final case class Margin(
  autoexpand: Option[Boolean],
           l: Option[Int],
           r: Option[Int],
           t: Option[Int],
           b: Option[Int],
         pad: Option[Int]
)

object Margin {
  def apply(
    autoexpand: JBoolean = null,
             l: JInt     = null,
             r: JInt     = null,
             t: JInt     = null,
             b: JInt     = null,
           pad: JInt     = null
  ): Margin =
    Margin(
      Option(autoexpand).map(b => b: Boolean),
      Option(l).map(n => n: Int),
      Option(r).map(n => n: Int),
      Option(t).map(n => n: Int),
      Option(b).map(n => n: Int),
      Option(pad).map(n => n: Int)
    )
}

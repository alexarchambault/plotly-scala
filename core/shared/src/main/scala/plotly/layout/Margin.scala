package plotly
package layout

import java.lang.{ Integer => JInt, Boolean => JBoolean }
import dataclass.data

@data(optionSetters = true) class Margin(
  autoexpand: Option[Boolean] = None,
           l: Option[Int] = None,
           r: Option[Int] = None,
           t: Option[Int] = None,
           b: Option[Int] = None,
         pad: Option[Int] = None
)

object Margin {
  def apply(l: Int, r: Int, t: Int, b: Int): Margin =
    Margin().withL(l).withR(r).withT(t).withB(b)

  @deprecated("Use Margin() and chain-call .with* methods on it instead", "0.8.0")
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

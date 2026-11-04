package plotly
package layout

import dataclass.data

@data(optionSetters = true) final case class Margin(
    autoexpand: Option[Boolean] = None,
    l: Option[Int] = None,
    r: Option[Int] = None,
    t: Option[Int] = None,
    b: Option[Int] = None,
    pad: Option[Int] = None
) extends MarginSetters

object Margin extends MarginDeprecatedApply {
  def apply(l: Int, r: Int, t: Int, b: Int): Margin =
    Margin().withL(l).withR(r).withT(t).withB(b)
}

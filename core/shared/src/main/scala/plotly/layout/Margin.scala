package plotly
package layout

import dataclass.data

@data(
  optionSetters = true,
  nullOptionBuilderMethodName = "from",
  nullOptionCopyMethodName = "updated",
  deprecatedSetters = true,
  deprecatedSettersMessage = "Use updated instead",
  deprecatedSettersSince = "0.8.6"
) final case class Margin(
    autoexpand: Option[Boolean] = None,
    l: Option[Int] = None,
    r: Option[Int] = None,
    t: Option[Int] = None,
    b: Option[Int] = None,
    pad: Option[Int] = None
)

object Margin {
  def apply(l: Int, r: Int, t: Int, b: Int): Margin =
    Margin().copy(l = Some(l), r = Some(r), t = Some(t), b = Some(b))
}

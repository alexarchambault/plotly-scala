package plotly
package layout

import dataclass.data
import plotly.element._

@data(optionSetters = true) class Font(
    size: Option[Int] = None,
    family: Option[String] = None,
    color: Option[Color] = None
)

object Font {

  def apply(size: Int, family: String, color: Color): Font =
    Font(Some(size), Some(family), Some(color))

  def apply(size: Int, family: String): Font =
    Font(Some(size), Some(family), None)

  def apply(size: Int): Font =
    Font(Some(size), None, None)

  def apply(color: Color): Font =
    Font(None, None, Some(color))
}

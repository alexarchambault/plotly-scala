package plotly
package layout

import dataclass.data
import plotly.element._

import scala.language.implicitConversions

/** The title of a plot or of an axis (`title` of the layout or of an axis in plotly.js).
  *
  * Since plotly.js 3, titles are objects, with their text in `text`, rather than strings. A `String`
  * is implicitly converted to a `Title` having it as text, so that one can write
  * `Layout().updated(title = "My plot")`. Other fields can be set with `from`, like
  * `Title.from(text = "My plot", font = Font(18))`.
  *
  * @param standoff
  *   for an axis title, the distance in pixels between the axis tick labels and the title
  * @param x
  *   for the layout title, its horizontal position, in normalized coordinates (0 to 1)
  * @param y
  *   for the layout title, its vertical position, in normalized coordinates (0 to 1)
  * @param xanchor
  *   for the layout title, its horizontal alignment with respect to `x`
  * @param yanchor
  *   for the layout title, its vertical alignment with respect to `y`
  * @param automargin
  *   whether the margins should be expanded to fit the title
  * @param side
  *   for a color bar title, its location with respect to the color bar
  * @param position
  *   for the title of a pie or funnelarea trace, its position with respect to the trace
  */
@data(
  optionSetters = true,
  nullOptionBuilderMethodName = "from",
  nullOptionCopyMethodName = "updated",
  deprecatedSetters = true,
  deprecatedSettersMessage = "Use updated instead",
  deprecatedSettersSince = "0.8.6"
) final case class Title(
    text: Option[String] = None,
    font: Option[Font] = None,
    standoff: Option[Double] = None,
    x: Option[Double] = None,
    y: Option[Double] = None,
    xanchor: Option[Anchor] = None,
    yanchor: Option[Anchor] = None,
    automargin: Option[Boolean] = None,
    side: Option[Side] = None,
    position: Option[TitlePosition] = None
)

object Title {
  implicit def fromText(text: String): Title =
    Title().copy(text = Some(text))
}

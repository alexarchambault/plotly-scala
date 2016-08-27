package plotly
package layout

import plotly.element._

import java.lang.{ Integer => JInt }

final case class Font(
    size: Option[Int],
  family: Option[String],
   color: Option[Color]
)

object Font {
  def apply(
      size: JInt   = null,
    family: String = null,
     color: Color  = null
  ): Font =
    Font(
      Option(size).map(x => x: Int),
      Option(family),
      Option(color)
    )
}

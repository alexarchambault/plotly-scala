package plotly
package layout

import java.lang.{ Boolean => JBoolean }

import dataclass.data
import plotly.element._

@data class Annotation(
       xref: Option[Ref],
       yref: Option[Ref],
          x: Option[Element],
          y: Option[Element],
    xanchor: Option[Anchor],
    yanchor: Option[Anchor],
       text: Option[Element],
       font: Option[Font],
  showarrow: Option[Boolean]
)

object Annotation {
  def apply(
         xref: Ref      = null,
         yref: Ref      = null,
            x: Element  = null,
            y: Element  = null,
      xanchor: Anchor   = null,
      yanchor: Anchor   = null,
         text: Element  = null,
         font: Font     = null,
    showarrow: JBoolean = null
  ): Annotation =
    Annotation(
      Option(xref),
      Option(yref),
      Option(x),
      Option(y),
      Option(xanchor),
      Option(yanchor),
      Option(text),
      Option(font),
      Option(showarrow).map(v => v: Boolean)
    )
}

package plotly
package layout

import java.lang.{ Boolean => JBoolean, Double => JDouble }

import dataclass.data
import plotly.element._

@data(optionSetters = true) class Annotation(
       xref: Option[Ref] = None,
       yref: Option[Ref] = None,
          x: Option[Element] = None,
          y: Option[Element] = None,
    xanchor: Option[Anchor] = None,
    yanchor: Option[Anchor] = None,
       text: Option[Element] = None,
       font: Option[Font] = None,
  showarrow: Option[Boolean] = None,
         ax: Option[Double] = None,
         ay: Option[Double] = None
)

object Annotation {
  @deprecated("Use Annotation() and chain-call .with* methods on it instead", "0.8.0")
  def apply(
         xref: Ref      = null,
         yref: Ref      = null,
            x: Element  = null,
            y: Element  = null,
      xanchor: Anchor   = null,
      yanchor: Anchor   = null,
         text: Element  = null,
         font: Font     = null,
    showarrow: JBoolean = null,
           ax: JDouble  = null,
           ay: JDouble  = null
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
      Option(showarrow).map(v => v: Boolean),
      Option(ax).map(x => x: Double),
      Option(ay).map(x => x: Double)
    )
}

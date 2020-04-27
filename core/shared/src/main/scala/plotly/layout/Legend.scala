package plotly
package layout

import java.lang.{ Double => JDouble }

import plotly.element._
import dataclass.data

@data class Legend(
            x: Option[Double],
            y: Option[Double],
   traceorder: Option[TraceOrder],
         yref: Option[Ref],
         font: Option[Font],
  bordercolor: Option[Color],
      bgcolor: Option[Color],
      xanchor: Option[Anchor],
      yanchor: Option[Anchor],
  orientation: Option[Orientation]
)

object Legend {
  def apply(
              x: JDouble    = null,
              y: JDouble    = null,
     traceorder: TraceOrder = null,
           yref: Ref        = null,
           font: Font       = null,
    bordercolor: Color      = null,
        bgcolor: Color      = null,
        xanchor: Anchor     = null,
        yanchor: Anchor     = null,
    orientation: Orientation= null
  ): Legend =
    Legend(
      Option(x).map(v => v: Double),
      Option(y).map(v => v: Double),
      Option(traceorder),
      Option(yref),
      Option(font),
      Option(bordercolor),
      Option(bgcolor),
      Option(xanchor),
      Option(yanchor),
      Option(orientation)
    )
}
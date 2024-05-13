package plotly
package layout

import java.lang.{Double => JDouble}

import dataclass._
import plotly.element._

@data(optionSetters = true) class Legend(
    x: Option[Double] = None,
    y: Option[Double] = None,
    traceorder: Option[TraceOrder] = None,
    yref: Option[Ref] = None,
    font: Option[Font] = None,
    bordercolor: Option[Color] = None,
    bgcolor: Option[Color] = None,
    xanchor: Option[Anchor] = None,
    yanchor: Option[Anchor] = None,
    @since
    orientation: Option[Orientation] = None
)

object Legend {
  @deprecated("Use Legend() and chain-call .with* methods on it instead", "0.8.0")
  def apply(
      x: JDouble = null,
      y: JDouble = null,
      traceorder: TraceOrder = null,
      yref: Ref = null,
      font: Font = null,
      bordercolor: Color = null,
      bgcolor: Color = null,
      xanchor: Anchor = null,
      yanchor: Anchor = null,
      orientation: Orientation = null
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

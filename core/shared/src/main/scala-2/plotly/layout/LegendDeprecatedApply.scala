package plotly
package layout

import java.lang.{Double => JDouble}
import plotly.element._

// Deprecated apply overloads, kept for binary compatibility on Scala 2 (see the Scala 3 counterparts
// of these traits)
private[plotly] trait LegendDeprecatedApply {
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

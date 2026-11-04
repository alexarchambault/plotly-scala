package plotly
package layout

import dataclass._
import plotly.element._

@data(optionSetters = true) final case class Legend(
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
) extends LegendSetters

object Legend extends LegendDeprecatedApply

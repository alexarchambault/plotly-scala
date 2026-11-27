package plotly
package layout

import dataclass.data
import plotly.element._

/** A polar subplot (`polar` in plotly.js) */
@data(
  optionSetters = true,
  nullOptionBuilderMethodName = "from",
  nullOptionCopyMethodName = "updated",
  deprecatedSetters = true,
  deprecatedSettersMessage = "Use updated instead",
  deprecatedSettersSince = "0.8.6"
) final case class Polar(
    domain: Option[Domain] = None,
    // the angular extent of the subplot, in degrees, like Seq(0.0, 180.0) for a half circle
    sector: Option[Seq[Double]] = None,
    // the fraction of the radius left empty at the center
    hole: Option[Double] = None,
    bgcolor: Option[Color] = None,
    radialaxis: Option[RadialAxis] = None,
    angularaxis: Option[AngularAxis] = None,
    gridshape: Option[PolarGridShape] = None,
    // how the bars of barpolar traces are drawn: stacked or overlaid
    barmode: Option[BarMode] = None,
    bargap: Option[Double] = None
)

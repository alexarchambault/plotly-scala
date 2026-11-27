package plotly
package layout

import dataclass.data
import plotly.element._

/** A color axis, that traces referencing it (with their `coloraxis` attribute, like "coloraxis" or
  * "coloraxis2") share: they're colored with its color scale, between its bounds, and share its
  * color bar
  */
@data(
  optionSetters = true,
  nullOptionBuilderMethodName = "from",
  nullOptionCopyMethodName = "updated",
  deprecatedSetters = true,
  deprecatedSettersMessage = "Use updated instead",
  deprecatedSettersSince = "0.8.6"
) final case class ColorAxis(
    colorscale: Option[ColorScale] = None,
    autocolorscale: Option[Boolean] = None,
    reversescale: Option[Boolean] = None,
    cauto: Option[Boolean] = None,
    cmin: Option[Double] = None,
    cmax: Option[Double] = None,
    cmid: Option[Double] = None,
    showscale: Option[Boolean] = None,
    colorbar: Option[ColorBar] = None
)

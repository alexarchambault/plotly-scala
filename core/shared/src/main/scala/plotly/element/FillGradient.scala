package plotly
package element

import dataclass.data

/** A gradient filling the area of scatter traces (`fillgradient` in plotly.js) */
@data(
  optionSetters = true,
  nullOptionBuilderMethodName = "from",
  nullOptionCopyMethodName = "updated",
  deprecatedSetters = true,
  deprecatedSettersMessage = "Use updated instead",
  deprecatedSettersSince = "0.8.6"
) final case class FillGradient(
    `type`: Option[GradientType] = None,
    colorscale: Option[ColorScale] = None,
    // the positions the color scale starts and stops at, in data coordinates
    start: Option[Double] = None,
    stop: Option[Double] = None
)

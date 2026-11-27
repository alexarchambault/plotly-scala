package plotly
package element

import dataclass.data

/** A gradient filling the area of scatter traces (`fillgradient` in plotly.js) */
@data(
  optionSetters = true,
  nullOptionBuilderMethodName = "from",
  nullOptionCopyMethodName = "withValues",
  deprecatedSetters = true,
  deprecatedSettersMessage = "Use withValues instead",
  deprecatedSettersSince = "0.8.6"
) final case class FillGradient(
    `type`: Option[GradientType] = None,
    colorscale: Option[ColorScale] = None,
    // the positions the color scale starts and stops at, in data coordinates
    start: Option[Double] = None,
    stop: Option[Double] = None
)

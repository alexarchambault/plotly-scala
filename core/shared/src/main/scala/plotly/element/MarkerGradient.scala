package plotly
package element

import dataclass.data

/** A gradient filling markers (`gradient` of markers in plotly.js) */
@data(
  optionSetters = true,
  nullOptionBuilderMethodName = "from",
  nullOptionCopyMethodName = "withValues",
  deprecatedSetters = true,
  deprecatedSettersMessage = "Use withValues instead",
  deprecatedSettersSince = "0.8.6"
) final case class MarkerGradient(
    `type`: Option[OneOrSeq[GradientType]] = None,
    // the color the gradient goes to
    color: Option[OneOrSeq[Color]] = None
)

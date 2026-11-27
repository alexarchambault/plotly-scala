package plotly
package element

import dataclass.data

/** The line drawn at the mean of the values of violins (`meanline` of violin traces in plotly.js) */
@data(
  optionSetters = true,
  nullOptionBuilderMethodName = "from",
  nullOptionCopyMethodName = "updated",
  deprecatedSetters = true,
  deprecatedSettersMessage = "Use updated instead",
  deprecatedSettersSince = "0.8.6"
) final case class MeanLine(
    visible: Option[Boolean] = None,
    color: Option[Color] = None,
    width: Option[Double] = None
)

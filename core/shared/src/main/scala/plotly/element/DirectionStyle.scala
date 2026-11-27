package plotly
package element

import dataclass.data

/** The style of the increasing or decreasing items of financial traces (`increasing` and
  * `decreasing` of ohlc traces in plotly.js)
  */
@data(
  optionSetters = true,
  nullOptionBuilderMethodName = "from",
  nullOptionCopyMethodName = "updated",
  deprecatedSetters = true,
  deprecatedSettersMessage = "Use updated instead",
  deprecatedSettersSince = "0.8.6"
) final case class DirectionStyle(
    line: Option[Line] = None
)

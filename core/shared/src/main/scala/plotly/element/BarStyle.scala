package plotly
package element

import dataclass.data

/** The style of a kind of bars of waterfall traces (`increasing`, `decreasing` and `totals` in
  * plotly.js)
  */
@data(
  optionSetters = true,
  nullOptionBuilderMethodName = "from",
  nullOptionCopyMethodName = "withValues",
  deprecatedSetters = true,
  deprecatedSettersMessage = "Use withValues instead",
  deprecatedSettersSince = "0.8.6"
) final case class BarStyle(
    marker: Option[Marker] = None
)

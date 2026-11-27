package plotly
package element

import dataclass.data
import plotly.layout.Font

/** The title of the legend group of a trace (`legendgrouptitle` in plotly.js) */
@data(
  optionSetters = true,
  nullOptionBuilderMethodName = "from",
  nullOptionCopyMethodName = "withValues",
  deprecatedSetters = true,
  deprecatedSettersMessage = "Use withValues instead",
  deprecatedSettersSince = "0.8.6"
) final case class LegendGroupTitle(
    text: Option[String] = None,
    font: Option[Font] = None
)

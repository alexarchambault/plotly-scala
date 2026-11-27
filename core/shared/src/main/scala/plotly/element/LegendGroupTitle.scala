package plotly
package element

import dataclass.data
import plotly.layout.Font

/** The title of the legend group of a trace (`legendgrouptitle` in plotly.js) */
@data(
  optionSetters = true,
  nullOptionBuilderMethodName = "from",
  nullOptionCopyMethodName = "updated",
  deprecatedSetters = true,
  deprecatedSettersMessage = "Use updated instead",
  deprecatedSettersSince = "0.8.6"
) final case class LegendGroupTitle(
    text: Option[String] = None,
    font: Option[Font] = None
)

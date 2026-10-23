package plotly.layout

import dataclass.data
import plotly.Range

@data(
  optionSetters = true,
  nullOptionBuilderMethodName = "from",
  nullOptionCopyMethodName = "withValues",
  deprecatedSetters = true,
  deprecatedSettersMessage = "Use copy instead",
  deprecatedSettersSince = "0.8.6"
) final case class RangeSlider(
    range: Option[Range] = None
)

package plotly.layout

import dataclass.data
import plotly.Range

@data(
  optionSetters = true,
  nullOptionBuilderMethodName = "from",
  nullOptionCopyMethodName = "withValues"
) class RangeSlider(
    range: Option[Range] = None
)

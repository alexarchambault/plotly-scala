package plotly.layout

import dataclass.data
import plotly.Sequence

@data(optionSetters = true) class RangeSlider(
  range: Option[Sequence] = None
)

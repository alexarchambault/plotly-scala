package plotly.layout

import dataclass.data
import plotly.Range

@data(optionSetters = true) class RangeSlider(
  range: Option[Range] = None
)

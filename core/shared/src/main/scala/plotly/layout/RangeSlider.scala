package plotly.layout

import dataclass.data
import plotly.Range

@data(optionSetters = true) final case class RangeSlider(
    range: Option[Range] = None
) extends RangeSliderSetters

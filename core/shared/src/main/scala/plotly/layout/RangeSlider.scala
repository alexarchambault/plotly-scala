package plotly.layout

import dataclass.data
import plotly.Sequence

@data class RangeSlider(
  range: Option[Sequence]
)
object RangeSlider {
  def apply(range: Sequence = null): RangeSlider = RangeSlider(Option(range))
}

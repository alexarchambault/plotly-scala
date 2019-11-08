package plotly.element

import dataclass.data

@data class Bins(
  start: Double,
  end: Double,
  size: Double
)

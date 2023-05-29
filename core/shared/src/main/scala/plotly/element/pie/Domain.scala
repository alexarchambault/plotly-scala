package plotly.element.pie

import dataclass.data
import plotly.Sequence

@data(optionSetters = true) class Domain(
  x: Option[Sequence] = None,
  y: Option[Sequence] = None,
  row: Option[Int] = None,
  column: Option[Int] = None,
)
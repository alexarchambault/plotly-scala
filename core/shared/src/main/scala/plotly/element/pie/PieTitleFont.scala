package plotly.element.pie

import dataclass.data
import plotly.element.{Color, OneOrSeq}

@data(optionSetters = true) class PieTitleFont(
  family: Option[OneOrSeq[String]] = None,
  size: Option[OneOrSeq[Double]] = None,
  color: Option[OneOrSeq[Color]] = None
)

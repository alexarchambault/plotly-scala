package plotly.layout

import dataclass.data
import plotly.element.{Color, Line}

@data class Shape (
     `type`: Option[String],
       xref: Option[String],
       yref: Option[String],
         x0: Option[String],
         y0: Option[Double],
         x1: Option[String],
         y1: Option[Double],
  fillcolor: Option[Color],
    opacity: Option[Double],
       line: Option[Line],
)

package plotly.layout

import dataclass.data
import plotly.element.{Color, Line}

@data(optionSetters = true) class Shape(
    `type`: Option[String] = None,
    xref: Option[String] = None,
    yref: Option[String] = None,
    x0: Option[String] = None,
    y0: Option[Double] = None,
    x1: Option[String] = None,
    y1: Option[Double] = None,
    fillcolor: Option[Color] = None,
    opacity: Option[Double] = None,
    line: Option[Line] = None
)

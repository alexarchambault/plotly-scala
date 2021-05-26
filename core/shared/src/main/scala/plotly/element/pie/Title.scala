package plotly.element.pie

import dataclass.data

@data(optionSetters = true) class Title(
  text: Option[String] = None,
  font: Option[PieTitleFont] = None,
  position: Option[PieTitlePosition] = None
)
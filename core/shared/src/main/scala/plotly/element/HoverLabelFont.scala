package plotly.element

import dataclass.data

@data(optionSetters = true) final case class HoverLabelFont(
    family: Option[OneOrSeq[String]] = None,
    size: Option[OneOrSeq[Double]] = None,
    color: Option[OneOrSeq[Color]] = None
) extends HoverLabelFontSetters

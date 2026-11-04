package plotly
package element

import dataclass.data

@data(optionSetters = true) final case class Line(
    shape: Option[LineShape] = None,
    color: Option[OneOrSeq[Color]] = None,
    width: Option[OneOrSeq[Double]] = None,
    dash: Option[Dash] = None,
    outliercolor: Option[Color] = None,
    outlierwidth: Option[Double] = None
) extends LineSetters

object Line extends LineDeprecatedApply

package plotly
package element

import dataclass.data

@data(
  optionSetters = true,
  nullOptionBuilderMethodName = "from",
  nullOptionCopyMethodName = "withValues",
  deprecatedSetters = true,
  deprecatedSettersMessage = "Use withValues instead",
  deprecatedSettersSince = "0.8.6"
) final case class Line(
    shape: Option[LineShape] = None,
    color: Option[OneOrSeq[Color]] = None,
    width: Option[OneOrSeq[Double]] = None,
    dash: Option[Dash] = None,
    outliercolor: Option[Color] = None,
    outlierwidth: Option[Double] = None
)

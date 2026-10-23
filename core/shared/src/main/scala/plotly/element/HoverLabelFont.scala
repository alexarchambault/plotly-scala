package plotly.element

import dataclass.data

@data(
  optionSetters = true,
  nullOptionBuilderMethodName = "from",
  nullOptionCopyMethodName = "withValues",
  deprecatedSetters = true,
  deprecatedSettersMessage = "Use copy instead",
  deprecatedSettersSince = "0.8.6"
) final case class HoverLabelFont(
    family: Option[OneOrSeq[String]] = None,
    size: Option[OneOrSeq[Double]] = None,
    color: Option[OneOrSeq[Color]] = None
)

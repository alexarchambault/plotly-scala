package plotly.element

import dataclass.data

@data(
  optionSetters = true,
  nullOptionBuilderMethodName = "from",
  nullOptionCopyMethodName = "withValues"
) class HoverLabelFont(
    family: Option[OneOrSeq[String]] = None,
    size: Option[OneOrSeq[Double]] = None,
    color: Option[OneOrSeq[Color]] = None
)

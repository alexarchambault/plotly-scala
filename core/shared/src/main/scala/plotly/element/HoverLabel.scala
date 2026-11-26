package plotly.element

import dataclass.data

@data(
  optionSetters = true,
  nullOptionBuilderMethodName = "from",
  nullOptionCopyMethodName = "withValues"
) final case class HoverLabel(
    bgcolor: Option[OneOrSeq[Color]] = None,
    bordercolor: Option[OneOrSeq[Color]] = None,
    font: Option[HoverLabelFont] = None,
    align: Option[OneOrSeq[Alignment]] = None,
    namelength: Option[OneOrSeq[Int]] = None,
    uirevision: Option[Element] = None
)

package plotly.element

import dataclass.{data, since}

@data(
  optionSetters = true,
  nullOptionBuilderMethodName = "from",
  nullOptionCopyMethodName = "withValues",
  deprecatedSetters = true,
  deprecatedSettersMessage = "Use withValues instead",
  deprecatedSettersSince = "0.8.6"
) final case class HoverLabel(
    bgcolor: Option[OneOrSeq[Color]] = None,
    bordercolor: Option[OneOrSeq[Color]] = None,
    font: Option[HoverLabelFont] = None,
    align: Option[OneOrSeq[Alignment]] = None,
    namelength: Option[OneOrSeq[Int]] = None,
    uirevision: Option[Element] = None,
    @since("0.8.6")
    showarrow: Option[Boolean] = None
)

package plotly.element

import dataclass.{data, since}

@data(
  optionSetters = true,
  nullOptionBuilderMethodName = "from",
  nullOptionCopyMethodName = "withValues",
  deprecatedSetters = true,
  deprecatedSettersMessage = "Use withValues instead",
  deprecatedSettersSince = "0.8.6"
) final case class HoverLabelFont(
    family: Option[OneOrSeq[String]] = None,
    size: Option[OneOrSeq[Double]] = None,
    color: Option[OneOrSeq[Color]] = None,
    @since("0.8.6")
    weight: Option[FontWeight] = None,
    style: Option[FontStyle] = None,
    variant: Option[FontVariant] = None,
    textcase: Option[TextCase] = None,
    lineposition: Option[String] = None,
    shadow: Option[String] = None
)

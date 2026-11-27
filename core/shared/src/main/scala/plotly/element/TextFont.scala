package plotly
package element

import dataclass.{data, since}

@data(
  optionSetters = true,
  nullOptionBuilderMethodName = "from",
  nullOptionCopyMethodName = "withValues",
  deprecatedSetters = true,
  deprecatedSettersMessage = "Use withValues instead",
  deprecatedSettersSince = "0.8.6"
) final case class TextFont(
    family: String,
    @since("0.8.6")
    color: Option[OneOrSeq[Color]] = None,
    size: Option[OneOrSeq[Double]] = None,
    weight: Option[FontWeight] = None,
    style: Option[FontStyle] = None,
    variant: Option[FontVariant] = None,
    textcase: Option[TextCase] = None,
    lineposition: Option[String] = None,
    shadow: Option[String] = None
)

package plotly

import dataclass.data

@data(
  optionSetters = true,
  nullOptionBuilderMethodName = "from",
  nullOptionCopyMethodName = "updated",
  deprecatedSetters = true,
  deprecatedSettersMessage = "Use updated instead",
  deprecatedSettersSince = "0.8.6"
) final case class Config(
    editable: Option[Boolean] = None,
    responsive: Option[Boolean] = None,
    plotlyServerURL: Option[String] = None
)

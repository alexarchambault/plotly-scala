package plotly

import dataclass.data

@data(
  optionSetters = true,
  nullOptionBuilderMethodName = "from",
  nullOptionCopyMethodName = "withValues",
  deprecatedSetters = true,
  deprecatedSettersMessage = "Use withValues instead",
  deprecatedSettersSince = "0.8.6"
) final case class Config(
    editable: Option[Boolean] = None,
    responsive: Option[Boolean] = None,
    showEditInChartStudio: Option[Boolean] = None,
    plotlyServerURL: Option[String] = None
)

package plotly

import dataclass.data

@data(
  optionSetters = true,
  nullOptionBuilderMethodName = "from",
  nullOptionCopyMethodName = "withValues"
) class Config(
    editable: Option[Boolean] = None,
    responsive: Option[Boolean] = None,
    showEditInChartStudio: Option[Boolean] = None,
    plotlyServerURL: Option[String] = None
)

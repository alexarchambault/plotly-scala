package plotly

import dataclass.data

@data(optionSetters = true) final case class Config(
    editable: Option[Boolean] = None,
    responsive: Option[Boolean] = None,
    showEditInChartStudio: Option[Boolean] = None,
    plotlyServerURL: Option[String] = None
) extends ConfigSetters

object Config extends ConfigDeprecatedApply

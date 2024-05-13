package plotly

import java.lang.{Boolean => JBoolean}
import dataclass.data

@data(optionSetters = true) class Config(
    editable: Option[Boolean] = None,
    responsive: Option[Boolean] = None,
    showEditInChartStudio: Option[Boolean] = None,
    plotlyServerURL: Option[String] = None
)

object Config {
  @deprecated("Use Config() and chain-call .with* methods on it instead", "0.8.0")
  def apply(
      editable: JBoolean = null,
      responsive: JBoolean = null,
      showEditInChartStudio: JBoolean = null,
      plotlyServerURL: String = null
  ): Config =
    new Config(
      Option(editable),
      Option(responsive),
      Option(showEditInChartStudio),
      Option(plotlyServerURL)
    )
}

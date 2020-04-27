package plotly

import java.lang.{ Boolean => JBoolean }
import dataclass.data

@data class Config(
                    editable: Option[Boolean],
                  responsive: Option[Boolean],
       showEditInChartStudio: Option[Boolean],
             plotlyServerURL: Option[String]
)

object Config {
  def apply(
                    editable: JBoolean = null,
                  responsive: JBoolean = null,
       showEditInChartStudio: JBoolean = null,
             plotlyServerURL: String   = null
  ): Config =
    new Config(
      Option(editable),
      Option(responsive),
      Option(showEditInChartStudio),
      Option(plotlyServerURL)
    )
}

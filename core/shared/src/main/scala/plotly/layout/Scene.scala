package plotly
package layout

import java.lang.{ Integer => JInt, Double => JDouble, Boolean => JBoolean }

import dataclass.data
import plotly.element._

@data class Scene(
  xaxis: Option[Axis],
  yaxis: Option[Axis],
  zaxis: Option[Axis]
)

object Scene {
  def apply(
    xaxis: Axis = null,
    yaxis: Axis = null,
    zaxis: Axis = null
  ): Scene = new Scene(
    Option(xaxis),
    Option(yaxis),
    Option(zaxis)
  )
}

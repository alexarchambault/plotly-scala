package plotly
package layout

import java.lang.{Boolean => JBoolean, Double => JDouble, Integer => JInt}

import dataclass.data
import plotly.element._

@data(optionSetters = true) class Scene(
    xaxis: Option[Axis] = None,
    yaxis: Option[Axis] = None,
    zaxis: Option[Axis] = None
)

object Scene {
  @deprecated("Use Scene() and chain-call .with* methods on it instead", "0.8.0")
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

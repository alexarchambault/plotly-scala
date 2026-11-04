package plotly
package layout

import java.lang.{Boolean => JBoolean, Double => JDouble, Integer => JInt}
import plotly.element._

// Deprecated apply overloads, kept for binary compatibility on Scala 2 (see the Scala 3 counterparts
// of these traits)
private[plotly] trait SceneDeprecatedApply {
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

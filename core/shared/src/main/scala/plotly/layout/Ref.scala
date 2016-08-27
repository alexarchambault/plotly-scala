package plotly
package layout

import plotly.element._

sealed abstract class Ref(val label: String) extends Product with Serializable

object Ref {
  case object Paper extends Ref("paper")
  case class Axis(underlying: AxisReference) extends Ref(underlying.label)
}

package plotly
package element

sealed abstract class Dash(val label: String) extends Product with Serializable

object Dash {
  case object Solid   extends Dash("solid")
  case object DashDot extends Dash("dashdot")
  case object Dot     extends Dash("dot")
}

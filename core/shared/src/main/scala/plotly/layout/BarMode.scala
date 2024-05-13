package plotly
package layout

sealed abstract class BarMode(val label: String) extends Product with Serializable

object BarMode {
  case object Group    extends BarMode("group")
  case object Stack    extends BarMode("stack")
  case object Overlay  extends BarMode("overlay")
  case object Relative extends BarMode("relative")
}

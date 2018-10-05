package plotly
package element

sealed abstract class BarTextPosition(val label: String) extends Product with Serializable

object BarTextPosition {
  case object Inside  extends BarTextPosition("inside")
  case object Outside extends BarTextPosition("outside")
  case object Auto    extends BarTextPosition("auto")
  case object None    extends BarTextPosition("none")
}

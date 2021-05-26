package plotly.element.pie

sealed abstract class PieTextPosition(val label: String) extends Product with Serializable

object PieTextPosition {
  case object Inside  extends PieTextPosition("inside")
  case object Outside extends PieTextPosition("outside")
  case object Auto    extends PieTextPosition("auto")
  case object None    extends PieTextPosition("none")
}

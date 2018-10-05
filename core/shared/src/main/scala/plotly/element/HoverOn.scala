package plotly.element

sealed abstract class HoverOn(val label: String) extends Product with Serializable

object HoverOn {
  case object Points extends HoverOn("points")
  case object Fills extends HoverOn("fills")
  case object PointsFill extends HoverOn("points+fills")
}

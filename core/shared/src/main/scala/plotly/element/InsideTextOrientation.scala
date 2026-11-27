package plotly
package element

sealed abstract class InsideTextOrientation(val label: String) extends Product with Serializable

object InsideTextOrientation {
  case object Horizontal extends InsideTextOrientation("horizontal")
  case object Radial     extends InsideTextOrientation("radial")
  case object Tangential extends InsideTextOrientation("tangential")
  case object Auto       extends InsideTextOrientation("auto")
}

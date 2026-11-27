package plotly
package element

/** What shows hover labels in box traces: the boxes, the points, or both */
sealed abstract class BoxHoverOn(val label: String) extends Product with Serializable

object BoxHoverOn {
  case object Boxes       extends BoxHoverOn("boxes")
  case object Points      extends BoxHoverOn("points")
  case object BoxesPoints extends BoxHoverOn("boxes+points")
}

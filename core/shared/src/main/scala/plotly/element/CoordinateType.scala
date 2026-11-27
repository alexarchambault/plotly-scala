package plotly
package element

/** Whether the coordinates of heatmap-like traces are given by an array (x or y), or scaled from a
  * start and a step (x0 and dx, or y0 and dy)
  */
sealed abstract class CoordinateType(val label: String) extends Product with Serializable

object CoordinateType {
  case object Array  extends CoordinateType("array")
  case object Scaled extends CoordinateType("scaled")
}

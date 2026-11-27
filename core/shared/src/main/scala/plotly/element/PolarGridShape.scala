package plotly
package element

/** The shape of the grid of polar subplots: circles, or polygons (linear), like radar charts have */
sealed abstract class PolarGridShape(val label: String) extends Product with Serializable

object PolarGridShape {
  case object Circular extends PolarGridShape("circular")
  case object Linear   extends PolarGridShape("linear")
}

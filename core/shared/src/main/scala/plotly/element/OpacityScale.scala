package plotly
package element

/** How the opacity of 3D surfaces varies with their values: a mapping from normalized values (0 to 1)
  * to opacities, or one of the named scales, min, max, extremes and uniform
  */
sealed abstract class OpacityScale extends Product with Serializable

object OpacityScale {
  final case class CustomScale(values: Seq[(Double, Double)]) extends OpacityScale
  final case class NamedScale(name: String)                   extends OpacityScale
}

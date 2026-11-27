package plotly
package element

sealed abstract class GradientType(val label: String) extends Product with Serializable

object GradientType {
  case object Radial     extends GradientType("radial")
  case object Horizontal extends GradientType("horizontal")
  case object Vertical   extends GradientType("vertical")
  case object None       extends GradientType("none")
}

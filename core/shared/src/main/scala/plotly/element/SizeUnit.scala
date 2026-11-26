package plotly
package element

/** The unit of a length, like the thickness or the length of a color bar */
sealed abstract class SizeUnit(val label: String) extends Product with Serializable

object SizeUnit {
  case object Fraction extends SizeUnit("fraction")
  case object Pixels   extends SizeUnit("pixels")
}

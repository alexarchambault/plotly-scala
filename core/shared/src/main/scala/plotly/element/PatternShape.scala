package plotly
package element

/** The shape of the pattern filling areas or bars: none, diagonal lines, crosses, horizontal or
  * vertical lines, plus signs, or dots
  */
sealed abstract class PatternShape(val label: String) extends Product with Serializable

object PatternShape {
  case object None            extends PatternShape("")
  case object DiagonalUp      extends PatternShape("/")
  case object DiagonalDown    extends PatternShape("\\")
  case object Cross           extends PatternShape("x")
  case object HorizontalLines extends PatternShape("-")
  case object VerticalLines   extends PatternShape("|")
  case object Plus            extends PatternShape("+")
  case object Dots            extends PatternShape(".")
}

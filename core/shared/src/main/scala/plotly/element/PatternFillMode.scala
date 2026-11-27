package plotly
package element

/** Whether a pattern replaces the color of the area it fills, or is drawn over it */
sealed abstract class PatternFillMode(val label: String) extends Product with Serializable

object PatternFillMode {
  case object Replace extends PatternFillMode("replace")
  case object Overlay extends PatternFillMode("overlay")
}

package plotly
package element

/** How the gaps of stacked scatter traces are filled: with zeros (infer zero), or interpolated */
sealed abstract class StackGaps(val label: String) extends Product with Serializable

object StackGaps {
  case object InferZero   extends StackGaps("infer zero")
  case object Interpolate extends StackGaps("interpolate")
}

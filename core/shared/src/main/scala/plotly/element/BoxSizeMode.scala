package plotly
package element

/** Whether boxes span the quartiles of the values, or their standard deviation */
sealed abstract class BoxSizeMode(val label: String) extends Product with Serializable

object BoxSizeMode {
  case object Quartiles         extends BoxSizeMode("quartiles")
  case object StandardDeviation extends BoxSizeMode("sd")
}

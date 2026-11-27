package plotly
package element

/** Whether cumulative histograms sum the bins from the left (increasing) or from the right */
sealed abstract class CumulativeDirection(val label: String) extends Product with Serializable

object CumulativeDirection {
  case object Increasing extends CumulativeDirection("increasing")
  case object Decreasing extends CumulativeDirection("decreasing")
}

package plotly
package element

/** Whether the bins of cumulative histograms include their own values, exclude them, or include
  * half of them
  */
sealed abstract class CurrentBin(val label: String) extends Product with Serializable

object CurrentBin {
  case object Include extends CurrentBin("include")
  case object Exclude extends CurrentBin("exclude")
  case object Half    extends CurrentBin("half")
}

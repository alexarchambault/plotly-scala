package plotly
package element

/** Whether the size of the text of bars is constrained to fit inside them (inside), to fit outside
  * of them in the plot area (outside), both, or not constrained (none)
  */
sealed abstract class ConstrainText(val label: String) extends Product with Serializable

object ConstrainText {
  case object Inside  extends ConstrainText("inside")
  case object Outside extends ConstrainText("outside")
  case object Both    extends ConstrainText("both")
  case object None    extends ConstrainText("none")
}

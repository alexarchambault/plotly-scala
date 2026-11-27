package plotly
package element

/** Where the text inside bars is drawn: at their end, middle or start */
sealed abstract class InsideTextAnchor(val label: String) extends Product with Serializable

object InsideTextAnchor {
  case object End    extends InsideTextAnchor("end")
  case object Middle extends InsideTextAnchor("middle")
  case object Start  extends InsideTextAnchor("start")
}

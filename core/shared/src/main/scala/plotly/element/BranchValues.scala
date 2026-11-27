package plotly
package element

/** Whether the values of the parents of hierarchical traces are their total (total), or what remains
  * on top of the values of their children (remainder)
  */
sealed abstract class BranchValues(val label: String) extends Product with Serializable

object BranchValues {
  case object Remainder extends BranchValues("remainder")
  case object Total     extends BranchValues("total")
}

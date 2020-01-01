package plotly
package element

sealed abstract class Ticks(val label: String) extends Product with Serializable

object Ticks {
  case object Outside extends Ticks("outside")
  case object Inside extends Ticks("inside")
  case object Empty extends Ticks("")
}

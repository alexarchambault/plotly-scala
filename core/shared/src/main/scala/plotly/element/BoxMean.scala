package plotly
package element

sealed abstract class BoxMean extends Product with Serializable

object BoxMean {
  case class Bool(value: Boolean) extends BoxMean
  sealed abstract class Labeled(val label: String) extends BoxMean

  val True = Bool(true)
  val False = Bool(false)

  case object SD extends Labeled("sd")
}

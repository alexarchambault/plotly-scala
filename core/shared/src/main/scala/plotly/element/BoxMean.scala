package plotly
package element

import dataclass.data

sealed abstract class BoxMean extends Product with Serializable

object BoxMean {
  @data final case class Bool(value: Boolean)      extends BoxMean with BoxMeanBoolSetters
  sealed abstract class Labeled(val label: String) extends BoxMean

  val True  = Bool(true)
  val False = Bool(false)

  case object SD extends Labeled("sd")
}

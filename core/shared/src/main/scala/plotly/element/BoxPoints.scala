package plotly
package element

import dataclass.data

sealed abstract class BoxPoints extends Product with Serializable

object BoxPoints {
  @data(
    deprecatedSetters = true,
    deprecatedSettersMessage = "Use copy instead",
    deprecatedSettersSince = "0.8.6"
  ) final case class Bool(value: Boolean)          extends BoxPoints
  sealed abstract class Labeled(val label: String) extends BoxPoints

  val False = Bool(false)
  val True  = Bool(true)

  case object All               extends Labeled("all")
  case object SuspectedOutliers extends Labeled("suspectedoutliers")
  case object Outliers          extends Labeled("outliers")
}

package plotly
package element

/** The side of violins their density is drawn on */
sealed abstract class ViolinSide(val label: String) extends Product with Serializable

object ViolinSide {
  case object Both     extends ViolinSide("both")
  case object Positive extends ViolinSide("positive")
  case object Negative extends ViolinSide("negative")
}

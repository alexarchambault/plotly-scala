package plotly
package element

import scala.language.implicitConversions

/** Whether a trace is visible: shown (true), hidden (false), or only shown in the legend, from where
  * it can be shown (legendonly)
  */
sealed abstract class Visible extends Product with Serializable

object Visible {
  case object True       extends Visible
  case object False      extends Visible
  case object LegendOnly extends Visible

  implicit def fromBoolean(visible: Boolean): Visible =
    if (visible) True else False
}

package plotly
package element

/** Whether violins of the same scale group have the same area (width) or an area proportional to
  * their number of values (count)
  */
sealed abstract class ViolinScaleMode(val label: String) extends Product with Serializable

object ViolinScaleMode {
  case object Width extends ViolinScaleMode("width")
  case object Count extends ViolinScaleMode("count")
}

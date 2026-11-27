package plotly
package element

/** What the angles of markers are relative to: the previous point (previous), or the up direction */
sealed abstract class MarkerAngleRef(val label: String) extends Product with Serializable

object MarkerAngleRef {
  case object Previous extends MarkerAngleRef("previous")
  case object Up       extends MarkerAngleRef("up")
}

package plotly
package layout

// Values of the scattermode layout attribute (not called ScatterMode, to avoid
// clashing with plotly.element.ScatterMode)
sealed abstract class ScatterLayoutMode(val label: String) extends Product with Serializable

object ScatterLayoutMode {
  case object Group   extends ScatterLayoutMode("group")
  case object Overlay extends ScatterLayoutMode("overlay")
}

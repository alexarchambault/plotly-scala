package plotly
package element

sealed abstract class ContourColoring(val label: String) extends Product with Serializable

object ContourColoring {
  case object Fill    extends ContourColoring("fill")
  case object Heatmap extends ContourColoring("heatmap")
  case object Lines   extends ContourColoring("lines")
  case object None    extends ContourColoring("none")
}

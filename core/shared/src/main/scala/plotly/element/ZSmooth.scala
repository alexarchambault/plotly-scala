package plotly
package element

/** The smoothing of the values of heatmap traces (`zsmooth` in plotly.js) */
sealed abstract class ZSmooth extends Product with Serializable

object ZSmooth {
  sealed abstract class Labeled(val label: String) extends ZSmooth

  case object Fast extends Labeled("fast")
  case object Best extends Labeled("best")
  // no smoothing (false in plotly.js)
  case object False extends ZSmooth
}

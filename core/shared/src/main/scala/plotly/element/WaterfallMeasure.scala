package plotly
package element

/** The kind of the values of waterfall traces: a change of the running total (relative), a new
  * running total (absolute), or the running total itself (total)
  */
sealed abstract class WaterfallMeasure(val label: String) extends Product with Serializable

object WaterfallMeasure {
  case object Relative extends WaterfallMeasure("relative")
  case object Absolute extends WaterfallMeasure("absolute")
  case object Total    extends WaterfallMeasure("total")
}

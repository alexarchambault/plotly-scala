package plotly
package element

/** How the quartiles of box and violin traces are computed */
sealed abstract class QuartileMethod(val label: String) extends Product with Serializable

object QuartileMethod {
  case object Linear    extends QuartileMethod("linear")
  case object Exclusive extends QuartileMethod("exclusive")
  case object Inclusive extends QuartileMethod("inclusive")
}

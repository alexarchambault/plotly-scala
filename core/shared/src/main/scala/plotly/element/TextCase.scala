package plotly
package element

/** The case text is drawn in, whatever its case in the source */
sealed abstract class TextCase(val label: String) extends Product with Serializable

object TextCase {
  case object Normal   extends TextCase("normal")
  case object WordCaps extends TextCase("word caps")
  case object Upper    extends TextCase("upper")
  case object Lower    extends TextCase("lower")
}

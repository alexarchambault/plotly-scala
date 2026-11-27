package plotly
package element

sealed abstract class FontStyle(val label: String) extends Product with Serializable

object FontStyle {
  case object Normal extends FontStyle("normal")
  case object Italic extends FontStyle("italic")
}

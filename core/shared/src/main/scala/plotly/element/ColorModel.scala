package plotly.element

sealed abstract class ColorModel(val label: String) extends Product with Serializable

object ColorModel {
  case object RGB extends ColorModel("rgb")
  case object RGBA extends ColorModel("rgba")
  case object RGBA256 extends ColorModel("rgba256")
  case object HSL extends ColorModel("hsl")
  case object HSLA extends ColorModel("hsla")
}

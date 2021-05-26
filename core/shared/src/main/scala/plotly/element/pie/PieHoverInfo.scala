package plotly.element.pie

sealed abstract class PieHoverInfo extends Product with Serializable {
  def label: String
}

object PieHoverInfo {

  def all: PieHoverInfo = All
  def skip: PieHoverInfo = Skip
  def none: PieHoverInfo = None
  def apply(elements: Element*): PieHoverInfo = Combination(elements)

  sealed abstract class Element(override val label: String) extends PieHoverInfo
  case object Text extends Element("text")
  case object Name extends Element("name")
  case object Percent extends Element("percent")
  case object Value extends Element("value")
  case object Label extends Element("label")


  case object All extends PieHoverInfo {
    def label = "all"
  }

  case object Skip extends PieHoverInfo {
    def label = "skip"
  }

  val None: Combination = Combination(Nil)

  final case class Combination(elements: Seq[Element]) extends PieHoverInfo {
    def label: String = elements.map(_.label).mkString("+")
  }
}

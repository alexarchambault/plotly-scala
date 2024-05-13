package plotly.element

sealed abstract class HoverInfo extends Product with Serializable {
  def label: String
}

object HoverInfo {

  def all: HoverInfo  = All
  def none: HoverInfo = None
  def skip: HoverInfo = Skip
  def apply(elements: Element*): HoverInfo =
    Combination(elements)

  sealed abstract class Element(override val label: String) extends HoverInfo

  case object X     extends Element("x")
  case object Y     extends Element("y")
  case object Z     extends Element("z")
  case object Text  extends Element("text")
  case object Name  extends Element("name")
  case object Color extends Element("color")

  case object All extends HoverInfo {
    def label = "all"
  }
  val None = Combination(Nil)
  case object Skip extends HoverInfo {
    def label = "skip"
  }

  final case class Combination(elements: Seq[Element]) extends HoverInfo {
    def label: String = elements.map(_.label).mkString("+")
  }

}

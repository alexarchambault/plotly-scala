package plotly.element

sealed abstract class HoverInfo extends Product with Serializable {
  def label: String
}

object HoverInfo {

  def all: HoverInfo                       = All
  def none: HoverInfo                      = None
  def skip: HoverInfo                      = Skip
  def apply(elements: Element*): HoverInfo =
    Combination(elements)

  sealed abstract class Element(override val label: String) extends HoverInfo

  case object X     extends Element("x")
  case object Y     extends Element("y")
  case object Z     extends Element("z")
  case object Text  extends Element("text")
  case object Name  extends Element("name")
  case object Color extends Element("color")

  // pie-like and hierarchical traces (pie, funnelarea, sunburst, treemap)
  case object Label         extends Element("label")
  case object Value         extends Element("value")
  case object Percent       extends Element("percent")
  case object CurrentPath   extends Element("current path")
  case object PercentRoot   extends Element("percent root")
  case object PercentEntry  extends Element("percent entry")
  case object PercentParent extends Element("percent parent")

  // waterfall traces
  case object Initial extends Element("initial")
  case object Delta   extends Element("delta")
  case object Final   extends Element("final")

  // funnel traces
  case object PercentInitial  extends Element("percent initial")
  case object PercentPrevious extends Element("percent previous")
  case object PercentTotal    extends Element("percent total")

  // polar traces
  case object R     extends Element("r")
  case object Theta extends Element("theta")

  val elements: Seq[Element] = Seq(
    X,
    Y,
    Z,
    Text,
    Name,
    Color,
    Label,
    Value,
    Percent,
    CurrentPath,
    PercentRoot,
    PercentEntry,
    PercentParent,
    Initial,
    Delta,
    Final,
    PercentInitial,
    PercentPrevious,
    PercentTotal,
    R,
    Theta
  )
  private[plotly] lazy val elementMap = elements.map(e => e.label -> e).toMap

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

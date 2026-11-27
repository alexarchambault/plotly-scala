package plotly.demo.sankey

import plotly._
import plotly.demo.DemoChart
import plotly.element._
import plotly.layout._

object EnergyFlowSankey extends DemoChart {

  def plotlyDocUrl = "https://plotly.com/javascript/sankey-diagram/#basic-sankey-diagram"
  def id           = this.getClass.getSimpleName.dropRight(1)
  def source       = EnergyFlowSankeySource.source

  // demo source start

  val nodes = Seq(
    ("Solar", Color.RGB(254, 203, 82)),
    ("Wind", Color.RGB(25, 211, 243)),
    ("Gas", Color.RGB(255, 161, 90)),
    ("Electricity", Color.RGB(99, 110, 250)),
    ("Heat", Color.RGB(239, 85, 59)),
    ("Homes", Color.RGB(0, 204, 150)),
    ("Industry", Color.RGB(171, 99, 250)),
    ("Losses", Color.RGB(160, 160, 160))
  )
  def index(name: String) = nodes.indexWhere(_._1 == name)

  // energy flows, in TWh
  val flows = Seq(
    ("Solar", "Electricity", 30.0),
    ("Wind", "Electricity", 45.0),
    ("Gas", "Electricity", 40.0),
    ("Gas", "Heat", 35.0),
    ("Electricity", "Homes", 45.0),
    ("Electricity", "Industry", 50.0),
    ("Electricity", "Losses", 20.0),
    ("Heat", "Homes", 20.0),
    ("Heat", "Industry", 10.0),
    ("Heat", "Losses", 5.0)
  )

  // the links take the color of their source, lighter
  def lighter(color: Color.RGB) = Color.RGBA(color.r, color.g, color.b, 0.35)

  val data = Seq(
    Sankey(
      SankeyNode().updated(
        label = nodes.map(_._1),
        color = nodes.map(_._2: Color),
        pad = 20.0,
        thickness = 20.0
      ),
      SankeyLink().updated(
        source = flows.map(f => index(f._1)),
        target = flows.map(f => index(f._2)),
        value = flows.map(_._3),
        color = flows.map(f => lighter(nodes(index(f._1))._2): Color)
      )
    ).updated(valuesuffix = " TWh")
  )

  val layout = Layout().updated(title = "Energy Flows", font = Font(13))

  // demo source end

}

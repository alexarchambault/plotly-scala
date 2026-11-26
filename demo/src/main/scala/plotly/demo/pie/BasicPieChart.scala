package plotly.demo.pie

import plotly._
import plotly.demo.DemoChart
import plotly.element._
import plotly.layout._

object BasicPieChart extends DemoChart {

  def plotlyDocUrl = "https://plotly.com/javascript/pie-charts/#basic-pie-chart"
  def id           = this.getClass.getSimpleName.dropRight(1)
  def source       = BasicPieChartSource.source

  // demo source start

  // share of the electricity generated in a year, by source, in %
  val sources = Seq(
    "Gas"     -> 38.0,
    "Nuclear" -> 18.0,
    "Coal"    -> 16.0,
    "Wind"    -> 14.0,
    "Solar"   -> 8.0,
    "Hydro"   -> 6.0
  )

  val data = Seq(
    Pie(sources.map(_._2), sources.map(_._1)).withValues(
      textinfo = TextInfo(TextInfo.Label, TextInfo.Percent),
      hoverinfo = HoverInfo(HoverInfo.Label, HoverInfo.Value),
      // pull the renewable sources out of the pie
      pull = sources.map(s => if (Set("Wind", "Solar", "Hydro")(s._1)) 0.06 else 0.0),
      sort = false,
      direction = RotationDirection.Clockwise,
      marker = Marker().withValues(line = Line().withValues(color = Color.StringColor("white"), width = 2.0))
    )
  )

  val layout = Layout().withValues(title = "Electricity Generation", showlegend = false)

  // demo source end

}

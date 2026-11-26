package plotly.demo.lineandscatter

import plotly.Scatter
import plotly.demo.DemoChart
import plotly.element._
import plotly.layout._

object GroupedScatterPlot extends DemoChart {

  val id = "grouped-scatter-plot"

  val plotlyDocUrl = "https://plotly.com/javascript/line-and-scatter/#grouped-scatter-plot"

  def source = GroupedScatterPlotSource.source

  // demo source start

  val countries = Seq("South Korea", "China", "Canada")

  val data = Seq(
    "Gold"   -> Seq(24, 10, 9),
    "Silver" -> Seq(13, 15, 12),
    "Bronze" -> Seq(11, 8, 12)
  ).map { case (name, medals) =>
    Scatter(countries, medals)
      .withValues(name = name, mode = ScatterMode(ScatterMode.Markers))
  }

  val layout = Layout().withValues(
    title = "Grouped by Country",
    xaxis = Axis().withValues(title = "Country"),
    yaxis = Axis().withValues(title = "Medals"),
    scattermode = ScatterLayoutMode.Group,
    scattergap = 0.7
  )

  // demo source end

}

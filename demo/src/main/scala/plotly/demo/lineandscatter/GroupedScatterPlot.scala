package plotly.demo.lineandscatter

import plotly._
import plotly.demo.DemoChart
import plotly.element._
import plotly.layout._

object GroupedScatterPlot extends DemoChart {

  def plotlyDocUrl = "https://plotly.com/javascript/line-and-scatter/#grouped-scatter-plot"
  def id           = "grouped-scatter-plot"
  def source       = GroupedScatterPlotSource.source

  // demo source start

  val countries = Seq("United States", "China", "Japan", "Australia", "France")

  // Paris 2024 Olympics medal table, top 5 nations
  val medals = Seq(
    ("Gold", "#d4a72c", Seq(40, 40, 20, 18, 16)),
    ("Silver", "#a7adba", Seq(44, 27, 12, 19, 26)),
    ("Bronze", "#c0793a", Seq(42, 24, 13, 16, 22))
  )

  val data = medals.map { case (name, color, counts) =>
    Scatter(countries, counts)
      .withValues(
        name = name,
        mode = ScatterMode(ScatterMode.Markers),
        marker = Marker().withValues(
          size = 14,
          color = Color.StringColor(color),
          line = Line().withValues(color = Color.StringColor("white"), width = 1.5)
        )
      )
  }

  val layout = Layout().withValues(
    title = "Paris 2024 Olympics Medals",
    yaxis = Axis().withValues(title = "Medals"),
    scattermode = ScatterLayoutMode.Group,
    scattergap = 0.6
  )

  // demo source end

}

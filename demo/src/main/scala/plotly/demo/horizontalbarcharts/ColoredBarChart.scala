package plotly.demo.horizontalbarcharts

import plotly._
import plotly.demo.DemoChart
import plotly.element._
import plotly.layout._

object ColoredBarChart extends DemoChart {

  def plotlyDocUrl = "https://plot.ly/javascript/horizontal-bar-charts/#colored-bar-chart"
  def id           = "colored-bar-chart"
  def source       = ColoredBarChartSource.source

  // demo source start

  val teams = Seq("Platform", "Mobile", "Web", "Data")

  // hours per week (fictional)
  val activities = Seq(
    ("Coding", "#636efa", Seq(18, 15, 17, 12)),
    ("Code review", "#00cc96", Seq(6, 5, 7, 4)),
    ("Meetings", "#ffa15a", Seq(8, 11, 9, 13)),
    ("Support", "#ef553b", Seq(4, 5, 3, 7))
  )

  val data = activities.map { case (name, color, hours) =>
    Bar(hours, teams)
      .updated(
        name = name,
        orientation = Orientation.Horizontal,
        marker = Marker().updated(
          color = Color.StringColor(color),
          line = Line().updated(color = Color.StringColor("white"), width = 1.0)
        )
      )
  }

  val layout = Layout().updated(
    title = "How Each Team Spends Its Week",
    xaxis = Axis().updated(title = "Hours"),
    barmode = BarMode.Stack
  )

  // demo source end

}

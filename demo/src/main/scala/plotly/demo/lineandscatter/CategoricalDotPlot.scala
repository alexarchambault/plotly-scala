package plotly.demo.lineandscatter

import plotly.Scatter
import plotly.demo.DemoChart
import plotly.element._
import plotly.layout._

object CategoricalDotPlot extends DemoChart {

  val id = "categorical-dot-plot"

  val plotlyDocUrl = "https://plot.ly/javascript/line-and-scatter/#categorical-dot-plot"

  def source = CategoricalDotPlotSource.source

  // demo source start

  val country = Seq(
    "Switzerland (2011)",
    "Chile (2013)",
    "Japan (2014)",
    "United States (2012)",
    "Slovenia (2014)",
    "Canada (2011)",
    "Poland (2010)",
    "Estonia (2015)",
    "Luxembourg (2013)",
    "Portugal (2011)"
  )

  val votingPop = Seq(
    40.0, 45.7, 52.0, 53.6, 54.1, 54.2, 54.5, 54.7, 55.1, 56.6
  )

  val regVoters = Seq(
    49.1, 42.0, 52.7, 84.3, 51.7, 61.1, 55.3, 64.2, 91.1, 58.9
  )

  val trace1 = Scatter(
    votingPop,
    country,
    mode = ScatterMode(ScatterMode.Markers),
    name = "Percent of estimated voting age population",
    marker = Marker(
      color = Color.RGBA(156, 165, 196, 0.95),
      line = Line(
        color = Color.RGBA(156, 165, 196, 1.0),
        width = 1.0
      ),
      symbol = Symbol.Circle(),
      size = 16
    )
  )

  val trace2 = Scatter(
    regVoters,
    country,
    mode = ScatterMode(ScatterMode.Markers),
    name = "Percent of estimated registered voters",
    marker = Marker(
      color = Color.RGBA(204, 204, 204, 0.95),
      line = Line(
        color = Color.RGBA(217, 217, 217, 1.0),
        width = 1.0
      ),
      symbol = Symbol.Circle(),
      size = 16
    )
  )

  val data = Seq(trace1, trace2)

  val layout = Layout(
    title = "Votes cast for ten lowest voting age population in OECD countries",
    xaxis = Axis(
      showgrid = false,
      showline = true,
      linecolor = Color.RGB(102, 102, 102),
      titlefont = Font(
        color = Color.RGB(204, 204, 204)
      ),
      tickfont = Font(
        color = Color.RGB(102, 102, 102)
      ),
      autotick = false,
      dtick = 10.0,
      ticks = Ticks.Outside,
      tickcolor = Color.RGB(102, 102, 102)
    ),
    margin = Margin(
      l = 140,
      r = 40,
      b = 50,
      t = 80
    ),
    legend = Legend(
      font = Font(
        size = 10
      ),
      yanchor = Anchor.Middle,
      xanchor = Anchor.Right
    ),
    width = 600,
    height = 400,
    paper_bgcolor = Color.RGB(254, 247, 234),
    plot_bgcolor = Color.RGB(254, 247, 234),
    hovermode = HoverMode.Closest
  )

  // demo source end

}

package plotly.demo.lineandscatter

import plotly._
import plotly.demo.DemoChart
import plotly.element._
import plotly.layout._

object CategoricalDotPlot extends DemoChart {

  def plotlyDocUrl = "https://plot.ly/javascript/line-and-scatter/#categorical-dot-plot"
  def id           = "categorical-dot-plot"
  def source       = CategoricalDotPlotSource.source

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

  def dots(values: Seq[Double], name: String, color: Color) =
    Scatter(values, country)
      .withValues(
        mode = ScatterMode(ScatterMode.Markers),
        name = name,
        marker = Marker().withValues(
          color = color,
          line = Line().withValues(color = Color.StringColor("white"), width = 1.5),
          symbol = Symbol.Circle(),
          size = 14
        )
      )

  val data = Seq(
    dots(votingPop, "% of voting age population", Color.RGBA(99, 110, 250, 0.95)),
    dots(regVoters, "% of registered voters", Color.RGBA(255, 161, 90, 0.95))
  )

  val layout = Layout().withValues(
    title = "Votes Cast in the Ten Lowest-Turnout OECD Countries",
    xaxis = Axis().withValues(
      showgrid = false,
      showline = true,
      linecolor = Color.RGB(102, 102, 102),
      tickfont = Font(Color.RGB(102, 102, 102)),
      tickmode = TickMode.Linear,
      dtick = 10.0,
      ticks = Ticks.Outside,
      tickcolor = Color.RGB(102, 102, 102),
      ticksuffix = "%"
    ),
    yaxis = Axis().withValues(automargin = true, gridcolor = Color.RGB(230, 232, 240)),
    legend = Legend().withValues(
      font = Font(size = 11),
      orientation = Orientation.Horizontal,
      xanchor = Anchor.Center,
      x = 0.5,
      y = -0.15
    ),
    hovermode = HoverMode.Closest
  )

  // demo source end

}

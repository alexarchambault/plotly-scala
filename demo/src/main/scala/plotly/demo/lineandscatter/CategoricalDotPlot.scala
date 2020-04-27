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

  val trace1 = Scatter(votingPop, country)
    .withMode(ScatterMode(ScatterMode.Markers))
    .withName("Percent of estimated voting age population")
    .withMarker(
      Marker()
        .withColor(Color.RGBA(156, 165, 196, 0.95))
        .withLine(
          Line()
            .withColor(Color.RGBA(156, 165, 196, 1.0))
            .withWidth(1.0)
        )
        .withSymbol(Symbol.Circle())
        .withSize(16)
    )

  val trace2 = Scatter(regVoters, country)
    .withMode(ScatterMode(ScatterMode.Markers))
    .withName("Percent of estimated registered voters")
    .withMarker(
      Marker()
        .withColor(Color.RGBA(204, 204, 204, 0.95))
        .withLine(
          Line()
            .withColor(Color.RGBA(217, 217, 217, 1.0))
            .withWidth(1.0)
        )
        .withSymbol(Symbol.Circle())
        .withSize(16)
    )

  val data = Seq(trace1, trace2)

  val layout = Layout()
    .withTitle("Votes cast for ten lowest voting age population in OECD countries")
    .withXaxis(
      Axis()
        .withShowgrid(false)
        .withShowline(true)
        .withLinecolor(Color.RGB(102, 102, 102))
        .withTitlefont(
          Font(Color.RGB(204, 204, 204))
        )
        .withTickfont(
          Font(Color.RGB(102, 102, 102))
        )
        .withAutotick(false)
        .withDtick(10.0)
        .withTicks(Ticks.Outside)
        .withTickcolor(Color.RGB(102, 102, 102))
    )
    .withMargin(
      Margin(
        l = 140,
        r = 40,
        b = 50,
        t = 80
      )
    )
    .withLegend(
      Legend()
        .withFont(Font(size = 10))
        .withYanchor(Anchor.Middle)
        .withXanchor(Anchor.Right)
    )
    .withWidth(600)
    .withHeight(400)
    .withPaper_bgcolor(Color.RGB(254, 247, 234))
    .withPlot_bgcolor(Color.RGB(254, 247, 234))
    .withHovermode(HoverMode.Closest)

  // demo source end

}

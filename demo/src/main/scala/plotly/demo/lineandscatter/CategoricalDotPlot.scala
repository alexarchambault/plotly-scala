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
    .copy(
      mode = Some(ScatterMode(ScatterMode.Markers)),
      name = Some("Percent of estimated voting age population"),
      marker = Some(
        Marker().copy(
          color = Some(Color.RGBA(156, 165, 196, 0.95)),
          line = Some(
            Line().copy(color = Some(Color.RGBA(156, 165, 196, 1.0)), width = Some(1.0))
          ),
          symbol = Some(Symbol.Circle()),
          size = Some(16)
        )
      )
    )

  val trace2 = Scatter(regVoters, country)
    .copy(
      mode = Some(ScatterMode(ScatterMode.Markers)),
      name = Some("Percent of estimated registered voters"),
      marker = Some(
        Marker().copy(
          color = Some(Color.RGBA(204, 204, 204, 0.95)),
          line = Some(
            Line().copy(color = Some(Color.RGBA(217, 217, 217, 1.0)), width = Some(1.0))
          ),
          symbol = Some(Symbol.Circle()),
          size = Some(16)
        )
      )
    )

  val data = Seq(trace1, trace2)

  val layout = Layout().copy(
    title = Some("Votes cast for ten lowest voting age population in OECD countries"),
    xaxis = Some(
      Axis().copy(
        showgrid = Some(false),
        showline = Some(true),
        linecolor = Some(Color.RGB(102, 102, 102)),
        titlefont = Some(Font(Color.RGB(204, 204, 204))),
        tickfont = Some(Font(Color.RGB(102, 102, 102))),
        autotick = Some(false),
        dtick = Some(10.0),
        ticks = Some(Ticks.Outside),
        tickcolor = Some(Color.RGB(102, 102, 102))
      )
    ),
    margin = Some(
      Margin(
        l = 140,
        r = 40,
        b = 50,
        t = 80
      )
    ),
    legend = Some(
      Legend().copy(font = Some(Font(size = 10)), yanchor = Some(Anchor.Middle), xanchor = Some(Anchor.Right))
    ),
    width = Some(600),
    height = Some(400),
    paper_bgcolor = Some(Color.RGB(254, 247, 234)),
    plot_bgcolor = Some(Color.RGB(254, 247, 234)),
    hovermode = Some(HoverMode.Closest)
  )

  // demo source end

}

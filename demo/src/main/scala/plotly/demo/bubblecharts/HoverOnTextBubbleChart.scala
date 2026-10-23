package plotly.demo.bubblecharts

import plotly.Scatter
import plotly.demo.DemoChart
import plotly.element.{Color, Marker, ScatterMode}
import plotly.layout.Layout

object HoverOnTextBubbleChart extends DemoChart {

  def plotlyDocUrl = "https://plot.ly/javascript/bubble-charts/#hover-text-on-bubble-charts"
  def id           = "hover-on-text-bubble-chart"
  def source       = HoverOnTextBubbleChartSource.source

  // demo source start

  val trace1 = Scatter(Seq(1, 2, 3, 4), Seq(10, 11, 12, 13))
    .copy(
      text = Some(
        Seq(
          """A
    size = 40""",
          """B
    size = 60""",
          """C
    size = 80""",
          """D
    size = 100"""
        )
      ),
      mode = Some(ScatterMode(ScatterMode.Markers)),
      marker = Some(
        Marker().copy(
          color = Some(
            Seq(Color.RGB(93, 164, 214), Color.RGB(255, 144, 14), Color.RGB(44, 160, 101), Color.RGB(255, 65, 54))
          ),
          size = Some(Seq(40, 60, 80, 100))
        )
      )
    )

  val data = Seq(trace1)

  val layout = Layout().copy(
    title = Some("Bubble Chart Hover Text"),
    showlegend = Some(false),
    height = Some(400),
    width = Some(600)
  )

  // demo source end

}

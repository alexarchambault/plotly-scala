package plotly.demo.bubblecharts

import plotly.Scatter
import plotly.demo.DemoChart
import plotly.element.{Color, Marker, ScatterMode}
import plotly.layout.Layout

object HoverOnTextBubbleChart extends DemoChart {

  def plotlyDocUrl = "https://plot.ly/javascript/bubble-charts/#hover-text-on-bubble-charts"
  def id = "hover-on-text-bubble-chart"
  def source = HoverOnTextBubbleChartSource.source

  // demo source start
  
  val trace1 = Scatter(
    Seq(1, 2, 3, 4),
    Seq(10, 11, 12, 13),
    text = Seq("""A
    size = 40""", """B
    size = 60""", """C
    size = 80""", """D
    size = 100"""),
    mode = ScatterMode(ScatterMode.Markers),
    marker = Marker(
      color = Seq(Color.RGB(93, 164, 214), Color.RGB(255, 144, 14), Color.RGB(44, 160, 101), Color.RGB(255, 65, 54)),
      size = Seq(40, 60, 80, 100)
    )
  )

  val data = Seq(trace1)

  val layout = Layout(
    title = "Bubble Chart Hover Text",
    showlegend = false,
    height = 400,
    width = 600
  )
  
  // demo source end

}

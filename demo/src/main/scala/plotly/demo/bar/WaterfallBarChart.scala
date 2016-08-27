package plotly.demo.bar

import plotly.Bar
import plotly.demo.DemoChart
import plotly.element.{Color, Line, Marker}
import plotly.layout.{Annotation, BarMode, Font, Layout}

object WaterfallBarChart extends DemoChart {

  def plotlyDocUrl = "https://plot.ly/javascript/bar-charts/#waterfall-bar-chart"
  def id = "waterfall-bar-chart"
  def source = WaterfallBarChartSource.source

  // demo source start

  val xData = Seq(
    "Product Revenue",
    "Services Revenue",
    "Total Revenue",
    "Fixed Costs",
    "Variable Costs",
    "Total Costs",
    "Total"
  )

  val yData = Seq(400, 660, 660, 590, 400, 400, 340)

  val textList = Seq("$430K", "$260K", "$690K", "$-120K", "$-200K", "$-320K", "$370K")

  //Base

  val trace1 = Bar(
    x = xData,
    y = Seq(0, 430, 0, 570, 370, 370, 0),
    marker = Marker(
      color = Color.RGBA(1, 1, 1, 0.0)
    )
  )

  //Revenue

  val trace2 = Bar(
    xData,
    Seq(430, 260, 690, 0, 0, 0, 0),
    marker = Marker(
      color = Color.RGBA(55, 128, 191, 0.7),
      line = Line(
        color = Color.RGBA(55, 128, 191, 1.0),
        width = 2.0
      )
    )
  )

  //Cost

  val trace3 = Bar(
    xData,
    Seq(0, 0, 0, 120, 200, 320, 0),
    marker = Marker(
      color = Color.RGBA(219, 64, 82, 0.7),
      line = Line(
        color = Color.RGBA(219, 64, 82, 1.0),
        width = 2.0
      )
    )
  )

  //Profit

  val trace4 = Bar(
    xData,
    Seq(0, 0, 0, 0, 0, 0, 370),
    marker = Marker(
      color = Color.RGBA(50,171, 96, 0.7),
      line = Line(
        color = Color.RGBA(50, 171, 96, 1.0),
        width = 2.0
      )
    )
  )

  val data = Seq(trace1, trace2, trace3, trace4)

  val annotations = xData.zip(yData).zip(textList).map {
    case ((x, y), text) =>
      Annotation(
        x = x,
        y = y,
        text = text,
        font = Font(
          family = "Arial",
          size = 14,
          color = Color.RGBA(245, 246, 249, 1)
        ),
        showarrow = false
      )
  }

  val layout = Layout(
    title = "Annual Profit 2015",
    barmode = BarMode.Stack,
    paper_bgcolor = Color.RGBA(245, 246, 249, 1),
    plot_bgcolor = Color.RGBA(245, 246, 249, 1),
    width = 600,
    height = 400,
    showlegend = false,
    annotations = annotations
  )

  // demo source end

}

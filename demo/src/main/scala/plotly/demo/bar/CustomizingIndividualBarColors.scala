package plotly.demo.bar

import plotly._
import plotly.demo.DemoChart
import plotly.element._
import plotly.layout._

object CustomizingIndividualBarColors extends DemoChart {

  def plotlyDocUrl = "https://plotly.com/javascript/bar-charts/#customizing-individual-bar-colors"
  def id           = "customizing-individual-bar-colors"
  def source       = CustomizingIndividualBarColorsSource.source

  // demo source start

  val features = Seq("Search", "Export", "Dark mode", "Sharing", "Comments", "Templates")

  // share of weekly active users using each feature (fictional)
  val usage = Seq(78, 52, 64, 23, 41, 57)

  val defaultColor   = Color.RGBA(180, 185, 205, 0.8)
  val highlightColor = Color.RGBA(239, 85, 59, 0.9)

  // highlight the least used feature
  val colors = usage.map(u => if (u == usage.min) highlightColor else defaultColor)

  val trace1 = Bar(features, usage)
    .withValues(marker = Marker().withValues(color = colors))

  val data = Seq(trace1)

  val leastUsed = features(usage.indexOf(usage.min))

  val layout = Layout().withValues(
    title = s"Least Used Feature: $leastUsed",
    yaxis = Axis().withValues(title = "Weekly active users", ticksuffix = "%")
  )

  // demo source end

}

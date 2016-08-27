package plotly.demo.bar

import plotly._
import plotly.demo.DemoChart
import plotly.element._
import plotly.layout._

object CustomizingIndividualBarColors extends DemoChart {

  def plotlyDocUrl = ""
  def id = "customizing-individual-bar-colors"
  def source = CustomizingIndividualBarColorsSource.source

  // demo source start

  val defaultColor = Color.RGBA(204,204,204,1)
  val highlightColor = Color.RGBA(222,45,38,0.8)

  val trace1 = Bar(
    Seq("Feature A", "Feature B", "Feature C", "Feature D", "Feature E"),
    Seq(20, 14, 23, 25, 22),
    marker = Marker(
      color = Seq(
        defaultColor, highlightColor, defaultColor, defaultColor, defaultColor
      )
    )
  )

  val data = Seq(trace1)

  val layout = Layout(
    title = "Least Used Feature"
  )

  // demo source end

}

package plotly.demo.box

import plotly._
import plotly.demo.DemoChart
import plotly.element._
import plotly.layout._

object NotchedBoxPlot extends DemoChart {

  def plotlyDocUrl = "https://plotly.com/javascript/box-plots/"
  def id           = this.getClass.getSimpleName.dropRight(1)
  def source       = NotchedBoxPlotSource.source

  // demo source start

  // simulated test scores of three classes: the notches show the confidence intervals of their
  // medians, that don't overlap when the medians differ significantly
  val random  = new scala.util.Random(12) // fixed seed, so that the plot is the same every time
  val classes = Seq(
    "Class A" -> Seq.fill(60)(68 + 9 * random.nextGaussian()),
    "Class B" -> Seq.fill(60)(72 + 8 * random.nextGaussian()),
    "Class C" -> Seq.fill(60)(80 + 7 * random.nextGaussian())
  )

  val data = classes.map { case (name, scores) =>
    Box(scores).updated(
      name = name,
      notched = true,
      boxmean = BoxMean.True,
      boxpoints = BoxPoints.All,
      jitter = 0.4,
      pointpos = -1.8,
      marker = Marker().updated(size = 4)
    )
  }

  val layout = Layout().updated(
    title = "Test Scores",
    yaxis = Axis().updated(title = "score"),
    showlegend = false
  )

  // demo source end

}

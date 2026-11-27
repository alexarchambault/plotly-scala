package plotly.demo.violin

import plotly._
import plotly.demo.DemoChart
import plotly.element._
import plotly.layout._

object SplitViolinPlot extends DemoChart {

  def plotlyDocUrl = "https://plotly.com/javascript/violin/#split-violin-plot"
  def id           = this.getClass.getSimpleName.dropRight(1)
  def source       = SplitViolinPlotSource.source

  // demo source start

  val days = Seq("Thu", "Fri", "Sat", "Sun")

  // simulated tips of lunch and dinner bills, by day
  val random             = new scala.util.Random(11) // fixed seed, so that the plot is the same every time
  def tips(mean: Double) = Seq.fill(80)(math.max(1.0, mean + 1.2 * random.nextGaussian()))

  def sample(means: Seq[Double]) = {
    val pairs = days.zip(means).flatMap { case (day, mean) => tips(mean).map(day -> _) }
    (pairs.map(_._1), pairs.map(_._2))
  }

  // the lunch and dinner violins of each day share an axis, on their own side of it
  val data = Seq(
    ("Lunch", ViolinSide.Negative, Color.RGB(99, 110, 250), Seq(2.6, 2.8, 3.0, 3.2)),
    ("Dinner", ViolinSide.Positive, Color.RGB(239, 85, 59), Seq(3.0, 3.1, 3.6, 3.8))
  ).map { case (meal, side, color, means) =>
    val (x, y) = sample(means)
    Violin(y, x).withValues(
      name = meal,
      legendgroup = meal,
      scalegroup = meal,
      side = side,
      width = 0.9,
      line = Line().withValues(color = color),
      meanline = MeanLine().withValues(visible = true),
      points = BoxPoints.False
    )
  }

  val layout = Layout().withValues(
    title = "Tips by Day and Meal",
    yaxis = Axis().withValues(title = "tip ($)", zeroline = false),
    violinmode = BoxMode.Overlay
  )

  // demo source end

}

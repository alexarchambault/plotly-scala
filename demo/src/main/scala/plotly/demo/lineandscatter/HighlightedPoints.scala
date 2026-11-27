package plotly.demo.lineandscatter

import plotly._
import plotly.demo.DemoChart
import plotly.element._
import plotly.layout._

object HighlightedPoints extends DemoChart {

  def plotlyDocUrl = "https://plotly.com/javascript/reference/scatter/#scatter-selectedpoints"
  def id           = this.getClass.getSimpleName.dropRight(1)
  def source       = HighlightedPointsSource.source

  // demo source start

  // simulated weights and heights of 200 people
  val random  = new scala.util.Random(8) // fixed seed, so that the plot is the same every time
  val heights = Seq.fill(200)(170 + 9 * random.nextGaussian())
  val weights = heights.map(h => 0.9 * h - 85 + 8 * random.nextGaussian())

  // the people whose BMI is above 27 are selected, and highlighted
  val bmis     = heights.zip(weights).map { case (h, w) => w / math.pow(h / 100, 2) }
  val selected = bmis.zipWithIndex.collect { case (bmi, idx) if bmi > 27 => idx }

  val data = Seq(
    Scatter(heights, weights).updated(
      mode = ScatterMode(ScatterMode.Markers),
      marker = Marker().updated(size = 8, color = Color.RGB(99, 110, 250)),
      selectedpoints = selected,
      selected = Selection().updated(
        marker = SelectionMarker().updated(color = Color.RGB(239, 85, 59), size = 11.0)
      ),
      unselected = Selection().updated(marker = SelectionMarker().updated(opacity = 0.3)),
      hovertemplate = "%{x:.0f} cm, %{y:.0f} kg<extra></extra>"
    )
  )

  val layout = Layout().updated(
    title = "People With a BMI Above 27",
    xaxis = Axis().updated(title = "height (cm)"),
    yaxis = Axis().updated(title = "weight (kg)")
  )

  // demo source end

}

package plotly.demo.heatmaps

import plotly._
import plotly.demo.DemoChart
import plotly.element._
import plotly.layout._

object AnnotatedHeatmap extends DemoChart {

  def plotlyDocUrl = "https://plot.ly/javascript/heatmaps/#annotated-heatmap"
  def id           = "annotated-heatmap"
  def source       = AnnotatedHeatmapSource.source

  // demo source start

  val classes = Seq("Cat", "Dog", "Bird", "Fish")

  // confusion matrix of a (fictional) image classifier:
  // one row per actual class, one column per predicted class
  val counts = Seq(
    Seq(88, 9, 2, 1),
    Seq(11, 83, 4, 2),
    Seq(3, 5, 86, 6),
    Seq(1, 2, 7, 90)
  )

  val accuracy = 100.0 * classes.indices.map(i => counts(i)(i)).sum / counts.flatten.sum

  val data = Seq(
    Heatmap(counts.reverse, classes, classes.reverse) // rows are drawn from the bottom up
      .updated(
        showscale = false,
        colorscale = ColorScale.CustomScale(
          Seq(
            (0, Color.StringColor("#f0f2f9")),
            (1, Color.StringColor("#3d47c9"))
          )
        )
      )
  )

  val layout = Layout().updated(
    title = f"Classifier Confusion Matrix (accuracy: $accuracy%.1f%%)",
    xaxis = Axis().updated(title = "Predicted", ticks = Ticks.Empty),
    yaxis = Axis().updated(title = "Actual", ticks = Ticks.Empty, ticksuffix = " "),
    annotations =
      for {
        (actual, row)       <- classes.zipWithIndex
        (predicted, column) <- classes.zipWithIndex
        count = counts(row)(column)
      } yield Annotation().updated(
        x = predicted,
        y = actual,
        xref = Ref.Axis(AxisReference.X1),
        yref = Ref.Axis(AxisReference.Y1),
        showarrow = false,
        text = count.toString,
        // light text on dark cells, dark text on light ones
        font = Font(Color.StringColor(if (count > 50) "white" else "#15172e"))
      )
  )

  // demo source end

}

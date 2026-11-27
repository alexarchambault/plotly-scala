package plotly.demo.heatmaps

import plotly._
import plotly.demo.DemoChart
import plotly.element._
import plotly.layout._

object CategoricalAxisHeatmap extends DemoChart {

  def plotlyDocUrl = "https://plot.ly/javascript/heatmaps/#heatmap-with-categorical-axis-labels"
  def id           = "categorical-axis-heatmap"
  def source       = CategoricalAxisHeatmapSource.source

  // demo source start

  val days  = Seq("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday")
  val hours = (0 until 24).map(h => f"$h%02d:00")

  def bump(hour: Int, peak: Double, width: Double) =
    math.exp(-math.pow(hour - peak, 2) / (2 * width * width))

  // simulated website visits: office hours during the week, evenings on weekends
  val visits = {
    // fixed seed, so that the plot is the same every time
    val random = new scala.util.Random(3)
    days.indices.map { day =>
      hours.indices.map { hour =>
        val weekend  = day >= 5
        val expected =
          if (weekend) 30 + 220 * bump(hour, 20, 3)
          else 30 + 320 * bump(hour, 11, 3) + 140 * bump(hour, 21, 2)
        (expected * (0.9 + 0.2 * random.nextDouble())).toInt
      }
    }
  }

  val data = Seq(
    Heatmap().updated(
      z = visits.reverse, // rows are drawn from the bottom up
      x = hours,
      y = days.reverse,
      colorscale = ColorScale.NamedScale("YlGnBu")
    )
  )

  val layout = Layout().updated(
    title = "Website Visits per Hour (simulated)",
    xaxis = Axis().updated(dtick = 3.0),
    yaxis = Axis().updated(automargin = true)
  )

  // demo source end

}

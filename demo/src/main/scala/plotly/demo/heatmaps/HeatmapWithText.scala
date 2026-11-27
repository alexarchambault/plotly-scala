package plotly.demo.heatmaps

import plotly._
import plotly.demo.DemoChart
import plotly.element._
import plotly.layout._

object HeatmapWithText extends DemoChart {

  def plotlyDocUrl = "https://plotly.com/javascript/heatmaps/#heatmap-with-text"
  def id           = this.getClass.getSimpleName.dropRight(1)
  def source       = HeatmapWithTextSource.source

  // demo source start

  val days  = Seq("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")
  val hours = Seq("8h", "10h", "12h", "14h", "16h", "18h", "20h")

  // average number of visitors of a museum, by day and hour
  val visitors = hours.indices.map { h =>
    days.indices.map { d =>
      // busier towards the weekend
      val dayFactor = if (d >= 5) 1.8 else 0.7 + 0.12 * d
      val peak      = math.exp(-math.pow(h - 3.2, 2) / 4)
      math.round(dayFactor * (20 + 110 * peak) * (if (d == 0) 0.0 else 1.0)).toDouble
    }
  }

  // the values are written in their cells, rather than added as annotations
  val data = Seq(
    Heatmap(visitors, days, hours).withValues(
      colorscale = ColorScale.NamedScale("YlGnBu"),
      reversescale = true,
      texttemplate = "%{z:.0f}",
      textfont = Font(11),
      hovertemplate = "%{x} %{y}: %{z:.0f} visitors<extra></extra>",
      xgap = 2.0,
      ygap = 2.0
    )
  )

  val layout = Layout().withValues(
    title = "Museum Visitors (Closed on Mondays)",
    yaxis = Axis().withValues(autorange = true)
  )

  // demo source end

}

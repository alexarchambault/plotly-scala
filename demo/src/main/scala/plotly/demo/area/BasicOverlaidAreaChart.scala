package plotly.demo.area

import plotly._
import plotly.demo.DemoChart
import plotly.element._
import plotly.layout._

object BasicOverlaidAreaChart extends DemoChart {

  def plotlyDocUrl = "https://plot.ly/javascript/filled-area-plots/#basic-overlaid-area-chart"
  def id           = "basic-overlaid-area-chart"
  def source       = BasicOverlaidAreaChartSource.source

  // demo source start

  // hours of daylight on a given day of the year, at a given latitude
  def daylight(latitude: Double)(day: Int): Double = {
    val declination  = 23.44 * math.sin(2 * math.Pi * (284 + day) / 365)
    val cosHourAngle = -math.tan(math.toRadians(latitude)) * math.tan(math.toRadians(declination))
    24 / math.Pi * math.acos(math.max(-1.0, math.min(1.0, cosHourAngle)))
  }

  val days = 1 to 365

  val cities = Seq(
    ("Oslo", 59.9, (99, 110, 250)),
    ("Madrid", 40.4, (255, 161, 90)),
    ("Singapore", 1.35, (0, 204, 150))
  )

  val data = cities.map { case (city, latitude, (r, g, b)) =>
    Scatter(days, days.map(daylight(latitude)))
      .updated(
        name = city,
        fill = Fill.ToZeroY,
        // light fills, so that overlapping areas remain readable
        fillcolor = Color.RGBA(r, g, b, 0.15),
        line = Line().updated(color = Color.RGB(r, g, b), width = 2.5)
      )
  }

  val layout = Layout().updated(
    title = "Hours of Daylight Over the Year",
    xaxis = Axis().updated(title = "Day of the year"),
    yaxis = Axis().updated(title = "Hours", range = (0.0, 24.0), dtick = 6.0)
  )

  // demo source end

}

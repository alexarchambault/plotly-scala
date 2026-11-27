package plotly.demo.polar

import plotly._
import plotly.demo.DemoChart
import plotly.element._
import plotly.layout._

object WindRose extends DemoChart {

  def plotlyDocUrl = "https://plotly.com/javascript/wind-rose-charts/"
  def id           = this.getClass.getSimpleName.dropRight(1)
  def source       = WindRoseSource.source

  // demo source start

  val directions = Seq("N", "NE", "E", "SE", "S", "SW", "W", "NW")

  // share of the time the wind blows from each direction, in %, by wind speed
  val speeds = Seq(
    ("< 10 km/h", Seq(4.0, 3.0, 2.5, 2.0, 3.0, 5.0, 6.0, 4.5), Color.RGB(158, 202, 225)),
    ("10-25 km/h", Seq(3.0, 2.0, 1.5, 1.0, 2.5, 6.0, 8.0, 4.0), Color.RGB(66, 146, 198)),
    ("> 25 km/h", Seq(1.0, 0.5, 0.5, 0.3, 1.0, 3.5, 5.0, 2.0), Color.RGB(8, 69, 148))
  )

  val data = speeds.map { case (name, shares, color) =>
    BarPolar(shares, directions).updated(
      name = name,
      marker = Marker().updated(color = color, line = Line().updated(color = Color.StringColor("white"), width = 1.0))
    )
  }

  val layout = Layout().updated(
    title = "Wind Directions and Speeds",
    polar = Polar().updated(
      barmode = BarMode.Stack,
      bargap = 0.05,
      radialaxis = RadialAxis().updated(ticksuffix = "%", angle = 45.0),
      angularaxis = AngularAxis().updated(direction = RotationDirection.Clockwise)
    )
  )

  // demo source end

}

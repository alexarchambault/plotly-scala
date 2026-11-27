package plotly.demo.bar

import plotly._
import plotly.demo.DemoChart
import plotly.element._
import plotly.layout._

object RoundedBars extends DemoChart {

  def plotlyDocUrl = "https://plotly.com/javascript/bar-charts/#rounded-corners-on-bars"
  def id           = "rounded-bars"
  def source       = RoundedBarsSource.source

  // demo source start

  val countries = Seq("United States", "China", "Japan", "Australia", "France")

  // Paris 2024 Olympics medal table, top 5 nations
  val medals = Seq(
    ("Gold", "#d4a72c", Seq(40, 40, 20, 18, 16)),
    ("Silver", "#a7adba", Seq(44, 27, 12, 19, 26)),
    ("Bronze", "#c0793a", Seq(42, 24, 13, 16, 22))
  )

  val data = medals.map { case (name, color, counts) =>
    Bar(countries, counts)
      .updated(name = name, marker = Marker().updated(color = Color.StringColor(color)))
  }

  val layout = Layout().updated(
    title = "Paris 2024 Olympics Medals",
    yaxis = Axis().updated(title = "Medals"),
    bargap = 0.25,
    barcornerradius = 8
  )

  // demo source end

}

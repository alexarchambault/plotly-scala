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

  val countries = Seq("South Korea", "China", "Canada")

  val data = Seq(
    "Gold"   -> Seq(24, 10, 9),
    "Silver" -> Seq(13, 15, 12),
    "Bronze" -> Seq(11, 8, 12)
  ).map { case (name, medals) =>
    Bar(countries, medals).withName(name)
  }

  val layout = Layout()
    .withTitle("Grouped by Country")
    .withXaxis(Axis().withTitle("Country"))
    .withYaxis(Axis().withTitle("Medals"))
    .withBarcornerradius(15)

  // demo source end

}

package plotly.demo.funnel

import plotly._
import plotly.demo.DemoChart
import plotly.element._
import plotly.layout._

object StackedFunnel extends DemoChart {

  def plotlyDocUrl = "https://plotly.com/javascript/funnel-charts/#stacked-funnel"
  def id           = this.getClass.getSimpleName.dropRight(1)
  def source       = StackedFunnelSource.source

  // demo source start

  val stages = Seq("Visits", "Sign-ups", "Trials", "Purchases")

  // number of users reaching each stage of a sales funnel, by channel
  val data = Seq(
    "Search" -> Seq(1200.0, 720.0, 430.0, 190.0),
    "Social" -> Seq(900.0, 410.0, 180.0, 60.0),
    "Email"  -> Seq(400.0, 300.0, 210.0, 120.0)
  ).map { case (channel, users) =>
    Funnel(users, stages).updated(
      name = channel,
      orientation = Orientation.Horizontal,
      textinfo = TextInfo(TextInfo.Value, TextInfo.PercentInitial)
    )
  }

  val layout = Layout().updated(
    title = "Sales Funnel by Channel",
    funnelmode = BarMode.Stack,
    margin = Margin(100, 20, 90, 40)
  )

  // demo source end

}

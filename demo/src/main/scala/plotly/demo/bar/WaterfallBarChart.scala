package plotly.demo.bar

import plotly._
import plotly.demo.DemoChart
import plotly.element._
import plotly.layout._

object WaterfallBarChart extends DemoChart {

  def plotlyDocUrl = "https://plot.ly/javascript/bar-charts/#waterfall-bar-chart"
  def id           = "waterfall-bar-chart"
  def source       = WaterfallBarChartSource.source

  // demo source start

  // in thousands of dollars: revenue is positive, costs are negative
  val steps = Seq(
    "Products"       -> 430,
    "Services"       -> 260,
    "Fixed costs"    -> -120,
    "Variable costs" -> -200
  )

  // running total after each step, and the resulting profit
  val totals = steps.map(_._2).scanLeft(0)(_ + _)
  val profit = totals.last

  val revenueColor = Color.RGBA(0, 204, 150, 0.8)
  val costColor    = Color.RGBA(239, 85, 59, 0.8)
  val profitColor  = Color.RGBA(99, 110, 250, 0.9)

  def amount(value: Int) =
    (if (value < 0) "-$" else "+$") + math.abs(value) + "K"

  // each bar floats between the running totals before and after its step
  val trace1 = Bar(steps.map(_._1) :+ "Profit", steps.map(s => math.abs(s._2)) :+ profit)
    .withValues(
      base = totals.zip(totals.tail).map { case (before, after) => math.min(before, after).toDouble } :+ 0.0,
      text = steps.map(s => amount(s._2)) :+ s"$$${profit}K",
      textposition = BarTextPosition.Outside,
      marker = Marker().withValues(
        color = steps.map(s => if (s._2 >= 0) revenueColor else costColor) :+ profitColor
      )
    )

  val data = Seq(trace1)

  val layout = Layout().withValues(
    title = "Annual Profit 2025",
    yaxis = Axis().withValues(tickprefix = "$", ticksuffix = "K", range = (0.0, 800.0)),
    showlegend = false
  )

  // demo source end

}

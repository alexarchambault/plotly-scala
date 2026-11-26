package plotly.demo.timeseries

import plotly._
import plotly.demo.DemoChart
import plotly.element._
import plotly.layout._

object TimeSeries extends DemoChart {

  def plotlyDocUrl = "https://plot.ly/javascript/time-series/#date-strings"
  def id           = "time-series-chart"
  def source       = TimeSeriesSource.source

  // demo source start

  val daysInMonth = Seq(31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31)

  // "2025-01-01", "2025-01-02", …, "2025-12-31"
  val dates = for {
    (days, month) <- daysInMonth.zipWithIndex
    day           <- 1 to days
  } yield f"2025-${month + 1}%02d-$day%02d"

  // a random walk, simulating a stock price
  val prices = {
    // fixed seed, so that the plot is the same every time
    val random = new scala.util.Random(2025)
    dates.tail.scanLeft(100.0)((price, _) => price * (1 + 0.0008 + 0.015 * random.nextGaussian()))
  }

  val movingAverage = prices.indices.map { i =>
    val window = prices.slice(i - 29, i + 1)
    window.sum / window.length
  }

  val data = Seq(
    Scatter(dates, prices).withValues(
      name = "Daily close",
      line = Line().withValues(color = Color.StringColor("#636efa"), width = 1.5)
    ),
    Scatter(dates, movingAverage).withValues(
      name = "30-day average",
      line = Line().withValues(color = Color.StringColor("#ef553b"), width = 2.5)
    )
  )

  val layout = Layout().withValues(
    title = "Simulated Stock Price, 2025",
    xaxis = Axis().withValues(rangeslider = RangeSlider()),
    yaxis = Axis().withValues(tickprefix = "$")
  )

  // demo source end

}

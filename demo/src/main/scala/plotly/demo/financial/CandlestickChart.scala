package plotly.demo.financial

import plotly._
import plotly.demo.DemoChart
import plotly.element._
import plotly.layout._

object CandlestickChart extends DemoChart {

  def plotlyDocUrl = "https://plotly.com/javascript/candlestick-charts/"
  def id           = this.getClass.getSimpleName.dropRight(1)
  def source       = CandlestickChartSource.source

  // demo source start

  // simulated daily prices of a stock over four weeks of trading days, from Monday, March 3rd, 2025
  val days = for {
    week <- 0 until 4
    day  <- 0 until 5
  } yield LocalDateTime(2025, 3, 3 + 7 * week + day, 0, 0, 0)

  val random = new scala.util.Random(9) // fixed seed, so that the plot is the same every time
  val prices = days
    .scanLeft((0.0, 50.0)) { case ((_, previousClose), _) =>
      val open  = previousClose * (1 + 0.004 * random.nextGaussian())
      val close = open * (1 + 0.025 * random.nextGaussian())
      (open, close)
    }
    .tail
  val highs = prices.map { case (open, close) => math.max(open, close) * (1 + 0.012 * random.nextDouble()) }
  val lows  = prices.map { case (open, close) => math.min(open, close) * (1 - 0.012 * random.nextDouble()) }

  val data = Seq(
    Candlestick().updated(
      x = days,
      open = prices.map(_._1),
      high = highs,
      low = lows,
      close = prices.map(_._2),
      name = "ACME",
      // the width of the whiskers, relative to the width of the boxes
      whiskerwidth = 0.4
    )
  )

  val layout = Layout().updated(
    title = "ACME Stock Price",
    yaxis = Axis().updated(tickprefix = "$")
  )

  // demo source end

}

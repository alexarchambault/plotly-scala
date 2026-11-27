package plotly.demo.financial

import plotly._
import plotly.demo.DemoChart
import plotly.element._
import plotly.layout._

object OhlcChart extends DemoChart {

  def plotlyDocUrl = "https://plotly.com/javascript/ohlc-charts/"
  def id           = this.getClass.getSimpleName.dropRight(1)
  def source       = OhlcChartSource.source

  // demo source start

  // simulated daily prices of a stock over six weeks of trading days, from Monday, March 3rd, 2025
  val days = for {
    week <- 0 until 6
    day  <- 0 until 5
    dayOfMarch = 3 + 7 * week + day
  } yield
    if (dayOfMarch <= 31) LocalDateTime(2025, 3, dayOfMarch, 0, 0, 0)
    else LocalDateTime(2025, 4, dayOfMarch - 31, 0, 0, 0)

  val random = new scala.util.Random(5) // fixed seed, so that the plot is the same every time
  val prices = days
    .scanLeft((0.0, 100.0)) { case ((_, previousClose), _) =>
      val open  = previousClose * (1 + 0.005 * random.nextGaussian())
      val close = open * (1 + 0.02 * random.nextGaussian())
      (open, close)
    }
    .tail
  val highs = prices.map { case (open, close) => math.max(open, close) * (1 + 0.01 * random.nextDouble()) }
  val lows  = prices.map { case (open, close) => math.min(open, close) * (1 - 0.01 * random.nextDouble()) }

  val data = Seq(
    Ohlc(days, prices.map(_._1), highs, lows, prices.map(_._2)).withValues(
      name = "ACME",
      increasing = DirectionStyle().withValues(line = Line().withValues(color = Color.RGB(0, 204, 150))),
      decreasing = DirectionStyle().withValues(line = Line().withValues(color = Color.RGB(239, 85, 59))),
      line = Line().withValues(width = 2.0)
    )
  )

  val layout = Layout().withValues(
    title = "ACME Stock Price",
    yaxis = Axis().withValues(tickprefix = "$")
  )

  // demo source end

}

package plotly.demo.timeseries

import plotly.Scatter
import plotly.demo.NoLayoutDemoChart

object TimeSeries extends NoLayoutDemoChart {

  def plotlyDocUrl = "https://plot.ly/javascript/time-series/#date-strings"
  def id           = "time-series-chart"
  def source       = TimeSeriesSource.source

  // demo source start

  val data = Seq(
    Scatter(
      Seq("2013-10-04 22:23:00", "2013-11-04 22:23:00", "2013-12-04 22:23:00"),
      Seq(1, 3, 6)
    )
  )

  // demo source end

}

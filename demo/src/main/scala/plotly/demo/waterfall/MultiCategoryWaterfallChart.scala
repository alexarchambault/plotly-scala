package plotly.demo.waterfall

import plotly._
import plotly.demo.DemoChart
import plotly.element._
import plotly.layout._

object MultiCategoryWaterfallChart extends DemoChart {

  def plotlyDocUrl = "https://plotly.com/javascript/waterfall-charts/#multi-category-waterfall-chart"
  def id           = this.getClass.getSimpleName.dropRight(1)
  def source       = MultiCategoryWaterfallChartSource.source

  // demo source start

  // quarterly changes of a balance, grouped by year: the x values have two levels
  val years    = Seq("2024", "2025", "2025", "2025", "2025", "2026", "2026", "2026", "2026")
  val quarters = Seq("initial", "Q1", "Q2", "Q3", "total", "Q1", "Q2", "Q3", "total")
  val measures = quarters.map {
    case "initial" => WaterfallMeasure.Absolute
    case "total"   => WaterfallMeasure.Total
    case _         => WaterfallMeasure.Relative
  }

  val data = Seq(
    ("Account A", Seq(10.0, 2.0, 3.0, -1.0, 0.0, 1.0, 2.0, -4.0, 0.0)),
    ("Account B", Seq(8.0, 3.0, -2.0, 2.0, 0.0, -1.0, 4.0, 1.0, 0.0))
  ).map { case (name, values) =>
    Waterfall(Seq(years, quarters), values, measures).updated(name = name)
  }

  val layout = Layout().updated(
    title = "Balances by Quarter",
    waterfallgroupgap = 0.4,
    xaxis = Axis().updated(`type` = AxisType.MultiCategory)
  )

  // demo source end

}

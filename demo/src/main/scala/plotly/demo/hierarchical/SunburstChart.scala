package plotly.demo.hierarchical

import plotly._
import plotly.demo.DemoChart
import plotly.element._
import plotly.layout._

object SunburstChart extends DemoChart {

  def plotlyDocUrl = "https://plotly.com/javascript/sunburst-charts/#basic-sunburst-chart"
  def id           = this.getClass.getSimpleName.dropRight(1)
  def source       = SunburstChartSource.source

  // demo source start

  // the yearly budget of a household, in dollars, by category: (label, parent, amount)
  val budget = Seq(
    ("Budget", "", 0.0),
    ("Housing", "Budget", 0.0),
    ("Rent", "Housing", 18000.0),
    ("Utilities", "Housing", 2400.0),
    ("Food", "Budget", 0.0),
    ("Groceries", "Food", 7200.0),
    ("Restaurants", "Food", 2600.0),
    ("Transport", "Budget", 0.0),
    ("Car", "Transport", 4200.0),
    ("Public transit", "Transport", 900.0),
    ("Leisure", "Budget", 0.0),
    ("Travel", "Leisure", 3500.0),
    ("Hobbies", "Leisure", 1500.0)
  )

  // the budget and its categories have no amount of their own: they're the sum of their children
  val data = Seq(
    Sunburst(budget.map(_._1), budget.map(_._2), budget.map(_._3)).updated(
      branchvalues = BranchValues.Remainder,
      textinfo = TextInfo(TextInfo.Label, TextInfo.PercentRoot),
      insidetextorientation = InsideTextOrientation.Radial,
      marker = Marker().updated(line = Line().updated(color = Color.StringColor("white"), width = 2.0))
    )
  )

  val layout = Layout().updated(title = "Household Budget", margin = Margin(10, 10, 60, 10))

  // demo source end

}

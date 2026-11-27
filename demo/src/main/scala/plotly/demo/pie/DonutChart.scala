package plotly.demo.pie

import plotly._
import plotly.demo.DemoChart
import plotly.element._
import plotly.layout._

object DonutChart extends DemoChart {

  def plotlyDocUrl = "https://plotly.com/javascript/pie-charts/#donut-chart"
  def id           = this.getClass.getSimpleName.dropRight(1)
  def source       = DonutChartSource.source

  // demo source start

  val platforms = Seq("Desktop", "Mobile", "Tablet")

  // share of the visits of a website, by platform, in %, two years apart
  val years = Seq(
    "2015" -> Seq(62.0, 30.0, 8.0),
    "2025" -> Seq(35.0, 59.0, 6.0)
  )

  // one donut per year, side by side, with the year in its hole
  val data = years.zipWithIndex.map { case ((year, shares), idx) =>
    Pie(shares, platforms).withValues(
      name = year,
      hole = 0.5,
      textinfo = TextInfo(TextInfo.Percent),
      hoverinfo = HoverInfo(HoverInfo.Label, HoverInfo.Percent, HoverInfo.Name),
      domain = Domain().withValues(column = idx)
    )
  }

  val layout = Layout().withValues(
    title = "Website Visits by Platform",
    grid = Grid().withValues(rows = 1, columns = 2),
    annotations = years.zipWithIndex.map { case ((year, _), idx) =>
      Annotation().withValues(
        text = year,
        x = 0.225 + idx * 0.55,
        y = 0.5,
        xref = Ref.Paper,
        yref = Ref.Paper,
        showarrow = false,
        font = Font(20)
      )
    }
  )

  // demo source end

}

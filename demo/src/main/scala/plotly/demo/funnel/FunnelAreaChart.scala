package plotly.demo.funnel

import plotly._
import plotly.demo.DemoChart
import plotly.element._
import plotly.layout._

object FunnelAreaChart extends DemoChart {

  def plotlyDocUrl = "https://plotly.com/javascript/funnel-charts/#funnelarea-plot"
  def id           = this.getClass.getSimpleName.dropRight(1)
  def source       = FunnelAreaChartSource.source

  // demo source start

  // candidates at each stage of a hiring process, for two teams
  val stages = Seq("Applied", "Screened", "Interviewed", "Offered", "Hired")

  val data = Seq(
    ("Engineering", Seq(500.0, 210.0, 90.0, 24.0, 18.0), (0.0, 0.48)),
    ("Design", Seq(260.0, 120.0, 45.0, 12.0, 10.0), (0.52, 1.0))
  ).map { case (team, candidates, domainX) =>
    FunnelArea(candidates, stages).withValues(
      title = Title.from(text = team, position = TitlePosition.TopCenter),
      textinfo = TextInfo(TextInfo.Value, TextInfo.Label),
      // the areas of the two funnels are proportional to their number of candidates
      scalegroup = "teams",
      domain = Domain().withValues(x = domainX),
      marker = Marker().withValues(line = Line().withValues(color = Color.StringColor("white"), width = 2.0))
    )
  }

  val layout = Layout().withValues(title = "Hiring Pipelines", showlegend = false)

  // demo source end

}

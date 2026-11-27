package plotly.demo.polar

import plotly._
import plotly.demo.DemoChart
import plotly.element._
import plotly.layout._

object RadarChart extends DemoChart {

  def plotlyDocUrl = "https://plotly.com/javascript/radar-chart/"
  def id           = this.getClass.getSimpleName.dropRight(1)
  def source       = RadarChartSource.source

  // demo source start

  val skills = Seq("Scala", "Python", "SQL", "Statistics", "Visualization", "Communication")

  // self-assessed skill levels of two candidates, from 0 to 5
  val candidates = Seq(
    "Alice" -> Seq(5.0, 3.0, 4.0, 3.0, 4.0, 3.0),
    "Bob"   -> Seq(2.0, 5.0, 3.0, 5.0, 3.0, 4.0)
  )

  // the first point is repeated at the end, to close the shapes
  val data = candidates.map { case (name, levels) =>
    ScatterPolar(levels :+ levels.head, skills :+ skills.head).withValues(
      name = name,
      fill = Fill.ToSelf,
      opacity = 0.7
    )
  }

  val layout = Layout().withValues(
    title = "Skills",
    polar = Polar().withValues(
      gridshape = PolarGridShape.Linear,
      radialaxis = RadialAxis().withValues(visible = true, range = (0.0, 5.0), dtick = 1.0)
    )
  )

  // demo source end

}

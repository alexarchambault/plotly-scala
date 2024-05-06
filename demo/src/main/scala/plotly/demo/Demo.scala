package plotly.demo

import scala.scalajs.js.annotation.{JSExport, JSExportTopLevel}

import plotly.Plotly

import org.scalajs.dom

import scalatags.JsDom.all.{area => _, _}

@JSExportTopLevel("Demo") object Demo {

  val demos = Seq(
    "Line Charts" -> Seq(
      linecharts.BasicLinePlot,
      linecharts.LineAndScatterPlot
    ),
    "Scatter Plots" -> Seq(
      lineandscatter.CategoricalDotPlot
    ),
    "Bar Charts" -> Seq(
      bar.BasicBarChart,
      bar.GroupedBarChart,
      bar.BarChartWithDirectLabels,
      bar.CustomizingIndividualBarColors,
      bar.WaterfallBarChart
    ),
    "Horizontal Bar Charts" -> Seq(
      horizontalbarcharts.BasicHorizontalBarChart,
      horizontalbarcharts.ColoredBarChart
    ),
    "Time Series" -> Seq(
      timeseries.TimeSeries
    ),
    "Bubble Charts" -> Seq(
      bubblecharts.HoverOnTextBubbleChart
    ),
    "Filled Area Plots" -> Seq(
      area.BasicOverlaidAreaChart
    ),
    "Heatmaps" -> Seq(
      heatmaps.BasicHeatmap,
      heatmaps.CategoricalAxisHeatmap,
      heatmaps.CustomColorScaleHeatmap,
      heatmaps.AnnotatedHeatmap
    )
  )

  def unindent(source: String): String = {

    val lines         = source.linesIterator.toVector
    val nonEmptyLines = lines.filter(_.exists(!_.isSpaceChar))

    if (nonEmptyLines.isEmpty)
      source
    else {

      val dropCount = Stream
        .from(0)
        .takeWhile(idx => nonEmptyLines.forall(_(idx) == nonEmptyLines.head(idx)))
        .lastOption
        .fold(0)(_ + 1)

      if (dropCount == 0)
        source
      else
        lines
          .map(_.drop(dropCount))
          .mkString("\n")
    }
  }

  @JSExport def main(): Unit = {

    val mainDiv = dom.document.getElementById("demo")

    def chartTypeId(chartType: String) =
      chartType.toLowerCase.replace(' ', '-')

    val toc = div(
      style := "margin-bottom: 5em;",
      a(href := "https://github.com/alexarchambault/plotly-scala", h2("Sources")),
      h2("Examples"),
      div(
        style := "margin-left: 3em;",
        demos.map { case (chartType, chartDemos) =>
          val chartTypeId0 = chartTypeId(chartType)
          a(href := "#" + chartTypeId0, h3(chartType))
        }
      )
    )

    mainDiv.appendChild(toc.render)

    for ((chartType, chartDemos) <- demos) {
      Console.println(s"Rendering demos $chartType")

      val chartTypeId0 = chartTypeId(chartType)

      val chartTypeElem = h2(id := chartTypeId0, a(href := "#" + chartTypeId0, chartType))

      mainDiv.appendChild(chartTypeElem.render)

      for (demo <- chartDemos) {
        Console.println(s"Rendering demo ${demo.id}")

        val divId = s"demo-${demo.id}"

        val elem =
          div(
            id      := demo.id,
            `class` := "panel panel-default",
            div(`class` := "panel-heading", a(href := "#" + demo.id, h4(demo.id))),
            div(
              `class` := "panel-body",
              div(
                `class` := "example-code",
                pre(
                  code(
                    `class` := "language-scala",
                    s"""import plotly._
                       |import plotly.element._${if (demo.layout == null) "" else "\nimport plotly.layout._"}
                    """.stripMargin +
                      unindent(demo.source) +
                      s"""
                         |
                         |Plotly.plot("div-id", data${if (demo.layout == null) "" else ", layout"})""".stripMargin
                  )
                )
              ),
              div(id := divId, `class` := "plot-box"),
              p(a(href := demo.plotlyDocUrl, "Original plotly example"))
            )
          )

        mainDiv.appendChild(elem.render)

        if (demo.layout == null)
          Plotly.plot(divId, demo.data)
        else
          Plotly.plot(divId, demo.data, demo.layout)
      }
    }
  }
}

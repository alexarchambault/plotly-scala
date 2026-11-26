package plotly.demo

import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue
import scala.scalajs.js
import scala.scalajs.js.annotation.{JSExport, JSExportTopLevel}

import plotly.{Config, Plotly}
import plotly.layout.Layout

import org.scalajs.dom

import scalatags.JsDom.all.{area => _, _}
import scalatags.JsDom.tags2

@JSExportTopLevel("Demo") object Demo {

  final case class Category(name: String, icon: String, colors: (String, String), demos: Seq[DemoChart]) {
    def id: String = name.toLowerCase.replace(' ', '-')
  }

  val demos = Seq(
    Category(
      "Line Charts",
      Icons.line,
      ("#636efa", "#8c94ff"),
      Seq(
        linecharts.BasicLinePlot,
        linecharts.LineAndScatterPlot
      )
    ),
    Category(
      "Scatter Plots",
      Icons.scatter,
      ("#ab63fa", "#d2a6ff"),
      Seq(
        lineandscatter.CategoricalDotPlot,
        lineandscatter.GroupedScatterPlot
      )
    ),
    Category(
      "Bar Charts",
      Icons.bars,
      ("#ef553b", "#ff8a73"),
      Seq(
        bar.BasicBarChart,
        bar.GroupedBarChart,
        bar.BarChartWithDirectLabels,
        bar.CustomizingIndividualBarColors,
        bar.WaterfallBarChart,
        bar.RoundedBars
      )
    ),
    Category(
      "Horizontal Bar Charts",
      Icons.horizontalBars,
      ("#ffa15a", "#ffc58f"),
      Seq(
        horizontalbarcharts.BasicHorizontalBarChart,
        horizontalbarcharts.ColoredBarChart
      )
    ),
    Category(
      "Time Series",
      Icons.timeSeries,
      ("#19d3f3", "#7fe6f8"),
      Seq(
        timeseries.TimeSeries
      )
    ),
    Category(
      "Bubble Charts",
      Icons.bubbles,
      ("#ff6692", "#ffa3be"),
      Seq(
        bubblecharts.HoverOnTextBubbleChart
      )
    ),
    Category(
      "Filled Area Plots",
      Icons.area,
      ("#00cc96", "#5ee8c0"),
      Seq(
        area.BasicOverlaidAreaChart
      )
    ),
    Category(
      "Heatmaps",
      Icons.heatmap,
      ("#e4419b", "#ff8fc8"),
      Seq(
        heatmaps.BasicHeatmap,
        heatmaps.CategoricalAxisHeatmap,
        heatmaps.CustomColorScaleHeatmap,
        heatmaps.AnnotatedHeatmap
      )
    ),
    Category(
      "Histogram",
      Icons.histogram,
      ("#4a55e8", "#19d3f3"),
      Seq(
        histogram.BasicHistogram,
        histogram.StyledBasicHistogram,
        histogram.BivariateNormalHistogram,
        histogram.HistogramContourWithPoints
      )
    ),
    Category(
      "Violin Plots",
      Icons.violin,
      ("#ab63fa", "#ff6692"),
      Seq(
        violin.BasicViolinPlot,
        violin.SplitViolinPlot
      )
    ),
    Category(
      "Contour Plots",
      Icons.contour,
      ("#636efa", "#00cc96"),
      Seq(
        contour.BasicContourPlot,
        contour.ContourLinesWithLabels
      )
    ),
    Category(
      "Pie Charts",
      Icons.pie,
      ("#b6e880", "#00cc96"),
      Seq(
        pie.BasicPieChart,
        pie.DonutChart
      )
    )
  )

  def unindent(source: String): String = {

    val lines                         = source.linesIterator.toVector
    val nonEmptyLines: Vector[String] = lines.filter(_.exists(!_.isSpaceChar))

    if (nonEmptyLines.isEmpty)
      source
    else {

      val dropCount = LazyList
        .from(0)
        .takeWhile(idx => nonEmptyLines.forall(str => str(idx) == nonEmptyLines.head(idx)))
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

  /** "basic-line-plot" or "BasicHistogram" -> "Basic Line Plot" or "Basic Histogram" */
  def demoTitle(demo: DemoChart): String =
    demo.id
      .replaceAll("([a-z])([A-Z])", "$1 $2")
      .split('-')
      .map(_.capitalize)
      .mkString(" ")

  def fullSource(demo: DemoChart): String = {
    val hasLayout = demo.layout != null
    val imports   =
      Seq("import plotly._", "import plotly.element._") ++
        (if (hasLayout) Seq("import plotly.layout._") else Nil)
    val plotCall = s"""Plotly.newPlot("div-id", data${if (hasLayout) ", layout" else ""})"""
    imports.mkString("\n") + "\n" + unindent(demo.source).stripLineEnd + "\n\n" + plotCall
  }

  def copyButton(text: => String, label: String = "Copy") = {
    val btn = button(`class` := "copy-btn", `type` := "button", raw(Icons.copy), span(label)).render
    btn.onclick = { (_: dom.MouseEvent) =>
      dom.window.navigator.clipboard.writeText(text).toFuture.foreach { _ =>
        btn.classList.add("copied")
        btn.lastElementChild.textContent = "Copied!"
        dom.window.setTimeout(
          { () =>
            btn.classList.remove("copied")
            btn.lastElementChild.textContent = label
          },
          1600
        )
      }
    }
    btn
  }

  @JSExport def main(): Unit = {

    val root          = dom.document.getElementById("demo")
    val plotlyVersion = js.Dynamic.global.Plotly.version.asInstanceOf[String]
    val repoUrl       = "https://github.com/alexarchambault/plotly-scala"
    val demoCount     = demos.map(_.demos.length).sum

    val topBar = header(
      `class` := "topbar",
      a(`class`    := "brand", href := "#", raw(Icons.logo), "plotly-scala"),
      span(`class` := "pill", "v" + Properties.version),
      div(
        `class` := "topbar-links",
        a(href := "https://javadoc.io/doc/org.plotly-scala/plotly-render_2.13", raw(Icons.book), span("API docs")),
        a(href := "https://plotly.com/javascript/", raw(Icons.external), span("plotly.js")),
        a(href := repoUrl, raw(Icons.github), span("GitHub"))
      )
    )

    val almondDep = s"org.plotly-scala::plotly-almond:${Properties.version}"
    val millDep   = s"org.plotly-scala::plotly-render::${Properties.version}"
    def installBlock(tool: String, toolUrl: String, copyText: String)(content: Modifier*) =
      div(
        `class` := "install",
        div(
          `class` := "install-head",
          a(`class` := "install-label", href := toolUrl, raw(Icons.github), span(tool)),
          copyButton(copyText)
        ),
        pre(code(content))
      )
    val hero = tags2.section(
      `class` := "hero",
      div(
        `class` := "hero-inner",
        h1(span(`class` := "grad", "plotly-scala"), " demo"),
        p(
          `class` := "lead",
          "A gallery of plotly.js examples, ported to plotly-scala. Every chart below is built from " +
            "the Scala code next to it, compiled with Scala.js, and rendered live in your browser."
        ),
        div(
          `class` := "installs",
          installBlock("Almond", "https://github.com/almond-sh/almond", s"//> using dep $almondDep")(
            span(`class` := "kw", "//> using dep"),
            " ",
            span(`class` := "str", almondDep)
          ),
          installBlock(
            "Mill",
            "https://github.com/com-lihaoyi/mill",
            s"""def mvnDeps = Seq(\n  mvn"$millDep"\n)"""
          )(
            span(`class` := "kw", "def"),
            " mvnDeps = Seq(\n  ",
            span(`class` := "kw", "mvn"),
            span(`class` := "str", s""""$millDep""""),
            "\n)"
          )
        ),
        div(
          `class` := "stats",
          Seq(
            demoCount.toString    -> "Examples",
            demos.length.toString -> "Chart types",
            Properties.version    -> "plotly-scala",
            plotlyVersion         -> "plotly.js"
          ).map { case (value, label) =>
            div(`class` := "stat", div(`class` := "stat-value", value), div(`class` := "stat-label", label))
          }
        ),
        div(
          `class` := "powered-by",
          span(`class` := "eyebrow", span(`class` := "dot"), s"Powered by plotly.js $plotlyVersion")
        )
      )
    )

    val searchInput =
      input(`type` := "search", placeholder := "Filter examples…", attr("aria-label") := "Filter examples").render
    val noResults = div(`class` := "no-results", "No matching example").render

    val navLinks = for {
      category <- demos
      demo     <- category.demos
    } yield demo.id -> a(`class` := "nav-link", href := "#" + demo.id, demoTitle(demo)).render
    val linksById = navLinks.toMap

    val sidebar = tags2.aside(
      `class` := "sidebar",
      div(`class` := "search", raw(Icons.search), searchInput),
      tags2.nav(
        demos.map { category =>
          div(
            `class`               := "nav-group",
            attr("data-category") := category.id,
            a(
              `class` := "nav-group-title",
              href    := "#" + category.id,
              category.name,
              span(`class` := "count", category.demos.length.toString)
            ),
            category.demos.map(demo => linksById(demo.id))
          )
        },
        noResults
      )
    )

    val plotsToRender = js.Dictionary.empty[DemoChart]

    val sections = demos.map { category =>
      tags2.section(
        `class` := "category",
        id      := category.id,
        div(
          `class` := "category-header",
          div(
            `class` := "category-icon",
            style   := s"--c1: ${category.colors._1}; --c2: ${category.colors._2};",
            raw(category.icon)
          ),
          h2(a(href := "#" + category.id, category.name)),
          span(`class` := "count", s"${category.demos.length} example${if (category.demos.length > 1) "s" else ""}")
        ),
        category.demos.map { demo =>
          val divId  = s"demo-${demo.id}"
          val source = fullSource(demo)
          plotsToRender(divId) = demo
          div(
            `class`             := "card",
            id                  := demo.id,
            attr("data-search") := s"${demoTitle(demo)} ${category.name}".toLowerCase,
            div(
              `class` := "card-header",
              h3(a(href := "#" + demo.id, demoTitle(demo), span(`class` := "anchor", "#"))),
              a(
                `class` := "ext",
                href    := demo.plotlyDocUrl,
                target  := "_blank",
                rel     := "noopener",
                "plotly.js example",
                raw(Icons.external)
              )
            ),
            div(
              `class` := "card-body",
              div(`class` := "plot-pane", div(id := divId, `class` := "plot-box")),
              div(
                `class` := "code-pane",
                div(
                  `class` := "code-toolbar",
                  div(`class`  := "dots", span(), span(), span()),
                  span(`class` := "lang", "Scala"),
                  copyButton(source)
                ),
                pre(code(`class` := "language-scala", source))
              )
            )
          )
        }
      )
    }

    val footerElem = footer(
      `class` := "footer",
      p(
        "Built with ",
        a(href := repoUrl, "plotly-scala"),
        " ",
        code(Properties.version),
        " (",
        a(href := s"$repoUrl/commit/${Properties.commitHash}", code(Properties.commitHash.take(8))),
        ") and plotly.js ",
        code(plotlyVersion),
        "."
      ),
      p("Examples adapted from the ", a(href := "https://plotly.com/javascript/", "plotly.js documentation"), ".")
    )

    root.appendChild(topBar.render)
    root.appendChild(hero.render)
    root.appendChild(div(`class` := "layout", sidebar, tags2.main(`class` := "content", sections)).render)
    root.appendChild(footerElem.render)

    dom.document.querySelectorAll("code.language-scala").foreach { elem =>
      js.Dynamic.global.Prism.highlightElement(elem)
    }

    // Charts are rendered when they're about to scroll into view, rather than all at once on page load
    val config                      = Config().withValues(responsive = true)
    def render(divId: String): Unit =
      for (demo <- plotsToRender.remove(divId)) {
        Console.println(s"Rendering demo ${demo.id}")
        Plotly.newPlot(divId, demo.data, Option(demo.layout).getOrElse(Layout()), config)
      }

    val plotObserver = new dom.IntersectionObserver(
      (entries, _) => entries.filter(_.isIntersecting).foreach(entry => render(entry.target.id)),
      new dom.IntersectionObserverInit { rootMargin = "400px 0px" }
    )
    plotsToRender.keys.foreach(divId => plotObserver.observe(dom.document.getElementById(divId)))

    // Highlights the sidebar link of the last example whose top is above a third of the viewport
    val cards                           = dom.document.querySelectorAll(".card").toSeq
    var activeLink: Option[dom.Element] = None
    def updateActiveLink(): Unit        = {
      val threshold = dom.window.innerHeight / 3
      val current   = cards
        .filter(card => !card.classList.contains("hidden") && card.getBoundingClientRect().top <= threshold)
        .lastOption
        .flatMap(card => linksById.get(card.id))
      if (current != activeLink) {
        activeLink.foreach(_.classList.remove("active"))
        current.foreach(_.classList.add("active"))
        activeLink = current
      }
    }
    var updatePending = false
    dom.window.addEventListener(
      "scroll",
      { (_: dom.Event) =>
        if (!updatePending) {
          updatePending = true
          dom.window.requestAnimationFrame { _ =>
            updatePending = false
            updateActiveLink()
          }
        }
      }
    )
    updateActiveLink()

    searchInput.oninput = { (_: dom.Event) =>
      val query = searchInput.value.trim.toLowerCase
      for (card <- cards) {
        val matches = card.getAttribute("data-search").contains(query)
        card.classList.toggle("hidden", !matches)
        linksById.get(card.id).foreach(_.classList.toggle("hidden", !matches))
      }
      for (section <- dom.document.querySelectorAll(".category, .nav-group")) {
        val empty = section.querySelectorAll(".card:not(.hidden), .nav-link:not(.hidden)").length == 0
        section.classList.toggle("hidden", empty)
      }
      noResults.classList.toggle("visible", cards.forall(_.classList.contains("hidden")))
      updateActiveLink()
      // Charts in cards that were hidden need to pick up their container size again
      dom.window.dispatchEvent(new dom.Event("resize"))
    }
  }
}

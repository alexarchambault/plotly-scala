package plotly

import java.nio.charset.StandardCharsets
import java.nio.file.Files

class RawJsonTests extends munit.FunSuite {

  private val figure =
    """{"data": [{"type": "sankey", "node": {"label": ["a", "b"]}, "link": {"source": [0], "target": [1], "value": [1]}}], "layout": {"title": {"text": "</script>"}}}"""

  test("jsSnippetFromJson passes the figure as is to plotly.js") {
    val js = Plotly.jsSnippetFromJson("chart", figure)
    assert(js.contains(""""type": "sankey""""))
    assert(js.contains("Plotly.newPlot('chart', figure);"))
  }

  test("jsSnippetFromJson escapes things that would close the script element") {
    val js = Plotly.jsSnippetFromJson("chart", figure)
    assert(!js.contains("</script>"))
    assert(js.contains("\\u003c/script>"))
  }

  test("jsSnippetFromJson escapes the div id") {
    val js = Plotly.jsSnippetFromJson("a'b\\c", figure)
    assert(js.contains("""Plotly.newPlot('a\'b\\c', figure);"""))
  }

  test("invalid figures are rejected") {
    val invalid = Seq(
      "",
      "{",
      "[]",
      "\"data\"",
      """{"data": []} {}""",
      """{"data": [}""",
      """{"data": [1 2]}""",
      """{"data" []}""",
      """{"data": [], }""",
      """{"data": [tru]}""",
      """{"data": ["\x"]}""",
      """{"data": [01]}""",
      "{\"data\": " + "[" * 2000 + "]" * 2000 + "}"
    )
    for (json <- invalid) {
      val res = scala.util.Try(Plotly.jsSnippetFromJson("chart", json))
      assert(res.failed.toOption.exists(_.isInstanceOf[IllegalArgumentException]), s"Not rejected: $json")
    }
  }

  test("valid figures are accepted") {
    val valid = Seq(
      "{}",
      " { } ",
      figure,
      """{"data": [{"x": [1, -2.5e3, null], "visible": true, "name": "a\"b\tc"}], "layout": {}, "config": {"responsive": false}}"""
    )
    for (json <- valid)
      Plotly.jsSnippetFromJson("chart", json)
  }

  test("plotJson writes an HTML file") {
    val dir = Files.createTempDirectory("plotly-scala-tests")
    try {
      val f = Plotly.plotJson(
        dir.resolve("plot.html").toString,
        figure,
        title = "a <b> & c",
        openInBrowser = false
      )
      val html = new String(Files.readAllBytes(f.toPath), StandardCharsets.UTF_8)
      assert(html.contains("<title>a &lt;b&gt; &amp; c</title>"))
      assert(html.contains(Plotly.jsSnippetFromJson("chart", figure)))
    } finally {
      val s = Files.list(dir)
      try s.forEach(Files.delete(_))
      finally s.close()
      Files.delete(dir)
    }
  }

}

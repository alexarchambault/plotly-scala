package plotly

class ResourceTests extends munit.FunSuite {

  test("plotly.min.js must be found in resources") {
    assert(Plotly.plotlyMinJs.nonEmpty)
  }

}

package plotly

import org.scalatest.PropSpec

class ResourceTests extends PropSpec {

  property("plotly.min.js must be found in resources") {
    assert(Plotly.plotlyMinJs.nonEmpty)
  }

}

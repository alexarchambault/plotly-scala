package plotly

import org.scalatest.propspec.AnyPropSpec

class ResourceTests extends AnyPropSpec {

  property("plotly.min.js must be found in resources") {
    assert(Plotly.plotlyMinJs.nonEmpty)
  }

}

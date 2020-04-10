package plotly

import org.scalatest.FlatSpec

import scala.collection.mutable.ArrayBuffer

class SequenceTests extends FlatSpec {

  "The implicit sequence conversion" should "convert a List to a Sequence" in {
    assert((List(1, 2, 3): Sequence) === Sequence.Doubles(List(1d, 2d, 3d)))
  }

  it should "convert a mutable ArrayBuffer to a Sequence" in {
    assert((ArrayBuffer(1, 2, 3): Sequence) === Sequence.Doubles(ArrayBuffer(1d, 2d, 3d)))
  }

}

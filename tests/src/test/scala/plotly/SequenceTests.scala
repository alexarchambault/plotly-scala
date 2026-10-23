package plotly

import scala.collection.mutable.ArrayBuffer

class SequenceTests extends munit.FunSuite {

  test("The implicit sequence conversion should convert a List to a Sequence") {
    assertEquals(List(1, 2, 3): Sequence, Sequence.Doubles(List(1d, 2d, 3d)))
  }

  test("The implicit sequence conversion should convert a mutable ArrayBuffer to a Sequence") {
    assertEquals(ArrayBuffer(1, 2, 3): Sequence, Sequence.Doubles(List(1d, 2d, 3d)))
  }

  test("The implicit sequence conversion should convert a nested mutable ArrayBuffer to a Sequence") {
    val mutableNestedDoubles: ArrayBuffer[ArrayBuffer[Double]] = ArrayBuffer(
      ArrayBuffer(1d, 2d),
      ArrayBuffer(3d, 4d)
    )

    val nestedDoublesList: List[List[Double]] = List(
      List(1d, 2d),
      List(3d, 4d)
    )

    assertEquals(mutableNestedDoubles: Sequence, Sequence.NestedDoubles(nestedDoublesList))
  }

}

package io.circe.altgeneric

import cats.implicits._
import io.circe.{ Decoder, Encoder, Json }
import utest._

object DefaultTests extends TestSuite {

  case class WithDefaults(
    i: Int,
    s: String = "b"
  )

  val tests = TestSuite {
    'simple - {
      val encoder = Encoder[WithDefaults]
      val decoder = Decoder[WithDefaults]

      val value0 = WithDefaults(2, "a")
      val json0 = encoder(value0)
      val expectedJson0 = Json.obj(
        "i" -> Json.fromInt(2),
        "s" -> Json.fromString("a")
      )

      assert(json0 == expectedJson0)

      val value1 = WithDefaults(2)
      val json1 = encoder(value1)
      val expectedJson1 = Json.obj(
        "i" -> Json.fromInt(2)
      )

      assert(json1 == expectedJson1)

      val result0 = decoder.decodeJson(expectedJson0)
      val expectedResult0 = Either.right(value0)
      assert(result0 == expectedResult0)

      val result1 = decoder.decodeJson(expectedJson1)
      val expectedResult1 = Either.right(value1)
      assert(result1 == expectedResult1)
    }
  }

}

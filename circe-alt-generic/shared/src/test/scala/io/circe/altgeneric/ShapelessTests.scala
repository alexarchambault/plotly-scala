package io.circe.altgeneric

import org.scalacheck.{ Arbitrary, Prop }
import shapeless.test.illTyped
import utest._
import Util._
import io.circe.{ Decoder, Encoder, Json, parser => Parse }
import io.circe.syntax._


object ShapelessTests extends TestSuite {
  private def toFromJson[T: Encoder : Decoder](t: T): Decoder.Result[T] = t.asJson.as[T]

  private def sameAfterBeforeSerialization[T: Arbitrary : Encoder : Decoder]: Unit =
    Prop.forAll {
      t: T =>
        toFromJson(t) == Right(t)
    }.validate

  import org.scalacheck.Shapeless._

  val tests = TestSuite {
    'serializeDeserialize {
      'empty - {
        sameAfterBeforeSerialization[Empty.type]
      }

      'emptyCC - {
        sameAfterBeforeSerialization[EmptyCC]
      }

      'simple - {
        sameAfterBeforeSerialization[Simple]
      }

      'composed - {
        sameAfterBeforeSerialization[Composed]
      }

      'twiceComposed - {
        sameAfterBeforeSerialization[TwiceComposed]
      }

      'composedOptList - {
        sameAfterBeforeSerialization[ComposedOptList]
      }

      'nowThree - {
        sameAfterBeforeSerialization[NowThree]
      }

      'oi - {
        sameAfterBeforeSerialization[OI]
      }

      'oiLoose - {
        val json = Parse.parse("{}").right.toOption.get
        // assert macro crashes if result is substituted by its value below
        val result = json.as[OI]
        assert(result == Right(OI(None)))
      }

      'base - {
        sameAfterBeforeSerialization[Base]
      }

      'simpleWithJsDummy - {
        Encoder[Json]
        Decoder[Json]
        Encoder[SimpleWithJs]
        Decoder[SimpleWithJs]
        // Arbitrary[SimpleWithJs] doesn't seem fine
        // sameAfterBeforeSerialization[SimpleWithJs]
      }
    }
  }

  illTyped(" Encoder[NoArbitraryType] ")
  illTyped(" Decoder[NoArbitraryType] ")
  illTyped(" Encoder[ShouldHaveNoArb] ")
  illTyped(" Decoder[ShouldHaveNoArb] ")
  illTyped(" Encoder[ShouldHaveNoArbEither] ")
  illTyped(" Decoder[ShouldHaveNoArbEither] ")
  illTyped(" Encoder[BaseNoArb] ")
  illTyped(" Decoder[BaseNoArb] ")

}

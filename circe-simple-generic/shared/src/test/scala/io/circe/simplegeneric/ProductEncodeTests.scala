package io.circe.simplegeneric

import utest._
import org.scalacheck.{ Arbitrary, Prop }
import org.scalacheck.Shapeless._
import shapeless._
import io.circe.simplegeneric.derive.{ HListProductEncoder, JsonProductCodecFor, MkEncoder, ProductEncoder }
import io.circe.{ Encoder, Json, KeyEncoder }
import io.circe.syntax._
import Util._


object ProductEncodeTests extends TestSuite {

  case class WrappedMap(m: Map[String, Json])

  lazy val expectedEmptyEncoder =
    MkEncoder.product(
      ProductEncoder.generic(
        LabelledGeneric[Empty.type],
        Default.AsOptions[Empty.type],
        HListProductEncoder.hnil
      ),
      JsonProductCodecFor.default
    ).encoder

  lazy val expectedEmptyCCEncoder =
    MkEncoder.product(
      ProductEncoder.generic(
        LabelledGeneric[EmptyCC],
        Default.AsOptions[EmptyCC],
        HListProductEncoder.hnil
      ),
      JsonProductCodecFor.default
    ).encoder

  lazy val expectedSimpleEncoder =
    MkEncoder.product(
      ProductEncoder.generic(
        LabelledGeneric[Simple],
        Default.AsOptions[Simple],
        HListProductEncoder.hcons(
          Witness('i),
          Encoder.encodeInt,
          HListProductEncoder.hcons(
            Witness('s),
            Encoder.encodeString,
            HListProductEncoder.hcons(
              Witness('blah),
              Encoder.encodeBoolean,
              HListProductEncoder.hnil
            )
          )
        )
      ),
      JsonProductCodecFor.default
    ).encoder

  lazy val expectedComposedEncoder =
    MkEncoder.product(
      ProductEncoder.generic(
        LabelledGeneric[Composed],
        Default.AsOptions[Composed],
        HListProductEncoder.hcons(
          Witness('foo),
          expectedSimpleEncoder,
          HListProductEncoder.hcons(
            Witness('other),
            Encoder.encodeString,
            HListProductEncoder.hnil
          )
        )
      ),
      JsonProductCodecFor.default
    ).encoder

  lazy val expectedSimpleWithJsEncoder =
    MkEncoder.product(
      ProductEncoder.generic(
        LabelledGeneric[SimpleWithJs],
        Default.AsOptions[SimpleWithJs],
        HListProductEncoder.hcons(
          Witness('i),
          Encoder.encodeInt,
          HListProductEncoder.hcons(
            Witness('s),
            Encoder.encodeString,
            HListProductEncoder.hcons(
              Witness('v),
              Encoder.encodeJson,
              HListProductEncoder.hnil
            )
          )
        )
      ),
      JsonProductCodecFor.default
    ).encoder

  lazy val expectedWrappedMapEncoder =
    MkEncoder.product(
      ProductEncoder.generic(
        LabelledGeneric[WrappedMap],
        Default.AsOptions[WrappedMap],
        HListProductEncoder.hcons(
          Witness('m),
          Encoder.encodeMap[String, Json](KeyEncoder.encodeKeyString, Encoder.encodeJson),
          HListProductEncoder.hnil
        )
      ),
      JsonProductCodecFor.default
    ).encoder

  lazy val expectedOIEncoder =
    MkEncoder.product(
      ProductEncoder.generic(
        LabelledGeneric[OI],
        Default.AsOptions[OI],
        HListProductEncoder.hcons(
          Witness('oi),
          Encoder.encodeOption[Int](Encoder.encodeInt),
          HListProductEncoder.hnil
        )
      ),
      JsonProductCodecFor.default
    ).encoder


  def compareEncoders[T: Arbitrary](first: Encoder[T], second: Encoder[T]): Unit =
    Prop.forAll{
      t: T =>
        first(t) == second(t)
    }.validate

  def jsonIs[T: Encoder](t: T, json: Json): Unit = {
    assert(t.asJson == json)
  }


  val tests = TestSuite {

    'codec {
      'empty - {
        compareEncoders(Encoder[Empty.type], expectedEmptyEncoder)
      }

      'emptyCC - {
        compareEncoders(Encoder[EmptyCC], expectedEmptyCCEncoder)
      }

      'simple - {
        compareEncoders(Encoder[Simple], expectedSimpleEncoder)
      }

      'composed - {
        compareEncoders(Encoder[Composed], expectedComposedEncoder)
      }

      // Disabled, Arbitrary Json generation seems to take forever
      // 'simpleWithJs - {
      //   compareEncoders(Encoder[SimpleWithJs], expectedSimpleWithJsEncoder)
      // }

      // Looks like not enough WrappedMap can be generated
      // 'wrappedMap - {
      //   val arb = Gen.resize(1000, Arbitrary.arbitrary[WrappedMap])
      //   compareEncoders(Encoder[WrappedMap], expectedWrappedMapEncoder)(Arbitrary(arb))
      // }

      'withOption - {
        compareEncoders(Encoder[OI], expectedOIEncoder)
      }
    }

    'output {
      'empty - {
        jsonIs(Empty, Json.obj())
      }

      'emptyCC - {
        jsonIs(EmptyCC(), Json.obj())
      }

      'simple - {
        jsonIs(
          Simple(41, "aa", blah = false),
          Json.obj(
            "i" -> Json.fromInt(41),
            "s" -> Json.fromString("aa"),
            "blah" -> Json.fromBoolean(false)
          )
        )
      }

      'composed - {
        jsonIs(
          Composed(Simple(41, "aa", blah = false), "bbb"),
          Json.obj(
            "foo" -> Json.obj(
              "i" -> Json.fromInt(41),
              "s" -> Json.fromString("aa"),
              "blah" -> Json.fromBoolean(false)
            ),
            "other" -> Json.fromString("bbb")
          )
        )
      }

      'simpleWithJs - {
        jsonIs(
          SimpleWithJs(41, "aa", Json.arr(Json.fromInt(10), Json.obj("a" -> Json.fromBoolean(true)))),
          Json.obj(
            "i" -> Json.fromInt(41),
            "s" -> Json.fromString("aa"),
            "v" -> Json.arr(Json.fromInt(10), Json.obj("a" -> Json.fromBoolean(true)))
          )
        )
      }

      'wrappedMap - {
        jsonIs(
          WrappedMap(Map(
            "aa" -> Json.arr(Json.fromInt(10), Json.obj("a" -> Json.fromBoolean(true))),
            "bb" -> Json.obj("c" -> Json.fromBoolean(false))
          )),
          Json.obj(
            "m" -> Json.obj(
              "aa" -> Json.arr(Json.fromInt(10), Json.obj("a" -> Json.fromBoolean(true))),
              "bb" -> Json.obj("c" -> Json.fromBoolean(false))
            )
          )
        )
      }
    }

  }

}

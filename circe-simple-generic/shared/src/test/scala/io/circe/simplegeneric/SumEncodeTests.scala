package io.circe.simplegeneric

import utest._
import org.scalacheck.Shapeless._
import shapeless._
import derive._
import io.circe.{Encoder, Json}


object SumEncodeTests extends TestSuite {
  import ProductEncodeTests.{ compareEncoders, jsonIs }

  lazy val expectedBaseISEncoder =
    MkEncoder.product(
      ProductEncoder.generic(
        LabelledGeneric[BaseIS],
        Default.AsOptions[BaseIS],
        Lazy(
          HListProductEncoder.hcons(
            Witness('i),
            Strict(Encoder.encodeInt),
            HListProductEncoder.hcons(
              Witness('s),
              Strict(Encoder.encodeString),
              HListProductEncoder.hnil
            )
          )
        )
      ),
      JsonProductCodecFor.default
    ).encoder

  lazy val expectedBaseDBEncoder =
    MkEncoder.product(
      ProductEncoder.generic(
        LabelledGeneric[BaseDB],
        Default.AsOptions[BaseDB],
        Lazy(
          HListProductEncoder.hcons(
            Witness('d),
            Strict(Encoder.encodeDouble),
            HListProductEncoder.hcons(
              Witness('b),
              Strict(Encoder.encodeBoolean),
              HListProductEncoder.hnil
            )
          )
        )
      ),
      JsonProductCodecFor.default
    ).encoder

  lazy val expectedBaseLastEncoder =
    MkEncoder.product(
      ProductEncoder.generic(
        LabelledGeneric[BaseLast],
        Default.AsOptions[BaseLast],
        Lazy(
          HListProductEncoder.hcons(
            Witness('c),
            Strict(ProductEncodeTests.expectedSimpleEncoder),
            HListProductEncoder.hnil
          )
        )
      ),
      JsonProductCodecFor.default
    ).encoder

  lazy val expectedBaseEncoder = expectedBaseEncoderFor(JsonSumCodecFor.default)
  lazy val expectedBaseEncoderTypeField = expectedBaseEncoderFor(JsonSumCodecFor(JsonSumCodec.typeField))
  def expectedBaseEncoderFor(codecFor: JsonSumCodecFor[Base]) =
    MkEncoder.sum(
      SumEncoder.generic(
        LabelledGeneric[Base],
        CoproductSumEncoder.ccons(
          Witness('BaseDB),
          expectedBaseDBEncoder,
          CoproductSumEncoder.ccons(
            Witness('BaseIS),
            expectedBaseISEncoder,
            CoproductSumEncoder.ccons(
              Witness('BaseLast),
              expectedBaseLastEncoder,
              CoproductSumEncoder.cnil
            )
          )
        )
      ),
      codecFor
    ).encoder

  val derivedBaseEncoderTypeField = {
    implicit val codecFor = JsonSumCodecFor[Base](JsonSumCodec.typeField)
    Encoder[Base]
  }

  val tests = TestSuite {

    'codec {
      'base - {
        compareEncoders(Encoder[Base], expectedBaseEncoder)
      }

      'baseTypeField - {
        compareEncoders(derivedBaseEncoderTypeField, expectedBaseEncoderTypeField)
      }
    }

    'output {
      'base - {
        jsonIs(
          BaseLast(Simple(41, "aa", blah = false)): Base,
          Json.obj(
            "BaseLast" -> Json.obj(
              "c" -> Json.obj(
                "i" -> Json.fromInt(41),
                "s" -> Json.fromString("aa"),
                "blah" -> Json.fromBoolean(false)
              )
            )
          )
        )

        jsonIs(
          BaseIS(43, "aa"): Base,
          Json.obj(
            "BaseIS" -> Json.obj(
              "i" -> Json.fromInt(43),
              "s" -> Json.fromString("aa")
            )
          )
        )

        jsonIs(
          BaseDB(3.2, false): Base,
          Json.obj(
            "BaseDB" -> Json.obj(
              "d" -> Json.fromDoubleOrString(3.2),
              "b" -> Json.fromBoolean(false)
            )
          )
        )
      }
    }

  }

}

package io.circe.altgeneric
package derive

import cats.data.Xor
import shapeless._
import shapeless.labelled.{FieldType, field}
import io.circe.Decoder

abstract class MkDecoder[T] {
  def decoder: Decoder[T]
}

object MkDecoder {
  def apply[T](implicit decoder: MkDecoder[T]): MkDecoder[T] = decoder

  implicit def product[P]
   (implicit
    underlying: ProductDecoder[P],
    codecFor: JsonProductCodecFor[P]
   ): MkDecoder[P] =
    new MkDecoder[P] {
      def decoder = underlying(codecFor.codec)
    }

  implicit def sum[S]
   (implicit
    underlying: SumDecoder[S],
    codecFor: JsonSumCodecFor[S]
   ): MkDecoder[S] =
    new MkDecoder[S] {
      def decoder = underlying(codecFor.codec)
    }
}

abstract class ProductDecoder[P] {
  def apply(productCodec: JsonProductCodec): Decoder[P]
}

object ProductDecoder {
  def apply[P](implicit decoder: ProductDecoder[P]): ProductDecoder[P] = decoder

  def instance[P](f: JsonProductCodec => Decoder[P]): ProductDecoder[P] =
    new ProductDecoder[P] {
      def apply(productCodec: JsonProductCodec) =
        f(productCodec)
    }

  // Re-enable by making a dummy HList of defaults made of Option[_]
  // implicit def record[R <: HList]
  //  (implicit
  //    underlying: HListProductDecoder[R]
  //  ): ProductDecoder[R] =
  //   instance { productCodec =>
  //     underlying(productCodec)
  //   }

  implicit def generic[P, L <: HList, D <: HList]
   (implicit
     gen: LabelledGeneric.Aux[P, L],
     defaults: Default.AsOptions.Aux[P, D],
     underlying: Lazy[HListProductDecoder[L, D]]
   ): ProductDecoder[P] =
    instance { productCodec =>
      underlying.value(productCodec, defaults())
        .map(gen.from)
    }
}

abstract class HListProductDecoder[L <: HList, D <: HList] {
  def apply(productCodec: JsonProductCodec, defaults: D): Decoder[L]
}

object HListProductDecoder {
  def apply[L <: HList, D <: HList](implicit decoder: HListProductDecoder[L, D]): HListProductDecoder[L, D] =
    decoder

  def instance[L <: HList, D <: HList](f: (JsonProductCodec, D) => Decoder[L]): HListProductDecoder[L, D] =
    new HListProductDecoder[L, D] {
      def apply(productCodec: JsonProductCodec, defaults: D) =
        f(productCodec, defaults)
    }

  implicit val hnil: HListProductDecoder[HNil, HNil] =
    instance { (productCodec, defaults) =>
      Decoder.instance { c =>
        productCodec
          .decodeEmpty(c)
          .map(_ => HNil)
      }
    }

  implicit def hcons[K <: Symbol, H, T <: HList, TD <: HList]
   (implicit
     key: Witness.Aux[K],
     headDecode: Strict[Decoder[H]],
     tailDecode: HListProductDecoder[T, TD]
   ): HListProductDecoder[FieldType[K, H] :: T, Option[H] :: TD] =
    instance { (productCodec, defaults) =>
      lazy val tailDecode0 = tailDecode(productCodec, defaults.tail)

      Decoder.instance { c =>
        for {
          x <- productCodec.decodeField(key.value.name, c, headDecode.value, defaults.head)
          (h, remaining) = x
          t <- remaining.as(tailDecode0)
        } yield field[K](h) :: t
      }
    }
}

abstract class CoproductSumDecoder[C <: Coproduct] {
  def apply(sumCodec: JsonSumCodec): Decoder[C]
}

object CoproductSumDecoder {
  def apply[C <: Coproduct](implicit decoder: CoproductSumDecoder[C]): CoproductSumDecoder[C] =
    decoder

  def instance[C <: Coproduct](f: JsonSumCodec => Decoder[C]): CoproductSumDecoder[C] =
    new CoproductSumDecoder[C] {
      def apply(sumCodec: JsonSumCodec) =
        f(sumCodec)
    }

  implicit val cnil: CoproductSumDecoder[CNil] =
    instance { sumCodec =>
      Decoder.instance { c =>
        sumCodec
          .decodeEmpty(c)
          .map(t => t: CNil)
      }
    }

  implicit def ccons[K <: Symbol, H, T <: Coproduct]
   (implicit
     key: Witness.Aux[K],
     headDecode: Lazy[Decoder[H]],
     tailDecode: CoproductSumDecoder[T]
   ): CoproductSumDecoder[FieldType[K, H] :+: T] =
    instance { sumCodec =>
      lazy val tailDecode0 = tailDecode(sumCodec)

      Decoder.instance { c =>
        sumCodec.decodeField(key.value.name, c, headDecode.value).flatMap {
          case Left(tailCursor) => tailCursor.as(tailDecode0).map(Inr(_))
          case Right(h) => Xor.right(Inl(field[K](h)))
        }
      }
    }
}

abstract class SumDecoder[S] {
  def apply(sumCodec: JsonSumCodec): Decoder[S]
}

object SumDecoder {
  def apply[S](implicit decoder: SumDecoder[S]): SumDecoder[S] = decoder

  def instance[S](f: JsonSumCodec => Decoder[S]): SumDecoder[S] =
    new SumDecoder[S] {
      def apply(sumCodec: JsonSumCodec) =
        f(sumCodec)
    }

  implicit def union[U <: Coproduct]
   (implicit
     underlying: CoproductSumDecoder[U]
   ): SumDecoder[U] =
    instance { sumCodec =>
      underlying(sumCodec)
    }

  implicit def generic[S, C <: Coproduct]
   (implicit
     gen: LabelledGeneric.Aux[S, C],
     underlying: Strict[CoproductSumDecoder[C]]
   ): SumDecoder[S] =
    instance { sumCodec =>
      underlying.value(sumCodec)
        .map(gen.from)
    }
}

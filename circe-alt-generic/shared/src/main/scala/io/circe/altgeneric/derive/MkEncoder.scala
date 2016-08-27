package io.circe.altgeneric
package derive

import io.circe.Encoder
import shapeless._
import shapeless.labelled.FieldType

abstract class MkEncoder[T] {
  def encoder: Encoder[T]
}

object MkEncoder {
  def apply[T](implicit encoder: MkEncoder[T]): MkEncoder[T] = encoder

  implicit def product[P]
   (implicit
    underlying: Strict[ProductEncoder[P]],
    codecFor: JsonProductCodecFor[P]
   ): MkEncoder[P] =
    new MkEncoder[P] {
      def encoder = underlying.value(codecFor.codec)
    }

  implicit def sum[S]
   (implicit
    underlying: Strict[SumEncoder[S]],
    codecFor: JsonSumCodecFor[S]
   ): MkEncoder[S] =
    new MkEncoder[S] {
      def encoder = underlying.value(codecFor.codec)
    }
}

abstract class ProductEncoder[P] {
  def apply(productCodec: JsonProductCodec): Encoder[P]
}

object ProductEncoder {
  def apply[P](implicit encoder: ProductEncoder[P]): ProductEncoder[P] = encoder

  def instance[P](f: JsonProductCodec => Encoder[P]): ProductEncoder[P] =
    new ProductEncoder[P] {
      def apply(productCodec: JsonProductCodec) =
        f(productCodec)
    }

  // TODO Generate an HList made of Option[...] as to use as default
  // implicit def record[R <: HList]
  //  (implicit
  //    underlying: HListProductEncoder[R]
  //  ): ProductEncoder[R] =
  //   instance { productCodec =>
  //     underlying(productCodec)
  //   }

  implicit def generic[P, L <: HList, D <: HList]
   (implicit
     gen: LabelledGeneric.Aux[P, L],
     defaults: Default.AsOptions.Aux[P, D],
     underlying: Lazy[HListProductEncoder[L, D]]
   ): ProductEncoder[P] =
    instance { productCodec =>
      underlying.value(productCodec, defaults())
        .contramap(gen.to)
    }
}

abstract class HListProductEncoder[L <: HList, D <: HList] {
  def apply(productCodec: JsonProductCodec, defaults: D): Encoder[L]
}

object HListProductEncoder {
  def apply[L <: HList, D <: HList](implicit encoder: HListProductEncoder[L, D]): HListProductEncoder[L, D] =
    encoder

  def instance[L <: HList, D <: HList](f: (JsonProductCodec, D) => Encoder[L]): HListProductEncoder[L, D] =
    new HListProductEncoder[L, D] {
      def apply(productCodec: JsonProductCodec, defaults: D) =
        f(productCodec, defaults)
    }

  implicit val hnil: HListProductEncoder[HNil, HNil] =
    instance { (productCodec, _) =>
      Encoder.instance { _ =>
        productCodec.encodeEmpty
      }
    }

  implicit def hcons[K <: Symbol, H, T <: HList, TD <: HList]
   (implicit
     key: Witness.Aux[K],
     headEncode: Strict[Encoder[H]],
     tailEncode: HListProductEncoder[T, TD]
   ): HListProductEncoder[FieldType[K, H] :: T, Option[H] :: TD] =
    instance { (productCodec, defaults) =>
      lazy val defaultOpt = defaults.head.map(headEncode.value(_))
      lazy val tailEncode0 = tailEncode(productCodec, defaults.tail)

      Encoder.instance { l =>
        productCodec.encodeField(
          key.value.name -> headEncode.value(l.head),
          tailEncode0(l.tail),
          defaultOpt
        )
      }
    }
}


abstract class SumEncoder[S] {
  def apply(sumCodec: JsonSumCodec): Encoder[S]
}

object SumEncoder {
  def apply[S](implicit encoder: SumEncoder[S]): SumEncoder[S] = encoder

  def instance[S](f: JsonSumCodec => Encoder[S]): SumEncoder[S] =
    new SumEncoder[S] {
      def apply(sumCodec: JsonSumCodec) =
        f(sumCodec)
    }

  implicit def union[U <: Coproduct]
   (implicit
     underlying: CoproductSumEncoder[U]
   ): SumEncoder[U] =
    instance { sumCodec =>
      underlying(sumCodec)
    }

  implicit def generic[S, C <: Coproduct]
   (implicit
     gen: LabelledGeneric.Aux[S, C],
     underlying: Strict[CoproductSumEncoder[C]]
   ): SumEncoder[S] =
    instance { sumCodec =>
      underlying.value(sumCodec)
        .contramap(gen.to)
    }
}

abstract class CoproductSumEncoder[C <: Coproduct] {
  def apply(sumCodec: JsonSumCodec): Encoder[C]
}

object CoproductSumEncoder {
  def apply[C <: Coproduct](implicit encoder: CoproductSumEncoder[C]): CoproductSumEncoder[C] =
    encoder

  def instance[C <: Coproduct](f: JsonSumCodec => Encoder[C]): CoproductSumEncoder[C] =
    new CoproductSumEncoder[C] {
      def apply(sumCodec: JsonSumCodec) =
        f(sumCodec)
    }

  implicit val cnil: CoproductSumEncoder[CNil] =
    instance { sumCodec =>
      Encoder.instance { c =>
        sumCodec.encodeEmpty
      }
    }

  implicit def ccons[K <: Symbol, H, T <: Coproduct]
   (implicit
     key: Witness.Aux[K],
     headEncode: Lazy[Encoder[H]],
     tailEncode: CoproductSumEncoder[T]
   ): CoproductSumEncoder[FieldType[K, H] :+: T] =
    instance { sumCodec =>
      lazy val tailEncode0 = tailEncode(sumCodec)

      Encoder.instance {
        case Inl(h) =>
          sumCodec.encodeField(
            Right(key.value.name -> headEncode.value(h))
          )
        case Inr(r) =>
          tailEncode0(r)
      }
    }
}
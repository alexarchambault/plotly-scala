package io.circe.altgeneric.derive

import shapeless.{ LowPriority, Strict }
import io.circe.{ Decoder, Encoder }

trait DerivedInstances {

  implicit def derivedEncoder[T]
   (implicit
     ev: LowPriority,
     underlying: Strict[MkEncoder[T]]
   ): Encoder[T] =
    underlying.value.encoder

  implicit def derivedDecoder[T]
   (implicit
     ev: LowPriority,
     underlying: Strict[MkDecoder[T]]
   ): Decoder[T] =
    underlying.value.decoder
}

package plotly.internals

import argonaut.{DecodeJson, EncodeJson}
import scala.collection.immutable.Seq

trait ArgonautCodecsExtra {

  implicit def seqEncoder[T: EncodeJson]: EncodeJson[Seq[T]] =
    EncodeJson.of[Vector[T]].contramap(_.toVector)
  implicit def seqDecoder[T: DecodeJson]: DecodeJson[Seq[T]] =
    DecodeJson.of[Vector[T]].map(x => x)

}

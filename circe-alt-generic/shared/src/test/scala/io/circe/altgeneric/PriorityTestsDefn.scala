package io.circe.altgeneric

import io.circe.{Decoder, HCursor}

object PriorityTestsDefn {
  trait Flag

  case class CC(s: String, i: Int)

  object CC {
    implicit val decode: Decoder[CC] = new Decoder[CC] with Flag {
      def apply(c: HCursor) =
        Decoder[(String, Int)].apply(c).map { case (s, i) =>
          CC(s, i)
        }
    }
  }

  case class CC2(i: Int, s: String)
}

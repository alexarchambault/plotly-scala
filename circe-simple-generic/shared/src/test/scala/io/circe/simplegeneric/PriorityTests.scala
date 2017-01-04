package io.circe.simplegeneric

import io.circe.Decoder
import utest._

object PriorityTests extends TestSuite {
  import PriorityTestsDefn._

  val tests = TestSuite {
    'dontOverride - {
      Decoder[CC] match {
        case _: Flag =>
        case _ => throw new Exception(s"Default Decoder was overridden")
      }
    }

    'doOverride - {
      Decoder[CC2] match {
        case _: Flag => throw new Exception(s"Can't happen")
        case _ =>
      }
    }
  }

}

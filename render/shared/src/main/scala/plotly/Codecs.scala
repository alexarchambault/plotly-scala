package plotly

import argonaut._
import argonaut.ArgonautShapeless._
import plotly.internals.ArgonautCodecsExtra
import plotly.internals.ArgonautCodecsInternals._
import plotly.layout._

object Codecs extends ArgonautCodecsExtra {

  implicit val argonautEncodeTrace = EncodeJson.of[Trace]
  implicit val argonautDecodeTrace = DecodeJson.of[Trace]

  implicit val argonautEncodeLayout = EncodeJson.of[Layout]
  implicit val argonautDecodeLayout = DecodeJson.of[Layout]

  implicit val argonautEncodeConfig = EncodeJson.of[Config]
  implicit val argonautDecodeConfig = DecodeJson.of[Config]
}

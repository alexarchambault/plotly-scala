package plotly

import com.github.plokhotnyuk.jsoniter_scala.core.JsonValueCodec
import plotly.internals.JsoniterCodecsInternals
import plotly.layout._

object Codecs {

  implicit val traceCodec: JsonValueCodec[Trace]       = JsoniterCodecsInternals.traceCodec
  implicit val tracesCodec: JsonValueCodec[Seq[Trace]] = JsoniterCodecsInternals.tracesCodec
  implicit val layoutCodec: JsonValueCodec[Layout]     = JsoniterCodecsInternals.layoutCodec
  implicit val configCodec: JsonValueCodec[Config]     = JsoniterCodecsInternals.configCodec
}

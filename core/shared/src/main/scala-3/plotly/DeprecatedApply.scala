// On Scala 2, these traits hold the deprecated apply overloads of the companions extending them,
// kept for binary compatibility. These overloads have default arguments, which clash with those
// of the apply method of case classes on Scala 3, where there's no binary compatibility to maintain
// with former versions anyway.

package plotly {
  private[plotly] trait ConfigDeprecatedApply
  private[plotly] trait ScatterDeprecatedApply
  private[plotly] trait BoxDeprecatedApply
  private[plotly] trait BarDeprecatedApply
  private[plotly] trait HistogramDeprecatedApply
  private[plotly] trait SurfaceDeprecatedApply
  private[plotly] trait HeatmapDeprecatedApply
  private[plotly] trait CandlestickDeprecatedApply
}

package plotly.element {
  private[plotly] trait ErrorDataDeprecatedApply
  private[plotly] trait ErrorPercentDeprecatedApply
  private[plotly] trait ErrorConstantDeprecatedApply
  private[plotly] trait LineDeprecatedApply
  private[plotly] trait MarkerDeprecatedApply
}

package plotly.layout {
  private[plotly] trait AnnotationDeprecatedApply
  private[plotly] trait AxisDeprecatedApply
  private[plotly] trait FontDeprecatedApply
  private[plotly] trait LayoutDeprecatedApply
  private[plotly] trait LegendDeprecatedApply
  private[plotly] trait MarginDeprecatedApply
  private[plotly] trait SceneDeprecatedApply
}

package plotly.demo

import plotly.Trace
import plotly.layout.Layout

import scala.collection.immutable.Seq

trait DemoChart {
  def plotlyDocUrl: String
  def id: String
  def source: String
  def data: Seq[Trace]
  def layout: Layout
}

trait NoLayoutDemoChart extends DemoChart {
  final def layout: Layout = null
}

# plotly-scala

Scala bindings for [plotly.js](https://plot.ly/javascript/)

[![Build Status](https://travis-ci.org/alexarchambault/plotly-scala.svg?branch=master)](https://travis-ci.org/alexarchambault/plotly-scala)
[![Join the chat at https://gitter.im/alexarchambault/plotly-scala](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/alexarchambault/plotly-scala?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)
[![Maven Central](https://img.shields.io/maven-central/v/org.plotly-scala/plotly-render_2.13.svg)](https://maven-badges.herokuapp.com/maven-central/org.plotly-scala/plotly-render_2.13)
[![ScalaDoc](http://javadoc-badge.appspot.com/org.plotly-scala/plotly-render_2.13.svg?label=scaladoc)](http://javadoc-badge.appspot.com/org.plotly-scala/plotly-render_2.13)

[Demo](https://alexarchambault.github.io/plotly-scala/)

*plotly-scala* is a Scala library able to output JSON that can be passed to [plotly.js](https://plot.ly/javascript/). Its classes closely follow the API of plotly.js, so that one can use plotly-scala by following the [documentation](https://plot.ly/javascript/) of plotly.js. These classes can be converted to JSON, that can be fed directly to plotly.js.

It can be used from [almond](https://github.com/jupyter-scala/jupyter-scala/tree/develop), from scala-js, or from a Scala REPL like [Ammonite](https://github.com/lihaoyi/Ammonite), to plot things straightaway in the browser.

It runs demos of the plotly.js documentation during its tests, to ensure that it is fine with all their features. That allows it to reliably cover a wide range of the plotly.js features - namely, all the examples of the supported sections of the plotly.js documentation are guaranteed to be fine.

It is published for both scala 2.12 and 2.13.

## Table of content

1. [Quick start](#quick-start)
2. [Rationale](#rationale)
3. [Internals](#internals)
4. [Supported features](#supported-features)

## Quick start

### From almond

Add the `org.plotly-scala::plotly-almond:0.8.1` dependency to the notebook. (Latest version: [![Maven Central](https://img.shields.io/maven-central/v/org.plotly-scala/plotly-render_2.13.svg)](https://maven-badges.herokuapp.com/maven-central/org.plotly-scala/plotly-render_2.13))
Then initialize plotly-scala, and use it, like
```scala
import $ivy.`org.plotly-scala::plotly-almond:0.8.1`

import plotly._
import plotly.element._
import plotly.layout._
import plotly.Almond._

val (x, y) = Seq(
  "Banana" -> 10,
  "Apple" -> 8,
  "Grapefruit" -> 5
).unzip

Bar(x, y).plot()
```

#### JupyterLab
If you're using [JupyterLab](https://jupyterlab.readthedocs.io/en/stable/), you have to install [jupyterlab-plotly](https://plotly.com/python/getting-started/#jupyterlab-support-python-35) to enable support for rendering Plotly charts:
```bash
jupyter labextension install jupyterlab-plotly
```

Also, you may have to install plotlywidget and plotly-extension with `--no-build` option and then build them if graph won't show up and you find a message `ReferenceError: require is not defined` in debug console in browser.

```bash
export NODE_OPTIONS=--max-old-space-size=4096
jupyter labextension install plotlywidget@0.8.0 --no-build
jupyter labextension install @jupyterlab/plotly-extension@0.18.2 --no-build
jupyter lab build
unset NODE_OPTIONS
```

Reference is [here](https://github.com/plotly/plotly_express/issues/38)

### From scala-js

Add the corresponding dependency to your project, like
```scala
libraryDependencies += "org.plotly-scala" %%% "plotly-render" % "0.8.1"
```
(Latest version: [![Maven Central](https://img.shields.io/maven-central/v/org.plotly-scala/plotly-render_2.13.svg)](https://maven-badges.herokuapp.com/maven-central/org.plotly-scala/plotly-render_2.13))

From your code, add some imports for plotly,
```scala
import plotly._, element._, layout._, Plotly._
```

Then define plots like
```scala
val x = (0 to 100).map(_ * 0.1)
val y1 = x.map(d => 2.0 * d + util.Random.nextGaussian())
val y2 = x.map(math.exp)

val plot = Seq(
  Scatter(x, y1).withName("Approx twice"),
  Scatter(x, y2).withName("Exp")
)
```
and plot them with

```scala
val lay = Layout().withTitle("Curves")
plot.plot("plot", lay)  // attaches to div element with id 'plot'
```


### From Ammonite

Load the corresponding dependency, and some imports, like
```scala
import $ivy.`org.plotly-scala::plotly-render:0.8.1`
import plotly._, element._, layout._, Plotly._
```

Then plot things like
```scala
val labels = Seq("Banana", "Banano", "Grapefruit")
val valuesA = labels.map(_ => util.Random.nextGaussian())
val valuesB = labels.map(_ => 0.5 + util.Random.nextGaussian())

Seq(
  Bar(labels, valuesA, name = "A"),
  Bar(labels, valuesB, name = "B")
).plot(
  title = "Level"
)
```


## Rationale

Most high-level Javascript libraries for plotting have well designed APIs, enforcing immutability and almost relying on typed objects, although not explicitly. Yet, the few existing Scala libraries for plotting still try to mimick [matplotlib](http://matplotlib.org/) or Matlab, and have APIs requiring users to mutate things, in order to do plots. They also tend to lack a lot of features, especially compared to the current high-end Javascript plotting libraries. *plotly-scala* aims at filling this gap, by providing a reliable bridge from Scala towards the renowned [plotly.js](https://plot.ly/javascript/).

## Internals

*plotly-scala* consists in a bunch of definitions, mostly case classes and sealed class hierarchies, closely following the API of plotly.js. It also contains JSON codecs for those, allowing to convert them to JSON that can be passed straightaway to plotly.js.

Having the ability to convert these classes to JSON, the codecs can also go the other way around: from plotly.js-compatible JSON to plotly-scala Scala classes. This way of going is used by the tests of plotly-scala, to ensure that the examples of the plotly.js documentation, illustrating a wide range of the features of plotly.js, can be represented via the classes of plotly-scala. Namely, the Javascript examples of the documentation of plotly.js are run inside a [Rhino](https://developer.mozilla.org/en-US/docs/Mozilla/Projects/Rhino) VM, with mocks of the plotly API. These mocks allow to keep the Javascript objects passed to the plotly.js API, and convert them to JSON. These JSON objects are then validated against the codecs of plotly-scala, to ensure that all their fields can be decoded by them. If these are fine, this gives a proof that all the features of the examples have a counterpart in plotly-scala.

Internally, plotly-scala uses [circe](https://github.com/travisbrown/circe) (along with custom codec derivation mechanisms) to convert things to JSON, then render them. The circe objects don't appear in the plotly-scala API - circe is only used internally. The plotly-scala API only returns JSON strings, that can be passed to plotly.js. In subsequent versions, plotly-scala will likely try to shade circe and its dependencies, or switch to a more lightweight JSON library.

## Supported features

plotly-scala supports the features illustrated in the following sections of the plotly.js documentation:
- [Scatter Plots](https://plot.ly/javascript/line-and-scatter/),
- [Bubble Charts](https://plot.ly/javascript/bubble-charts/),
- [Line Charts](https://plot.ly/javascript/line-charts/),
- [Bar Charts](https://plot.ly/javascript/bar-charts/),
- [Horizontal Bar Charts](https://plot.ly/javascript/horizontal-bar-charts/),
- [Filled Area Plots](https://plot.ly/javascript/filled-area-plots/),
- [Time Series](https://plot.ly/javascript/time-series/),
- [Subplots](https://plot.ly/javascript/subplots/),
- [Multiple Axes](https://plot.ly/javascript/multiple-axes/),
- [Histograms](https://plot.ly/javascript/histograms/),
- [Log Plots](https://plot.ly/javascript/log-plot/), 
- [Image](https://plotly.com/javascript/reference/image/).

Some of these are illustrated in the [demo](https://alexarchambault.github.io/plotly-scala/) page.

## Adding support for extra plotly.js features

The following workflow can be followed to add support for extra sections of the plotly.js documentation:
- find the corresponding directory in the [source](https://github.com/alexarchambault/plotly-documentation/tree/eae136bb920c7542654a5e13cff04a0de175a08d/) of the plotly.js documentation. These directories can also be found in the sources of plotly-scala, under `plotly-documentation/_posts/plotly_js`, if its repository has been cloned with the `--recursive` option,
- enabling testing of the corresponding documentation section examples in the `DocumentationTests` class, around [this line](https://github.com/alexarchambault/plotly-scala/blob/master/tests/src/test/scala/plotly/doc/DocumentationTests.scala#L224),
- running the tests with `sbt ~test`,
- fixing the possible Javascript typos in the plotly-documentation submodule in the plotly-scala sources, so that the enabled JS snippets run fine with Rhino from the tests, then committing these fixes, either to [https://github.com/alexarchambault/plotly-documentation](`alexarchambault/plotly-documentation`) or [https://github.com/plotly/documentation](`plotly/documentation`),
- add the required fields / class definitions, and possibly codecs, to have the added tests pass.

## About

Battlefield tested since early 2016 at [Teads.tv](http://teads.tv)

Released under the LGPL v3 license, copyright 2016-2019 Alexandre Archambault and contributors.

Parts based on the original plotly.js API, which is copyright 2016 Plotly, Inc.

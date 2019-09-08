addSbtPlugin("com.geirsson" % "sbt-ci-release" % "1.3.1")
addSbtPlugin("org.scala-js" % "sbt-scalajs" % "0.6.28")
addSbtPlugin("org.portable-scala" % "sbt-scalajs-crossproject" % "0.6.1")
addSbtPlugin("com.jsuereth" % "sbt-pgp" % "1.1.2")
addSbtPlugin("com.typesafe" % "sbt-mima-plugin" % "0.6.0")
addSbtPlugin("io.get-coursier" % "sbt-shading" % sbtCoursierVersion)

addSbtCoursier

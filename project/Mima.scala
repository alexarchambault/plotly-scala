
import com.typesafe.tools.mima.core._
import com.typesafe.tools.mima.plugin.MimaPlugin
import sbt._
import sbt.Keys._

import scala.sys.process._

object Mima {

  lazy val renderFilters = Def.settings(
    MimaPlugin.autoImport.mimaBinaryIssueFilters ++= Seq(
      // users shouln't ever reference those
      ProblemFilters.exclude[Problem]("plotly.internals.shaded.*"),
    )
  )

}

package plotly.internals

import java.util.{Properties => JProperties}

object Properties {

  private lazy val props = {
    val p = new JProperties
    try {
      p.load(
        getClass
          .getClassLoader
          .getResourceAsStream("plotly/plotly-scala.properties")
      )
    }
    catch  {
      case _: NullPointerException =>
    }
    p
  }

  lazy val plotlyJsVersion = props.getProperty("plotly-js-version")
  lazy val version = props.getProperty("version")
  lazy val commitHash = props.getProperty("commit-hash")

}

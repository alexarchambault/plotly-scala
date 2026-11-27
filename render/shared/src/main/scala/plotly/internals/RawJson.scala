package plotly
package internals

import com.github.plokhotnyuk.jsoniter_scala.core._

/** Helpers for the methods accepting "raw" plotly.js figures, passed as JSON strings */
object RawJson {

  private def maxDepth = 1024

  // Reads a JSON value, and discards it.
  // Unlike JsonReader.skip, this checks that the value is valid JSON, rather than only looking
  // for the end of the value.
  private def readValue(in: JsonReader, depth: Int): Unit = {
    if (depth > maxDepth)
      in.decodeError(s"JSON nested more than $maxDepth levels deep")
    in.nextToken() match {
      case '{' =>
        if (!in.isNextToken('}')) {
          in.rollbackToken()
          while ({
            in.readKeyAsString()
            readValue(in, depth + 1)
            in.isNextToken(',')
          }) ()
          if (!in.isCurrentToken('}'))
            in.objectEndOrCommaError()
        }
      case '[' =>
        if (!in.isNextToken(']')) {
          in.rollbackToken()
          while ({
            readValue(in, depth + 1)
            in.isNextToken(',')
          }) ()
          if (!in.isCurrentToken(']'))
            in.arrayEndOrCommaError()
        }
      case '"' =>
        in.rollbackToken()
        in.readString(null)
      case 't' | 'f' =>
        in.rollbackToken()
        in.readBoolean()
      case 'n' =>
        // expects the 'n' token to have been consumed already
        in.readNullOrError((), "expected JSON value")
      case _ =>
        in.rollbackToken()
        in.readDouble()
    }
  }

  // Reads a JSON object, and discards it
  private val objectChecker: JsonValueCodec[Unit] =
    new JsonValueCodec[Unit] {
      def decodeValue(in: JsonReader, default: Unit): Unit =
        if (in.isNextToken('{')) {
          in.rollbackToken()
          readValue(in, 0)
        } else
          in.decodeError("expected a JSON object")
      def encodeValue(x: Unit, out: JsonWriter): Unit =
        throw new UnsupportedOperationException("Not meant to be used for encoding")
      def nullValue: Unit = ()
    }

  /** Checks that `figure` is a JSON object, like a plotly.js figure
    * (`{"data": […], "layout": {…}, "config": {…}}`)
    *
    * @throws IllegalArgumentException
    *   if it isn't
    */
  def checkFigure(figure: String): Unit =
    try readFromString(figure)(objectChecker)
    catch {
      case e: JsonReaderException =>
        throw new IllegalArgumentException(s"Invalid plotly.js figure JSON: ${e.getMessage}", e)
    }

  /** Escapes `json` so that it can be put in an HTML script element, as is
    *
    * `<` can only appear in JSON strings, where it can be replaced by its unicode escape. This
    * prevents things like `"</script>"` in strings from closing the script element.
    */
  def escapeForScript(json: String): String =
    json.replace("<", "\\u003c")
}

package plotly
package element

import dataclass.data

@data final case class TextFont(family: String) extends TextFontSetters

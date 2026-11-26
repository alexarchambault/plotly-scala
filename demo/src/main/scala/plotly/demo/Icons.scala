package plotly.demo

/** Inline SVG icons used by the demo page */
object Icons {

  private def stroked(body: String, viewBox: String = "0 0 24 24") =
    s"""<svg class="icon" viewBox="$viewBox" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" aria-hidden="true">$body</svg>"""

  private def filled(body: String, viewBox: String = "0 0 24 24") =
    s"""<svg class="icon" viewBox="$viewBox" fill="currentColor" aria-hidden="true">$body</svg>"""

  val logo =
    """<svg class="brand-mark" viewBox="0 0 32 32" aria-hidden="true">
      |  <defs>
      |    <linearGradient id="brand-gradient" x1="0" y1="0" x2="1" y2="1">
      |      <stop offset="0" stop-color="#636efa"/>
      |      <stop offset="0.55" stop-color="#ab63fa"/>
      |      <stop offset="1" stop-color="#00cc96"/>
      |    </linearGradient>
      |  </defs>
      |  <rect width="32" height="32" rx="8" fill="url(#brand-gradient)"/>
      |  <g fill="#fff">
      |    <rect x="7" y="17" width="4" height="8" rx="1.5" opacity="0.75"/>
      |    <rect x="14" y="11" width="4" height="14" rx="1.5" opacity="0.9"/>
      |    <rect x="21" y="7" width="4" height="18" rx="1.5"/>
      |  </g>
      |</svg>""".stripMargin

  val line = stroked("""<polyline points="3 17 8.5 11 13 14.5 21 6"/><polyline points="16 6 21 6 21 11"/>""")

  val scatter = filled(
    """<circle cx="5.5" cy="17" r="2"/><circle cx="10" cy="10" r="2"/><circle cx="14.5" cy="14" r="2"/>""" +
      """<circle cx="18.5" cy="6.5" r="2"/><circle cx="19" cy="17.5" r="2"/>"""
  )

  val bars =
    """<svg class="icon" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="3.5" stroke-linecap="round" aria-hidden="true"><path d="M6 20v-7M12 20V5M18 20v-10"/></svg>"""

  val horizontalBars =
    """<svg class="icon" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="3.5" stroke-linecap="round" aria-hidden="true"><path d="M4 6h10M4 12h16M4 18h7"/></svg>"""

  val timeSeries = stroked("""<polyline points="2 12 6 12 9 5 14 19 17 12 22 12"/>""")

  val bubbles = filled(
    """<circle cx="8" cy="14.5" r="5" opacity="0.9"/><circle cx="17" cy="7.5" r="3.2"/><circle cx="18" cy="17" r="2.4" opacity="0.8"/>"""
  )

  val area = filled("""<path d="M3 20V14l5-5 5 4 8-8v15z" opacity="0.9"/>""")

  val heatmap = filled(
    Seq(
      (4, 4, 1.0),
      (10, 4, 0.55),
      (16, 4, 0.3),
      (4, 10, 0.55),
      (10, 10, 1.0),
      (16, 10, 0.55),
      (4, 16, 0.3),
      (10, 16, 0.55),
      (16, 16, 1.0)
    ).map { case (x, y, opacity) =>
      s"""<rect x="$x" y="$y" width="5" height="5" rx="1" opacity="$opacity"/>"""
    }.mkString
  )

  val histogram = filled(
    Seq((3, 14), (7, 8), (11, 4), (15, 9), (19, 15)).map { case (x, y) =>
      s"""<rect x="$x" y="$y" width="3.6" height="${20 - y}" rx="0.8"/>"""
    }.mkString
  )

  val violin = filled(
    """<path d="M12 2c1 3 4 5 4 9s-3 5-3 7 1 3-1 4c-2-1-1-2-1-4s-3-3-3-7 3-6 4-9z"/>"""
  )

  val contour = stroked(
    """<ellipse cx="12" cy="12" rx="10" ry="8"/><ellipse cx="12.5" cy="12" rx="6.5" ry="5"/>""" +
      """<ellipse cx="13" cy="12" rx="3" ry="2.2"/>"""
  )

  val pie = filled(
    """<path d="M11 3a9 9 0 1 0 9 10h-9z"/><path d="M13 1v10h10A10 10 0 0 0 13 1z" opacity="0.7"/>"""
  )

  val github = filled(
    """<path d="M8 0C3.58 0 0 3.58 0 8c0 3.54 2.29 6.53 5.47 7.59.4.07.55-.17.55-.38 0-.19-.01-.82-.01-1.49-2.01.37-2.53-.49-2.69-.94-.09-.23-.48-.94-.82-1.13-.28-.15-.68-.52-.01-.53.63-.01 1.08.58 1.23.82.72 1.21 1.87.87 2.33.66.07-.52.28-.87.51-1.07-1.78-.2-3.64-.89-3.64-3.95 0-.87.31-1.59.82-2.15-.08-.2-.36-1.02.08-2.12 0 0 .67-.21 2.2.82.64-.18 1.32-.27 2-.27.68 0 1.36.09 2 .27 1.53-1.04 2.2-.82 2.2-.82.44 1.1.16 1.92.08 2.12.51.56.82 1.27.82 2.15 0 3.07-1.87 3.75-3.65 3.95.29.25.54.73.54 1.48 0 1.07-.01 1.93-.01 2.2 0 .21.15.46.55.38A8.013 8.013 0 0016 8c0-4.42-3.58-8-8-8z"/>""",
    "0 0 16 16"
  )

  val book = stroked(
    """<path d="M4 19.5A2.5 2.5 0 0 1 6.5 17H20"/><path d="M6.5 2H20v20H6.5A2.5 2.5 0 0 1 4 19.5v-15A2.5 2.5 0 0 1 6.5 2z"/>"""
  )

  val external = stroked(
    """<path d="M18 13v6a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V8a2 2 0 0 1 2-2h6"/><polyline points="15 3 21 3 21 9"/><line x1="10" y1="14" x2="21" y2="3"/>"""
  )

  val search = stroked("""<circle cx="11" cy="11" r="7"/><line x1="21" y1="21" x2="16.65" y2="16.65"/>""")

  val copy = stroked(
    """<rect x="9" y="9" width="13" height="13" rx="2"/><path d="M5 15H4a2 2 0 0 1-2-2V4a2 2 0 0 1 2-2h9a2 2 0 0 1 2 2v1"/>"""
  )
}

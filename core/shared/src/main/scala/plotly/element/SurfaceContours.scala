package plotly
package element

import dataclass.data

/** The contour lines of surface traces, along each axis (`contours` of surface traces in plotly.js) */
@data(
  optionSetters = true,
  nullOptionBuilderMethodName = "from",
  nullOptionCopyMethodName = "updated",
  deprecatedSetters = true,
  deprecatedSettersMessage = "Use updated instead",
  deprecatedSettersSince = "0.8.6"
) final case class SurfaceContours(
    x: Option[SurfaceContour] = None,
    y: Option[SurfaceContour] = None,
    z: Option[SurfaceContour] = None
)

/** The contour lines of a surface along one axis */
@data(
  optionSetters = true,
  nullOptionBuilderMethodName = "from",
  nullOptionCopyMethodName = "updated",
  deprecatedSetters = true,
  deprecatedSettersMessage = "Use updated instead",
  deprecatedSettersSince = "0.8.6"
) final case class SurfaceContour(
    show: Option[Boolean] = None,
    start: Option[Double] = None,
    end: Option[Double] = None,
    size: Option[Double] = None,
    color: Option[Color] = None,
    width: Option[Double] = None,
    // whether the lines are colored with the color scale of the surface
    usecolormap: Option[Boolean] = None,
    // the contour lines highlighted on hover
    highlight: Option[Boolean] = None,
    highlightcolor: Option[Color] = None,
    highlightwidth: Option[Double] = None,
    // projections of the lines on the walls of the scene
    project: Option[Projection] = None
)

/** The walls of the scene something is projected on */
@data(
  optionSetters = true,
  nullOptionBuilderMethodName = "from",
  nullOptionCopyMethodName = "updated",
  deprecatedSetters = true,
  deprecatedSettersMessage = "Use updated instead",
  deprecatedSettersSince = "0.8.6"
) final case class Projection(
    x: Option[Boolean] = None,
    y: Option[Boolean] = None,
    z: Option[Boolean] = None
)

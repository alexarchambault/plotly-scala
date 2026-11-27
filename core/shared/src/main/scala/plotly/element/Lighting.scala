package plotly
package element

import dataclass.data

/** The effects of the light on 3D surfaces (`lighting` in plotly.js) */
@data(
  optionSetters = true,
  nullOptionBuilderMethodName = "from",
  nullOptionCopyMethodName = "updated",
  deprecatedSetters = true,
  deprecatedSettersMessage = "Use updated instead",
  deprecatedSettersSince = "0.8.6"
) final case class Lighting(
    ambient: Option[Double] = None,
    diffuse: Option[Double] = None,
    specular: Option[Double] = None,
    roughness: Option[Double] = None,
    fresnel: Option[Double] = None
)

/** The position of the light of 3D surfaces (`lightposition` in plotly.js) */
@data(
  optionSetters = true,
  nullOptionBuilderMethodName = "from",
  nullOptionCopyMethodName = "updated",
  deprecatedSetters = true,
  deprecatedSettersMessage = "Use updated instead",
  deprecatedSettersSince = "0.8.6"
) final case class LightPosition(
    x: Option[Double] = None,
    y: Option[Double] = None,
    z: Option[Double] = None
)

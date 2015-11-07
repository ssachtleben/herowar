package json.excludes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import models.entity.game.Geometry;

/**
 * Mix-In class entity to json but exclude the geometry object.
 *
 * @author Sebastian Sachtleben
 */
public class ExcludeGeometryMixin {

   @SuppressWarnings("unused")
   @JsonIgnore
   private Geometry geometry;

}

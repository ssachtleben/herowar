package json.excludes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import models.entity.game.Geometry;

/**
 * Mix-In class entity to json but exclude the geometry property for mesh object.
 *
 * @author Alexander Wilhelmer
 */
public class MeshExcludeGeometryMixin {

   @SuppressWarnings("unused")
   @JsonIgnore
   private Geometry geometry;

}

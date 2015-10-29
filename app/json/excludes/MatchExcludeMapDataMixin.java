package json.excludes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import models.entity.game.Geometry;
import models.entity.game.Material;

import java.util.Date;
import java.util.List;


/**
 * Mix-In class entity to json but exclude the map data properties.
 *
 * @author Sebastian Sachtleben
 */
public class MatchExcludeMapDataMixin extends MapDataExcludeMixin {

   @JsonIgnore
   String description;

   @JsonIgnore
   String skybox;

   @JsonIgnore
   Integer teamSize;

   @JsonIgnore
   Integer prepareTime;

   @JsonIgnore
   Integer goldStart;

   @JsonIgnore
   Integer goldPerTick;

   @JsonIgnore
   Date cdate;

   @JsonIgnore
   Date udate;

   @JsonIgnore
   Long version;

   @JsonIgnore
   List<Material> materials;

   @JsonIgnore
   List<Geometry> staticGeometries;

}

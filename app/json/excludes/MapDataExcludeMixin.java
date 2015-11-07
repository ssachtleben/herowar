package json.excludes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import models.entity.game.*;

import java.util.Set;

/**
 * Mix-In class entity to json but exclude the map data properties.
 *
 * @author Alexander Wilhelmer
 */
public class MapDataExcludeMixin {

   @SuppressWarnings("unused")
   @JsonIgnore
   private Terrain terrain;

   @SuppressWarnings("unused")
   @JsonIgnore
   private Set<Tower> towers;

   @SuppressWarnings("unused")
   @JsonIgnore
   private Set<Wave> waves;

   @SuppressWarnings("unused")
   @JsonIgnore
   private Set<Mesh> objects;

   @SuppressWarnings("unused")
   @JsonIgnore
   private Set<Material> allMaterials;

   @SuppressWarnings("unused")
   @JsonIgnore
   private Set<Path> paths;

}

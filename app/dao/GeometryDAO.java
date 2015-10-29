package dao;

import models.entity.game.GeoMaterial;
import models.entity.game.Geometry;
import models.entity.game.Material;

import java.util.ArrayList;
import java.util.HashSet;


/**
 * @author Sebastian Sachtleben
 */
public class GeometryDAO extends BaseDAO<Long, Geometry> {
   public GeometryDAO() {
      super(Long.class, Geometry.class);
   }


   /**
    * For mapping geometry in Client
    *
    * @param Geometry
    */
   public void mapMaterials(Geometry geo) {
      geo.setMatIdMapper(new ArrayList<GeoMatId>());
      for (GeoMaterial geoMat : geo.getGeoMaterials()) {
         Material mat = geoMat.getId().getMaterial();
         GeoMatId id = new GeoMatId();
         id.setMaterialIndex(geoMat.getArrayIndex());
         id.setMaterialId(mat.getId());
         id.setMaterialName(mat.getName());
         geo.getMatIdMapper().add(id);
      }
   }

   public void createGeoMaterials(Geometry geometry, java.util.Map<Integer, Material> mapping) {
      if (geometry.getGeoMaterials() == null)
         geometry.setGeoMaterials(new HashSet<GeoMaterial>());
      for (Integer index : mapping.keySet()) {
         Material mat = mapping.get(index);
         GeoMaterial geoMat = new GeoMaterial();
         geoMat.setId(new GeoMaterial.PK());
         geoMat.getId().setMaterial(mat);
         geoMat.getId().setGeometry(geometry);
         geoMat.setArrayIndex(index.longValue());
         geometry.getGeoMaterials().add(geoMat);
      }
   }
}

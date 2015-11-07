package dao;

import com.google.inject.Inject;
import models.entity.game.Material;
import play.db.jpa.JPA;

import java.util.HashMap;
import java.util.List;

public class MaterialDAO extends BaseDAO<Long, Material> {
   @Inject
   private MaterialDAO materialDAO;

   public MaterialDAO() {
      super(Long.class, Material.class);
   }

   public Material getMaterialbyId(Long id) {
      return this.findUnique(id);
   }

   public Material mergeMaterial(Material material) {
      return this.merge(material);
   }

   public java.util.Map<Integer, Material> mapAndSave(List<Material> materials) {
      java.util.Map<Integer, Material> result = new HashMap<Integer, Material>();
      for (int i = 0; i < materials.size(); i++) {
         Material mat = materials.get(i);
         Material dbMat = null;
         if (mat.getDbgName() != null) {
            // For Geometry materials ...
            dbMat = this.getSingleByPropertyValue("dbgName", mat.getDbgName().trim());
         }
         if (dbMat == null && mat.getId() != null && mat.getId() > -1) {
            dbMat = materialDAO.getMaterialbyId(mat.getId());
         }
         if (dbMat == null) {
            if (mat.getId() != null && mat.getId() < 0) {
               mat.setId(null);
               mat.setSortIndex(i);
            }
            dbMat = mat;
            JPA.em().persist(dbMat);
         } else {
            mat.setId(dbMat.getId());
            dbMat = materialDAO.mergeMaterial(mat);
         }
         result.put(i, dbMat);
      }
      return result;
   }

}

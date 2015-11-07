package dao;


import models.entity.game.Mesh;

public class MeshDAO extends BaseDAO<Long, Mesh> {

   public MeshDAO() {
      super(Long.class, Mesh.class);
   }

}

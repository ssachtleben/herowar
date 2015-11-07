package dao;

import models.entity.game.Tower;

public class TowerDAO extends BaseDAO<Long, Tower> {

   public TowerDAO() {
      super(Long.class, Tower.class);
   }

}

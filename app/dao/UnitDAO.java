package dao;


import models.entity.game.Unit;

public class UnitDAO extends TreeDAO<Long, Unit> {

   public UnitDAO() {
      super(Long.class, Unit.class);
   }

}

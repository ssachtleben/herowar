package dao;


import models.entity.game.Wave;

public class WaveDAO extends BaseDAO<Long, Wave> {

   public WaveDAO() {
      super(Long.class, Wave.class);
   }


}

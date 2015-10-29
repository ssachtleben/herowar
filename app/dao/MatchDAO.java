package dao;

import models.entity.game.Match;
import models.entity.game.MatchState;
import play.db.jpa.JPA;

import java.util.List;

public class MatchDAO extends BaseDAO<Long, Match> {

   public MatchDAO() {
      super(Long.class, Match.class);
   }


   @SuppressWarnings("unchecked")
   public Match getOpenMatch() {
      List<Match> matches = JPA.em()
              .createQuery("SELECT m FROM " + Match.class.getSimpleName() + " m WHERE m.state = :state ORDER BY m.cdate DESC")
              .setParameter("state", MatchState.INIT).setMaxResults(1).getResultList();
      if (matches.size() > 0) {
         return matches.get(0);
      }
      return null;
   }

}

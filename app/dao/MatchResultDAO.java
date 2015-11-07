package dao;

import models.entity.game.MatchResult;
import models.entity.game.MatchState;
import models.entity.game.Player;
import play.db.jpa.JPA;

import java.util.List;

public class MatchResultDAO extends BaseDAO<String, MatchResult> {

   public MatchResultDAO() {
      super(String.class, MatchResult.class);
   }

   @SuppressWarnings("unchecked")
   public List<MatchResult> getHistory(Player player) {
      return JPA.em().createQuery("SELECT mr FROM " + MatchResult.class.getSimpleName()
                  + " mr join mr.player p join mr.match m WHERE p.id = :playerId AND m.state = :state ORDER BY m.cdate DESC")
            .setParameter("playerId", player.getId()).setParameter("state", MatchState.FINISH).setMaxResults(10).getResultList();
   }

   @SuppressWarnings("unchecked")
   public List<MatchResult> findOpen(Player player) {
      return JPA.em().createQuery("SELECT mr FROM " + MatchResult.class.getSimpleName()
                  + " mr join mr.player p join mr.match m WHERE p.id = :playerId AND m.state = :state ORDER BY m.cdate DESC")
            .setParameter("playerId", player.getId()).setParameter("state", MatchState.INIT).getResultList();
   }

}

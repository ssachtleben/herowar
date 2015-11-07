package controllers;

import dao.BaseDAO;
import models.entity.game.MatchToken;
import models.entity.game.Player;
import play.db.jpa.JPA;

import java.util.List;

public class MatchTokenDAO extends BaseDAO<String, MatchToken> {

   public MatchTokenDAO() {
      super(String.class, MatchToken.class);
   }

   public MatchToken getTokenById(String token) {
      return this.findUnique(token);
   }

   @SuppressWarnings("unchecked")
   public MatchToken findValid(Player player) {
      List<MatchToken> tokens = JPA.em()
            .createQuery("SELECT mt FROM " + MatchToken.class.getSimpleName() + " mt JOIN mt.player p WHERE p.id = :playerId AND mt.invalid = FALSE")
            .setParameter("playerId", player.getId()).getResultList();
      if (tokens.size() > 0) {
         return tokens.get(0);
      }
      return null;
   }

}

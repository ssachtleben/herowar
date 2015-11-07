package controllers;

import models.entity.game.Player;
import play.db.jpa.Transactional;
import play.mvc.Result;

/**
 * The Players controller handle api requests for the Player model.
 *
 * @author Sebastian Sachtleben
 * @author Alexander Wilhelmer
 */
public class Players extends BaseAPI<Long, Player> {

   public Players() {
      super(Long.class, Player.class);
   }

   @Transactional
   public Result show(Long id) {
      return this.show(id);
   }
}
package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.entity.User;
import network.Connection;
import play.db.jpa.JPA;
import play.mvc.Result;
import views.html.game;

public class Game extends BaseController {

   public Result index() {
      return ok(game.render());
   }

   public play.mvc.WebSocket<JsonNode> data() {
      try {
         return JPA.withTransaction(() -> {
            final User user = Application.getLocalUser(session());
            return new Connection(request().remoteAddress(), user);
         });
      }
      catch (Throwable e) {
         log().error("Exception occured during creation of websocket", e);
      }
      return null;
   }

}

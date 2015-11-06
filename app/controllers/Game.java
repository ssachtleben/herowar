package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.entity.User;
import network.Connection;
import play.db.jpa.JPA;
import play.libs.F;
import play.mvc.Result;
import play.mvc.WebSocket;
import views.html.game;

/**
 * Provides all /game related routes.
 *
 * @author Sebastian Sachtleben
 */
public class Game extends BaseController {

    /**
     * Render game index view.
     * <p>
     * The game itself is a single page application and don't need additional routes.
     * </p>
     *
     * @return The {@link Result} object.
     */
    public Result index() {
        return ok(game.render());
    }

    /**
     * Provides socket connection for exchanging game data.
     * <p>
     * This {@link play.mvc.WebSocket} only allow to send and receive {@link JsonNode} objects. Any other object type will result discoonect
     * the connection.
     * </p>
     *
     * @return The {@link play.mvc.WebSocket} object.
     */
    public play.mvc.WebSocket<JsonNode> data() {
        try {
            return JPA.withTransaction(new F.Function0<WebSocket<JsonNode>>() {
                @Override
                public play.mvc.WebSocket<JsonNode> apply() throws Throwable {
                    final User user = Application.getLocalUser(session());
                    return new Connection(request().remoteAddress(), user);
                }
            });
        } catch (Throwable e) {
            log().error("Exception occured during creation of websocket", e);
        }
        return null;
    }

}

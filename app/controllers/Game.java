package controllers;

import play.mvc.Result;
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

}

package controllers;

import models.entity.User;
import models.entity.game.PlayerSettings;
import play.db.jpa.Transactional;
import play.mvc.Result;

import java.lang.reflect.InvocationTargetException;

import static play.libs.Json.toJson;

/**
 * The Settings controller handle api requests for the PlayerSettings model of the current logged in user.
 *
 * @author Sebastian Sachtleben
 */
public class Settings extends BaseAPI<Long, PlayerSettings> {

    public Settings() {
        super(Long.class, PlayerSettings.class);
    }

    @Transactional
    public Result show() {
        PlayerSettings settings = getMySettings();
        if (settings == null) {
            return badRequest();
        }
        return ok(toJson(settings));
    }

    @Transactional
    public Result update() {
        PlayerSettings settings = getMySettings();
        if (settings == null) {
            return badRequest();
        }
        try {
            Object result = update(settings);
            if (result != null) {
                return ok(toJson(result));
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return badRequest();
    }

    private PlayerSettings getMySettings() {
        User user = Application.getLocalUser(session());
        if (user != null && user.getPlayer() != null && user.getPlayer().getSettings() != null) {
            return user.getPlayer().getSettings();
        }
        return null;
    }

}

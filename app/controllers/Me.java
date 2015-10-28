package controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import dao.UserDAO;
import models.User;
import play.Logger;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import java.io.IOException;

import static play.libs.Json.toJson;

/**
 * The Me controller allows our application to get information about the current logged in user.
 *
 * @author Sebastian Sachtleben
 */
public class Me extends Controller {
    private static final Logger.ALogger log = Logger.of(Me.class);

    @Inject
    UserDAO userDAO;

    public Result show() {
        User user = getLoggedInUser();
        if (user != null) {
            ObjectMapper mapper = new ObjectMapper();
            //mapper.getSerializationConfig().addMixInAnnotations(MatchResult.class, MatchResultSimpleMixin.class);
            try {
                return ok(mapper.writeValueAsString(user));
            } catch (IOException e) {
                log.error("Failed to create me json:", e);
            }
        }
        return ok("{}");
    }

    public Result checkUsername(String username) {
        return ok(toJson(userDAO.findByUsername(username) != null));
    }

    public Result checkEmail(String email) {
        return ok(toJson(userDAO.findByEmail(email) != null));
    }

    private User getLoggedInUser() {
        return Application.getLocalUser(session());
    }

}

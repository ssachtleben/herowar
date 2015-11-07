package controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import dao.EmailDAO;
import dao.UserDAO;
import models.entity.User;
import play.Logger;
import play.db.jpa.Transactional;
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
   EmailDAO emailDAO;

   @Inject
   UserDAO userDAO;

   /**
    * Returns the current logged in {@link User} entity json serialized.
    *
    * @return The json serialized logged in user.
    */
   @Transactional
   public Result show() {
      User user = Application.getLocalUser(session());
      if (user != null) {
         ObjectMapper mapper = new ObjectMapper();
         //mapper.getSerializationConfig().addMixInAnnotations(MatchResult.class, MatchResultSimpleMixin.class);
         try {
            return ok(mapper.writeValueAsString(user));
         }
         catch (IOException e) {
            log.error("Failed to create me json:", e);
         }
      }
      return ok("{}");
   }

   /**
    * Checks if the given username is available.
    *
    * @param username The username to check.
    * @return available boolean
    */
   @Transactional
   public Result checkUsername(String username) {
      return ok(toJson(userDAO.findByUsername(username) != null));
   }

   /**
    * Checks if the given email is available.
    *
    * @param email The email to check.
    * @return available boolean
    */
   @Transactional
   public Result checkEmail(String email) {
      return ok(toJson(emailDAO.findByAddress(email) != null));
   }

}

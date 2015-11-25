package controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssachtleben.play.plugin.auth.Auth;
import com.ssachtleben.play.plugin.auth.exceptions.AuthenticationException;
import com.ssachtleben.play.plugin.auth.models.PasswordEmailAuthUser;
import com.ssachtleben.play.plugin.auth.providers.PasswordEmail;
import core.MailService;
import dao.EmailDAO;
import dao.UserDAO;
import json.excludes.MatchResultSimpleMixin;
import models.entity.Email;
import models.entity.User;
import models.entity.game.MatchResult;
import play.Logger;
import play.db.jpa.Transactional;
import play.mvc.Result;

import javax.inject.Inject;
import java.io.IOException;
import java.util.Map;

import static play.libs.Json.toJson;

/**
 * The Me controller allows our application to get information about the current logged in user.
 *
 * @author Sebastian Sachtleben
 */
public class Me extends BaseController {

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
         mapper.addMixInAnnotations(MatchResult.class, MatchResultSimpleMixin.class);
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

   /**
    * Creates a new user with the given parameters from the request.
    *
    * @return Result of login process.
    * @throws AuthenticationException
    */
   @Transactional
   public Result signup() throws AuthenticationException {
      final Map<String, Object> params = getDataFromRequest();
      final User user = UserDAO.instance().create(new PasswordEmailAuthUser(params.get("email").toString(),
              params.get("password").toString(), new ObjectMapper().createObjectNode()),
              params.get("username").toString(), null, null);
      if (user != null) {
         final boolean newsletter = Boolean.parseBoolean(params.get("newsletter").toString());
         if (newsletter == true) {
            log().info(String.format("%s join the newsletter", user));
            user.setNewsletter(newsletter);
         }
         log().info(String.format("Created %s", user));
         MailService.instance().sendEmailConfirmation(getHost(), user.getEmails().iterator().next());
      }
      return Auth.login(ctx(), PasswordEmail.KEY);
   }

   @Transactional
   public Result emailConfirm(final String code) {
      final Email email = EmailDAO.instance().findByConfirmCode(code);
      if (email != null) {
         email.setConfirmed(true);
         log().info(String.format("%s confirmed via code %s", email, code));
      }
      return redirect(routes.Application.site());
   }


}

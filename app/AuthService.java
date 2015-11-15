import com.fasterxml.jackson.databind.JsonNode;
import com.ssachtleben.play.plugin.auth.annotations.Authenticates;
import com.ssachtleben.play.plugin.auth.models.*;
import com.ssachtleben.play.plugin.auth.providers.BaseProvider.AuthEvents;
import com.ssachtleben.play.plugin.auth.providers.Facebook;
import com.ssachtleben.play.plugin.auth.providers.Google;
import com.ssachtleben.play.plugin.auth.providers.PasswordEmail;
import com.ssachtleben.play.plugin.event.annotations.Observer;
import controllers.Application;
import dao.LinkedServiceDAO;
import dao.UserDAO;
import models.entity.LinkedService;
import models.entity.User;
import org.apache.commons.lang3.StringUtils;
import play.Logger;
import play.Play;
import play.mvc.Controller;
import play.mvc.Http.Context;

/**
 * Handles authentication for different providers.
 *
 * @author Sebastian Sachtleben
 */
public class AuthService extends Controller {
   private static final Logger.ALogger log = Logger.of(AuthService.class);

   @Authenticates(provider = PasswordEmail.KEY)
   public static Object handleUsernameLogin(final Context ctx, final PasswordEmailAuthUser identity) {
      log.info(String.format("~~~ handleUsernameLogin() [ctx=%s, identity=%s] ~~~", ctx, identity));
      log.info("Email: " + identity.email());
      log.info("Password: " + identity.clearPassword());
      log.info("HashedPW: " + identity.id());
      LinkedService account = LinkedServiceDAO.instance().findByEmail(identity.email());
      log.info("Account: " + account);
      if (account == null) {
         return null;
      }
      log.info("Check PW: " + PasswordUsernameAuthUser.checkPassword(account.getIdentifier(), identity.clearPassword()));
      return PasswordUsernameAuthUser.checkPassword(account.getIdentifier(), identity.clearPassword()) ? account.getUser().getId() : null;
   }

   @Authenticates(provider = Google.KEY)
   public static Object handleGoogleLogin(final Context ctx, final GoogleAuthUser identity) {
      log.info(String.format("~~~ handleGoogleLogin() [ctx=%s, identity=%s] ~~~", ctx, identity));
      final JsonNode data = identity.data();
      log.debug(String.format("Data: %s", data));
      final String email = data.get("email").asText();
      final String username = data.get("given_name").asText();
      final String avatar = data.has("picture") ? data.get("picture").asText() : null;
      log.debug("Email: " + email);
      log.debug("Username: " + username);
      log.debug("Avatar: " + avatar);
      if (StringUtils.isBlank(email)) {
         return null;
      }
      final Object userId = handleLogin(identity, email, username, avatar);
      if (userId != null) {
         final LinkedServiceDAO linkedServiceDAO = Play.application().injector().instanceOf(LinkedServiceDAO.class);
         final LinkedService linkedService = linkedServiceDAO.find(identity.provider(), identity.id());
         if (data.has("profile")) {
            linkedService.setLink(data.get("profile").asText());
         }
         if (data.has("name")) {
            linkedService.setName(data.get("name").asText());
         }
      }
      return userId;
   }

   @Authenticates(provider = Facebook.KEY)
   public static Object handleFacebookLogin(final Context ctx, final FacebookAuthUser identity) {
      log.debug(String.format("~~~ handleFacebookLogin() [ctx=%s, identity=%s] ~~~", ctx, identity));
      final JsonNode data = identity.data();
      log.debug(String.format("Data: %s", data));
      final String email = data.get("email").asText();
      final String username = data.get("first_name").asText();
      final String avatar = data.has("picture") ? data.get("picture").get("data").get("url").asText() : null;
      log.debug("Email: " + email);
      log.debug("Username: " + username);
      log.debug("Avatar: " + avatar);
      if (StringUtils.isBlank(email)) {
         return null;
      }
      final Object userId = handleLogin(identity, email, username, avatar);
      if (userId != null) {
         final LinkedServiceDAO linkedServiceDAO = Play.application().injector().instanceOf(LinkedServiceDAO.class);
         final LinkedService linkedService = linkedServiceDAO.find(identity.provider(), identity.id());
         if (data.has("link")) {
            linkedService.setLink(data.get("link").asText());
         }
         if (data.has("first_name") && data.has("last_name")) {
            linkedService.setName(data.get("first_name").asText() + " " + data.get("last_name").asText());
         }
      }
      return userId;
   }

   public static Object handleLogin(final Identity identity, final String emailAddress, final String username, final String avatar) {
      log.info(String.format("handleLogin [identity=%s, email=%s, username=%s, avatar=%s]", identity, emailAddress, username, avatar));
      User user = Application.getLocalUser();
      final LinkedServiceDAO linkedServiceDAO = Play.application().injector().instanceOf(LinkedServiceDAO.class);
      LinkedService linkedService = linkedServiceDAO.find(identity.provider(), identity.id());
      log.debug(String.format("Found LinkedService: %s", linkedService));
      if (linkedService == null) {
         log.debug(String.format("No linked account found for %s", identity));
         if (username != null) {
            if (user == null) {
               final UserDAO userDAO = Play.application().injector().instanceOf(UserDAO.class);
               user = userDAO.create(username, emailAddress, avatar);
               log.debug(String.format("Created new user: %s", user));
            }
            linkedService = linkedServiceDAO.create(identity.provider(), identity.id(), user);
         }
      } else {
         if (user != null) {
            // TODO: transport error message here...
            return null;
         }
         user = linkedService.getUser();
      }
      log.debug(String.format("Found user: %s", user));
      // updateUserData(avatar);
      if (identity instanceof OAuthAuthUser) {
         updateAccessToken((OAuthAuthUser) identity, linkedService.getId());
      }
      return user != null ? user.getId() : null;
   }

   /**
    * Save current token and the expire in time if exists in the
    * {@link LinkedService} object.
    *
    * @param authIdentity    The OAuthAuthUser identity.
    * @param linkedServiceId The current used linkedServiceId.
    */
   private static void updateAccessToken(final OAuthAuthUser authIdentity, final Long linkedServiceId) {
      //        final LinkedService linkedService = Ebean.find(LinkedService.class, linkedServiceId);
      //        linkedService.token = authIdentity.token().getToken();
      //        linkedService.expires = authIdentity.expires();
      //        linkedService.data = Json.stringify(authIdentity.data());
      //        linkedService.update();
   }

   /**
    * This method will be called when a user finished the authentication
    * process.
    *
    * @param ctx      The HTTP Context.
    * @param userId   The user id.
    * @param provider The provider key.
    */
   @Observer(topic = AuthEvents.AUTHENTICATION_SUCCESS)
   public static void authenticationSuccessful(final Context ctx, final Long userId, final String provider) {
      final User user = Application.getLocalUser(ctx.session());
      if (user != null) {
         log.debug(String.format("Authentication success: " + user.toString()));
         //            user.lastLogin = new Date();
         //            user.lastIp = ctx.request().remoteAddress();
         //            user.update();
      }
   }
}

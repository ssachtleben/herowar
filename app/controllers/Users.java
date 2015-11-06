package controllers;

import models.entity.User;
import play.data.Form;
import play.db.jpa.Transactional;
import play.mvc.Result;

import java.util.UUID;

import static play.libs.Json.toJson;

/**
 * Handles all api routes for the {@link User} entity.
 *
 * @author Alexander Wilhelmer
 * @author Sebastian Sachtleben
 */
public class Users extends BaseAPI<Long, User> {

   /**
    * Default constructor to allow injections.
    */
   public Users() {
      super(Long.class, User.class);
   }

   /**
    * Creates a new session id if not exists and saves in the session scope.
    */
   public void updateSessionId() {
      if (!session().containsKey("sid")) {
         final String sessionId = UUID.randomUUID().toString();
         session("sid", sessionId);
      }
   }

   /**
    * Creates a new session id if not exists, saves in the session scope and returns the id.
    *
    * @return The session id
    */
   public String getSessionId() {
      updateSessionId();
      return session("sid");
   }

   /**
    * Update the {@link User} entity from the data from the request.
    * TODO: permissions check ?!?
    *
    * @param id
    *             The id of the user (is taken from the request?!?)
    * @return The user as json object.
    */
   @Transactional
   public Result update(final Long id) {
      final User user = this.merge(Form.form(User.class).bindFromRequest().get());
      return ok(toJson(user));
   }

}

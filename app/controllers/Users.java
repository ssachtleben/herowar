package controllers;

import play.data.Form;
import play.db.jpa.Transactional;
import play.mvc.Result;

import java.util.UUID;

import static play.libs.Json.toJson;

/**
 * @author Alexander Wilhelmer
 */
public class Users extends BaseAPI<Long, Users> {

   private Users() {
      super(Long.class, Users.class);
   }

   public void updateSessionId() {
      if (!session().containsKey("sid")) {
         final String sessionId = UUID.randomUUID().toString();
         session("sid", sessionId);
      }
   }

   public String getSessionId() {
      return session("sid");
   }

   @Transactional
   public Result update(Long id) {
      Users user = this.merge(Form.form(Users.class).bindFromRequest().get());
      return ok(toJson(user));
   }


}

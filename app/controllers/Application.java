package controllers;

import com.ssachtleben.play.plugin.auth.Auth;
import dao.UserDAO;
import models.entity.User;
import org.apache.commons.lang3.math.NumberUtils;
import play.mvc.Http;
import play.mvc.Result;
import views.html.admin;
import views.html.editor;
import views.html.game;
import views.html.site;

public class Application extends BaseController {

   public static final String USER_ROLE = "user";
   public static final String ADMIN_ROLE = "admin";

   public static String[] roles = { USER_ROLE, ADMIN_ROLE };

   public static User getLocalUser(final Http.Session session) {
      final String userId = Auth.getLoggedIn(session());
      UserDAO userDAO = new UserDAO();
      final User user = NumberUtils.isNumber(userId) ? userDAO.getById(Long.parseLong(userId)) : null;
      return user;
   }

   public static User getLocalUser() {
      return getLocalUser(session());
   }

   public Result admin() {
      return ok(admin.render());
   }

   public Result editor() {
      return ok(editor.render());
   }

   public Result game() {
      return ok(game.render());
   }

   public Result site() {
      return ok(site.render());
   }

}

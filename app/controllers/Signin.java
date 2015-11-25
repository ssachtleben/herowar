package controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssachtleben.play.plugin.auth.Auth;
import com.ssachtleben.play.plugin.auth.Providers;
import com.ssachtleben.play.plugin.auth.exceptions.AuthenticationException;
import com.ssachtleben.play.plugin.auth.models.PasswordEmailAuthUser;
import com.ssachtleben.play.plugin.auth.providers.BaseOAuthProvider;
import com.ssachtleben.play.plugin.auth.providers.PasswordEmail;
import dao.UserDAO;
import models.entity.User;
import play.Play;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.Result;

import java.util.Map;

public class Signin extends BaseController {

   @SuppressWarnings("rawtypes")
   public Result url(final String provider) {
      final BaseOAuthProvider p = (BaseOAuthProvider) Providers.get(provider);
      if (p == null) {
         log().error("Failed to find provider for key " + provider);
         return redirect(routes.Application.site());
      }
      final String authUrl = p.authUrl();
      log().info("Auth url for " + provider + ": " + authUrl);
      return redirect(authUrl);
   }

   public Result login() throws AuthenticationException {
      return redirect(routes.Signin.url("facebook"));
   }

   @Transactional
   public Result auth(final String provider) throws AuthenticationException {
      log().info("Auth for provider: " + provider);
      return Auth.login(ctx(), provider);
   }

   public Result logout() {
      Auth.logout(session());
      return redirect(routes.Application.site());
   }

   public Result success(final String provider) {
      if (PasswordEmail.KEY.equals(provider)) {
         return Play.application().injector().instanceOf(Application.class).site();
      }
      return redirect(routes.Application.site());
   }

   public Result error(final String provider) {
      log().warn("Login via " + provider + " failed");
      return redirect(routes.Application.site());
   }

}

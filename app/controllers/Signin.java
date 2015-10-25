package controllers;

import com.ssachtleben.play.plugin.auth.Auth;
import com.ssachtleben.play.plugin.auth.Providers;
import com.ssachtleben.play.plugin.auth.exceptions.AuthenticationException;
import com.ssachtleben.play.plugin.auth.providers.BaseOAuthProvider;
import play.mvc.Result;


public class Signin extends BaseController {
   @SuppressWarnings("rawtypes")
   public Result url(final String provider) {
      final BaseOAuthProvider p = (BaseOAuthProvider) Providers.get(provider);
      if (p == null) {
         log().error("Failed to find provider for key " + provider);
         return redirect(routes.Application.index());
      }
      final String authUrl = p.authUrl();
      log().info("Auth url for " + provider + ": " + authUrl);
      return redirect(authUrl);
   }

   public Result login() throws AuthenticationException {
      return redirect(routes.Signin.url("facebook"));
   }

   public Result auth(final String provider) throws AuthenticationException {
      return Auth.login(ctx(), provider);
   }

   public Result logout() {
      Auth.logout(session());
      return redirect(routes.Application.index());
   }

   public Result success(final String provider) {
      return redirect(routes.Application.index());
   }

   public Result error(final String provider) {
      log().warn("Login via " + provider + " failed");
      return redirect(routes.Application.index());
   }


}

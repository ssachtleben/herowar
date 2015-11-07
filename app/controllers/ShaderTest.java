package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.shadertest;

public class ShaderTest extends Controller {

   public  Result index() {
      return ok(shadertest.render());
   }
}

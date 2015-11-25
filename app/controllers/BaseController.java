package controllers;

import play.Logger;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Controller;

import java.util.Map;

/**
 * The BaseController provides several base features for the controllers for
 *
 * @author Sebastian Sachtleben
 */
public class BaseController extends Controller {
   /**
    * The internal logger instance.
    */
   private final Logger.ALogger logger = Logger.of(getClass());

   /**
    * Gets the logger for the current class.
    *
    * @return The {@link ALogger} instance.
    */
   public Logger.ALogger log() {
      return logger;
   }

   @SuppressWarnings("unchecked")
   protected Map<String, Object> getDataFromRequest() {
      DynamicForm.Dynamic form = Form.form().bindFromRequest().get();
      if (form != null && form.getData() != null) {
         return form.getData();
      }
      return null;
   }

   protected String getHost() {
      return "http://" + request().host();
   }

}

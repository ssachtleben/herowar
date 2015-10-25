package controllers;

import play.Logger;
import play.mvc.Controller;

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

}

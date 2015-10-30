package jobs;

import jobs.utils.EntityImporter;
import models.entity.game.Environment;

import java.io.File;


/**
 * Syncronizes javascript files from "public/geometries/environment" with {@link Environment} models.
 *
 * TODO I Dont know ...
 * @author Sebastian Sachtleben
 */

public class EnvironmentImporter extends EntityImporter<Environment> {

   public String getBaseFolder() {
      return "public" + File.separator + "geometries" + File.separator + "environment";
   }

   @Override
   protected void process() {

   }
}

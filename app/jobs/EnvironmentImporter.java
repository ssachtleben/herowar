package jobs;

import jobs.utils.EntityImporter;
import models.entity.game.Environment;

import java.io.File;
import java.net.URI;


/**
 * Syncronizes javascript files from "public/geometries/environment" with {@link Environment} models.
 * <p>
 *
 * @author Sebastian Sachtleben
 */

public class EnvironmentImporter extends EntityImporter<Environment> {


   @Override
   protected void process() {

      this.importFromPath(URI.create("public" + File.separator + "geometries" + File.separator + "environment"), true);

   }


}

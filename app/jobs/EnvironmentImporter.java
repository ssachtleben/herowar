package jobs;

import jobs.utils.EntityImporter;
import models.entity.game.Environment;

import java.io.File;
import java.nio.file.Paths;


/**
 * Syncronizes javascript files from "public/geometries/environment" with {@link Environment} models.
 * <p>
 *
 * @author Sebastian Sachtleben
 */

public class EnvironmentImporter extends EntityImporter<Environment> {


   @Override
   protected void process() {

      this.importFromPath(Paths.get("public" + File.separator + "geometries" + File.separator + "environment"), true);

   }


}

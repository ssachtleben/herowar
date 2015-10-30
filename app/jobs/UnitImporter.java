package jobs;

import jobs.utils.EntityImporter;
import models.entity.game.Unit;

import java.io.File;
import java.net.URI;


/**
 * Syncronizes javascript files from "public/geometries/units" with {@link Unit} models.
 *
 * @author Sebastian Sachtleben
 */
public class UnitImporter extends EntityImporter<Unit> {


   @Override
   protected void process() {
      this.importFromPath(URI.create("public" + File.separator + "geometries" + File.separator + "units"), true);
   }
}

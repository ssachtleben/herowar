package jobs;

import jobs.utils.EntityImporter;
import models.entity.game.Unit;

import java.io.File;


/**
 * Syncronizes javascript files from "public/geometries/units" with {@link Unit} models.
 *
 * @author Sebastian Sachtleben
 */
public class UnitImporter extends EntityImporter<Unit> {


   public String getBaseFolder() {
      return "public" + File.separator + "geometries" + File.separator + "units";
   }

   @Override
   protected void process() {

   }
}

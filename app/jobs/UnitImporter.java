package jobs;

import com.ssachtleben.play.plugin.cron.annotations.StartJob;
import jobs.utils.EntityImporter;
import jobs.utils.FolderImporter;
import models.entity.game.Unit;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;


/**
 * Syncronizes javascript files from "public/geometries/units" with {@link Unit} models.
 *
 * @author Sebastian Sachtleben
 */
@StartJob(async=true)
public class UnitImporter extends FolderImporter<Unit> {

   @Override
   public Path getFolderPath() {
      return Paths.get("public" + File.separator + "geometries" + File.separator + "units");
   }

}

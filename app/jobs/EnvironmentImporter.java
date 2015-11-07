package jobs;

import com.ssachtleben.play.plugin.cron.annotations.StartJob;
import jobs.utils.FolderImporter;
import models.entity.game.Environment;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Syncronizes javascript files from "public/geometries/environment" with {@link Environment} models.
 * <p>
 *
 * @author Sebastian Sachtleben
 */
@StartJob(async = true)
public class EnvironmentImporter extends FolderImporter<Environment> {

   @Override
   public Path getFolderPath() {
      return Paths.get("public" + File.separator + "geometries" + File.separator + "environment");
   }

}

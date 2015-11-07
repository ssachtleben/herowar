package jobs;

import com.ssachtleben.play.plugin.cron.annotations.StartJob;
import jobs.utils.FolderImporter;
import models.entity.game.Tower;
import play.db.jpa.JPA;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Syncronizes javascript files from "public/geometries/towers" with {@link Tower} models.
 *
 * @author Sebastian Sachtleben
 */
@StartJob(async = true)
public class TowerImporter extends FolderImporter<Tower> {

   @Override
   public Path getFolderPath() {
      return Paths.get("public" + File.separator + "geometries" + File.separator + "towers");
   }

   @Override
   protected Tower createEntry(String name, Tower parent) {
      Tower entry = super.createEntry(name, parent);
      if (entry != null) {
         if (!JPA.em().contains(entry)) {
            JPA.em().persist(entry);
         } else {
            entry = JPA.em().merge(entry);
         }
      }
      return entry;
   }

}

package jobs;

import jobs.utils.EntityImporter;
import org.reflections.Reflections;
import play.Logger;

import java.util.Set;

/**
 * BaseImporter, loads all importer and calls run
 *
 * @author Alexander Wilhelmer
 */
public class BaseImporter {
   private static final Logger.ALogger log = Logger.of(BaseImporter.class);

   public void run() {
      Reflections reflections = new Reflections("jobs");
      Set<Class<? extends EntityImporter>> classes = reflections.getSubTypesOf(EntityImporter.class);

      // TODO implement hyrachie load ...

      log.info(String.format("BaseImporter found %s Importer classes", classes.size()));

      for (Class<? extends EntityImporter> clazz : classes) {
         try {
            log.info(String.format("Start Importer Class %s", clazz.getName()));
            clazz.newInstance().run();
         } catch (Exception e) {
            log.error("JOB ERROR:", e);
         }
      }
   }
}

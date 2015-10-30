package jobs;

import jobs.utils.DependsOn;
import jobs.utils.EntityImporter;
import org.reflections.Reflections;
import play.Logger;

import java.util.ArrayList;
import java.util.List;
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

      log.info(String.format("BaseImporter found %s Importer classes", classes.size()));

      List<Class<? extends EntityImporter>> sortedList = new ArrayList<>(classes);

      //TODO tooo easy sorted!
      sortedList.stream().sorted((c1, c2) -> new Integer(c1.getAnnotation(DependsOn.class) != null ? 1 : 0)
              .compareTo(new Integer(c2.getAnnotation(DependsOn.class) != null ? 1 : 0))).forEach(c1 -> {
                 try {
                    c1.newInstance().run();
                 } catch (InstantiationException | IllegalAccessException e) {
                    log.error("JOB ERROR:", e);
                 }
              }
      );
   }
}

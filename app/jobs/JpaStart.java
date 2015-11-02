package jobs;

import com.ssachtleben.play.plugin.cron.annotations.StartJob;
import com.ssachtleben.play.plugin.cron.jobs.SimpleJob;
import play.Logger;
import play.db.jpa.JPA;

/**
 * Initialize JPA transaction.
 *
 * @author Sebastian Sachtleben
 */
@StartJob(active = false)
public class JpaStart extends SimpleJob {

   private static final Logger.ALogger log = Logger.of(JpaStart.class);

   @Override
   public void run() {
      log.info("Initialize JPA");
      JPA.withTransaction(() -> log.info("First JPA call"));
   }

}

package jobs;

import com.ssachtleben.play.plugin.cron.annotations.CronJob;
import com.ssachtleben.play.plugin.cron.jobs.SimpleJob;
import play.Logger;

/**
 * This cronjob runs every midnight and cleans not by email confirmed accounts which are older than 24h.
 *
 * @author Sebastian Sachtleben
 */
@CronJob(pattern = "* * * * * ?", active = false)
public class RegistrationCleaner extends SimpleJob {

    private static final Logger.ALogger log = Logger.of(RegistrationCleaner.class);

    @Override
    public void run() {
        log.info("Running RegistrationCleaner ...");
    }
}

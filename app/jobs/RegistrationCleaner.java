package jobs;

import com.ssachtleben.play.plugin.cron.annotations.CronJob;
import com.ssachtleben.play.plugin.cron.jobs.SimpleJob;
import dao.EmailDAO;
import models.entity.Email;
import models.entity.User;
import play.Logger;
import play.db.jpa.JPA;

import java.util.List;

/**
 * <p>This cronjob cleans not by email confirmed accounts which are older than 24h.</p>
 *
 * <p>TODO: Pattern for later: 0 0 12 * * ? - Fire at 12pm (noon) every day</p>
 *
 * @author Sebastian Sachtleben
 */
@CronJob(pattern = "0 * * * * ?", active = true)
public class RegistrationCleaner extends SimpleJob {

    private static final Logger.ALogger log = Logger.of(RegistrationCleaner.class);

    @Override
    public void run() {
        log.debug("Running registration cleaner job ...");
        JPA.withTransaction(() -> {
            final List<Email> emails = EmailDAO.instance().getNotConfirmed(24);
            if (emails.size() == 0) {
                log.debug("Found no unconfirmed emails older than 24 hours");
                return;
            }
            log.info(String.format("Found unconfirmed emails older than 24 hours: %s", emails));
            for (Email email : emails) {
                final User user = email.getUser();
                if (user.getEmails().size() <= 1) {
                    // Delete also user since no confirmed email
                    log.info(String.format("Delete user: %s", user));
                    JPA.em().remove(user);
                } else {
                    // Just delete this email
                    log.info(String.format("Delete email: %s", email));
                    JPA.em().remove(email);
                }
            }
        });
    }
}

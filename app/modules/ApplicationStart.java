package modules;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import play.Application;
import play.Environment;
import play.Logger;
import play.db.jpa.JPA;
import play.libs.F;

/**
 * Execute code during application start.
 *
 * @author Sebastian Sachtleben
 */
@Singleton
public class ApplicationStart {

    private static final Logger.ALogger log = Logger.of(ApplicationStart.class);

    @Inject
    public ApplicationStart(final Environment env) {
        onStart(env);
    }

    private void onStart(final Environment env) {
        log.info("Executing startup tasks for mode " + env.mode());
        /**
        JPA.withTransaction(new F.Callback0() {
            @Override
            public void invoke() throws Throwable {
                log.info("First JPA call");
            }
        });
         **/
        log.info("Herowar stated");
    }

}

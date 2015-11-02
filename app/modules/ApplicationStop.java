package modules;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import play.Environment;
import play.Logger;
import play.db.jpa.JPA;
import play.inject.ApplicationLifecycle;
import play.libs.F;

/**
 * Execute code during application shutdown.
 *
 * @author Sebastian Sachtleben
 */
@Singleton
public class ApplicationStop {

    private static final Logger.ALogger log = Logger.of(ApplicationStop.class);

    @Inject
    public ApplicationStop(final ApplicationLifecycle lifecycle) {
        lifecycle.addStopHook(() -> {
            onStop();
            return F.Promise.pure(null);
        });
    }

    private void onStop() {
        log.info("Application shutdown...");
    }

}

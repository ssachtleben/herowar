import jobs.BaseImporter;
import play.Application;
import play.GlobalSettings;
import play.Logger;
import play.db.jpa.JPA;
import play.libs.F;
import play.mvc.Action;
import play.mvc.Http.Request;

import java.lang.reflect.Method;

/**
 * Global configuration for the application.
 *
 * @author Alexander Wilhelmer
 */
public class Global extends GlobalSettings {
    private static final Logger.ALogger log = Logger.of(Global.class);

    public Action<?> onRequest(Request request, Method actionMethod) {
        log.info("Before each request..." + request.toString());
        return super.onRequest(request, actionMethod);
    }

    @Override
    public void onStart(Application app) {
        log.info("Executing startup tasks");
        JPA.withTransaction(new F.Callback0() {
            @Override
            public void invoke() throws Throwable {
                log.info("First JPA call");
            }
        });
        BaseImporter importer = new BaseImporter();
        importer.run();
        log.info("Herowar stated");
    }

    @Override
    public void onStop(Application app) {
        log.info("Herowar shutdown...");

    }
}

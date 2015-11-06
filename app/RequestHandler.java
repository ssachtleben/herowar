import play.Logger;
import play.http.DefaultHttpRequestHandler;
import play.http.HttpRequestHandler;

import play.libs.F;
import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Result;

import java.lang.reflect.Method;

/**
 * @author Sebastian Sachtleben
 */
public class RequestHandler extends DefaultHttpRequestHandler implements HttpRequestHandler {

    private static final Logger.ALogger log = Logger.of(RequestHandler.class);

    @Override
    public Action createAction(Http.Request request, Method actionMethod) {
        return new Action.Simple() {
            @Override
            public F.Promise<Result> call(Http.Context ctx) throws Throwable {
                log.info(String.format("Request %s", request.toString()));
                return delegate.call(ctx);
            }
        };
    }

    @Override
    public Action wrapAction(Action action) {
        return action;
    }

}

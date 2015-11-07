package controllers;

import models.entity.game.Map;
import play.Logger;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.mvc.Result;

import static play.libs.Json.toJson;

/**
 * The Maps controller handle api requests for the Map model.
 *
 * @author Sebastian Sachtleben
 */
public class Maps extends BaseAPI<Long, Map> {

   private static final Logger.ALogger log = Logger.of(Maps.class);

   public Maps() {
      super(Long.class, Map.class);
   }

   @Transactional
   public Result update(Long id) {
      Map map = findUnique(id);
      map = JPA.em().merge(map);
      return ok(toJson(map));
   }

}


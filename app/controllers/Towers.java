package controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import json.excludes.ExcludeGeometryMixin;
import models.entity.game.Tower;
import play.Logger;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.mvc.Result;

import java.io.IOException;

import static play.libs.Json.toJson;

/**
 * The Towers controller handle api requests for the Tower model.
 *
 * @author Sebastian Sachtleben
 * @author Alexander Wilhelmer
 */
public class Towers extends BaseAPI<Long, Tower> {
   private static final Logger.ALogger log = Logger.of(Towers.class);

   public Towers() {
      super(Long.class, Tower.class);
   }

   @Transactional
   public Result list() {
      ObjectMapper mapper = new ObjectMapper();
      mapper.addMixIn(Tower.class, ExcludeGeometryMixin.class);
      try {
         return ok(mapper.writeValueAsString(super.get()));
      }
      catch (IOException e) {
         log.error("Failed to serialize tower:", e);
      }
      return badRequest("Unexpected error occurred");
   }

   @Transactional
   public Result show(Long id) {
      return super.show(id);
   }

   @Transactional
   public Result update(Long id) {
      Tower tower = super.findUnique(id);
      tower = JPA.em().merge(tower);
      return ok(toJson(tower));
   }

   @Transactional
   public Result delete(Long id) {
      return super.delete(id);
   }

   @Transactional
   public Result add() {
      return super.add();
   }
}

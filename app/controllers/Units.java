package controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import dao.UnitDAO;
import json.excludes.ExcludeGeometryMixin;
import models.entity.game.Unit;
import play.Logger;
import play.db.jpa.Transactional;
import play.mvc.Result;

import java.io.IOException;

/**
 * The Units controller handle api requests for the Unit model.
 *
 * @author Sebastian Sachtleben
 * @author Alexander Wilhelmer
 */
public class Units extends BaseAPI<Long, Unit> {

   private static final Logger.ALogger log = Logger.of(Environments.class);

   @Inject
   private UnitDAO unitDAO;

   public Units() {
      super(Long.class, Unit.class);
   }

   @Transactional
   public Result list() {
      ObjectMapper mapper = new ObjectMapper();
      mapper.addMixIn(Unit.class, ExcludeGeometryMixin.class);
      try {
         return ok(mapper.writeValueAsString(super.get()));
      }
      catch (IOException e) {
         log.error("Failed to serialize unit:", e);
      }
      return badRequest("Unexpected error occurred");
   }

   @Transactional
   public Result root() {
      ObjectMapper mapper = new ObjectMapper();
      mapper.addMixIn(Unit.class, ExcludeGeometryMixin.class);
      try {
         Unit root = unitDAO.getByName("Root");
         return ok(mapper.writeValueAsString(root.getChildren()));
      }
      catch (IOException e) {
         log.error("Failed to serialize root unit:", e);
      }
      return badRequest("Unexpected error occurred");
   }

   @Transactional
   public Result show(Long id) {
      return super.show(id);
   }

}

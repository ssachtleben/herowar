package controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import dao.EnvironmentDAO;
import json.excludes.ExcludeGeometryMixin;
import models.entity.game.Environment;
import play.Logger;
import play.db.jpa.Transactional;
import play.mvc.Result;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import static play.libs.Json.toJson;

/**
 * The Environment controller handle api requests for the Environment model.
 *
 * @author Alexander Wilhelmer
 */
public class Environments extends BaseAPI<Long, Environment> {

   private static final Logger.ALogger LOG = Logger.of(Environments.class);

   @Inject
   private EnvironmentDAO environmentDAO;

   public Environments() {
      super(Long.class, Environment.class);
   }

   @Transactional
   public Result list() {
      LOG.warn("called listAll without Excludes!");
      return ok(toJson(this.get()));
   }

   @Transactional
   public Result root() {
      ObjectMapper mapper = new ObjectMapper();
      mapper.addMixIn(Environment.class, ExcludeGeometryMixin.class);
      try {
         return ok(mapper.writeValueAsString(environmentDAO.getRootEntity()));
      }
      catch (IOException | IllegalAccessException | InvocationTargetException | NoSuchMethodException | InstantiationException e) {
         LOG.error("Failed to serialize root environment:", e);
      }
      return badRequest("Unexpected error occurred");
   }

   @Transactional
   public Result show(Long id) {
      return this.show(id);
   }

}

package controllers;

import models.entity.game.Terrain;
import play.Logger;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.mvc.Result;

import static play.libs.Json.toJson;

/**
 * The Terrains controller handle api requests for the Terrain model.
 *
 * @author Sebastian Sachtleben
 * @author Alexander Wilhelmer
 */
public class Terrains extends BaseAPI<Long, Terrain> {
   private static final Logger.ALogger log = Logger.of(Terrains.class);

   public Terrains() {
      super(Long.class, Terrain.class);
   }

   @Transactional
   public Result list() {
      log.warn("called listAll without Excludes!");
      return super.list();
   }

   @Transactional
   public Result show(Long id) {
      return super.show(id);
   }

   @Transactional
   public Result update(Long id) {
      Terrain terrain = super.findUnique(id);
      terrain = JPA.em().merge(terrain);
      return ok(toJson(terrain));
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

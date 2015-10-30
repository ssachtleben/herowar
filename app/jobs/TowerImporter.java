package jobs;

import jobs.utils.EntityImporter;
import models.entity.game.Tower;
import play.db.jpa.JPA;

import java.io.File;


/**
 * Syncronizes javascript files from "public/geometries/towers" with {@link Tower} models.
 *
 * @author Sebastian Sachtleben
 */

public class TowerImporter extends EntityImporter<Tower> {


   public String getBaseFolder() {
      return "public" + File.separator + "geometries" + File.separator + "towers";
   }

   @Override
   protected void process() {

   }

   @Override
   protected Tower createEntry(String name, Tower parent) {
      Tower entry = super.createEntry(name, parent);
      if (entry != null) {
         if (!JPA.em().contains(entry)) {
            JPA.em().persist(entry);
         } else {
            entry = JPA.em().merge(entry);
         }
      }
      return entry;
   }

}

package jobs;

import dao.MapDAO;
import jobs.utils.DependsOn;
import jobs.utils.EntityImporter;
import models.entity.game.Map;
import org.hibernate.Session;
import org.hibernate.jdbc.Work;
import play.Logger;
import play.Play;
import play.db.jpa.JPA;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Imports example maps.
 *
 *
 * @author Sebastian Sachtleben
 */
//@StartJob(async = true, active = true)
@DependsOn(values = { UserImporter.class, EnvironmentImporter.class, TowerImporter.class, UnitImporter.class })
public class ExampleMapImporter extends EntityImporter<Map> {
   private static final Logger.ALogger log = Logger.of(ExampleMapImporter.class);


   @Override
   protected void process() {
      MapDAO mapDAO = Play.application().injector().instanceOf(MapDAO.class);
      Map map = mapDAO.getMapById(102L);
      if (map == null) {
         createMapFromSQL("pathToExil.sql");
      }
      map = mapDAO.getMapById(101L);
      if (map == null) {
         createMapFromSQL("tutorial.sql");
      }
   }

   private void createMapFromSQL(String filename) {
      BufferedReader bReader = null;
      Session sess = (Session) JPA.em().getDelegate();
      try {
         File file = Play.application().getFile("external" + File.separator + "sql" + File.separator + "maps" + File.separator + filename);
         bReader = new BufferedReader(new FileReader(file));
         StringBuffer sql = new StringBuffer();
         String line;
         while ((line = bReader.readLine().trim()) != null) {
            if (line.startsWith("--") || line.isEmpty())
               continue;

            sql.append(line);
            if (line.endsWith(");")) {
               final String tmp = sql.toString();

               sql = new StringBuffer();
               log.info(String.format("Process SQL: %s", tmp));
               sess.doWork(new Work() {
                  @Override
                  public void execute(Connection connection) throws SQLException {
                     connection.createStatement().execute(tmp);
                  }
               });
            }

         }
      } catch (Exception ex) {
         // log.error("Couldn't execute " + sqlFileOnClasspath, ex);
      } finally {
         if (bReader != null)
            try {
               bReader.close();
            } catch (IOException e) {

            }
      }
   }

}

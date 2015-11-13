package jobs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssachtleben.play.plugin.auth.models.PasswordEmailAuthUser;
import com.ssachtleben.play.plugin.cron.annotations.StartJob;
import com.ssachtleben.play.plugin.cron.jobs.SimpleJob;
import controllers.Application;
import dao.NewsDAO;
import dao.SecurityRoleDAO;
import dao.UserDAO;
import models.entity.SecurityRole;
import models.entity.game.LevelRange;
import play.Logger;
import play.Play;
import play.db.jpa.JPA;

/**
 * @author Alexander Wilhelmer
 * @author Sebastian Sachtleben
 */
@StartJob
public class UserImporter extends SimpleJob {

   private static final Logger.ALogger log = Logger.of(UserImporter.class);

   public static boolean isReady = false;

   @Override
   public void run() {
      JPA.withTransaction(() -> importData());
   }

   private void importData() {
      initialSecurityRoles();
      createAdminUser();
      createDummyNews();
      createLevelRanges();
      JPA.em().flush();
      isReady = true;
   }

   private void initialSecurityRoles() {
      SecurityRoleDAO securityRoleDAO = Play.application().injector().instanceOf(SecurityRoleDAO.class);
      if (securityRoleDAO.getAll().size() != 0) {
         return;
      }
      log.info("Creating security roles");
      for (final String roleName : Application.roles) {
         final SecurityRole role = new SecurityRole();
         role.setRoleName(roleName);
         JPA.em().persist(role);
         log.info("Save role: " + role.getName());
      }
   }

   private void createAdminUser() {
      UserDAO userDAO = Play.application().injector().instanceOf(UserDAO.class);
      if (userDAO.findByUsername("admin") != null) {
         return;
      }
      log.info("Creating admin user");
      userDAO.create(new PasswordEmailAuthUser("admin@herowar.com", "admin", new ObjectMapper().createObjectNode()), "admin", "FirstName", "LastName");
   }

   private void createDummyNews() {
      NewsDAO newsDAO = Play.application().injector().instanceOf(NewsDAO.class);
      if (newsDAO.getNewsCount() != 0) {
         return;
      }
      UserDAO userDAO = Play.application().injector().instanceOf(UserDAO.class);
      log.info("Creating dummy news");
      newsDAO.create("Lorem ipsum dolor sit amet",
            "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.",
            userDAO.findByUsername("admin"));
   }

   private void createLevelRanges() {
      LevelRange range = JPA.em().find(LevelRange.class, 1L);
      if (range != null) {
         return;
      }
      log.info("Creating level ranges");
      JPA.em().persist(new LevelRange(5000L));
      JPA.em().persist(new LevelRange(15000L));
      JPA.em().persist(new LevelRange(50000L));
      JPA.em().persist(new LevelRange(250000L));
      JPA.em().persist(new LevelRange(750000L));
   }

}

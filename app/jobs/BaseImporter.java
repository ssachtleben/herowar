package jobs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssachtleben.play.plugin.auth.models.PasswordEmailAuthUser;
import controllers.Application;
import dao.SecurityRoleDAO;
import dao.UserDAO;
import models.SecurityRole;
import models.User;
import play.Logger;
import play.Play;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;

/**
 * BaseImporter for basic Data in Database on startup
 *
 * @author Alexander Wilhelmer
 */
public class BaseImporter {
    private static final Logger.ALogger log = Logger.of(BaseImporter.class);

    public void run() {
        JPA.withTransaction(new play.libs.F.Callback0() {
            @Override
            @Transactional
            public void invoke() throws Throwable {
                initialSecurityRoles();
                createAdminUser();
                System.out.println("BaseImporter is done...");
            }
        });
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
            log.info("Admin already exists!");
            return;
        }
        log.info("Creating admin user");
        User user = userDAO.create(new PasswordEmailAuthUser("admin@rockspotter.com", "admin", new ObjectMapper().createObjectNode()), "", "FirstName", "LastName");


    }
}

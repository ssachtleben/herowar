package dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.ssachtleben.play.plugin.auth.models.PasswordEmailAuthUser;
import controllers.Application;
import models.entity.Email;
import models.entity.User;
import play.Logger;
import play.Play;
import play.db.jpa.JPA;

import java.util.Collections;
import java.util.Date;

/**
 * Handles all database queries for the {@link User} entity.
 *
 * @author Alexander Wilhelmer
 * @author Sebastian Sachtleben
 */
public class UserDAO extends BaseDAO<Long, User> {

   @Inject
   private SecurityRoleDAO securityRoleDAO;

   @Inject
   private LinkedServiceDAO linkedAccountDAO;

   public UserDAO() {
      super(Long.class, User.class);
   }

   public static UserDAO instance() {
      return Play.application().injector().instanceOf(UserDAO.class);
   }

   public User findByEmail(String email) {
      return getSingleByPropertyValue("email", email);
   }

   public String findUniqueUsername(String username) {
      if (!existsUsername(username)) {
         return username;
      }
      int count = 0;
      while (existsUsername(username + Integer.toString(count))) {
         count++;
      }
      return username + Integer.toString(count);
   }

   public boolean existsUsername(String username) {
      return countSingleByPropertyValue("username", username) > 0;
   }

   public User create(final String username, final String email, final String avatar) {
      return create(new PasswordEmailAuthUser(email, null, new ObjectMapper().createObjectNode()), username, null, null);
   }

   public User create(final PasswordEmailAuthUser identity, final String username, final String firstName, final String lastName) {
      return create(identity, username, firstName, lastName, null);
   }

   public User create(final PasswordEmailAuthUser identity, final String username, final String firstName, final String lastName, String userRole) {
      final User user = new User();
      if (userRole == null)
         userRole = Application.USER_ROLE;
      user.setRoles(Collections.singleton(securityRoleDAO.findByRoleName(userRole)));
      user.setActive(true);
      user.setLastLogin(new Date());
      user.setFirstName(firstName);
      user.setLastName(lastName);
      user.setPassword(identity.hashedPassword());
      if (!identity.data().isNull()) {
         if (identity.data().get("avatar") != null)
            user.setAvatar(identity.data().get("avatar").textValue());
      }

      final Email email = new Email();
      email.setAddress(identity.email());
      email.setMain(true);
      email.setConfirmed(false);
      email.setUser(user);

      user.getEmails().add(email);

      // TODO: send email validation

      // TODO: remove this field ... (saved already in email)
      user.setEmailValidated(false);
      user.setUsername(username);
      JPA.em().persist(user);
      user.setLinkedServices(Collections.singleton(linkedAccountDAO.create(identity.provider(), identity.id(), user)));
      Logger.info("Saved new user " + user.getUsername());
      return user;
   }

   public void delete(User user) {
      JPA.em().remove(user);
   }

   public User findByUsername(final String username) {
      return getSingleByPropertyValue("username", username);
   }

   public void merge(User user, User user2) {
      user = super.merge(user2);
      user.setUsername(user2.getUsername());
      user.setEmails(user2.getEmails());
      user.setActive(user2.isActive());
      user.setNewsletter(user2.isNewsletter());
   }
}
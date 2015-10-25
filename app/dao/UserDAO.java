package dao;

import com.google.inject.Inject;
import com.ssachtleben.play.plugin.auth.models.Identity;
import controllers.Application;
import models.Email;
import models.User;
import org.apache.commons.lang3.StringUtils;
import play.Logger;
import play.db.jpa.JPA;

import java.util.Collections;
import java.util.Date;


/**
 * @author Alexander Wilhelmer
 */
public class UserDAO extends BaseDAO<Long, User> {
   @Inject
   private SecurityRoleDAO securityRoleDAO;
   @Inject
   private LinkedAccountDAO linkedAccountDAO;

   public UserDAO() {
      super(Long.class, User.class);
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

   public User create(final Identity identity, final String emailAddress, final String username, final String password, String avatar) {
      final User user = new User();
      user.setRoles(Collections.singletonList(securityRoleDAO.findByRoleName(Application.USER_ROLE)));
      user.setActive(true);
      user.setLastLogin(new Date());
      if (StringUtils.isNotBlank(avatar)) {
         user.setAvatar(avatar);
      }

      final Email email = new Email();
      email.setAddress(emailAddress);
      email.setMain(true);
      email.setConfirmed(true);
      email.setUser(user);
      user.getEmails().add(email);

      //user.setEmail(email);
      user.setEmailValidated(false);
      user.setUsername(username);
      JPA.em().persist(user);
      //user.setLinkedServices(Collections.singletonList(linkedAccountDAO.create(Application.USER_ROLE, identity, user)));
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
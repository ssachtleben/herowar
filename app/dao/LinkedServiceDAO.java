package dao;

import com.ssachtleben.play.plugin.auth.providers.PasswordEmail;
import models.entity.LinkedService;
import models.entity.ServiceType;
import models.entity.User;
import play.Play;
import play.db.jpa.JPA;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class LinkedServiceDAO extends BaseDAO<Long, LinkedService> {

   public LinkedServiceDAO() {
      super(Long.class, LinkedService.class);
   }

   public static LinkedServiceDAO instance() {
      return Play.application().injector().instanceOf(LinkedServiceDAO.class);
   }

   public LinkedService create(final String type, final String identifier, final User account) {
      final ServiceType typeVal = ServiceType.valueOf(type.toUpperCase());
      final LinkedService linkedService = new LinkedService();
      linkedService.setIdentifier(identifier);
      linkedService.setType(typeVal);
      linkedService.setUser(account);
      JPA.em().persist(linkedService);
      return linkedService;
   }

   @SuppressWarnings("unchecked")
   public List<LinkedService> find(final User user) {
      return JPA.em().createQuery(String.format("FROM %s la WHERE la.user = :user", LinkedService.class.getSimpleName())).setParameter("key", user)
            .setParameter("username", user).getResultList();
   }

   public LinkedService find(final String key, final String userId) {
      CriteriaBuilder builder = this.getCriteriaBuilder();
      CriteriaQuery<LinkedService> q = this.getCriteria();
      Root<LinkedService> root = this.getRoot(q);
      q.where(builder.and(builder.equal(root.get("type"), ServiceType.valueOf(key.toUpperCase())), builder.equal(root.get("identifier"), userId)));
      try {
         return JPA.em().createQuery(q).getSingleResult();
      }
      catch (NoResultException e) {
         return null;
      }
   }

   @SuppressWarnings("unchecked")
   public LinkedService findByUsername(String key, String username) {
      List<LinkedService> accounts = JPA.em()
            .createQuery(String.format("FROM %s la WHERE la.type = :key AND la.user.username = :username", LinkedService.class.getSimpleName()))
            .setParameter("key", ServiceType.valueOf(key.toUpperCase())).setParameter("username", username).getResultList();
      if (accounts != null && accounts.size() > 0) {
         return accounts.get(0);
      }
      return null;
   }

   public LinkedService findByEmail(String email) {
      List<LinkedService> accounts = JPA.em()
              .createQuery(String.format("SELECT la FROM %s la JOIN la.user.emails e WHERE la.type = :key AND e.address = :email", LinkedService.class.getSimpleName()))
              .setParameter("key", ServiceType.valueOf(PasswordEmail.KEY.toUpperCase())).setParameter("email", email).getResultList();
      if (accounts != null && accounts.size() > 0) {
         return accounts.get(0);
      }
      return null;
   }
}
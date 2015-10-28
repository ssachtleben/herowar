package dao;

import models.LinkedService;
import models.ServiceType;
import models.User;
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

   public LinkedService find(final String key, final String userId) {
      CriteriaBuilder builder = this.getCriteriaBuilder();
      CriteriaQuery<LinkedService> q = this.getCriteria();
      Root<LinkedService> root = this.getRoot(q);
      q.where(builder.and(builder.equal(root.get("providerKey"), key), builder.equal(root.get("providerUserId"), userId)));
      try {
         return JPA.em().createQuery(q).getSingleResult();
      } catch (NoResultException e) {
         return null;
      }
   }

   @SuppressWarnings("unchecked")
   public LinkedService findByUsername(String key, String username) {
      List<LinkedService> accounts = JPA
              .em()
              .createQuery(
                      String.format("FROM %s la WHERE la.providerKey = :key AND la.user.username = :username", LinkedService.class.getSimpleName()))
              .setParameter("key", key).setParameter("username", username).getResultList();
      if (accounts != null && accounts.size() > 0) {
         return accounts.get(0);
      }
      return null;
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

}

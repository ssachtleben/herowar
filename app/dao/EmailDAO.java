package dao;

import models.entity.Email;
import play.Play;
import play.db.jpa.JPA;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Handles all database queries for the {@link Email} entity.
 *
 * @author Sebastian Sachtleben
 */
public class EmailDAO extends BaseDAO<Long, Email> {

   public EmailDAO() {
      super(Long.class, Email.class);
   }

   public static EmailDAO instance() {
      return Play.application().injector().instanceOf(EmailDAO.class);
   }

   public Email findByAddress(String address) {
      return getSingleByPropertyValue("address", address);
   }

   public Email findByConfirmCode(String confirmCode) {
      return getSingleByPropertyValue("confirmCode", confirmCode);
   }

   public List<Email> getNotConfirmed(final int createdBeforeHours) {
      final Calendar cal = Calendar.getInstance();
      cal.add(Calendar.HOUR, createdBeforeHours * -1);
      final CriteriaBuilder builder = getCriteriaBuilder();
      final CriteriaQuery<Email> q = getCriteria();
      final Root<Email> root = q.from(Email.class);
      final List<Predicate> predicates = new ArrayList<>();
      predicates.add(builder.equal(root.get("confirmed"), false));
      predicates.add(builder.lessThan(root.get("cdate"), cal.getTime()));
      q.where(builder.and(predicates.toArray(new Predicate[0])));
      return JPA.em().createQuery(q).getResultList();
   }

}
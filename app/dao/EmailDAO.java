package dao;

import models.entity.Email;
import play.Play;
import play.db.jpa.JPA;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
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

   public List<Email> getNotConfirmed(final int createdBeforeHours) {
      final Calendar cal = Calendar.getInstance();
      cal.add(Calendar.HOUR, createdBeforeHours * -1);
      final CriteriaBuilder builder = getCriteriaBuilder();
      final CriteriaQuery<Email> q = getCriteriaWithRoot();
      final List<Predicate> predicates = new ArrayList<Predicate>();
      predicates.add(builder.equal(getRoot(q).get("confirmed"), false));
      predicates.add(builder.lessThan(getRoot(q).get("cdate"), cal.getTime()));
      q.where(builder.and(predicates.toArray(new Predicate[0])));
      q.select(q.from(Email.class));
      return JPA.em().createQuery(q).getResultList();
   }

}
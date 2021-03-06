package dao;

import play.db.jpa.JPA;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.List;

/**
 * @author Alexander Wilhelmer
 */
public abstract class BaseDAO<K extends Serializable, T extends Object> {

   protected Class<K> idClass;
   protected Class<T> entityClass;

   public BaseDAO(Class<K> idClass, Class<T> entityClass) {
      this.idClass = idClass;
      this.entityClass = entityClass;
   }

   public List<T> getAll() {
      return JPA.em().createQuery(getCriteriaWithRoot()).getResultList();
   }

   public T getById(K id) {
      return getSingleByPropertyValue("id", id);
   }

   public T getByName(String name) {
      return getSingleByPropertyValue("name", name);
   }

   protected CriteriaQuery<T> getCriteria() {
      return getCriteriaBuilder().createQuery(entityClass);
   }

   protected Root<T> getRoot(CriteriaQuery<T> crit) {
      return crit.from(entityClass);
   }

   protected CriteriaQuery<T> getCriteriaWithRoot() {
      CriteriaQuery<T> query = getCriteriaBuilder().createQuery(entityClass);
      getRoot(query);
      return query;
   }

   protected CriteriaBuilder getCriteriaBuilder() {
      return JPA.em().getCriteriaBuilder();
   }

   protected T getSingleByPropertyValue(String property, Object value) {
      CriteriaBuilder builder = getCriteriaBuilder();
      CriteriaQuery<T> q = getCriteria();
      q.where(builder.equal(getRoot(q).get(property), value));
      try {
         return JPA.em().createQuery(q).getSingleResult();
      }
      catch (NoResultException e) {
         return null;
      }
   }

   protected long countSingleByPropertyValue(String property, Object value) {
      CriteriaBuilder builder = getCriteriaBuilder();
      CriteriaQuery<Long> q = builder.createQuery(Long.class);
      Root<T> root = q.from(entityClass);
      q.select(builder.count(root));
      q.where(builder.equal(root.get(property), value));
      try {
         return JPA.em().createQuery(q).getSingleResult();
      }
      catch (NoResultException e) {
         return 0;
      }
   }

   protected long getBaseCount() {
      CriteriaBuilder builder = getCriteriaBuilder();
      CriteriaQuery<Long> q = builder.createQuery(Long.class);
      Root<T> root = q.from(entityClass);
      q.select(builder.count(root));
      return JPA.em().createQuery(q).getSingleResult();
   }

   public boolean delete(K id) {
      try {
         T obj = JPA.em().find(entityClass, id);
         JPA.em().remove(obj);
      }
      catch (NoResultException e) {
         return false;
      }
      return true;
   }

   public T findUnique(K id) {
      try {
         return JPA.em().find(entityClass, id);
      }
      catch (NoResultException e) {
         return null;
      }
   }

   public T merge(T obj) {
      return JPA.em().merge(obj);
   }
}

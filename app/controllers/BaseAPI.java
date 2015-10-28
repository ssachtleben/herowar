package controllers;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.PropertyUtils;
import play.Logger;
import play.data.DynamicForm;
import play.data.Form;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static play.libs.Json.toJson;

/**
 * The BaseAPI provides the basic api functions.
 *
 * @author Alexander Wilhelmer
 * @author Sebastian Sachtleben
 */
public abstract class BaseAPI<K extends Serializable, T extends Object> extends Controller {
   private static final Logger.ALogger log = Logger.of(BaseAPI.class);

   @SuppressWarnings("unused")
   private Class<K> idClass;
   private Class<T> entityClass;

   /**
    * Default constructor.
    *
    * @param idClass
    *                The idClass to set.
    * @param entityClass
    *                The entityClass to set.
    */
   public BaseAPI(Class<K> idClass, Class<T> entityClass) {
      super();
      this.idClass = idClass;
      this.entityClass = entityClass;
   }

   /**
    * Returns a list of all entites of the given entity.
    *
    * @return The list of entites.
    */
   @Transactional
   public Result list() {
      return ok(toJson(get()));
   }

   /**
    * Retuns a unique entity with the given id of the given entity.
    *
    * @param id
    *             The id of the entity.
    * @return The entity object.
    */
   @Transactional
   public Result show(K id) {
      try {
         return ok(toJson(getEntityManager().find(entityClass, id)));
      } catch (NoResultException | NullPointerException e) {
         return badRequest("No Result");
      }
   }

   /**
    * Deletes an entity with the given id.
    *
    * @param id
    *             The id of the entity to delete.
    * @return An empty json response.
    */
   public Result delete(K id) {
      try {
         T obj = getEntityManager().find(entityClass, id);
         getEntityManager().remove(obj);
      } catch (NoResultException e) {
         return badRequest("No Result");
      }
      return ok("{}");
   }

   /**
    * Persist a new entity with the given data from the request.
    *
    * @return The json serialized object.
    */
   public Result add() {
      T obj = Form.form(entityClass).bindFromRequest().get();
      getEntityManager().persist(obj);
      return ok(toJson(obj));
   }


   protected List<T> get() {
      CriteriaQuery<T> query = getCriteria();
      query.from(entityClass);
      return getEntityManager().createQuery(query).getResultList();
   }

   protected CriteriaQuery<T> getCriteria() {
      return getCriteriaBuilder().createQuery(entityClass);
   }

   protected Root<T> getRoot() {
      return getCriteria().from(entityClass);
   }

   protected CriteriaBuilder getCriteriaBuilder() {
      return getEntityManager().getCriteriaBuilder();
   }

   protected T find(K id) {
      return getEntityManager().find(entityClass, id);
   }

   public T findUnique(K id) {
      try {
         return getEntityManager().find(entityClass, id);
      } catch (NoResultException e) {
         return null;
      }
   }

   public T merge(T obj) {
      return getEntityManager().merge(obj);
   }

   protected EntityManager getEntityManager() {
      return JPA.em();
   }

   public Object update(T obj) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
      Map<String, Object> data = getDataFromRequest();
      if (data == null) {
         return null;
      }
      if (data.containsKey("id")) {
         data.remove("id");
      }
      log.info("Found values for " + obj + ": " + Json.stringify(toJson(data)));
      return updateProperties(obj, data);
   }

   private Object updateProperties(Object obj, Map<String, Object> data) throws IllegalAccessException, InvocationTargetException,
           NoSuchMethodException {
      final Iterator<Map.Entry<String, Object>> iter = data.entrySet().iterator();
      while (iter.hasNext()) {
         final Map.Entry<String, Object> entry = iter.next();
         if (PropertyUtils.isWriteable(obj, entry.getKey())) {
            final Class<?> type = PropertyUtils.getPropertyType(obj, entry.getKey());
            final Object value = ConvertUtils.convert(entry.getValue(), type);
            if (value != null) {
               log.info("Update " + entry.getKey() + " with " + value.toString());
               PropertyUtils.setProperty(obj, entry.getKey(), value);
            } else {
               log.error("Failed to cast " + entry.getValue().toString() + " to " + type.getName());
            }
         }
      }
      return obj;
   }

   @SuppressWarnings("unchecked")
   private Map<String, Object> getDataFromRequest() {
      DynamicForm.Dynamic form = Form.form().bindFromRequest().get();
      if (form != null && form.getData() != null) {
         return form.getData();
      }
      return null;
   }
}

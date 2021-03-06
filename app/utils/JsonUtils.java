package utils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.JsonNode;
import json.JsonFieldName;
import models.entity.game.Vector3;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.ClassUtils;
import org.hibernate.Hibernate;
import play.Logger;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.*;

/**
 * @author Sebastian Sachtleben
 */
public class JsonUtils {

   private static final Logger.ALogger log = Logger.of(JsonUtils.class);

   public static void parse(Object result, JsonNode node) {
      try {
         List<Class<?>> classes = new ArrayList<Class<?>>();
         findClasses(result, classes);
         parse(result, node, classes);
      }
      catch (Exception e) {
         log.error("", e);
      }
   }

   private static void findClasses(Object obj, List<Class<?>> classes)
         throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
      if (obj == null) {
         return;
      }
      Class<?> root = obj.getClass();
      if (Hibernate.getClass(obj) != null) {
         root = Hibernate.getClass(obj);
      }
      if (!classes.contains(root)) {
         classes.add(root);
      }
      Field[] fields = root.getDeclaredFields();
      for (Field field : fields) {
         boolean isCollection = false;
         Class<?> propertyType = field.getType();
         if (field.getType().isAssignableFrom(List.class) || field.getType().isAssignableFrom(Set.class)) {
            ParameterizedType pt = (ParameterizedType) field.getGenericType();
            propertyType = (Class<?>) pt.getActualTypeArguments()[0];
            isCollection = true;
         }
         if (!classes.contains(propertyType) && !propertyType.isPrimitive() && ClassUtils.wrapperToPrimitive(propertyType) == null && !propertyType
               .isAssignableFrom(String.class) && !propertyType.isAssignableFrom(Date.class) && PropertyUtils.isWriteable(obj, field.getName())
               && !propertyType.isEnum() && !field.isAnnotationPresent(JsonIgnore.class)) {
            Object propertyObj = PropertyUtils.getProperty(obj, field.getName());
            if (propertyObj == null || isCollection) {
               try {
                  propertyObj = propertyType.newInstance();
               }
               catch (InstantiationException e) {
                  log.error("Failed to create instance of " + propertyType);
               }
            }
            if (propertyObj != null) {
               findClasses(propertyObj, classes);
            }
         }
      }
   }

   public static void parse(Object result, JsonNode node, List<Class<?>> classes) {
      parse(result, null, node, classes);
   }

   @SuppressWarnings("unchecked")
   public static void parse(Object result, Object parent, JsonNode node, List<Class<?>> classes) {
      Iterator<String> fieldIt = node.fieldNames();
      while (fieldIt.hasNext()) {
         String field = fieldIt.next();
         JsonNode fieldNode = node.get(field);
         if (fieldNode != null && !fieldNode.isNull()) {
            try {
               field = getRealField(field, result);
               Class<?> propClass = PropertyUtils.getPropertyType(result, field);
               Object value = null;
               if (propClass != null) {
                  if (propClass.isAssignableFrom(Double.class)) {

                     value = fieldNode.asDouble();
                  } else if (propClass.isAssignableFrom(String.class)) {
                     if (fieldNode.isArray()) {
                        value = fieldNode.toString();
                     } else {
                        value = fieldNode.asText();
                        if (value == null)
                           value = play.libs.Json.stringify(fieldNode);
                     }
                  } else if (propClass.isAssignableFrom(Float.class)) {
                     value = Double.valueOf(fieldNode.asDouble()).floatValue();
                  } else if (propClass.isAssignableFrom(Long.class)) {
                     value = fieldNode.asLong();
                  } else if (propClass.isAssignableFrom(Integer.class)) {
                     value = fieldNode.asInt();
                  } else if (propClass.isAssignableFrom(Short.class)) {
                     value = Integer.valueOf(fieldNode.asInt()).shortValue();
                  } else if (propClass.isAssignableFrom(Boolean.class)) {
                     value = fieldNode.asBoolean();
                  }
                  // else if (propClass.isAssignableFrom(Date.class)) {
                  // value = new Date(fieldNode.getLongValue());
                  // }
                  else if (propClass.isAssignableFrom(Vector3.class)) {
                     Vector3 vector = new Vector3();
                     if (fieldNode.get("x") != null)
                        vector.setX(fieldNode.get("x").asDouble());
                     if (fieldNode.get("y") != null)
                        vector.setY(fieldNode.get("y").asDouble());
                     if (fieldNode.get("z") != null)
                        vector.setZ(fieldNode.get("z").asDouble());
                     value = vector;
                  } else if (propClass.isAssignableFrom(List.class) || propClass.isAssignableFrom(Set.class)) {
                     Field javaField = result.getClass().getDeclaredField(field);
                     ParameterizedType pt = (ParameterizedType) javaField.getGenericType();
                     Class<?> genericType = (Class<?>) pt.getActualTypeArguments()[0];
                     if (classes.contains(genericType)) {
                        Collection<Object> col = (Collection<Object>) PropertyUtils.getProperty(result, field);
                        int currentIndex = 0;
                        Iterator<JsonNode> nodes = fieldNode.elements();
                        while (nodes.hasNext()) {
                           JsonNode colNode = (JsonNode) nodes.next();
                           Object element = null;
                           if (col.size() > currentIndex) {
                              element = col.toArray()[currentIndex];
                              parse(element, result, colNode, classes);
                              log.debug("Changed collection entry: " + element.toString());
                           } else {
                              element = genericType.newInstance();
                              parse(element, result, colNode, classes);
                              log.debug("Added collection entry: " + element.toString());
                           }
                           col.add(element);
                           currentIndex++;
                        }
                        log.debug("Index " + currentIndex + " of " + col.size());
                        while (col.size() > currentIndex) {
                           log.debug("Remove col index " + currentIndex + " of " + col.size());
                           col.remove(col.toArray()[currentIndex]);
                        }
                        value = col;
                     }
                  } else if (propClass.isEnum()) {
                     value = fieldNode.asText();
                     Class<? extends Enum<?>> enumClass = (Class<? extends Enum<?>>) propClass;
                     Enum<?>[] enums = enumClass.getEnumConstants();
                     for (Enum<?> elem : enums) {
                        if (elem.name().equals(value)) {
                           value = elem;
                           break;
                        }
                     }
                  } else if (classes.contains(propClass)) {
                     Object element = propClass.newInstance();
                     parse(element, result, fieldNode, classes);
                     value = element;
                  } else {
                     log.warn(String.format("Ignored parsing Class: <%s> in Bean <%s> Field <%s>", propClass.getSimpleName(),
                           result.getClass().getSimpleName(), field));
                  }
                  if (value != null) {
                     PropertyUtils.setProperty(result, field, value);
                  }
               } else {
                  log.warn(String.format("Field <%s> not found in Bean <%s>", field, result.getClass().getSimpleName()));
               }
            }
            catch (Exception e) {
               log.error("", e);
            }
         }
      }
      try {
         if (parent != null) {
            Long id = (Long) PropertyUtils.getProperty(parent, "id");
            if (id != null) {
               Field[] fields = result.getClass().getDeclaredFields();
               for (Field field : fields) {
                  Class<?> fieldClass = PropertyUtils.getPropertyType(result, field.getName());
                  if (fieldClass != null && fieldClass.equals(parent.getClass())) {
                     PropertyUtils.setProperty(result, field.getName(), parent);
                     break;
                  }
               }
            }
         }
      }
      catch (Exception e) {
         log.error("", e);
      }
   }

   private static String getRealField(String name, Object result) {
      Field target = null;
      try {
         target = result.getClass().getField(name);
      }
      catch (NoSuchFieldException e) {
      }
      catch (SecurityException e) {
      }
      if (target == null) {
         Field[] fields = result.getClass().getDeclaredFields();
         for (Field field : fields) {
            if (field.isAnnotationPresent(JsonFieldName.class)) {
               JsonFieldName mapping = field.getAnnotation(JsonFieldName.class);
               String newName = mapping.name();
               if (newName.equals(name)) {
                  // Sdasd
                  log.debug("Return new name " + field.getName());
                  return field.getName();
               }
            }
         }
      }
      return name;
   }

}

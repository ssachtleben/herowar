package models.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * @author Sebastian Sachtleben
 */
@Entity
@SuppressWarnings("serial")
public class UserPermission implements Serializable {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   private String value;

   public String getValue() {
      return value;
   }

   // GETTER & SETTER //

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public void setValue(String value) {
      this.value = value;
   }


}
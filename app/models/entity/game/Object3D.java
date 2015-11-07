package models.entity.game;

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
@Deprecated
public class Object3D implements Serializable {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   private String name;
   private String description;

   // GETTER & SETTER //

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getDescription() {
      return description;
   }

   public void setDescription(String description) {
      this.description = description;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o)
         return true;
      if (o == null || getClass() != o.getClass())
         return false;

      Object3D object3D = (Object3D) o;

      if (id != null ? !id.equals(object3D.id) : object3D.id != null)
         return false;
      if (name != null ? !name.equals(object3D.name) : object3D.name != null)
         return false;
      return !(description != null ? !description.equals(object3D.description) : object3D.description != null);

   }

   @Override
   public int hashCode() {
      int result = id != null ? id.hashCode() : 0;
      result = 31 * result + (name != null ? name.hashCode() : 0);
      result = 31 * result + (description != null ? description.hashCode() : 0);
      return result;
   }
}

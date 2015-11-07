package models.entity.game;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author Sebastian Sachtleben
 */
@Entity
@Table(name = "achievement", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
@SuppressWarnings("serial")
public class Achievement implements Serializable {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @NotNull
   private String name;

   private String description;

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

      Achievement that = (Achievement) o;

      if (id != null ? !id.equals(that.id) : that.id != null)
         return false;
      if (name != null ? !name.equals(that.name) : that.name != null)
         return false;
      return !(description != null ? !description.equals(that.description) : that.description != null);

   }

   @Override
   public int hashCode() {
      int result = id != null ? id.hashCode() : 0;
      result = 31 * result + (name != null ? name.hashCode() : 0);
      result = 31 * result + (description != null ? description.hashCode() : 0);
      return result;
   }
}

package models.entity.game;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@SuppressWarnings("serial")
public class LevelRange implements Serializable {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;
   private Long exp;

   public LevelRange() {
   }

   public LevelRange(Long exp) {
      this.exp = exp;
   }

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public Long getExp() {
      return exp;
   }

   public void setExp(Long exp) {
      this.exp = exp;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;

      LevelRange that = (LevelRange) o;

      if (id != null ? !id.equals(that.id) : that.id != null) return false;
      return !(exp != null ? !exp.equals(that.exp) : that.exp != null);

   }

   @Override
   public int hashCode() {
      int result = id != null ? id.hashCode() : 0;
      result = 31 * result + (exp != null ? exp.hashCode() : 0);
      return result;
   }
}

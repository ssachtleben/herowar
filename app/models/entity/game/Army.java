package models.entity.game;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * @author Sebastian Sachtleben
 */
// @Entity
@SuppressWarnings("serial")
public class Army implements Serializable {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @ManyToOne
   private Wave wave;

   @OneToMany(cascade = CascadeType.ALL, mappedBy = "army")
   private Set<Unit> units;

   // GETTER & SETTER //

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public Wave getWave() {
      return wave;
   }

   public void setWave(Wave wave) {
      this.wave = wave;
   }

   public Set<Unit> getUnits() {
      return units;
   }

   public void setUnits(Set<Unit> units) {
      this.units = units;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o)
         return true;
      if (o == null || getClass() != o.getClass())
         return false;

      Army army = (Army) o;

      return !(id != null ? !id.equals(army.id) : army.id != null);

   }

   @Override
   public int hashCode() {
      return id != null ? id.hashCode() : 0;
   }
}
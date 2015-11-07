package models.entity.game;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Sebastian Sachtleben
 */
@Entity
@SuppressWarnings("serial")
public class TowerWeapon implements Serializable {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
   @JsonIgnore
   private Tower tower;

   private TowerWeaponType type;
   private Vector3 position;

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public Tower getTower() {
      return tower;
   }

   public void setTower(Tower tower) {
      this.tower = tower;
   }

   public TowerWeaponType getType() {
      return type;
   }

   public void setType(TowerWeaponType type) {
      this.type = type;
   }

   public Vector3 getPosition() {
      return position;
   }

   public void setPosition(Vector3 position) {
      this.position = position;
   }

   @Override
   public String toString() {
      return "TowerWeapon [id=" + id + ", type=" + type + ", position=" + (position != null ? position.toString() : null) + "]";
   }

   @Override
   public boolean equals(Object o) {
      if (this == o)
         return true;
      if (o == null || getClass() != o.getClass())
         return false;

      TowerWeapon that = (TowerWeapon) o;

      if (id != null ? !id.equals(that.id) : that.id != null)
         return false;
      return !(position != null ? !position.equals(that.position) : that.position != null);

   }

   @Override
   public int hashCode() {
      int result = id != null ? id.hashCode() : 0;
      result = 31 * result + (position != null ? position.hashCode() : 0);
      return result;
   }
}

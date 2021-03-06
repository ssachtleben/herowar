package models.entity.game;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Sebastian Sachtleben
 */
@Entity
@Table(name = "environment", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
@SuppressWarnings("serial")
public class Environment implements Serializable {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @NotNull
   private String name;

   @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "parent")
   private Set<Environment> children = new HashSet<Environment>();

   @JsonIgnore
   @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
   @JoinColumn(name = "parent_id")
   private Environment parent;

   @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
   private Geometry geometry;

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

   public Set<Environment> getChildren() {
      return children;
   }

   public void setChildren(Set<Environment> children) {
      this.children = children;
   }

   public Environment getParent() {
      return parent;
   }

   public void setParent(Environment parent) {
      this.parent = parent;
   }

   public Geometry getGeometry() {
      return geometry;
   }

   public void setGeometry(Geometry geometry) {
      this.geometry = geometry;
   }

   @Override
   public String toString() {
      return "Environment [id=" + id + ", name=" + name + "]";
   }

   @Override
   public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((id == null) ? 0 : id.hashCode());
      result = prime * result + ((name == null) ? 0 : name.hashCode());
      return result;
   }

   @Override
   public boolean equals(Object obj) {
      if (this == obj)
         return true;
      if (obj == null)
         return false;
      if (getClass() != obj.getClass())
         return false;
      Environment other = (Environment) obj;
      if (id == null) {
         if (other.id != null)
            return false;
      } else if (!id.equals(other.id))
         return false;
      if (name == null) {
         if (other.name != null)
            return false;
      } else if (!name.equals(other.name))
         return false;
      return true;
   }

}

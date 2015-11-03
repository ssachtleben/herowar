package models.entity.game;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import models.entity.User;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;


@Entity
@Table(name = "texture")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Texture implements Serializable {
   private static final long serialVersionUID = 4686002174493439419L;

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Lob
   private String map;

   @ManyToOne(fetch = FetchType.LAZY, optional = true, cascade = CascadeType.REFRESH)
   @JsonIgnore
   private User uploadUser;

   // isUsed In
   @ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY, mappedBy = "texture")
   @JsonIgnore
   private Set<Material> materials;

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public String getMap() {
      return map;
   }

   public void setMap(String map) {
      this.map = map;
   }

   public User getUploadUser() {
      return uploadUser;
   }

   public void setUploadUser(User uploadUser) {
      this.uploadUser = uploadUser;
   }

   public Set<Material> getMaterials() {
      return materials;
   }

   public void setMaterials(Set<Material> materials) {
      this.materials = materials;
   }


   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;

      Texture texture = (Texture) o;

      if (id != null ? !id.equals(texture.id) : texture.id != null) return false;
      return !(map != null ? !map.equals(texture.map) : texture.map != null);

   }

   @Override
   public int hashCode() {
      int result = id != null ? id.hashCode() : 0;
      result = 31 * result + (map != null ? map.hashCode() : 0);
      return result;
   }
}

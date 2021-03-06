package models.entity.game;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "mesh")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Mesh implements Serializable {
   private static final long serialVersionUID = 1L;
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;
   private String name;
   private Boolean visible;
   @Embedded
   @AttributeOverrides({ @AttributeOverride(name = "x", column = @Column(name = "position_x")),
         @AttributeOverride(name = "y", column = @Column(name = "position_y")),
         @AttributeOverride(name = "z", column = @Column(name = "position_z")) })
   private Vector3 position;
   @Embedded
   @AttributeOverrides({ @AttributeOverride(name = "x", column = @Column(name = "rotation_x")),
         @AttributeOverride(name = "y", column = @Column(name = "rotation_y")),
         @AttributeOverride(name = "z", column = @Column(name = "rotation_z")) })
   private Vector3 rotation;
   @Embedded
   @AttributeOverrides({ @AttributeOverride(name = "x", column = @Column(name = "scale_x")),
         @AttributeOverride(name = "y", column = @Column(name = "scale_y")), @AttributeOverride(name = "z", column = @Column(name = "scale_z")) })
   private Vector3 scale;
   @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH, optional = false)
   @JoinColumn(name = "geo_id", referencedColumnName = "id")
   private Geometry geometry;
   @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH, optional = false)
   @JoinColumn(name = "map_id", referencedColumnName = "id")
   @JsonIgnore
   private Map map;
   @Transient
   private Long geoId;

   public Mesh() {

   }

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public Vector3 getPosition() {
      return position;
   }

   public void setPosition(Vector3 position) {
      this.position = position;
   }

   public Vector3 getRotation() {
      return rotation;
   }

   public void setRotation(Vector3 rotation) {
      this.rotation = rotation;
   }

   public Vector3 getScale() {
      return scale;
   }

   public void setScale(Vector3 scale) {
      this.scale = scale;
   }

   public Geometry getGeometry() {
      return geometry;
   }

   public void setGeometry(Geometry geometry) {
      this.geometry = geometry;
   }

   public Map getMap() {
      return map;
   }

   public void setMap(Map map) {
      this.map = map;
   }

   public Long getGeoId() {
      return geoId;
   }

   public void setGeoId(Long geoId) {
      this.geoId = geoId;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public Boolean getVisible() {
      return visible;
   }

   public void setVisible(Boolean visible) {
      this.visible = visible;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o)
         return true;
      if (o == null || getClass() != o.getClass())
         return false;

      Mesh mesh = (Mesh) o;

      if (id != null ? !id.equals(mesh.id) : mesh.id != null)
         return false;
      if (name != null ? !name.equals(mesh.name) : mesh.name != null)
         return false;
      if (visible != null ? !visible.equals(mesh.visible) : mesh.visible != null)
         return false;
      if (position != null ? !position.equals(mesh.position) : mesh.position != null)
         return false;
      if (rotation != null ? !rotation.equals(mesh.rotation) : mesh.rotation != null)
         return false;
      if (scale != null ? !scale.equals(mesh.scale) : mesh.scale != null)
         return false;
      return !(geoId != null ? !geoId.equals(mesh.geoId) : mesh.geoId != null);

   }

   @Override
   public int hashCode() {
      int result = id != null ? id.hashCode() : 0;
      result = 31 * result + (name != null ? name.hashCode() : 0);
      result = 31 * result + (visible != null ? visible.hashCode() : 0);
      result = 31 * result + (position != null ? position.hashCode() : 0);
      result = 31 * result + (rotation != null ? rotation.hashCode() : 0);
      result = 31 * result + (scale != null ? scale.hashCode() : 0);
      result = 31 * result + (geoId != null ? geoId.hashCode() : 0);
      return result;
   }
}

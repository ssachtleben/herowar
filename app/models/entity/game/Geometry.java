package models.entity.game;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import dao.GeoMatId;
import json.GeometryDeserializer;
import json.GeometrySerializer;
import json.StringArray;
import models.entity.BaseModel;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@JsonDeserialize(using = GeometryDeserializer.class)
@JsonSerialize(using = GeometrySerializer.class)
@SuppressWarnings("serial")
public class Geometry extends BaseModel implements Serializable {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column(columnDefinition = "text")
   @StringArray(type = StringArray.ArrayType.DOUBLE)
   private String vertices = "[]";

   @Column(columnDefinition = "text")
   @StringArray(type = StringArray.ArrayType.DOUBLE)
   private String faces = "[]";

   @Column(columnDefinition = "text")
   @StringArray(type = StringArray.ArrayType.OBJECT)
   private String morphTargets = "[]";

   @Column(columnDefinition = "text")
   @StringArray(type = StringArray.ArrayType.DOUBLE)
   private String morphColors = "[]";

   @Column(columnDefinition = "text")
   @StringArray(type = StringArray.ArrayType.DOUBLE)
   private String normals = "[]";

   @Column(columnDefinition = "text")
   @StringArray(type = StringArray.ArrayType.DOUBLE)
   private String colors = "[]";

   @Column(columnDefinition = "text")
   @StringArray(type = StringArray.ArrayType.DOUBLE, dimensions = 2)
   private String uvs = "[[]]";

   @Column(columnDefinition = "text")
   @StringArray(type = StringArray.ArrayType.OBJECT)
   private String bones;

   @Column(columnDefinition = "text")
   @StringArray(type = StringArray.ArrayType.DOUBLE)
   private String skinIndices;

   @Column(columnDefinition = "text")
   @StringArray(type = StringArray.ArrayType.DOUBLE)
   private String skinWeights;

   @Column(columnDefinition = "text")
   private String animation;

   @Column(scale = 6)
   private Double scale = 1D;

   @Enumerated(EnumType.STRING)
   private GeometryType type;

   @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
   private GeoMetaData metadata;

   @OneToOne(fetch = FetchType.LAZY, mappedBy = "geometry", optional = true)
   @JsonIgnore
   private Terrain terrain;

   @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH, mappedBy = "geometry")
   @JsonIgnore
   private Set<Mesh> meshes = new HashSet<Mesh>();

   @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "id.geometry")
   @JsonIgnore
   private Set<GeoMaterial> geoMaterials = new HashSet<GeoMaterial>();

   @Transient
   private List<GeoMatId> matIdMapper = new ArrayList<GeoMatId>();

   @Transient
   private List<Material> materials = new ArrayList<Material>();

   // GETTER & SETTER //

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public String getVertices() {
      return vertices;
   }

   public void setVertices(String vertices) {
      this.vertices = vertices;
   }

   public String getFaces() {
      return faces;
   }

   public void setFaces(String faces) {
      this.faces = faces;
   }

   public String getMorphTargets() {
      return morphTargets;
   }

   public void setMorphTargets(String morphTargets) {
      this.morphTargets = morphTargets;
   }

   public String getMorphColors() {
      return morphColors;
   }

   public void setMorphColors(String morphColors) {
      this.morphColors = morphColors;
   }

   public String getNormals() {
      return normals;
   }

   public void setNormals(String normals) {
      this.normals = normals;
   }

   public String getColors() {
      return colors;
   }

   public void setColors(String colors) {
      this.colors = colors;
   }

   public String getUvs() {
      return uvs;
   }

   public void setUvs(String uvs) {
      this.uvs = uvs;
   }

   public String getBones() {
      return bones;
   }

   public void setBones(String bones) {
      this.bones = bones;
   }

   public String getSkinIndices() {
      return skinIndices;
   }

   public void setSkinIndices(String skinIndices) {
      this.skinIndices = skinIndices;
   }

   public String getSkinWeights() {
      return skinWeights;
   }

   public void setSkinWeights(String skinWeights) {
      this.skinWeights = skinWeights;
   }

   public String getAnimation() {
      return animation;
   }

   public void setAnimation(String animation) {
      this.animation = animation;
   }

   public Double getScale() {
      return scale;
   }

   public void setScale(Double scale) {
      this.scale = scale;
   }

   public GeoMetaData getMetadata() {
      return metadata;
   }

   public void setMetadata(GeoMetaData metadata) {
      this.metadata = metadata;
   }

   public GeometryType getType() {
      return type;
   }

   public void setType(GeometryType type) {
      this.type = type;
   }

   public Terrain getTerrain() {
      return terrain;
   }

   public void setTerrain(Terrain terrain) {
      this.terrain = terrain;
   }

   public List<GeoMatId> getMatIdMapper() {
      return matIdMapper;
   }

   public void setMatIdMapper(List<GeoMatId> matIdMapper) {
      this.matIdMapper = matIdMapper;
   }

   public Set<GeoMaterial> getGeoMaterials() {
      return geoMaterials;
   }

   public void setGeoMaterials(Set<GeoMaterial> geoMaterials) {
      this.geoMaterials = geoMaterials;
   }

   public List<Material> getMaterials() {
      return materials;
   }

   public void setMaterials(List<Material> materials) {
      this.materials = materials;
   }

   public Set<Mesh> getMeshes() {
      return meshes;
   }

   public void setMeshes(Set<Mesh> meshes) {
      this.meshes = meshes;
   }

   @Override
   public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((id == null) ? 0 : id.hashCode());
      result = prime * result + ((metadata == null) ? 0 : metadata.hashCode());
      result = prime * result + ((type == null) ? 0 : type.hashCode());
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
      Geometry other = (Geometry) obj;
      if (id == null) {
         if (other.id != null)
            return false;
      } else if (!id.equals(other.id))
         return false;
      if (metadata == null) {
         if (other.metadata != null)
            return false;
      } else if (!metadata.equals(other.metadata))
         return false;
      if (type != other.type)
         return false;
      return true;
   }

   @Override
   public String toString() {
      return "Geometry [id=" + id + ", vertices=" + vertices + ", faces=" + faces + ", morphTargets=" + morphTargets + ", morphColors=" + morphColors
            + ", normals=" + normals + ", colors=" + colors + ", uvs=" + uvs + ", scale=" + scale + ", type=" + type + ", metadata=" + metadata
            + ", terrain=" + terrain + ", meshes=" + meshes + ", geoMaterials=" + geoMaterials + ", matIdMapper=" + matIdMapper + ", materials="
            + materials + "]";
   }

}

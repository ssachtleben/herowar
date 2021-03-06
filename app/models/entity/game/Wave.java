package models.entity.game;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Sebastian Sachtleben
 */
@Entity
@SuppressWarnings("serial")
public class Wave implements Serializable {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   private String name;

   private Integer prepareTime;
   private Integer waveTime;
   private Integer quantity;
   private Integer sortOder;

   @Type(type = "yes_no")
   private Boolean requestable = Boolean.TRUE;

   @Type(type = "yes_no")
   private Boolean autostart = Boolean.FALSE;

   @ManyToOne(cascade = CascadeType.REFRESH)
   @JoinColumn(name = "map_id")
   @JsonIgnore
   private Map map;

   // @OneToMany(cascade = CascadeType.ALL, mappedBy="wave")
   // private Set<Army> armies;

   @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
   @JoinTable(name = "wave_units")
   @JsonIgnore
   private Set<Unit> units = new HashSet<Unit>();

   @ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.REFRESH })
   @JoinColumn(name = "path_id")
   @JsonIgnore
   private Path path;

   @Transient
   private Long pathId;

   @Transient
   private List<Long> unitIds;

   // GETTER & SETTER //

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public Map getMap() {
      return map;
   }

   public void setMap(Map map) {
      this.map = map;
   }

   public Set<Unit> getUnits() {
      return units;
   }

   public void setUnits(Set<Unit> units) {
      this.units = units;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public Integer getPrepareTime() {
      return prepareTime;
   }

   public void setPrepareTime(Integer prepareTime) {
      this.prepareTime = prepareTime;
   }

   public Integer getWaveTime() {
      return waveTime;
   }

   public void setWaveTime(Integer waveTime) {
      this.waveTime = waveTime;
   }

   public Integer getQuantity() {
      return quantity;
   }

   public void setQuantity(Integer quantity) {
      this.quantity = quantity;
   }

   public Path getPath() {
      return path;
   }

   public void setPath(Path path) {
      this.path = path;
   }

   public Long getPathId() {
      return pathId;
   }

   public void setPathId(Long pathId) {
      this.pathId = pathId;
   }

   public List<Long> getUnitIds() {
      return unitIds;
   }

   public void setUnitIds(List<Long> unitIds) {
      this.unitIds = unitIds;
   }

   public Integer getSortOder() {
      return sortOder;
   }

   public void setSortOder(Integer sortOder) {
      this.sortOder = sortOder;
   }

   /**
    * Is it possible to call this wave before the current running wave is over?
    *
    * @return Boolean
    */
   public Boolean isRequestable() {
      return requestable;
   }

   public void setRequestable(Boolean requestable) {
      this.requestable = requestable;
   }

   /**
    * The wave is starting after the wave before is done, this should be usually on false (maybe the tutorial map needs true here).
    *
    * @return Boolean
    */
   public Boolean isAutostart() {
      return autostart;
   }

   public void setAutostart(Boolean autostart) {
      this.autostart = autostart;
   }

   // public Set<Army> getArmies() {
   // return armies;
   // }
   //
   // public void setArmies(Set<Army> armies) {
   // this.armies = armies;
   // }

   @Override
   public boolean equals(Object o) {
      if (this == o)
         return true;
      if (o == null || getClass() != o.getClass())
         return false;

      Wave wave = (Wave) o;

      if (id != null ? !id.equals(wave.id) : wave.id != null)
         return false;
      if (name != null ? !name.equals(wave.name) : wave.name != null)
         return false;
      if (prepareTime != null ? !prepareTime.equals(wave.prepareTime) : wave.prepareTime != null)
         return false;
      if (waveTime != null ? !waveTime.equals(wave.waveTime) : wave.waveTime != null)
         return false;
      if (quantity != null ? !quantity.equals(wave.quantity) : wave.quantity != null)
         return false;
      if (sortOder != null ? !sortOder.equals(wave.sortOder) : wave.sortOder != null)
         return false;
      if (requestable != null ? !requestable.equals(wave.requestable) : wave.requestable != null)
         return false;
      if (autostart != null ? !autostart.equals(wave.autostart) : wave.autostart != null)
         return false;
      return !(pathId != null ? !pathId.equals(wave.pathId) : wave.pathId != null);

   }

   @Override
   public int hashCode() {
      int result = id != null ? id.hashCode() : 0;
      result = 31 * result + (name != null ? name.hashCode() : 0);
      result = 31 * result + (prepareTime != null ? prepareTime.hashCode() : 0);
      result = 31 * result + (waveTime != null ? waveTime.hashCode() : 0);
      result = 31 * result + (quantity != null ? quantity.hashCode() : 0);
      result = 31 * result + (sortOder != null ? sortOder.hashCode() : 0);
      result = 31 * result + (requestable != null ? requestable.hashCode() : 0);
      result = 31 * result + (autostart != null ? autostart.hashCode() : 0);
      result = 31 * result + (pathId != null ? pathId.hashCode() : 0);
      return result;
   }
}

package models.entity.game;

import com.fasterxml.jackson.annotation.JsonIgnore;
import models.entity.User;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@SuppressWarnings("serial")
public class Player implements Serializable {

   @Id
   @GeneratedValue(generator = "playerGen")
   @GenericGenerator(name = "playerGen", strategy = "foreign", parameters = @Parameter(name = "property", value = "user"))
   private Long id;

   @OneToOne(mappedBy = "player")
   @NotNull
   @JsonIgnore
   private User user;

   private Long level = 1L;
   private Long experience = 0L;
   private Long wins = 0L;
   private Long losses = 0L;
   private Long kills = 0L;
   private Long assists = 0L;

   @OneToMany(fetch = FetchType.LAZY, mappedBy = "player", cascade = { CascadeType.ALL })
   @JsonIgnore
   private Set<MatchResult> matchResults = new HashSet<MatchResult>();

   @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
   @PrimaryKeyJoinColumn
   @JsonIgnore
   private PlayerSettings settings;

   // CONSTRUCTOR //

   public Player() {
      settings = new PlayerSettings(this);
   }

   public Player(User user) {
      this();
      this.user = user;
   }

   // GETTER & SETTER //

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public User getUser() {
      return user;
   }

   public void setUser(User user) {
      this.user = user;
   }

   public Long getLevel() {
      return level;
   }

   public void setLevel(Long level) {
      this.level = level;
   }

   public Long getExperience() {
      return experience;
   }

   public void setExperience(Long experience) {
      this.experience = experience;
   }

   public Long getWins() {
      return wins;
   }

   public void setWins(Long wins) {
      this.wins = wins;
   }

   public Long getLosses() {
      return losses;
   }

   public void setLosses(Long losses) {
      this.losses = losses;
   }

   public Long getKills() {
      return kills;
   }

   public void setKills(Long kills) {
      this.kills = kills;
   }

   public Long getAssists() {
      return assists;
   }

   public void setAssists(Long assists) {
      this.assists = assists;
   }

   public Set<MatchResult> getMatchResults() {
      return matchResults;
   }

   public void setMatchResults(Set<MatchResult> matchResults) {
      this.matchResults = matchResults;
   }

   public PlayerSettings getSettings() {
      return settings;
   }

   public void setSettings(PlayerSettings settings) {
      this.settings = settings;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o)
         return true;
      if (o == null || getClass() != o.getClass())
         return false;

      Player player = (Player) o;

      if (id != null ? !id.equals(player.id) : player.id != null)
         return false;
      if (level != null ? !level.equals(player.level) : player.level != null)
         return false;
      if (experience != null ? !experience.equals(player.experience) : player.experience != null)
         return false;
      if (wins != null ? !wins.equals(player.wins) : player.wins != null)
         return false;
      if (losses != null ? !losses.equals(player.losses) : player.losses != null)
         return false;
      if (kills != null ? !kills.equals(player.kills) : player.kills != null)
         return false;
      return !(assists != null ? !assists.equals(player.assists) : player.assists != null);

   }

   @Override
   public int hashCode() {
      int result = id != null ? id.hashCode() : 0;
      result = 31 * result + (level != null ? level.hashCode() : 0);
      result = 31 * result + (experience != null ? experience.hashCode() : 0);
      result = 31 * result + (wins != null ? wins.hashCode() : 0);
      result = 31 * result + (losses != null ? losses.hashCode() : 0);
      result = 31 * result + (kills != null ? kills.hashCode() : 0);
      result = 31 * result + (assists != null ? assists.hashCode() : 0);
      return result;
   }
}

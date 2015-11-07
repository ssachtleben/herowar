package models.entity.game;

import models.entity.BaseModel;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * The match entity represents a played match in our application.
 *
 * @author Sebastian Sachtleben
 */
@Entity
@Table(name = "matches")
@SuppressWarnings("serial")
public class Match extends BaseModel {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;
   private Long preloadTime;
   private Long gameTime;
   private Integer lives;

   @Type(type = "yes_no")
   private Boolean victory;

   @Enumerated(EnumType.STRING)
   private MatchState state = MatchState.INIT;

   @ManyToOne(cascade = { CascadeType.REFRESH }, optional = false)
   @JoinColumn(name = "map_id")
   private Map map;

   @OneToMany(cascade = CascadeType.ALL, mappedBy = "match")
   private Set<MatchResult> playerResults = new HashSet<MatchResult>();

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public Long getPreloadTime() {
      return preloadTime;
   }

   public void setPreloadTime(Long preloadTime) {
      this.preloadTime = preloadTime;
   }

   public Long getGameTime() {
      return gameTime;
   }

   public void setGameTime(Long gameTime) {
      this.gameTime = gameTime;
   }

   public Integer getLives() {
      return lives;
   }

   public void setLives(Integer lives) {
      this.lives = lives;
   }

   public Boolean getVictory() {
      return victory;
   }

   public void setVictory(Boolean victory) {
      this.victory = victory;
   }

   public MatchState getState() {
      return state;
   }

   public void setState(MatchState state) {
      this.state = state;
   }

   public Map getMap() {
      return map;
   }

   public void setMap(Map map) {
      this.map = map;
   }

   public Set<MatchResult> getPlayerResults() {
      return playerResults;
   }

   public void setPlayerResults(Set<MatchResult> playerResults) {
      this.playerResults = playerResults;
   }

   /*
    * (non-Javadoc)
    *
    * @see java.lang.Object#toString()
    */
   @Override
   public String toString() {
      return "Match [id=" + id + "]";
   }

   @Override
   public boolean equals(Object o) {
      if (this == o)
         return true;
      if (o == null || getClass() != o.getClass())
         return false;
      if (!super.equals(o))
         return false;

      Match match = (Match) o;

      if (id != null ? !id.equals(match.id) : match.id != null)
         return false;
      if (preloadTime != null ? !preloadTime.equals(match.preloadTime) : match.preloadTime != null)
         return false;
      if (gameTime != null ? !gameTime.equals(match.gameTime) : match.gameTime != null)
         return false;
      if (lives != null ? !lives.equals(match.lives) : match.lives != null)
         return false;
      return !(victory != null ? !victory.equals(match.victory) : match.victory != null);

   }

   @Override
   public int hashCode() {
      int result = super.hashCode();
      result = 31 * result + (id != null ? id.hashCode() : 0);
      result = 31 * result + (preloadTime != null ? preloadTime.hashCode() : 0);
      result = 31 * result + (gameTime != null ? gameTime.hashCode() : 0);
      result = 31 * result + (lives != null ? lives.hashCode() : 0);
      result = 31 * result + (victory != null ? victory.hashCode() : 0);
      return result;
   }
}

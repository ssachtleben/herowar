package models.entity.game;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;

/**
 * The PlayerMatchResult contains information about player specific details for a match.
 *
 * @author Sebastian Sachtleben
 */
@Entity
@SuppressWarnings("serial")
public class MatchResult implements Serializable {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   private Long score = 0L;
   private Long kills = 0L;

   @ManyToOne(cascade = { CascadeType.REFRESH }, optional = false)
   @JoinColumn(name = "player_id")
   private Player player;

   @OneToOne(cascade = { CascadeType.REFRESH }, mappedBy = "result")
   @JsonIgnore
   private MatchToken token;

   @ManyToOne(cascade = { CascadeType.REFRESH }, optional = false)
   @JoinColumn(name = "match_id")
   @JsonIgnore
   private Match match;

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public Long getScore() {
      return score;
   }

   public void setScore(Long score) {
      this.score = score;
   }

   public Long getKills() {
      return kills;
   }

   public void setKills(Long kills) {
      this.kills = kills;
   }

   public Player getPlayer() {
      return player;
   }

   public void setPlayer(Player player) {
      this.player = player;
   }

   public MatchToken getToken() {
      return token;
   }

   public void setToken(MatchToken token) {
      this.token = token;
   }

   public Match getMatch() {
      return match;
   }

   public void setMatch(Match match) {
      this.match = match;
   }

   @Override
   public String toString() {
      return "MatchResult [id=" + id + ", score=" + score + "]";
   }

   @Override
   public boolean equals(Object o) {
      if (this == o)
         return true;
      if (o == null || getClass() != o.getClass())
         return false;

      MatchResult that = (MatchResult) o;

      if (id != null ? !id.equals(that.id) : that.id != null)
         return false;
      if (score != null ? !score.equals(that.score) : that.score != null)
         return false;
      return !(kills != null ? !kills.equals(that.kills) : that.kills != null);

   }

   @Override
   public int hashCode() {
      int result = id != null ? id.hashCode() : 0;
      result = 31 * result + (score != null ? score.hashCode() : 0);
      result = 31 * result + (kills != null ? kills.hashCode() : 0);
      return result;
   }
}

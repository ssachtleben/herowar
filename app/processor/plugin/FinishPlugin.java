package processor.plugin;

import dao.MatchDAO;
import models.entity.game.Match;
import models.entity.game.MatchResult;
import models.entity.game.MatchState;
import models.entity.game.Player;
import network.Connection;
import network.server.GameDefeatPacket;
import network.server.GameVictoryPacket;
import play.Logger;
import play.Play;
import play.db.jpa.JPA;
import processor.CacheConstants;
import processor.GameProcessor;
import processor.GameProcessor.State;
import processor.meta.AbstractPlugin;
import processor.meta.IPlugin;

import java.util.Date;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The FinishPlugin sends informations about the state of the game and clean up.
 *
 * @author Sebastian Sachtleben
 */
public class FinishPlugin extends AbstractPlugin implements IPlugin {
   private final static Logger.ALogger log = Logger.of(FinishPlugin.class);

   private long finishTimer = -1;
   private boolean done = false;

   public FinishPlugin(GameProcessor processor) {
      super(processor);
   }

   @Override
   public void process(double delta, long now) {
      if (finishTimer == -1) {
         finishTimer = now;
      }
      if (!done && finishTimer + 2000 <= now) {
         done = true;
         finishTimer = now;
         if (getMap().getLives() <= 0) {
            GameDefeatPacket packet = new GameDefeatPacket();
            broadcast(packet);
            log.info("<" + game().getTopicName() + "> Game is defeated");
         } else {
            GameVictoryPacket packet = new GameVictoryPacket();
            broadcast(packet);
            log.info("<" + game().getTopicName() + "> Game is victory");
         }
         saveResults();
      } else if (done && finishTimer + 2000 <= now) {
         // TODO: stop properly the game here and cleanup
         game().stop();
      }
   }

   private void saveResults() {
      final boolean victory = getMap().getLives() > 0;
      final int lives = getMap().getLives();
      JPA.withTransaction(new play.libs.F.Callback0() {
         @Override
         public void invoke() throws Throwable {
            Match match = Play.application().injector().instanceOf(MatchDAO.class).getById(getMatch().getId());
            match.setState(MatchState.FINISH);
            match.setVictory(victory);
            match.setLives(lives);
            match.setGameTime(new Date().getTime() - (match.getCdate().getTime() + match.getPreloadTime()));
            Iterator<MatchResult> results = match.getPlayerResults().iterator();
            while (results.hasNext()) {
               final MatchResult result = results.next();
               final Player player = result.getPlayer();
               ConcurrentHashMap<String, Object> cache = getPlayerCache(player.getId());
               result.setScore(Math.round(Double.parseDouble(cache.get(CacheConstants.SCORE).toString())));
               result.setKills(Long.parseLong(cache.get(CacheConstants.KILLS).toString()));
               result.getToken().setInvalid(true);
               player.setKills(player.getKills() + result.getKills());
               if (match.getVictory()) {
                  player.setWins(player.getWins() + 1);
               } else {
                  player.setLosses(player.getLosses() + 1);
               }
               player.setExperience(player.getExperience() + (match.getVictory() ? 1000 : 100));
            }
         }
      });
   }

   @Override
   public void add(Connection connection) {
      // TODO Auto-generated method stub
   }

   @Override
   public void remove(Connection connection) {
      // TODO Auto-generated method stub
   }

   @Override
   public State onState() {
      return State.FINISH;
   }

   @Override
   public String toString() {
      return "FinishPlugin";
   }
}

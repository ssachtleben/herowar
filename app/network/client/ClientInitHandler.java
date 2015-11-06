package network.client;

import com.ssachtleben.play.plugin.event.Events;
import com.ssachtleben.play.plugin.event.ReferenceStrength;
import com.ssachtleben.play.plugin.event.annotations.Observer;
import dao.MatchTokenDAO;
import models.entity.game.MatchToken;
import network.Connection;
import network.server.AccessDeniedPacket;
import network.server.AccessGrantedPacket;
import play.Logger;
import play.Play;
import play.db.jpa.JPA;
import utils.EventKeys;

public class ClientInitHandler {
   private static final Logger.ALogger log = Logger.of(ClientInitHandler.class);

   @Observer(topic = "ClientInitPacket", referenceStrength = ReferenceStrength.STRONG)
   public static void observe(final Connection connection, final ClientInitPacket packet) {
      JPA.withTransaction(new play.libs.F.Callback0() {
         @Override
         public void invoke() throws Throwable {
            MatchToken matchToken = Play.application().injector().instanceOf(MatchTokenDAO.class).getTokenById(packet.getToken());
            if (matchToken != null) {
               log.info(String.format("Accepted [connection=%s, matchToken=%s]", connection, matchToken));
               Events.instance().publish(EventKeys.PLAYER_JOIN, connection, matchToken);
               connection.send(new AccessGrantedPacket());
            } else {
               connection.send(new AccessDeniedPacket());
            }
         }
      });
   }

}

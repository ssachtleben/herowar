package network.client;

import com.ssachtleben.play.plugin.event.ReferenceStrength;
import com.ssachtleben.play.plugin.event.annotations.Observer;
import network.Connection;
import play.Logger;
import play.db.jpa.JPA;

public class ClientWaveRequestHandler {
   private static final Logger.ALogger log = Logger.of(ClientWaveRequestHandler.class);

   @Observer(topic = "ClientWaveRequestPacket", referenceStrength = ReferenceStrength.STRONG)
   public static void observe(final Connection connection, final ClientWaveRequestPacket packet) {
      JPA.withTransaction(new play.libs.F.Callback0() {
         @Override
         public void invoke() throws Throwable {
            log.info("Request next wave...");
            connection.game().setWaveRequest(true);
         }
      });
   }

}

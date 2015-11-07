package network.client;

import com.ssachtleben.play.plugin.event.ReferenceStrength;
import com.ssachtleben.play.plugin.event.annotations.Observer;
import network.Connection;
import play.Logger;
import play.db.jpa.JPA;

public class ClientTutorialUpdateHandler {
   private static final Logger.ALogger log = Logger.of(ClientTutorialUpdateHandler.class);

   @Observer(topic = "ClientTutorialUpdatePacket", referenceStrength = ReferenceStrength.STRONG)
   public static void observe(final Connection connection, final ClientTutorialUpdatePacket packet) {
      JPA.withTransaction(new play.libs.F.Callback0() {
         @Override
         public void invoke() throws Throwable {
            log.info("Request next tutorial step...");
            connection.game().setTutorialUpdate(true);
         }
      });
   }

}

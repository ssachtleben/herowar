package network.client;

import com.ssachtleben.play.plugin.event.ReferenceStrength;
import com.ssachtleben.play.plugin.event.annotations.Observer;
import network.Connection;
import network.server.ChatMessagePacket;
import network.server.ChatMessagePacket.Layout;
import play.db.jpa.JPA;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ClientChatMessageHandler {
   private static final DateFormat df = new SimpleDateFormat("hh:mm");

   @Observer(topic = "ClientChatMessagePacket", referenceStrength = ReferenceStrength.STRONG)
   public static void observe(final Connection connection, final ClientChatMessagePacket packet) {
      JPA.withTransaction(new play.libs.F.Callback0() {
         @Override
         public void invoke() throws Throwable {
            connection.game().broadcast(new ChatMessagePacket(Layout.USER,
                        "[" + df.format(new Date()) + "] " + connection.user().getUsername() + ": " + packet.getMessage()));
         }
      });
   }

}

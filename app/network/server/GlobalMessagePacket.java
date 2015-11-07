package network.server;

import network.BasePacket;
import network.PacketType;

/**
 * The GlobalMessagePacket will be send to a player and contains a global message.
 *
 * @author Sebastian Sachtleben
 */
@SuppressWarnings("serial")
public class GlobalMessagePacket extends BasePacket {

   private String message;

   public GlobalMessagePacket(String message) {
      super();
      this.type = PacketType.GlobalMessagePacket;
      this.message = message;
   }

   public String getMessage() {
      return message;
   }

   public void setMessage(String message) {
      this.message = message;
   }
}

package network.server;

import network.BasePacket;
import network.PacketType;

/**
 * Server send access granted packet.
 *
 * @author Alexander Wilhelmer
 */

@SuppressWarnings("serial")
public class AccessGrantedPacket extends BasePacket {

   public AccessGrantedPacket() {
      this.type = PacketType.AccessGrantedPacket;
   }

   @Override
   public String toString() {
      return "AccessGrantedPacket [type=" + type + "]";
   }

}

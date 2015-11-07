package network.server;

import network.BasePacket;
import network.PacketType;

/**
 * Server sends info that a new player left.
 *
 * @author Alexander Wilhelmer
 */
@SuppressWarnings("serial")
public class PlayerLeavePacket extends BasePacket {

   public PlayerLeavePacket() {
      this.type = PacketType.PlayerLeavePacket;
   }

   @Override
   public String toString() {
      return "PlayerLeavePacket [type=" + type + "]";
   }

}

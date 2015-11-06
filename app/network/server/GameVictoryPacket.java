package network.server;

import network.BasePacket;
import network.PacketType;

/**
 * Server send GameVictoryPacket on victory.
 * 
 * @author Sebastian Sachtleben
 */
@SuppressWarnings("serial")
public class GameVictoryPacket extends BasePacket {

	public GameVictoryPacket() {
		this.type = PacketType.GameVictoryPacket;
	}

	@Override
	public String toString() {
		return "GameVictoryPacket [type=" + type + "]";
	}
}

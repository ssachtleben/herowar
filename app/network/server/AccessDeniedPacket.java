package network.server;

import network.BasePacket;
import network.PacketType;

/**
 * Server send access denied packet.
 * 
 * @author Alexander Wilhelmer
 */
@SuppressWarnings("serial")
public class AccessDeniedPacket extends BasePacket {

	public AccessDeniedPacket() {
		this.type = PacketType.AccessDeniedPacket;
	}

	@Override
	public String toString() {
		return "AccessDeniedPacket [type=" + type + "]";
	}
}

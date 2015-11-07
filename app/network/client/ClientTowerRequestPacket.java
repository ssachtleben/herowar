package network.client;

import models.entity.game.Vector3;
import network.ClientPacket;
import network.PacketType;


/**
 * The ClientTowerRequestPacket will be send from client when he request to build a tower somewhere.
 * 
 * @author Sebastian Sachtleben
 */
@ClientPacket(type = PacketType.ClientTowerRequestPacket)
@SuppressWarnings("serial")
public class ClientTowerRequestPacket extends BaseClientAuthPacket {

	private Long id;
	private Vector3 position;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Vector3 getPosition() {
		return position;
	}

	public void setPosition(Vector3 position) {
		this.position = position;
	}

	@Override
	public String toString() {
		return "ClientTowerRequestPacket [type=" + type + ", createdTime=" + createdTime + ", id=" + id + ", position=" + position + "]";
	}
}

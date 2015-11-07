package processor.plugin;


import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import models.TowerModel;
import models.UnitModel;
import network.Connection;
import network.server.TowerAttackPacket;
import network.server.TowerTargetPacket;
import processor.GameProcessor;
import processor.GameProcessor.State;
import processor.meta.AbstractPlugin;
import processor.meta.IPlugin;

/**
 * The TowerUpdatePlugin handles all tower on the map and calculates the current target and handle shots.
 * 
 * @author Sebastian Sachtleben
 */
public class TowerPlugin extends AbstractPlugin implements IPlugin {

	public TowerPlugin(GameProcessor processor) {
		super(processor);
	}

	@Override
	public void process(double delta, long now) {
		Set<UnitModel> units = game().getUnits();

		Collection<TowerModel> towers = game().getTowerCache().values();

		Iterator<TowerModel> iter = towers.iterator();
		while (iter.hasNext()) {
			TowerModel tower = iter.next();
			UnitModel target = null;
			synchronized (units) {
				target = tower.findTarget(units);
			}
			if (target != null) {
				if (target != tower.getTarget()) {
					TowerTargetPacket packet = new TowerTargetPacket(tower.getId(), target.getId());
					broadcast(packet);
				}
				tower.rotateTo(target, delta);
				if (tower.shoot(target)) {
					long damage = tower.calculateDamage(target);
					target.hit(tower, damage);
					TowerAttackPacket packet = new TowerAttackPacket(tower.getId(), damage);
					broadcast(packet);
				}
			}
			tower.setTarget(target);
		}
	}

	@Override
	public void add(Connection connection) {
		// Empty
	}

	@Override
	public void remove(Connection connection) {
		// Empty
	}

	@Override
	public State onState() {
		return State.GAME;
	}

	@Override
	public String toString() {
		return "TowerPlugin";
	}
}

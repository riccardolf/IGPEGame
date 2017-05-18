package it.unical.igpe.logic;

import it.unical.igpe.tools.GameConfig;
import it.unical.igpe.tools.Lootable;

public class HealthPack extends AbstractStaticObject implements Lootable{
	private int regen;
	
	public HealthPack() {
		regen = GameConfig.REGENERATION;
	}

	@Override
	public int looted() {
		return regen;
	}
}

package it.unical.igpe.entity;

import it.unical.igpe.GameConfig;

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

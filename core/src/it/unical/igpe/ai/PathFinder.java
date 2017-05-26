package it.unical.igpe.ai;

import it.unical.igpe.logic.Enemy;

public interface PathFinder {
	/**
	 * 
	 * @param enemy The entity that will be moving along the path. This provides basic informations
	 * @param sx The x coordinate of the start location
	 * @param sy The y coordinate of the start location
	 * @param tx The x coordinate of the target location
	 * @param ty The y coordinate of the target location
	 * @return
	 */
	public Path findPath(Enemy enemy, int sx, int sy, int tx, int ty);
}

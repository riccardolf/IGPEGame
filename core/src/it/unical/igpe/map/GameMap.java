package it.unical.igpe.map;

public class GameMap implements TileBasedMap {
	public static final int WIDTH = 64;
	public static final int HEIGHT = 64;
	
	/** Indicate ground terrain at a given location */
	public static final int GROUND = 0;
	/** Indicate wall terrain at a given location */
	public static final int WALL = 1;
	/** Indicate door to end level at a given location */
	public static final int END = 2;
	
	/** The terrain settings for each tile in the map */
	private int[][] terrain = new int[WIDTH][HEIGHT];
	/** The unit in each tile of the map */
	private int[][] units = new int[WIDTH][HEIGHT];
	/** Indicator if a given tile has been visited during the search */
	private boolean[][] visited = new boolean[WIDTH][HEIGHT];
	
	@Override
	public int getWidthInTiles() {
		return WIDTH;
	}

	@Override
	public int getHeightInTiles() {
		return HEIGHT;
	}

	@Override
	public void pathFinderVisited(int x, int y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean blocked(Mover mover, int x, int y) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public float getCost(Mover mover, int sx, int sy, int tx, int ty) {
		return 1;
	}
	
}

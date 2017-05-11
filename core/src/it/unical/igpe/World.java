package it.unical.igpe;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;

import it.unical.igpe.entity.Bullet;
import it.unical.igpe.entity.Enemy;
import it.unical.igpe.entity.Player;
import it.unical.igpe.entity.Wall;

public class World {
	// Array di booleani con i muri, al movimento del player, controllo se in quella posizione c'è il muro
	// for su bls, for su enemy, se c'è una collisione, l'enemy prende danno e il proiettile viene rimosso
	private Player player;
	private Vector2 posP;
	private LinkedList<Bullet> bls;
	private LinkedList<Wall> wls;
	private LinkedList<Enemy> ens;
	private int[][] map;
	public float rotation;
	TileLayer layer;

	@SuppressWarnings("static-access")
	public World() {
		posP = new Vector2(100, 100);
		player = new Player(posP);
		Enemy enemy1 = new Enemy(new Vector2(600, 600));
		Enemy enemy2 = new Enemy(new Vector2(-600, -600));
		Enemy enemy3 = new Enemy(new Vector2(-600, 600));
		bls = new LinkedList<Bullet>();
		wls = new LinkedList<Wall>();
		ens = new LinkedList<Enemy>();
		ens.add(enemy1);
		ens.add(enemy2);
		ens.add(enemy3);

		map = new int[128][128];
		try {
			layer = layer.FromFile("map.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}

		map = layer.map;
		for (int y = 0; y < map.length; y++)
			for (int x = 0; x < map[y].length; x++)
				if (map[y][x] == 1)
					wls.add(new Wall(new Vector2(y * 64, x * 64)));

	}

	public void updateWorld() {
		rotation = calculateAngle((float) Gdx.input.getX(), (float) Gdx.input.getY());

		if (Gdx.input.isKeyPressed(Input.Keys.W) && Gdx.input.isKeyPressed(Input.Keys.A))
			player.MoveUpLeft();
		else if (Gdx.input.isKeyPressed(Input.Keys.W) && Gdx.input.isKeyPressed(Input.Keys.D))
			player.MoveUpRight();
		else if (Gdx.input.isKeyPressed(Input.Keys.S) && Gdx.input.isKeyPressed(Input.Keys.A))
			player.MoveDownLeft();
		else if (Gdx.input.isKeyPressed(Input.Keys.S) && Gdx.input.isKeyPressed(Input.Keys.D))
			player.MoveDownRight();

		else if (Gdx.input.isKeyPressed(Input.Keys.W))
			player.MoveUp();
		else if (Gdx.input.isKeyPressed(Input.Keys.A))
			player.MoveLeft();
		else if (Gdx.input.isKeyPressed(Input.Keys.S))
			player.MoveDown();
		else if (Gdx.input.isKeyPressed(Input.Keys.D))
			player.MoveRight();

		player.updateBoundingBox();
		bls = player.getBullets();
		for (Bullet bullet : bls) {
			bullet.update();
		}

		if (Gdx.input.justTouched())
			player.fire(rotation + 90);
		
		for(Iterator<Bullet> it = bls.iterator(); it.hasNext();) {
			Bullet b = (Bullet) it.next();
			for(Iterator<Enemy> iter = ens.iterator(); iter.hasNext();) {
				Enemy e = (Enemy) iter.next();
				if(b.handleCollision(e.getBoundingBox())) {
					System.out.println("Collide");
					e.hit(10f);
					it.remove();
				}
			}
		}
		
		/*for (Enemy e : ens) {
			e.findPathToTarget(player.getPos());
		}	*/	
	}

	public float calculateAngle(float x, float y) {
		return (float) Math.toDegrees((Math.PI / 2 - Math.atan2(GameConfig.HEIGHT / 2 - y, GameConfig.WIDTH / 2 - x)));
	}

	public Player getPlayer() {
		return player;
	}

	public LinkedList<Enemy> getEnemy() {
		return ens;
	}

	public Vector2 getPosP() {
		return posP;
	}

	public LinkedList<Bullet> getBls() {
		return bls;
	}

	public LinkedList<Wall> getWls() {
		return wls;
	}

	public LinkedList<Enemy> getEns() {
		return ens;
	}

	public int[][] getMap() {
		return map;
	}
}

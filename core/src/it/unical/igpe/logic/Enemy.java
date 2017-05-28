package it.unical.igpe.logic;

import java.awt.Rectangle;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Random;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Array.ArrayIterator;

import it.unical.igpe.map.Mover;
import it.unical.igpe.tools.GameConfig;
import it.unical.igpe.tools.Updatable;

public class Enemy extends AbstractGameObject implements Updatable, Mover {
	private boolean ChaseObj;
	private LinkedList<Player> players;
	private ListIterator<Vector2> iterator;
	private Vector2 target;
	private int targetDistance;
	private Array<Vector2> path;

	public Enemy(Vector2 _pos, Player _player) {
		boundingBox = new Rectangle((int) _pos.x, (int) _pos.y, 64, 64);
		ID = "enemy";
		alive = true;
		HP = 100f;
		speed = GameConfig.MOVESPEED + 40;
		ChaseObj = true;
		players = new LinkedList<Player>();
		players.add(_player);
		path = new Array<Vector2>();
	}

	@Override
	public boolean update() {
		if(this.HP <= 0)
			return false;
		if(!ChaseObj) {
			Random r = new Random();
			int i = r.nextInt(4);
			switch (i) {
			case 0:
				this.MoveLeft();
				break;
			case 1:
				this.MoveUp();
				break;
			case 2:
				this.MoveRight();
				break;
			case 3:
				this.MoveDown();
				break;
			default:
				break;
			}
		}
		else if(ChaseObj) {
//			target = players.getFirst().getPos();
//			//Enemy choose closest player (multiplayer)
//			targetDistance = (int) Math.sqrt(Math.pow((players.getFirst().getPos().x - this.getBoundingBox().x), 2)
//								 + Math.pow(players.getFirst().getPos().y - this.getBoundingBox().y, 2));
//			for (Player player : players) {
//				if(Math.sqrt(Math.pow((player.getPos().x - this.getBoundingBox().x), 2)
//					+ Math.pow(player.getPos().y - this.getBoundingBox().y, 2)) < targetDistance) {
//					target = player.getPos();
//					targetDistance = (int) Math.sqrt(Math.pow((player.getPos().x - this.getBoundingBox().x), 2)
//							+ Math.pow(player.getPos().y - this.getBoundingBox().y, 2));
//				}
//			}
//			followPath();
			ArrayIterator<Vector2> iter = (ArrayIterator<Vector2>) path.iterator();
			while(iter.hasNext()) {	
				Vector2 pos = iter.next();
				this.setPos(pos);
				System.out.println("Pos set" + pos.toString());
				iter.remove();
				System.out.println("Pos removed" + pos.toString());
				break;
			}
		}
		return true;
	}

	public void hit(float dmg) {
		this.HP -= dmg;
		if (HP <= 0)
			alive = false;
	}

	public void setPath(Array<Vector2> array) {
		path = array;
	}

	public void followPath() {
		if (this.boundingBox.x < target.x + 100)
			this.MoveRight();
		else if (this.boundingBox.x > target.x - 100)
			this.MoveLeft();
		else if (this.boundingBox.y < target.y + 100)
			this.MoveUp();
		else if (this.boundingBox.y > target.y - 100)
			this.MoveDown();
	}

	public boolean canShoot(Vector2 target) {
		if (Math.sqrt(Math.pow((target.x - this.getBoundingBox().x), 2)
				+ Math.pow(target.y - this.getBoundingBox().y, 2)) < 128) {
			return true;
		}
		return false;
	}

	@Override
	public int getType() {
		return 1;
	}
}

package it.unical.igpe.logic;

import java.awt.Rectangle;
import java.util.LinkedList;
import java.util.Random;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.IntArray;

import it.unical.igpe.tools.Updatable;

public class Enemy extends AbstractGameObject implements Updatable {
	private boolean chaseObj;
	private boolean shootingObj;
	public float rotation;
	private Vector2 dir;
	private LinkedList<Player> players;
	private IntArray path;

	public Enemy(Vector2 _pos, Player _player) {
		boundingBox = new Rectangle((int) _pos.x, (int) _pos.y, 64, 64);
		ID = "enemy";
		alive = true;
		HP = 100f;
		speed = 1;
		chaseObj = true;
		players = new LinkedList<Player>();
		players.add(_player);
		path = new IntArray();
		dir = new Vector2();
	}

	@Override
	public boolean update() {
		float startx = this.getBoundingBox().x / 32;
		float starty = this.getBoundingBox().y / 32;
		float targetx = players.getFirst().getBoundingBox().x / 32;
		float targety = players.getFirst().getBoundingBox().y / 32;
		dir = new Vector2(targetx - startx, targety - starty);
		dir.rotate90(-1);
		rotation = dir.angle();
		if (this.HP <= 0)
			return false;
		if (this.getPos().dst(players.getFirst().getPos()) < 256
				&& this.getPos().dst(players.getFirst().getPos()) > 192) {
			chaseObj = true;
			shootingObj = false;
		} else if (this.getPos().dst(players.getFirst().getPos()) <= 192) {
			chaseObj = false;
			shootingObj = true;
		} else {
			chaseObj = false;
			shootingObj = false;
		}

		if (!chaseObj && !shootingObj) {
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
		} else if (chaseObj) {
			for (int i = 0; i < path.size; i += 2) {
				float x = path.get(i);
				float y = path.get(i + 1);
				this.setPos(new Vector2(x * 64, y * 64));
			}
		} else if (shootingObj) {
			
		}
		return true;
	}

	public void hit(float dmg) {
		this.HP -= dmg;
		if (HP <= 0)
			alive = false;
	}

	public void setPath(IntArray intArray) {
		path = intArray;
	}

	public void followPath(Vector2 pos) {
		if (this.boundingBox.x < pos.x + 32)
			this.MoveRight();
		if (this.boundingBox.x > pos.x + 32)
			this.MoveLeft();
		if (this.boundingBox.y < pos.y + 32)
			this.MoveUp();
		if (this.boundingBox.y > pos.y + 32)
			this.MoveDown();
	}

	public boolean canShoot(Vector2 target) {
		if (Math.sqrt(Math.pow((target.x - this.getBoundingBox().x), 2)
				+ Math.pow(target.y - this.getBoundingBox().y, 2)) < 128) {
			return true;
		}
		return false;
	}
}

package it.unical.igpe.logic;

import java.awt.Rectangle;
import java.util.LinkedList;
import java.util.Random;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.IntArray;


import it.unical.igpe.tools.GameConfig;
import it.unical.igpe.tools.Updatable;

public class Enemy extends AbstractGameObject implements Updatable {
	private boolean ChaseObj;
	private LinkedList<Player> players;
	private IntArray path;

	public Enemy(Vector2 _pos, Player _player) {
		boundingBox = new Rectangle((int) _pos.x, (int) _pos.y, 64, 64);
		ID = "enemy";
		alive = true;
		HP = 100f;
		speed = GameConfig.MOVESPEED + 32;
		ChaseObj = true;
		players = new LinkedList<Player>();
		players.add(_player);
		path = new IntArray();
	}

	@Override
	public boolean update() {
		if(this.HP <= 0)
			return false;
		if(this.getPos().dst(players.getFirst().getPos()) < 256)
			ChaseObj = true;
		else
			ChaseObj = false;
		
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
			for(int i = 0; i < path.size; i+=2) {
				float x = path.get(i);
				float y = path.get(i+1);
				this.setPos(new Vector2(x * 64, y * 64));
			}
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
		if (this.boundingBox.x < pos.x)
			this.MoveRight();
		else if (this.boundingBox.x > pos.x)
			this.MoveLeft();
		else if (this.boundingBox.y < pos.y)
			this.MoveUp();
		else if (this.boundingBox.y > pos.y)
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

package it.unical.igpe.logic;

import java.awt.Rectangle;
import java.util.LinkedList;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.IntArray;

import it.unical.igpe.game.World;
import it.unical.igpe.tools.GameConfig;
import it.unical.igpe.tools.TileType;

public class Enemy extends AbstractGameObject {
	public boolean chaseObj;
	public boolean shootingObj;
	public Bullet singleBullet;
	private float shootDelay;
	private TileType nextTile;
	private Rectangle box;
	private Vector2 dir;
	private LinkedList<Player> players;
	private IntArray path;

	public Enemy(Vector2 _pos, Player _player) {
		boundingBox = new Rectangle((int) _pos.x, (int) _pos.y, 64, 64);
		ID = "enemy";
		alive = true;
		HP = 100f;
		speed = 4;
		chaseObj = true;
		players = new LinkedList<Player>();
		players.add(_player);
		path = new IntArray();
		dir = new Vector2();
	}

	public boolean update(float delta) {
		// TODO: get closer player from the list
		float startx = this.getBoundingBox().x;
		float starty = this.getBoundingBox().y;
		float targetx = players.getFirst().getBoundingBox().x;
		float targety = players.getFirst().getBoundingBox().y;
		dir = new Vector2(targetx - startx, targety - starty);
		dir.rotate90(-1);
		angle = dir.angle();
		if (this.HP <= 0)
			return false;
		if (this.getPos().dst(players.getFirst().getPos()) < GameConfig.ENEMY_RADIUS
				&& this.getPos().dst(players.getFirst().getPos()) > GameConfig.ENEMY_SHOOT_RADIUS) {
			chaseObj = true;
			shootingObj = false;
		} else if (this.getPos().dst(players.getFirst().getPos()) <= GameConfig.ENEMY_SHOOT_RADIUS && shootDelay > 1) {
			chaseObj = false;
			this.shoot(new Vector2(startx + 32, starty + 32));
			shootDelay = 0;
			shootingObj = true;
		} else if (path.size == 0) {
			chaseObj = false;
			shootingObj = false;
		}

		if (chaseObj) {
			float y = path.pop();
			float x = path.pop();
			this.followPath(new Vector2(x * 64, y * 64));
		}
		
		shootDelay += delta;
		return true;
	}

	private void shoot(Vector2 v) {
		singleBullet = new Bullet(v, (float) Math.toRadians(angle) + 90, "enemy");
	}

	public void hit(float dmg) {
		this.HP -= dmg;
		if (HP <= 0)
			alive = false;
	}

	public void setPath(IntArray intArray) {
		path = intArray;
	}

	public IntArray getPath() {
		return path;
	}
	
	public void followPath(Vector2 pos) {

		if (this.boundingBox.y > pos.y) {
			box = new Rectangle(this.getBoundingBox().x, this.getBoundingBox().y - GameConfig.MOVESPEED,
					this.getBoundingBox().width, this.getBoundingBox().height);
			nextTile = World.getNextTile(box);
			if (nextTile != TileType.WALL)
				this.MoveUp();
		}
		if (this.boundingBox.x > pos.x) {
			box = new Rectangle(this.getBoundingBox().x - GameConfig.MOVESPEED, this.getBoundingBox().y,
					this.getBoundingBox().width, this.getBoundingBox().height);
			nextTile = World.getNextTile(box);
			if (nextTile != TileType.WALL)
				this.MoveLeft();
		}
		if (this.boundingBox.y < pos.y) {
			box = new Rectangle(this.getBoundingBox().x, this.getBoundingBox().y + GameConfig.MOVESPEED,
					this.getBoundingBox().width, this.getBoundingBox().height);
			nextTile = World.getNextTile(box);
			if (nextTile != TileType.WALL)
				this.MoveDown();
		}
		if (this.boundingBox.x < pos.x) {
			box = new Rectangle(this.getBoundingBox().x + GameConfig.MOVESPEED, this.getBoundingBox().y,
					this.getBoundingBox().width, this.getBoundingBox().height);
			nextTile = World.getNextTile(box);
			if (nextTile != TileType.WALL)
				this.MoveRight();
		}
	}
}

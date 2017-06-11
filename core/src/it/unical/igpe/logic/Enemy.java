package it.unical.igpe.logic;

import java.awt.Rectangle;
import java.util.LinkedList;
import java.util.Random;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.IntArray;

import it.unical.igpe.game.World;
import it.unical.igpe.tools.GameConfig;
import it.unical.igpe.tools.TileType;
import it.unical.igpe.tools.Updatable;

public class Enemy extends AbstractGameObject implements Updatable{
	public boolean chaseObj;
	public boolean canShoot;
	public boolean canMove;
	public int startx;
	public int starty;
	public int targetx;
	public int targety;
	private float shootDelay;
	private float followDelay;
	private float followTimer;
	private float lastMovement;
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
		speed = 1.5f;
		chaseObj = true;
		canShoot = false;
		players = new LinkedList<Player>();
		players.add(_player);
		path = new IntArray();
		dir = new Vector2();
		Random random = new Random();
		followTimer = random.nextFloat() + 6f;
		startx = this.getBoundingBox().x + 32;
		starty = this.getBoundingBox().y + 32;
		targetx = players.getFirst().getBoundingBox().x + 32;
		targety = players.getFirst().getBoundingBox().y + 32;
		lastMovement = 0;
		canMove = true;
	}

	public void update(float delta) {
		if (this.HP <= 0)
			this.alive = false;
		canMove = true;
		startx = this.getBoundingBox().x + 32;
		starty = this.getBoundingBox().y + 32;
		if (this.getPos().dst(players.getFirst().getPos()) < GameConfig.ENEMY_RADIUS
				&& this.getPos().dst(players.getFirst().getPos()) > GameConfig.ENEMY_SHOOT_RADIUS) {
			targetx = players.getFirst().getBoundingBox().x + 32;
			targety = players.getFirst().getBoundingBox().y + 32;
			followDelay = 0;
		} else if (followDelay > followTimer) {
			Random r = new Random();
			targetx = startx + (r.nextInt(16) - 8) * 32;
			targety = starty + (r.nextInt(16) - 8) * 32;
			followDelay = 0;
		} else if (this.getPos().dst(players.getFirst().getPos()) < GameConfig.ENEMY_SHOOT_RADIUS) {
			canMove = false;
			followDelay = 0;
		}

		dir = new Vector2(targetx - startx, targety - starty);
		dir.rotate90(-1);
		angle = dir.angle();

		if (this.getPos().dst(players.getFirst().getPos()) <= GameConfig.ENEMY_SHOOT_RADIUS) {
			targetx = players.getFirst().getBoundingBox().x + 32;
			targety = players.getFirst().getBoundingBox().y + 32;
			followDelay = 0;
			if (shootDelay > 1) {
				shootDelay = 0;
				canShoot = true;
			}
		}

		if (path.size != 0 && lastMovement > 0.3f && canMove) {
			float y = path.pop();
			float x = path.pop();
			this.followPath(new Vector2(x * 64, y * 64));
		}

		shootDelay += delta;
		followDelay += delta;
		lastMovement += delta;
	}

	public Bullet fire() {
		this.canShoot = false;
		return new Bullet(new Vector2(this.getBoundingBox().x + 32, this.getBoundingBox().y + 32),
				(float) Math.toRadians(angle + 90f), "enemy", 15);
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

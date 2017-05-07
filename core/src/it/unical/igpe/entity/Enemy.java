package it.unical.igpe.entity;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import it.unical.igpe.GameConfig;

public class Enemy extends AbstractGameObject {
	public Enemy(Vector2 _pos) {
		pos = new Vector2(_pos);
		boundingBox = new Rectangle(pos.x, pos.y, 64, 64);
		ID = "enemy";
		alive = true;
		HP = 100f;
		speed = GameConfig.MOVESPEED / 1.5f;
	}
	
	public void hit(float dmg) {
		this.HP -= dmg;
	}
	
	public void findPathToTarget(Vector2 target) {
		// Simple AI, follow you into the level, TODO 8 directions
		if(this.pos.x < target.x)
			this.MoveRight();
		if(this.pos.x > target.x)
			this.MoveLeft();
		if(this.pos.y < target.y)
			this.MoveUp();
		if(this.pos.y > target.y)
			this.MoveDown();
	}
}

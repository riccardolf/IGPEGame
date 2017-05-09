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
		speed = GameConfig.MOVESPEED * 0.3f;
	}
	
	public void hit(float dmg) {
		this.HP -= dmg;
	}
	
	public void findPathToTarget(Vector2 target) {
		// Simple AI, follow you into the level
		if(this.pos.x < target.x && this.pos.y < target.y)
			this.MoveUpRight();
		else if(this.pos.x < target.x && this.pos.y > target.y)
			this.MoveDownRight();
		else if(this.pos.x > target.x && this.pos.y < target.y)
			this.MoveUpLeft();
		else if(this.pos.x > target.x && this.pos.y > target.y)
			this.MoveDownLeft();
		else if(this.pos.x < target.x)
			this.MoveRight();
		else if(this.pos.x > target.x)
			this.MoveLeft();
		else if(this.pos.y < target.y)
			this.MoveUp();
		else if(this.pos.y > target.y)
			this.MoveDown();
	}
}

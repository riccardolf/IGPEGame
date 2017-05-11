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
		speed = GameConfig.MOVESPEED * 0.5f;
	}
	
	public void hit(float dmg) {
		this.HP -= dmg;
	}
	
	public void findPathToTarget(Vector2 target) {
		// Simple AI, follow you into the level
		if(this.pos.x < target.x + 100 && this.pos.y + 100 < target.y)
			this.MoveUpRight();
		else if(this.pos.x < target.x + 100 && this.pos.y - 100 > target.y)
			this.MoveDownRight();
		else if(this.pos.x > target.x - 100 && this.pos.y + 100 < target.y)
			this.MoveUpLeft();
		else if(this.pos.x > target.x - 100 && this.pos.y - 100 > target.y)
			this.MoveDownLeft();
		else if(this.pos.x < target.x + 100)
			this.MoveRight();
		else if(this.pos.x > target.x - 100)
			this.MoveLeft();
		else if(this.pos.y < target.y + 100)
			this.MoveUp();
		else if(this.pos.y > target.y - 100)
			this.MoveDown();
	}
}

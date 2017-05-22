package it.unical.igpe.logic;

import java.awt.Rectangle;
import com.badlogic.gdx.math.Vector2;

import it.unical.igpe.tools.GameConfig;

public class Enemy extends AbstractGameObject {
	public Enemy(Vector2 _pos) {
		boundingBox = new Rectangle((int)_pos.x, (int)_pos.y, 64, 64);
		ID = "enemy";
		alive = true;
		HP = 100f;
		speed = GameConfig.MOVESPEED * 0.5f;
	}
	
	public void hit(float dmg) {
		this.HP -= dmg;
		if(HP <= 0)
			alive = false;
	}
	
	public void findPathToTarget(Vector2 target) {
		if(this.boundingBox.x < target.x + 100)
			this.MoveRight();
		else if(this.boundingBox.x > target.x - 100)
			this.MoveLeft();
		else if(this.boundingBox.y < target.y + 100)
			this.MoveUp();
		else if(this.boundingBox.y > target.y - 100)
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

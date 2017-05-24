package it.unical.igpe.tools;

//import java.awt.Rectangle;
//import java.util.LinkedList;
//
//import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.Input;
//
//import it.unical.igpe.logic.Player;

public class InputHandler {
//	Player player;
//	LinkedList<Tile> tiles;
//	public void update() {
//		if (Gdx.input.isKeyPressed(Input.Keys.W) && Gdx.input.isKeyPressed(Input.Keys.A)) {
//			if (!player.getReloading())
//				state = PlayerState.RUNNING;
//			box = new Rectangle(player.getBoundingBox().x - GameConfig.MOVESPEED,
//					player.getBoundingBox().y + GameConfig.MOVESPEED, player.getBoundingBox().width,
//					player.getBoundingBox().height);
//			if (!checkCollisionWall(box))
//				player.MoveUpLeft();
//		} else if (Gdx.input.isKeyPressed(Input.Keys.W) && Gdx.input.isKeyPressed(Input.Keys.D)) {
//			if (!player.getReloading())
//				state = PlayerState.RUNNING;
//			box = new Rectangle(player.getBoundingBox().x + GameConfig.MOVESPEED,
//					player.getBoundingBox().y + GameConfig.MOVESPEED, player.getBoundingBox().width,
//					player.getBoundingBox().height);
//			if (!checkCollisionWall(box))
//				player.MoveUpRight();
//		} else if (Gdx.input.isKeyPressed(Input.Keys.S) && Gdx.input.isKeyPressed(Input.Keys.A)) {
//			if (!player.getReloading())
//				state = PlayerState.RUNNING;
//			box = new Rectangle(player.getBoundingBox().x - GameConfig.MOVESPEED,
//					player.getBoundingBox().y - GameConfig.MOVESPEED, player.getBoundingBox().width,
//					player.getBoundingBox().height);
//			if (!checkCollisionWall(box))
//				player.MoveDownLeft();
//		} else if (Gdx.input.isKeyPressed(Input.Keys.S) && Gdx.input.isKeyPressed(Input.Keys.D)) {
//			if (!player.getReloading())
//				state = PlayerState.RUNNING;
//			box = new Rectangle(player.getBoundingBox().x + GameConfig.MOVESPEED,
//					player.getBoundingBox().y - GameConfig.MOVESPEED, player.getBoundingBox().width,
//					player.getBoundingBox().height);
//			if (!checkCollisionWall(box))
//				player.MoveDownRight();
//		} else if (Gdx.input.isKeyPressed(Input.Keys.W)) {
//			if (!player.getReloading())
//				state = PlayerState.RUNNING;
//			box = new Rectangle(player.getBoundingBox().x, player.getBoundingBox().y + GameConfig.MOVESPEED,
//					player.getBoundingBox().width, player.getBoundingBox().height);
//			if (!checkCollisionWall(box))
//				player.MoveUp();
//		} else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
//			if (!player.getReloading())
//				state = PlayerState.RUNNING;
//			box = new Rectangle(player.getBoundingBox().x - GameConfig.MOVESPEED, player.getBoundingBox().y,
//					player.getBoundingBox().width, player.getBoundingBox().height);
//			if (!checkCollisionWall(box))
//				player.MoveLeft();
//		} else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
//			if (!player.getReloading())
//				state = PlayerState.RUNNING;
//			box = new Rectangle(player.getBoundingBox().x, player.getBoundingBox().y - GameConfig.MOVESPEED,
//					player.getBoundingBox().width, player.getBoundingBox().height);
//			if (!checkCollisionWall(box))
//				player.MoveDown();
//		} else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
//			if (!player.getReloading())
//				state = PlayerState.RUNNING;
//			box = new Rectangle(player.getBoundingBox().x + GameConfig.MOVESPEED, player.getBoundingBox().y,
//					player.getBoundingBox().width, player.getBoundingBox().height);
//			if (!checkCollisionWall(box))
//				player.MoveRight();
//		}
//
//		// Fire and Reloading action of the player
//		if (Gdx.input.justTouched()) {
//			float px = (float) (player.getPos().x + 32);
//			float py = (float) (player.getPos().y + 32);
//			
//			px -= Math.sin(Math.toRadians((int)rotation));
//			py += Math.cos(Math.toRadians((int)rotation));
//			
//			player.fire(px, py ,rotation + 90f);
//			player.checkAmmo();
//		}
//		if (Gdx.input.isKeyPressed(Input.Keys.R) && !player.getReloading()) {
//			player.reload();
//		}
//		else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) {
//			player.setActWeapon("pistol");
//		}
//		else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)) {
//			player.setActWeapon("shotgun");
//		}
//		else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_3)) {
//			player.setActWeapon("rifle");
//		}
//	}
}

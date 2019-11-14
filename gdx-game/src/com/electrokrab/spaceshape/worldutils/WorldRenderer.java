package com.electrokrab.spaceshape.worldutils;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.utils.*;
import com.electrokrab.spaceshape.*;
import com.electrokrab.spaceshape.objects.*;
import com.electrokrab.spaceshape.resourses.*;
import com.electrokrab.spaceshape.settings.*;
import com.electrokrab.spaceshape.screens.*;

public class WorldRenderer {
    World world;
    OrthographicCamera cam;
    SpriteBatch batch;
    float stateTime, alpha, alphaval;
	int backGroundY=0;
	int hitTimer;
	long timeAlpha,missedTime;
    public WorldRenderer (SpriteBatch batch, World world) {
        this.world = world;
		
        cam = new OrthographicCamera();
        cam.setToOrtho(false, SpaceShape.SCREEN_WIDTH, SpaceShape.SCREEN_HEIGHT);
        this.batch = batch;
        stateTime = 0f;
		alpha= 1f;
		hitTimer=5;
		timeAlpha= 0;
		alphaval= 1f;
		missedTime=0;
    }

    public void render () {
        cam.update();
        batch.setProjectionMatrix(cam.combined);
        renderBackground();
        renderObjects();
    }

    public void renderBackground () {
		if(world.state==World.WORLD_STATE_RUNNING){
		backGroundY-=6f;
		}
        batch.enableBlending();
        batch.begin();
		batch.setColor(1,1,1,1);
        batch.draw(Assets.starSkyBGTex, 0,0,SpaceShape.SCREEN_WIDTH, backGroundY, SpaceShape.SCREEN_WIDTH, SpaceShape.SCREEN_HEIGHT*2);
        batch.end();
    }

    public void renderObjects () {
		
		batch.enableBlending();
		batch.begin();
		batch.setColor(1,1, 1, alpha);
		renderShip(batch, alpha);
		batch.end();
		
		batch.enableBlending();
		batch.begin();
		batch.setColor(1,1,1,1);
        renderMainShipFire();
        renderEnemies();
		renderHealthBar();
		renderMissed();
		renderMissedBar();
        batch.end();
    }

    //render the ship depending on ship state
    public void renderShip (SpriteBatch batch, float apha) {
		
		TextureRegion keyFrame;
		stateTime += Gdx.graphics.getDeltaTime();
		keyFrame = Assets.shipAnim.getKeyFrame(stateTime, true);
		
		if(world.ship.state==MainShip.SHIP_HIT){
			if(TimeUtils.nanoTime()-timeAlpha> 1000000000/2){
				alpha-=alphaval;
				if(Settings.getViration()){
				Gdx.input.vibrate(100);
				}
				alphaval*=-1f;
				if(hitTimer<=0)
				{
				world.ship.state=MainShip.SHIP_STATE_ALIVE;
					}
				if(hitTimer<=2){
					world.hitB=false;
				}
			hitTimer-=1;
			timeAlpha= TimeUtils.nanoTime();			
			}
		}
		if(world.ship.state==MainShip.SHIP_STATE_ALIVE){
			alpha=1f;
			alphaval= 1f;
			hitTimer=5;
		}
		batch.draw(keyFrame, world.ship.xPos, world.ship.yPos, world.ship.SHIP_WIDTH, world.ship.SHIP_HEIGHT);
			
	}
	private void renderMissed(){
		if(world.ship.state==MainShip.SHIP_MISSED_ENEMY){
			batch.draw(Assets.missed, 40, 450, 400, 250);
		
			if(TimeUtils.nanoTime()-world.ship.missedTime > 1000000000){
						
				world.ship.state=MainShip.SHIP_STATE_ALIVE;
				missedTime= TimeUtils.nanoTime();
			}
			
		}
		
		
	}
	
	private void renderMissedBar(){
		if(world.state!=World.WORLD_STATE_GAME_OVER){
		switch(MainShip.missedCount){
			case 0:
				batch.draw(Assets.miss1, 360,700,100,40);
				break;
			case 1:
				batch.draw(Assets.miss2, 360,700,100,40);
				break;
			case 2:
				batch.draw(Assets.miss3, 360,700,100,40);
				break;
			case 3:
				batch.draw(Assets.miss4, 360,700,100,40);
				break;
			}
		}
	}
	private void renderHealthBar(){
		if(world.state!=World.WORLD_STATE_GAME_OVER){
		switch(MainShip.shipHP){
			case 1:
			batch.draw(Assets.health1, 360,750,100,40);
				break;
			case 2:
			batch.draw(Assets.health2, 360,750,100,40);
				break;
			case 3:
			batch.draw(Assets.health3, 360,750,100,40);
				break;
			case 4:
			batch.draw(Assets.health4, 360,750,100,40);
				break;
			case 5:
			batch.draw(Assets.health5, 360,750,100,40);
				break;
			}
		}
	}

    private void renderMainShipFire(){
		
        for(MainLaser laser : world.mainShipLasers){
			if(laser.bulletState==MainLaser.BULLET_CIRCLE){
            batch.draw(Assets.circBullet, laser.xPos, laser.yPos, laser.BULLET_WIDTH, laser.BULLET_HEIGHT);
			}else{
				if(laser.bulletState==MainLaser.BULLET_SQUARE){
					batch.draw(Assets.sqBullet, laser.xPos, laser.yPos, laser.BULLET_WIDTH, laser.BULLET_HEIGHT);
				}else{
					if(laser.bulletState==MainLaser.BULLET_TRIAGLE){
						batch.draw(Assets.trBullet, laser.xPos, laser.yPos,laser.BULLET_WIDTH, laser.BULLET_HEIGHT);
					}
				}
			}
        }
    }

    private void renderEnemies () {
		
		for(Enemy enemy: world.enemies){
			
			switch(enemy.skinState){
				case Enemy.ENEMY_CIRCLE:
				
					if(enemy.state==Enemy.ENEMY_IS_DEAD){		
						batch.draw(Assets.circEnemDead, enemy.xPos, enemy.yPos, enemy.ENEMY_WIDTH, enemy.ENEMY_HEIGHT);
						}else{
					batch.draw(Assets.circEnem, enemy.xPos, enemy.yPos, enemy.ENEMY_WIDTH, enemy.ENEMY_HEIGHT);
					}
					break;
				case Enemy.ENEMY_TRIANGLE:
					if(enemy.state== Enemy.ENEMY_IS_DEAD){
					batch.draw(Assets.trEnemDead, enemy.xPos, enemy.yPos, enemy.ENEMY_WIDTH, enemy.ENEMY_HEIGHT);
					} else{
					batch.draw(Assets.trEnem, enemy.xPos, enemy.yPos, enemy.ENEMY_WIDTH, enemy.ENEMY_HEIGHT);
					}
					break;
				case Enemy.ENEMY_SQUARE:
					if(enemy.state== Enemy.ENEMY_IS_DEAD){
					batch.draw(Assets.sqEnemDead, enemy.xPos, enemy.yPos, enemy.ENEMY_WIDTH, enemy.ENEMY_HEIGHT);
					}else	{
					batch.draw(Assets.sqEnem, enemy.xPos, enemy.yPos, enemy.ENEMY_WIDTH, enemy.ENEMY_HEIGHT);
				}
					break;
			}
        }
    }
}

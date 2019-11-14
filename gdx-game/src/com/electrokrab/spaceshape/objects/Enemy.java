package com.electrokrab.spaceshape.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Random;
import com.badlogic.gdx.math.*;

public class Enemy extends DynamicGameObject{

    public static final int ENEMY_IS_ALIVE = 1;
    public static final int ENEMY_IS_DEAD = 2;

    public static final float ENEMY_WIDTH = 90;
    public static final float ENEMY_HEIGHT = 90;
	public static final float ENEMY_VELOCITY= 100;
	public static long lastEnemyTime;
    public static boolean newEnem;
	public float xPos;
    public float yPos;
    public int state;
	public long enemyDeathTime;
    float stateTime= 0;
	public int skinState;
	public static final int ENEMY_CIRCLE = 1;
	public static final int ENEMY_SQUARE= 2;
	public static final int ENEMY_TRIANGLE= 3;
	public static float enemySpeed;
	float enemyAccel;
	
    public Enemy(float x, float y){
        super(x, y, ENEMY_WIDTH, ENEMY_HEIGHT);
		xPos= x;
		yPos= y;
        state = ENEMY_IS_ALIVE;
		skinEnem();
		enemyDeathTime=0;
		enemyAccel= 0;
		newEnem=false;
    }

    public void update(float deltaTime){
		yPos -= (ENEMY_VELOCITY+enemyAccel)* Gdx.graphics.getDeltaTime();
		this.bounds = new Rectangle(xPos, yPos, ENEMY_WIDTH, ENEMY_HEIGHT);
		stateTime += deltaTime;
		
    }
	
	public void updateSpawnTime(){
		lastEnemyTime=TimeUtils.nanoTime();
	}

	public void updateEnemyVel(){
		enemySpeed+=5;
		enemyAccel= enemySpeed;
	}

    public void skinEnem(){
		int rSkin= MathUtils.random(1,3);
		switch(rSkin){
			case 1:
			skinState= ENEMY_CIRCLE;
				break;
			case 2:
			skinState= ENEMY_SQUARE;
				break;
			case 3:
			skinState= ENEMY_TRIANGLE;	
				break;
		}
	}
    public void hit(){
			
		state = ENEMY_IS_DEAD;
		enemyAccel= -ENEMY_VELOCITY;
		enemyDeathTime= TimeUtils.nanoTime();
		
    }
}

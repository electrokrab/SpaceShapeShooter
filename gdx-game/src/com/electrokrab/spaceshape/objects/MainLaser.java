package com.electrokrab.spaceshape.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.TimeUtils;

public class MainLaser extends DynamicGameObject{

    public static final int BULLET_NOT_HIT = 1;
    public static final int BULLET_HIT = 2;
    public static final int BULLET_WIDTH = 90;
    public static final int BULLET_HEIGHT = 90;
    public static final int BULLET_VELOCITY = 400;

    public float xPos;
    public float yPos;
    public static float lastFireTime;
    public int state;
	public static int bulletNum;
	public int bulletState;
	public static final int BULLET_CIRCLE=1;
	public static final int BULLET_SQUARE= 2;;
	public static final int BULLET_TRIAGLE= 3;
	
    public MainLaser(float x, float y){
        super(x, y, BULLET_WIDTH, BULLET_HEIGHT);
        xPos = x;
        yPos = y;
        state = BULLET_NOT_HIT;
		skinBullet();
        lastFireTime = TimeUtils.nanoTime();
    }

    //update laser position
    public void update(){
        yPos +=BULLET_VELOCITY * Gdx.graphics.getDeltaTime();
        this.bounds = new Rectangle(xPos, yPos, BULLET_WIDTH, BULLET_HEIGHT);
    }
	public void skinBullet(){
		switch( bulletNum){
			case 1: bulletState= BULLET_CIRCLE;
				break;
			case 2: bulletState= BULLET_SQUARE;
				break;
			case 3: bulletState= BULLET_TRIAGLE;
				break;
		}
    }
	
    public void hitEnemy(){
		
        state = BULLET_HIT;
		
    }
}

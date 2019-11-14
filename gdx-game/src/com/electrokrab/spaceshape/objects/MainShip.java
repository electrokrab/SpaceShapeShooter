package com.electrokrab.spaceshape.objects;

import com.badlogic.gdx.math.*;
import com.badlogic.gdx.utils.*;

public class MainShip extends DynamicGameObject {
    public static final int SHIP_STATE_ALIVE = 1;
    public static final int SHIP_DEAD = 2;
	public static final int SHIP_HIT= 3;
	public static final int SHIP_MISSED_ENEMY= 4;
    public static final float SHIP_WIDTH = 85;
    public static final float SHIP_HEIGHT = 115;
	public static int shipHP,missedCount;
    public static float xPos;
    public static float yPos;
	public int hitTimer= 5;
    public static int state;
	public static long missedTime;
    float stateTime;

    public MainShip (float x, float y) {
        super(x, y, SHIP_WIDTH, SHIP_HEIGHT);
        stateTime = 0;
		yPos= 130;
		xPos=240-SHIP_WIDTH/2;
		shipHP= 5;
		missedTime=0;
		missedCount= 0;
        state = SHIP_STATE_ALIVE;
    }

    //update ships position
    public void update (float deltaTime, float x) {
        this.bounds = new Rectangle(xPos, yPos, SHIP_WIDTH, SHIP_HEIGHT);
        stateTime += deltaTime;
		}
		
    //updates ship status when geting hit
    public void gotHit () {
		shipHP--;
		state= SHIP_HIT;
		if(shipHP==0){
		state = SHIP_DEAD;
		}
		stateTime= 0;
	}

	public void missedEnemy() {
		
		missedCount++;
		state= SHIP_MISSED_ENEMY;
		if(missedCount ==4){
			state = SHIP_DEAD;
		}
		stateTime= 0;
	}
	
}

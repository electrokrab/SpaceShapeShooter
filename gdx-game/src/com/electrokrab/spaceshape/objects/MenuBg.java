package com.electrokrab.spaceshape.objects;

import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.utils.*;
import com.electrokrab.spaceshape.*;
import com.electrokrab.spaceshape.resourses.*;

public class MenuBg
{
    OrthographicCamera cam;
    SpriteBatch batch;
	public Rectangle enemy;
    float stateTime, alpha, alphaval;
	float xPos,yPos;
	boolean state,touch;
	int gen;
	long hitTimer;
	long timeAlpha, enemyDeadTime;
    public MenuBg(SpriteBatch batch) {
       
        cam = new OrthographicCamera();
        cam.setToOrtho(false, SpaceShape.SCREEN_WIDTH, SpaceShape.SCREEN_HEIGHT);
        this.batch = batch;
		
		state= true;
        stateTime = 0f;
		alpha= 0.1f;
		enemyDeadTime=0;
		timeAlpha= 0;
		alphaval= 0.1f;
		addEnemy();
	}
	public void render () {
		
        cam.update();
        batch.setProjectionMatrix(cam.combined);
        batch.enableBlending();
		batch.begin();
		batch.setColor(1,1,1,alpha);
		renderEnemies(gen,state);
		batch.end();
		alphaMod();
		
    }
	
	public void alphaMod(){
		if(state){
			if(TimeUtils.nanoTime()-timeAlpha> 1000000000/2){
				alpha+=alphaval;
				if(alpha>=0.9f)
				{
					alphaval*=(-1f);
				} else 
				if(alpha<=0.1f){
					alphaval*=(-1f);
					addEnemy();
				}
			
				timeAlpha= TimeUtils.nanoTime();			
			}
		}else{
			
			if(TimeUtils.nanoTime()-enemyDeadTime>2000000000){
				
				addEnemy();
				
			}
		}
	}
	public void toched(){
		if(state){
			state=false;
			alpha=1f;
			enemyDeadTime= TimeUtils.nanoTime();
		}
	}
	public void addEnemy(){
	state=true;
	alpha= 0.1f;
	alphaval= 0.1f;
	int xNum= MathUtils.random(1,2);
	gen= MathUtils.random(1,3);
	yPos= MathUtils.random(20,690);
	switch(xNum){
		case 1: xPos= 40;
		break;
		case 2: xPos= 350;
		break;
	}
	enemy= new Rectangle(xPos,yPos,90,90);
		
	}
	
	private void renderEnemies (int gen, boolean state) {

			switch(gen){
				case 1:
					if(state==false){		
						batch.draw(Assets.circEnemDead, xPos, yPos, 90, 90);
					}else{
						batch.draw(Assets.circEnem, xPos, yPos, 90, 90);
					}
					break;
				case 2:
					if(state==false){
						batch.draw(Assets.trEnemDead, xPos, yPos, 90, 90);
					} else{
						batch.draw(Assets.trEnem, xPos, yPos, 90, 90);
					}
					break;
				case 3:
					if(state==false){
						batch.draw(Assets.sqEnemDead, xPos, yPos, 90, 90);
					}else	{
						batch.draw(Assets.sqEnem, xPos,yPos, 90, 90);
					}
					break;
			}
        }
    
}

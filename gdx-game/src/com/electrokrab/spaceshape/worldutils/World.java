package com.electrokrab.spaceshape.worldutils;

import com.badlogic.gdx.math.*;
import com.badlogic.gdx.utils.*;
import com.electrokrab.spaceshape.*;
import com.electrokrab.spaceshape.objects.*;
import java.util.*;
import com.badlogic.gdx.*;
import com.electrokrab.spaceshape.settings.*;
import com.electrokrab.spaceshape.resourses.*;

public class World {
    public interface WorldListener {
        public void shoot();
        public void hit();
		public void right();
		public void left();
		public void shiphit();
    }

    public static final int WORLD_STATE_RUNNING = 0;
    public static final int WORLD_STATE_GAME_OVER = 1;
    public final MainShip ship;
    public final Array<Enemy> enemies;
	public Iterator<Enemy> enemyIter;
	public boolean addNew;
    public final ArrayList<MainLaser> mainShipLasers;
    public final WorldListener listener;
    public final Random rand;
	public static boolean hitB;
	public int enemyX;
	public float enemyY,enemSpeed;
    public int score;
    public int state;
	int spawnTime;
	
    public World(WorldListener listener) {
        this.ship = new MainShip(MainShip.xPos, MainShip.yPos);
        this.enemies= new Array<Enemy>();
		addNew= false;
		hitB=false;
		this.mainShipLasers = new ArrayList<MainLaser>();
		spawnTime= 2;
        this.listener = listener;
        rand = new Random();
		Enemy.enemySpeed=0;
		MainShip.shipHP= 5;
		MainShip.missedCount=0;
        this.score = 0;
        this.state = WORLD_STATE_RUNNING;
		}
		
    //Update each object
    public void update(float deltaTime) {
		updateShip(deltaTime);
		updateEnemies(deltaTime);
        updateMainShipFire(deltaTime);
        if (ship.state!=MainShip.SHIP_DEAD){
		checkCollisions();
		}
        checkGameOver();
    }

    //Updates ship position if not touched
    private void updateShip(float deltaTime){
        if (ship.state != MainShip.SHIP_DEAD) {
            ship.update(deltaTime, ship.xPos);
        }
    }

    //update ship position if touched
    public void updateShip(float deltaTime, float x) {
        if (ship.state != MainShip.SHIP_DEAD) {
			if(ship.xPos-ship.SHIP_WIDTH/2 < SpaceShape.SCREEN_WIDTH/2+150)
			{
			listener.right();
            ship.update(deltaTime, x);
			}
			if(ship.xPos>SpaceShape.SCREEN_WIDTH/2-150){
			listener.left();
			ship.update(deltaTime, x);
			}
			
        }
		
    }

    //Shoot laser after a second sense last laser
    public void addMainShipFire(){
        //fire laser after cool down time if ship is moving
        if(ship.state != MainShip.SHIP_DEAD){
            if(TimeUtils.nanoTime() - MainLaser.lastFireTime > 1000000000 / 2.5){
                listener.shoot();
                mainShipLasers.add(new MainLaser(ship.xPos, 200));
            }
        }
    }

    //update laser position
    public void updateMainShipFire(float deltaTime){
        int len = mainShipLasers.size();
        for (int i = 0; i < len; i++) {
            MainLaser laser = mainShipLasers.get(i);
            laser.update();
            if(laser.state == MainLaser.BULLET_HIT || laser.yPos >= 800){
                mainShipLasers.remove(laser);
                len= mainShipLasers.size();
            }
        }
    }

	private void addEnemies(){
		int numPos= MathUtils.random(1,3);
			if(addNew){
				switch(numPos){
					case 1: enemyX= 40;
					break;
					case 2: enemyX= 190;
					break;
					case 3: enemyX= 340;
					break;
			}
			enemyY=800;
				Enemy enemy= new Enemy(enemyX, enemyY);
				enemy.skinEnem();
				enemies.add(enemy);
				enemy.updateSpawnTime();
				enemy.updateEnemyVel();
				addNew=false;
			}
	}
	private void createEnemy(){
		int random=MathUtils.random(-1,1); 
		
		if(enemies.size==0){
			if(TimeUtils.nanoTime()-Enemy.lastEnemyTime>1.5*1000000000){ 	
				addNew=true;
			}
		}else
		if(enemies.size>=1){
			if((enemyY+(random*50))<550&& addNew==false){
				addNew=true;
			}
		}
	}
    //update enemy position
    public void updateEnemies(float deltaTime) {
		
		if( ship.state!= MainShip.SHIP_DEAD){
		addEnemies();
		createEnemy();
			enemyIter = enemies.iterator();
				while(enemyIter.hasNext()) {
				Enemy enemy = enemyIter.next();
				enemy.update(deltaTime);
				enemyY= enemy.yPos;
				if(enemy.state == Enemy.ENEMY_IS_DEAD){
					
					if(TimeUtils.nanoTime()-enemy.enemyDeathTime> 0.3*1000000000){
						enemyIter.remove();
					}
					
				}
				if(enemy.yPos <=100){
					enemyIter.remove();
					if(ship.state== MainShip.SHIP_STATE_ALIVE){
					ship.missedEnemy();
					if(Settings.getViration()){
						Gdx.input.vibrate(100);
					}
					enemSpeed= Enemy.enemySpeed;
					ship.missedTime= TimeUtils.nanoTime();
					}
				}
				
					if(ship.state==MainShip.SHIP_MISSED_ENEMY){
					  
						if(enemyY<800){
						enemies.removeValue(enemy ,true);
						enemy.enemySpeed= enemSpeed;
						}
					AudioManager.playSound(AudioManager.missed,AudioManager.sEnable,AudioManager.soundVolume);
				} else
					if(ship.state==MainShip.SHIP_HIT&& hitB){
						if(enemyY<800){
							enemies.removeValue(enemy ,true);
							enemy.enemySpeed=enemSpeed;
						}
					}
				
			}
		}
		
	}
	
	
    
    //Check main ship collisions with each object
    private void checkCollisions() {
		checkEnemyCollisions();
		checkMainLaserCollisions();
    }
	
    //check if ship laser hits any enemies
    private void checkMainLaserCollisions(){
        //Check if laser hits enemy
        int lasLen = mainShipLasers.size();

        for(int i = 0; i < lasLen; i++){
            MainLaser laser = mainShipLasers.get(i);
			enemyIter = enemies.iterator();
			while (enemyIter.hasNext()){
				Enemy enemy = enemyIter.next();
                if(laser.bounds.overlaps(enemy.bounds)&& enemy.state== Enemy.ENEMY_IS_ALIVE){
					mainShipLasers.remove(laser);
					lasLen= mainShipLasers.size();
                    if(laser.bulletState==enemy.skinState){
					
                    listener.hit();
                    laser.hitEnemy();
                    enemy.hit();
					score += 1;
					
					}
                }
            }
        }
    }

    //if collide with enemy ship gets hit
    private void checkEnemyCollisions() {
		if(ship.state== MainShip.SHIP_STATE_ALIVE){
		enemyIter = enemies.iterator();
        while(enemyIter.hasNext()) {
           Enemy enemy= enemyIter.next();
            if (enemy.bounds.overlaps(ship.bounds)&& enemy.state== Enemy.ENEMY_IS_ALIVE) {
				enemyIter.remove();
				hitB= true;
				listener.shiphit();
				ship.gotHit();
				enemSpeed= Enemy.enemySpeed;
                //TODO: show explosion animation
				}
            }
        }
    }

    //Checks if game is over
    private void checkGameOver() {
        if (ship.state == MainShip.SHIP_DEAD) {
            state = WORLD_STATE_GAME_OVER;
        }
    }
}

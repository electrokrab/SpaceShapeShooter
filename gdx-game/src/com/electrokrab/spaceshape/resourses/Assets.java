package com.electrokrab.spaceshape.resourses;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Texture.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.assets.*;
import com.badlogic.gdx.assets.loaders.*;
import java.security.spec.*;

public class Assets {
	public static BitmapFont font;
	public static Texture starSkyBGTex;
	public static Texture spaceshipTex;
	public static Texture gameOver, sssTitle;
	public static Texture electrokrabLogo;
	public static Animation shipAnim;
	private static int FRAME_C= 2;
	private static int FRAME_R= 2;
	private static int index= 0;
	
	public static Texture dis_en_pause;
	public static TextureRegion disable, enable;
	public static TextureRegion pause;
	
	public static Texture sc_set;
	public static TextureRegion score, settings;
	
	public static Texture audioSet;
	public static TextureRegion musicOn, musicOff;
	public static TextureRegion soundOn, soundOff;
	public static TextureRegion minus, plus;
	
	public static Texture menuButtons;
	public static TextureRegion start, back;
	public static TextureRegion resume, retry;
	public static TextureRegion exit,about;

	public static Texture missed;
	
	private static Texture shapes;
	public static TextureRegion circBtn, circBullet;
	public static TextureRegion sqBtn, sqBullet;
	public static TextureRegion trBtn, trBullet;
	private static int widthTex, heightTex, mWidth,mHeight;
	public static Texture health;
	public static TextureRegion health1, health2, health3, health4, health5;
	
	public static Texture missedBar;
	public static TextureRegion miss1, miss2, miss3, miss4;
	
	public static Texture enemies;
	public static TextureRegion circEnem, circEnemDead;
	public static TextureRegion sqEnem, sqEnemDead;
	public static TextureRegion trEnem, trEnemDead;
	
	public static Texture loadTexture (String file) {
		return new Texture(Gdx.files.internal(file));
	}

	public static void load () {
		
		font = new BitmapFont(Gdx.files.internal("font/my.fnt"), Gdx.files.internal("font/my.png"), false);
		
		font.getRegion().getTexture().setFilter(TextureFilter.Linear,TextureFilter.Linear);
	
		starSkyBGTex= loadTexture("starsky1.png");
		starSkyBGTex.setWrap(Texture.TextureWrap.Repeat,Texture.TextureWrap.Repeat);
		gameOver= loadTexture("gameover.png");
		sssTitle= loadTexture("sss.png");
		dis_en_pause= loadTexture("dis_en_pause.png");
		disable= new TextureRegion(dis_en_pause, 0,0,dis_en_pause.getWidth()/2,dis_en_pause.getHeight()/2);
		enable= new TextureRegion(dis_en_pause,dis_en_pause.getWidth()/2,0,dis_en_pause.getWidth()/2,dis_en_pause.getHeight()/2);
		pause= new TextureRegion(dis_en_pause, 0,dis_en_pause.getHeight()/2,dis_en_pause.getWidth()/2,dis_en_pause.getHeight()/2);
			
		sc_set= loadTexture("sc_set.png");
		score= new TextureRegion(sc_set, 0,0,sc_set.getWidth()/2,sc_set.getHeight());
		settings= new TextureRegion(sc_set,sc_set.getWidth()/2,0,sc_set.getWidth()/2,sc_set.getHeight());
		
		audioSet= loadTexture("audioset.png");
		soundOn= new TextureRegion(audioSet,0,0,100,100);
		soundOff= new TextureRegion(audioSet, 100,0,100,100);
		musicOn= new TextureRegion(audioSet, 0,100,100,100);
		musicOff= new TextureRegion(audioSet, 100,100,100,100);
		plus= new TextureRegion(audioSet, 0,200,100,100);
		minus= new TextureRegion(audioSet, 100,200,100,100);

		menuButtons= loadTexture("menubuttons.png");
		mWidth= menuButtons.getWidth()/2;
		mHeight=menuButtons.getHeight()/3;
		
		start= new TextureRegion(menuButtons,0,0, mWidth,mHeight);
		resume= new TextureRegion(menuButtons,mWidth,0, mWidth,mHeight);
		retry= new TextureRegion(menuButtons,0,mHeight, mWidth,mHeight);
		about= new TextureRegion(menuButtons,mWidth,mHeight, mWidth,mHeight);
		exit= new TextureRegion(menuButtons,0,menuButtons.getHeight()- mHeight, mWidth,mHeight);
		back=new TextureRegion(menuButtons,mWidth,menuButtons.getHeight() -mHeight,mWidth ,mHeight);
		
		shapes= loadTexture("shapes.png");
		widthTex= shapes.getWidth()/2;
		heightTex= shapes.getHeight()/3;
		circBtn= new TextureRegion(shapes,0,0,widthTex,heightTex);
		circBullet= new TextureRegion(shapes, widthTex,0,widthTex,heightTex);
		sqBtn= new TextureRegion(shapes, 0,100,widthTex,heightTex);
		sqBullet= new TextureRegion(shapes, widthTex,100,widthTex,heightTex);
		trBtn= new TextureRegion(shapes, 0,200,widthTex,heightTex);
		trBullet= new TextureRegion(shapes, widthTex,200,widthTex,heightTex);
		
		health= loadTexture("healthbar.png");
		health1= new TextureRegion(health,0,0, 100,100);
		health2= new TextureRegion(health,100,0, 100,100);
		health3= new TextureRegion(health,0,100, 100,100);
		health4= new TextureRegion(health,100,100, 100,100);
		health5= new TextureRegion(health,0,200, 100,100);
		
		enemies= loadTexture("enemies.png");
		circEnem= new TextureRegion(enemies, 0,0,100,100);
		circEnemDead= new TextureRegion(enemies, 100,0,100,100);
		sqEnem= new TextureRegion(enemies, 0,100,100,100);
		sqEnemDead= new TextureRegion(enemies, 100,100,100,100);
		trEnem= new TextureRegion(enemies, 0,200,100,100);
		trEnemDead= new TextureRegion(enemies, 100,200,100,100);
		
		missedBar= loadTexture("missedbar.png");
		miss1= new TextureRegion(missedBar,missedBar.getWidth()/2,missedBar.getHeight()/2,missedBar.getWidth()/2,missedBar.getHeight()/2);
		miss2= new TextureRegion(missedBar,0,missedBar.getHeight()/2,missedBar.getWidth()/2,missedBar.getHeight()/2);
		miss3= new TextureRegion(missedBar,missedBar.getWidth()/2,0,missedBar.getWidth()/2,missedBar.getHeight()/2);
		miss4= new TextureRegion(missedBar,0,0,missedBar.getWidth()/2,missedBar.getHeight()/2);
		
		missed= loadTexture("missed.png");
		
		electrokrabLogo= loadTexture("electrokrab.png");
		spaceshipTex = loadTexture("spaceships.png");
		
		TextureRegion[][] tmp= TextureRegion.split(spaceshipTex, spaceshipTex.getWidth()/2, spaceshipTex.getHeight()/2);
		TextureRegion[] shipFrames= new TextureRegion[FRAME_C* FRAME_R];
		for (int i = 0; i < FRAME_R; i++)
		{
			for (int j = 0; j < FRAME_C; j++)
			{
				shipFrames[index++] = tmp[i][j];
				if( index> 4)
				{
					index= 0;
				}
			}
		}
		shipAnim = new Animation(0.05f, shipFrames);
	}
	public static void dispose(){
		font.dispose();
		spaceshipTex.dispose();
		electrokrabLogo.dispose();
		starSkyBGTex.dispose();
		gameOver.dispose();
		dis_en_pause.dispose();
		sc_set.dispose();
		audioSet.dispose();
		shapes.dispose();
		menuButtons.dispose();
		enemies.dispose();
		health.dispose();
		missed.dispose();
		missedBar.dispose();
	}
	
}

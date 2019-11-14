package com.electrokrab.spaceshape;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.electrokrab.spaceshape.resourses.*;
import com.electrokrab.spaceshape.screens.*;
import com.electrokrab.spaceshape.settings.*;
import com.electrokrab.spaceshape.objects.*;

public class SpaceShape extends Game {
	
	public static int SCREEN_WIDTH= 480;
	public static int SCREEN_HEIGHT= 800;
	public SpriteBatch batch;
	public static int lastScreen;
	public final static int MENU_SCREEN=0;
	public final static int GAME_SCREEN= 1;
	public final static int HIGHSCORES_SCREEN= 2;
	private AdsController adsController;
	
	public SpaceShape(AdsController adsController){
		this.adsController=adsController;
	}
	public AdsController getAds() {
		return adsController;
	}
	
public void create() {
		batch = new SpriteBatch();
		Settings.load();
		Assets.load();
		AudioManager.init();
		this.setScreen(new MainMenuScreen(this));
	}
	public void render() {
		super.render(); // important!
	}

	@Override
	public void dispose()
	{
		// TODO: Implement this method
		super.dispose();
		Assets.dispose();
		AudioManager.dispose();
	}
}

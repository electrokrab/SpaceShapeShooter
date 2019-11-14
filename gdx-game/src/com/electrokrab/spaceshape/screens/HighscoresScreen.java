package com.electrokrab.spaceshape.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.Input.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.math.*;
import com.electrokrab.spaceshape.*;
import com.electrokrab.spaceshape.resourses.*;
import com.electrokrab.spaceshape.settings.*;

public class HighscoresScreen implements Screen, InputProcessor {

    SpaceShape game;
    OrthographicCamera guiCam;
    Rectangle back;
    Vector3 touchPoint;
	float scoreWidth;
	float fontX, fontY;
	
	String highScore,score1,score2,score3,score4,score5;
    public HighscoresScreen(SpaceShape game){
        this.game = game;
		game.getAds().showAds(true);
        touchPoint = new Vector3();
		back= new Rectangle(20,30,150,60);
        guiCam = new OrthographicCamera();
        guiCam.setToOrtho(false, SpaceShape.SCREEN_WIDTH, SpaceShape.SCREEN_HEIGHT);
		highScore= "HIGHSCORES:";
		
    }

    public void update(){
		 if (Gdx.input.justTouched()) {
            guiCam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));

			if(back.contains(touchPoint.x, touchPoint.y)){
				
				if(game.lastScreen==SpaceShape.MENU_SCREEN){
					game.setScreen(new MainMenuScreen(this.game));
					game.lastScreen=SpaceShape.HIGHSCORES_SCREEN;
				} else
				if(game.lastScreen== SpaceShape.GAME_SCREEN){
				game.setScreen( new GameScreen(this.game));
				game.lastScreen=SpaceShape.HIGHSCORES_SCREEN;
				}
				AudioManager.playSound(AudioManager.click,AudioManager.sEnable,AudioManager.soundVolume);
				AudioManager.vibrate(AudioManager.vEnable);
				return;
            }
		}
	
    }
	
    public void draw(){
        GL20 gl = Gdx.gl;
        gl.glClearColor(1, 0, 0, 1);
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        guiCam.update();
        game.batch.setProjectionMatrix(guiCam.combined);

        game.batch.disableBlending();
        game.batch.begin();
        game.batch.draw(Assets.starSkyBGTex, 0, 0,0,0,SpaceShape.SCREEN_WIDTH,SpaceShape.SCREEN_HEIGHT);
        game.batch.end();
		fontY= SpaceShape.SCREEN_HEIGHT-150;
      
		game.batch.enableBlending();
		game.batch.begin();
        game.batch.draw(Assets.back, back.x,back.y, back.width, back.height);
		
		Assets.font.setScale(1.6f,1.4f);
		scoreWidth= Assets.font.getBounds(highScore).width;
		Assets.font.draw(game.batch,highScore,SpaceShape.SCREEN_WIDTH/2-(scoreWidth/2),fontY+70);
		game.batch.end();
		
		game.batch.enableBlending();
		game.batch.begin();
		Assets.font.setScale(1.4f,1.2f);
		for(int i=9; i>=0;i--){
			if((i+1)>=10){
				fontX= (SpaceShape.SCREEN_WIDTH/2)-178;
			}else{
				fontX= (SpaceShape.SCREEN_WIDTH/2)-160;
			}
			Assets.font.draw(game.batch,(i+1)+". "+ Settings.highscores[i][1]+" Score: "+Settings.highscores[i][2], fontX,fontY-i*50);
			
		}
		
		game.batch.end();
		
    }

    @Override
    public void render (float delta) {
        update();
        draw();
    }
	
	@Override
	public void show(){
		Gdx.input.setInputProcessor(this);
		Gdx.input.setCatchBackKey(true);
	}

	@Override
	public boolean keyDown(int keycode)
	{
		return false;
	}

	@Override
	public boolean keyUp(int keycode)
	{
		if(keycode == Keys.BACK ){
			if(game.lastScreen==SpaceShape.MENU_SCREEN){
			game.setScreen(new MainMenuScreen(game));
			} else
			if(game.lastScreen== SpaceShape.GAME_SCREEN){
				game.setScreen( new GameScreen(this.game));
			}
			this.dispose();
		}
		return true;
	}

	@Override
	public boolean keyTyped(char character)
	{
		
		return false;
	}

	@Override
	public boolean touchDown(int x, int y, int pointer, int button)
	{
		return false;
	}

	@Override
	public boolean touchUp(int x, int y, int pointer, int button)
	{
		return false;
	}

	@Override
	public boolean touchDragged(int x, int y, int pointer)
	{

		return false;
	}

	@Override
	public boolean mouseMoved(int x, int y)
	{
		
		return false;
	}

	@Override
	public boolean scrolled(int amount)
	{
		return false;
	}

	@Override
	public void resize(int width, int height)
	{
		
	}

	@Override
	public void hide()
	{
		Gdx.input.setInputProcessor(null);
	}

	@Override
	public void pause()
	{
		// TODO: Implement this method
	}

	@Override
	public void resume()
	{
		// TODO: Implement this method
	}

	@Override
	public void dispose()
	{
		Gdx.input.setInputProcessor(null);
		Gdx.input.setCatchBackKey(false);
		
	}
}

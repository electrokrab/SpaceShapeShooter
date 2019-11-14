package com.electrokrab.spaceshape.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.Input.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.glutils.*;
import com.badlogic.gdx.math.*;
import com.electrokrab.spaceshape.SpaceShape;
import com.electrokrab.spaceshape.objects.*;
import com.electrokrab.spaceshape.resourses.*;
import com.electrokrab.spaceshape.settings.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.*;
import com.badlogic.gdx.scenes.scene2d.*;
import com.electrokrab.spaceshape.objects.Buttons.*;

public class MainMenuScreen implements Screen,InputProcessor {

  	SpaceShape game;
	MenuBg enemy;
	OrthographicCamera camera;
	ShapeRenderer shapeRender= new ShapeRenderer();
	Rectangle startBounds;
	Rectangle highScoresBounds;
	Rectangle settingsBounds;
	Rectangle aboutBounds;
	Vector3 touchPoint;
	String lastScore, userName,title;
	float fntW, fntH;
	float scoreWidth,nameWidth,xFont,yFont;
	public MainMenuScreen(SpaceShape game) {
		this.game = game;
		game.getAds().showAds(true);
		enemy= new MenuBg(game.batch);
		camera = new OrthographicCamera();
		camera.setToOrtho(false, SpaceShape.SCREEN_WIDTH, SpaceShape.SCREEN_HEIGHT);
		touchPoint= new Vector3();
		yFont= SpaceShape.SCREEN_HEIGHT-230;
		highScoresBounds= new Rectangle(115, 330, 250,60); 
		startBounds= new Rectangle(115, 400, 250, 70);
		settingsBounds= new Rectangle(115,260,250,60);
		aboutBounds= new Rectangle(115,190,250,60);
		fntW=1.8f;
		fntH= 1.6f;
	}

	public void update()
	{
		if(Settings.userName!=Settings.lastscore[0]){
			Settings.setLastScore(Settings.getUsername(),0);
		}
		if (Gdx.input.justTouched())
		{
            camera.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
			if(enemy.enemy.contains(touchPoint.x,touchPoint.y)){
				enemy.toched();
				AudioManager.vibrate(AudioManager.vEnable);
				AudioManager.playSound(AudioManager.hitEnemy, AudioManager.sEnable,AudioManager.soundVolume);
				return;
			}
			if(highScoresBounds.contains(touchPoint.x, touchPoint.y)){
				game.lastScreen= SpaceShape.MENU_SCREEN;	
				game.setScreen(new HighscoresScreen(game));
				AudioManager.vibrate(AudioManager.vEnable);
				AudioManager.playSound(AudioManager.click, AudioManager.sEnable,AudioManager.soundVolume);
				return;
			}
            if (startBounds.contains(touchPoint.x, touchPoint.y)){
                game.setScreen(new GameScreen(game));
				game.lastScreen= SpaceShape.GAME_SCREEN;
				AudioManager.vibrate(AudioManager.vEnable);
				AudioManager.playSound(AudioManager.click, AudioManager.sEnable, AudioManager.soundVolume);
                return;
            }
			if (settingsBounds.contains(touchPoint.x, touchPoint.y)){
                game.setScreen(new SettingsScreen(game));
				AudioManager.vibrate(AudioManager.vEnable);
				AudioManager.playSound(AudioManager.click, AudioManager.sEnable,AudioManager.soundVolume);
                return;
            }
			if (aboutBounds.contains(touchPoint.x, touchPoint.y)){
                game.setScreen(new AboutScreen(game));
				AudioManager.vibrate(AudioManager.vEnable);
				AudioManager.playSound(AudioManager.click, AudioManager.sEnable,AudioManager.soundVolume);
                return;
            }
		}
	}
	
	public void draw () {
        GL20 gl = Gdx.gl;
        gl.glClearColor(1, 0, 0, 1);
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
        game.batch.setProjectionMatrix(camera.combined);
		game.batch.enableBlending();
		game.batch.begin();
		game.batch.draw(Assets.starSkyBGTex,0,0,0,0,SpaceShape.SCREEN_WIDTH,SpaceShape.SCREEN_HEIGHT);
		game.batch.end();
		
		enemy.render();
		
        game.batch.enableBlending();
        game.batch.begin();
		game.batch.setColor(1,1,1,1);
		Assets.font.setScale(fntW, fntH);
		
		userName="Player: "+Settings.lastscore[0];
		nameWidth= Assets.font.getBounds(userName).width;
		lastScore= "Last Score:"+Settings.lastscore[1];
		scoreWidth= Assets.font.getBounds(lastScore).width;
		Assets.font.draw(game.batch, userName,(SpaceShape.SCREEN_WIDTH/2)-(nameWidth /2),yFont);
		Assets.font.draw(game.batch, lastScore,(SpaceShape.SCREEN_WIDTH/2)-(scoreWidth/2),yFont-40);
		game.batch.draw(Assets.electrokrabLogo,10,20,90,70);
		game.batch.draw(Assets.start, startBounds.x,startBounds.y,startBounds.width,startBounds.height);
		game.batch.draw(Assets.score,highScoresBounds.x,highScoresBounds.y,highScoresBounds.width,highScoresBounds.height);
		game.batch.draw(Assets.settings,settingsBounds.x,settingsBounds.y,settingsBounds.width,settingsBounds.height);
        game.batch.draw(Assets.about,aboutBounds.x,aboutBounds.y,aboutBounds.width,aboutBounds.height);
		game.batch.end();
		
		game.batch.enableBlending();
		game.batch.begin();
		game.batch.draw(Assets.sssTitle,(SpaceShape.SCREEN_WIDTH/2)-200,yFont+10, 400, 120);
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

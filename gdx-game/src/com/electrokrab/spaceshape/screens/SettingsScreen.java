package com.electrokrab.spaceshape.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.Input.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.math.*;
import com.electrokrab.spaceshape.*;
import com.electrokrab.spaceshape.resourses.*;
import com.electrokrab.spaceshape.settings.*;
import com.electrokrab.spaceshape.settings.Settings;
import com.electrokrab.spaceshape.objects.Buttons.*;


public class SettingsScreen implements Screen,InputProcessor {

	SpaceShape game;
	OrthographicCamera guiCam;
	Vector3 touchPoint;
	String title, vibrate;
	TextInputButton userNameBounds;
	MusicSetButton musicSetBounds;
	SoundSetButton soundSetBounds;
	VibroSetButton vibroSetBounds;
	MusicVolDButton musicVolDBounds;
	MusicVolUpButton musicVolUBounds;
	SoundVolUButton soundVolUBounds;
	SoundVolDButton soundVolDBounds;
	Rectangle backBounds;
	float xFont, yFont, xTitle;
	
	public SettingsScreen(SpaceShape game) {
		
		this.game = game;
		game.getAds().showAds(true);
		guiCam = new OrthographicCamera();
        guiCam.setToOrtho(false, SpaceShape.SCREEN_WIDTH, SpaceShape.SCREEN_HEIGHT);
		yFont= SpaceShape.SCREEN_HEIGHT-220;		
		touchPoint = new Vector3();
		backBounds= new Rectangle(20,30,150,60);
		title= "Tap to Rename.";
		userNameBounds= new TextInputButton(yFont,1.8f,1.6f);
		musicSetBounds= new MusicSetButton(40,350,70,80);
		vibroSetBounds= new VibroSetButton(40,210,1.f,.8f);	
		musicVolUBounds= new MusicVolUpButton(110,390,60,60);
		musicVolDBounds= new MusicVolDButton(110,310,60,60);
		
		soundSetBounds= new SoundSetButton(300,350,70,80);
		soundVolUBounds= new SoundVolUButton(380,390,60,60);
		soundVolDBounds= new SoundVolDButton(380,310,60,60);
	}

	@Override
	public void render(float delta) {
		update();
		draw();
	}

	private void draw() {

		GL20 gl = Gdx.gl;
        gl.glClearColor(1, 0, 0, 1);
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        guiCam.update();
		
        game.batch.setProjectionMatrix(guiCam.combined);
        game.batch.disableBlending();
        game.batch.begin();
        game.batch.draw(Assets.starSkyBGTex, 0,0,0, 0,SpaceShape.SCREEN_WIDTH, SpaceShape.SCREEN_HEIGHT);
        game.batch.end();
		
		userNameBounds.drawBtn(game.batch);
		
        game.batch.enableBlending();	
		game.batch.begin();
		game.batch.draw(Assets.back,backBounds.x,backBounds.y,backBounds.width,backBounds.height);
		
		musicSetBounds.drawBtn(game.batch);
		soundSetBounds.drawBtn(game.batch);
		musicVolDBounds.drawBtn(game.batch);
		musicVolUBounds.drawBtn(game.batch);
		soundVolUBounds.drawBtn(game.batch);
		soundVolDBounds.drawBtn(game.batch);
		game.batch.end();
		
		game.batch.begin();
		Assets.font.setScale(1f,.8f);
		xTitle= (SpaceShape.SCREEN_WIDTH/2)- (Assets.font.getBounds(title).width/2);
		Assets.font.draw(game.batch, title, xTitle, yFont-50);
		Assets.font.draw(game.batch,"Volume: "+Settings.musicVol,40,300);
		Assets.font.draw(game.batch,"Volume: "+Settings.soundVol,300,300);
		game.batch.end();
		
		vibroSetBounds.drawBtn(game.batch);

		}

	private void update() {
		userNameBounds.update();
		AudioManager.update();
		AudioManager.playMusic(Settings.getMusicSet());
		AudioManager.setMusicVolume(AudioManager.musicVolume);
		if(Settings.userName!=Settings.lastscore[0]){
			Settings.setLastScore(Settings.getUsername(),0);
		}
		if (Gdx.input.justTouched()) {
			guiCam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
			
			if(backBounds.contains(touchPoint.x, touchPoint.y)){
				AudioManager.vibrate(AudioManager.vEnable);
				AudioManager.playSound(AudioManager.click, AudioManager.sEnable,AudioManager.soundVolume);
				game.setScreen(new MainMenuScreen(game));
				
				return;
			}
			vibroSetBounds.buttonTouched(touchPoint.x,touchPoint.y);
		
			musicSetBounds.buttonTouched(touchPoint.x, touchPoint.y);
			
			musicVolUBounds.buttonTouched(touchPoint.x, touchPoint.y);
			musicVolDBounds.buttonTouched(touchPoint.x,touchPoint.y);
		
			soundSetBounds.buttonTouched(touchPoint.x,touchPoint.y);
			soundVolUBounds.buttonTouched(touchPoint.x,touchPoint.y);
			soundVolDBounds.buttonTouched(touchPoint.x,touchPoint.y);
			
			userNameBounds.buttonTouched(touchPoint.x,touchPoint.y);
		}
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

			this.dispose();
			game.setScreen(new MainMenuScreen(game));

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

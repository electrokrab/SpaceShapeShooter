package com.electrokrab.spaceshape.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.Input.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.math.*;
import com.electrokrab.spaceshape.*;
import com.electrokrab.spaceshape.resourses.*;
import com.electrokrab.spaceshape.objects.Buttons.*;
import android.graphics.*;

public class AboutScreen implements Screen, InputProcessor {
	SpaceShape game;
	OrthographicCamera guiCam;
	Rectangle back;
	Vector3 touchPoint;
	float abWidth,crWidth,contWidth,htpWidth;
	float fontX, aboutY,creditsY,contY,htpY;
	String about,credits,contacts,htp;
	String abText,crText,coText,htpText;
	
	public AboutScreen(SpaceShape game){
		this.game = game;
		game.getAds().showAds(true);
		touchPoint = new Vector3();
		back= new Rectangle(20, 30,150,60);
		guiCam = new OrthographicCamera();
		guiCam.setToOrtho(false, SpaceShape.SCREEN_WIDTH, SpaceShape.SCREEN_HEIGHT);
		about= "About:";
		credits= "Credits: ";
		contacts= "Contacts: ";
		htp= "How to play: ";
		aboutY= SpaceShape.SCREEN_HEIGHT-80;	
		abText= "Thanks for using my game! Hope you enjoy it!";
		crText= "All design, programming, arts, sounds and music was created by electrokrab.";
		htpText="Tap the right side of the screen to move the ship right and the left side to move it left."+
		"To shoot an enemy send a bullet of the same shape as the enemy's.";
		
		}
		
		public void update(){
			
			if (Gdx.input.justTouched()) {
				guiCam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));

				if(back.contains(touchPoint.x, touchPoint.y)){
					game.setScreen(new MainMenuScreen(game));
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
			creditsY= aboutY-Assets.font.getWrappedBounds(abText,420).height-60;
			htpY=creditsY-Assets.font.getWrappedBounds(crText,420).height-60;
			game.batch.enableBlending();
			game.batch.begin();
			game.batch.draw(Assets.back, back.x, back.y, back.width, back.height);
			Assets.font.setScale(1.3f,1.1f);
			abWidth= Assets.font.getBounds(about).width;
			Assets.font.draw(game.batch,about,SpaceShape.SCREEN_WIDTH/2-(abWidth/2),aboutY);
			crWidth= Assets.font.getBounds(credits).width;
			Assets.font.draw(game.batch,credits,SpaceShape.SCREEN_WIDTH/2-(crWidth/2),creditsY);
			htpWidth= Assets.font.getBounds(htp).width;
			Assets.font.draw(game.batch,htp,SpaceShape.SCREEN_WIDTH/2-(htpWidth/2),htpY);
			game.batch.end();
		
			game.batch.enableBlending();
			game.batch.begin();
			Assets.font.setScale(1.1f,.9f);
			Assets.font.drawWrapped(game.batch, abText, 30,aboutY-40,420);
			Assets.font.drawWrapped(game.batch, crText, 30,creditsY-40,420);
			Assets.font.drawWrapped(game.batch, htpText, 30,htpY-40,420);
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
				game.setScreen(new MainMenuScreen(game));
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


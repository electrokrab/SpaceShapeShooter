package com.electrokrab.spaceshape.objects.Buttons;

import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.*;
import com.electrokrab.spaceshape.settings.*;
import com.electrokrab.spaceshape.resourses.*;

public class MusicSetButton{

	private float x, y, width, height;

	private Rectangle musicBounds;

	public MusicSetButton(float x, float y, float width, float height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		musicBounds = new Rectangle(x, y, width, height);
	
	}
	
	 public void drawBtn(SpriteBatch batch){
		
		 if(Settings.getMusicSet()){
			 batch.draw(Assets.musicOn,musicBounds.x, musicBounds.y, musicBounds.width,musicBounds.height);
		 }else{
			 batch.draw(Assets.musicOff, musicBounds.x,musicBounds.y,musicBounds.width,musicBounds.height);
		 }
		 
	 }

	public void buttonTouched(float touchX, float touchY){
	
		if(musicBounds.contains( touchX, touchY)){

			if(Settings.getMusicSet()==true|| Settings.getMusicVol()==0){
				Settings.setMusicSet(false);
			}else{
				Settings.setMusicSet(true);
				AudioManager.playSound(AudioManager.click, AudioManager.sEnable,AudioManager.soundVolume);
			}
			AudioManager.vibrate(AudioManager.vEnable);
			return;
		}
	}
	
}

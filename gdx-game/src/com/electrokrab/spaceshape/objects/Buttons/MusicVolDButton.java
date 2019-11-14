package com.electrokrab.spaceshape.objects.Buttons;

import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.*;
import com.electrokrab.spaceshape.settings.*;
import com.electrokrab.spaceshape.resourses.*;

public class MusicVolDButton{

	private float x, y, width, height;

	private Rectangle musicVolDBounds;

	public MusicVolDButton(float x, float y, float width, float height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		musicVolDBounds = new Rectangle(x, y, width, height);
	
	}
	
	 public void drawBtn(SpriteBatch batch){
		
		 batch.draw(Assets.minus,musicVolDBounds.x,musicVolDBounds.y,musicVolDBounds.width,musicVolDBounds.height);
	 }

	public void buttonTouched(float touchX, float touchY){
	
		if(musicVolDBounds.contains( touchX, touchY)){
			if(Settings.getMusicVol()>0&& Settings.getMusicSet()){
				Settings.setMusicVol(Settings.getMusicVol()-1);
				AudioManager.playSound(AudioManager.click, AudioManager.sEnable,AudioManager.soundVolume);
				AudioManager.vibrate(AudioManager.vEnable);
			}else 
			if(Settings.getMusicVol()==0&& Settings.getMusicSet()){
				Settings.setMusicSet(false);
				AudioManager.playSound(AudioManager.click, AudioManager.sEnable,AudioManager.soundVolume);
				AudioManager.vibrate(AudioManager.vEnable);
			}
			return;
			
		}
	}
	
}

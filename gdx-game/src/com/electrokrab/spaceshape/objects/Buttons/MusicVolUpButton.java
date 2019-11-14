package com.electrokrab.spaceshape.objects.Buttons;

import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.*;
import com.electrokrab.spaceshape.settings.*;
import com.electrokrab.spaceshape.resourses.*;

public class MusicVolUpButton{

	private float x, y, width, height;

	private Rectangle musicVolUBounds;

	public MusicVolUpButton(float x, float y, float width, float height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		musicVolUBounds = new Rectangle(x, y, width, height);

	}

	public void drawBtn(SpriteBatch batch){

		batch.draw(Assets.plus,musicVolUBounds.x,musicVolUBounds.y,musicVolUBounds.width,musicVolUBounds.height);
	}

	public void buttonTouched(float touchX, float touchY){

		if(musicVolUBounds.contains( touchX, touchY)){
			if(Settings.getMusicVol()<10&& Settings.getMusicSet()){
				Settings.setMusicVol(Settings.getMusicVol()+1);
				AudioManager.playSound(AudioManager.click, AudioManager.sEnable,AudioManager.soundVolume);
				AudioManager.vibrate(AudioManager.vEnable);
			} else 
			if(Settings.getMusicVol()<10&& Settings.getMusicSet()==false){
				Settings.setMusicSet(true);
				Settings.setMusicVol(Settings.getMusicVol()+1);
				AudioManager.playSound(AudioManager.click, AudioManager.sEnable,AudioManager.soundVolume);
				AudioManager.vibrate(AudioManager.vEnable);
			}

			return;
		}
	}

}

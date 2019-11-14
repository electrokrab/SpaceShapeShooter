package com.electrokrab.spaceshape.objects.Buttons;

import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.*;
import com.electrokrab.spaceshape.settings.*;
import com.electrokrab.spaceshape.resourses.*;

public class SoundVolDButton{

	private float x, y, width, height;

	private Rectangle soundVolDBounds;

	public SoundVolDButton(float x, float y, float width, float height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		soundVolDBounds = new Rectangle(x, y, width, height);

	}

	public void drawBtn(SpriteBatch batch){

		batch.draw(Assets.minus,soundVolDBounds.x,soundVolDBounds.y,
		soundVolDBounds.width, soundVolDBounds.height);
	}

	public void buttonTouched(float touchX, float touchY){

		if(soundVolDBounds.contains( touchX, touchY)){
			if(Settings.getSoundVol()>0&& Settings.getSoundSet()){
				Settings.setSoundVol(Settings.getSoundVol()-1);
				AudioManager.playSound(AudioManager.click, AudioManager.sEnable,AudioManager.soundVolume);
				AudioManager.vibrate(AudioManager.vEnable);
			} else 
			if(Settings.getSoundVol()==0&& Settings.getSoundSet()){
				Settings.setSoundSet(false);
				AudioManager.playSound(AudioManager.click, AudioManager.sEnable,AudioManager.soundVolume);
				AudioManager.vibrate(AudioManager.vEnable);
			}
			return;
		}
	}

}

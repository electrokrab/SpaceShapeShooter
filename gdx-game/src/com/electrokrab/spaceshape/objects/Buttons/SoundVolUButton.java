package com.electrokrab.spaceshape.objects.Buttons;

import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.*;
import com.electrokrab.spaceshape.settings.*;
import com.electrokrab.spaceshape.resourses.*;

public class SoundVolUButton{

	private float x, y, width, height;

	private Rectangle soundVolUBounds;

	public SoundVolUButton(float x, float y, float width, float height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		soundVolUBounds = new Rectangle(x, y, width, height);

	}

	public void drawBtn(SpriteBatch batch){

		batch.draw(Assets.plus,soundVolUBounds.x,soundVolUBounds.y, 
		soundVolUBounds.width,soundVolUBounds.height);
	}

	public void buttonTouched(float touchX, float touchY){

		if(soundVolUBounds.contains( touchX, touchY)){
			if(Settings.getSoundVol()<10&& Settings.getSoundSet()){
				Settings.setSoundVol(Settings.getSoundVol()+1);
				AudioManager.playSound(AudioManager.click, AudioManager.sEnable,AudioManager.soundVolume);
				AudioManager.vibrate(AudioManager.vEnable);
			} else 
			if(Settings.getSoundVol()<10&& Settings.getSoundSet()==false){
				Settings.setSoundSet(true);
				Settings.setSoundVol(Settings.getSoundVol()+1);
				AudioManager.playSound(AudioManager.click, AudioManager.sEnable,AudioManager.soundVolume);
				AudioManager.vibrate(AudioManager.vEnable);
			}

			return;
		}
	}

}

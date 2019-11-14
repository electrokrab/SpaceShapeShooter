package com.electrokrab.spaceshape.objects.Buttons;

import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.*;
import com.electrokrab.spaceshape.settings.*;
import com.electrokrab.spaceshape.resourses.*;

public class SoundSetButton{

	private float x, y, width, height;

	private Rectangle soundBounds;

	public SoundSetButton(float x, float y, float width, float height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		soundBounds = new Rectangle(x, y, width, height);
	
	}
	
	 public void drawBtn(SpriteBatch batch){
		
		 if(Settings.getSoundSet()){
			 batch.draw(Assets.soundOn,soundBounds.x,soundBounds.y,soundBounds.width,soundBounds.height);
		 }else{
			 batch.draw(Assets.soundOff, soundBounds.x,soundBounds.y,soundBounds.width,soundBounds.height);
		 }
		 
	 }

	public void buttonTouched(float touchX, float touchY){
	
		if(soundBounds.contains( touchX, touchY)){

			if(Settings.getSoundSet()==true|| Settings.getSoundVol()==0){
				Settings.setSoundSet(false);

			}else{
				Settings.setSoundSet(true);
				AudioManager.playSound(AudioManager.click, AudioManager.sEnable,AudioManager.soundVolume);
			}
			AudioManager.vibrate(AudioManager.vEnable);
			return;
		}
	}
	
}

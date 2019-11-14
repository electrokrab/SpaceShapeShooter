package com.electrokrab.spaceshape.objects.Buttons;

import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.*;
import com.electrokrab.spaceshape.settings.*;
import com.electrokrab.spaceshape.resourses.*;
import com.badlogic.gdx.graphics.*;
import com.electrokrab.spaceshape.*;

public class VibroSetButton{
	private OrthographicCamera cam;
	private float space, fontX, fontY, rectY;
	private String vibro;
	private float stringW,stringH,size,fontW,fontH;
	private Rectangle vibroBounds;
	private BitmapFont font;

	public VibroSetButton(float x, float y,float width,float height) {
	cam = new OrthographicCamera();
    cam.setToOrtho(false, SpaceShape.SCREEN_WIDTH, SpaceShape.SCREEN_HEIGHT);
	
	rectY= y;
	fontX= x;
	font= Assets.font;
	font.setScale(width,height);
	vibro= "Vibrate: ";
	update();
	}
	public void update(){
		stringW= font.getBounds(vibro).width;
		stringH= font.getData().lineHeight;
		space= font.getData().spaceWidth;
		size= stringH+(stringH/6);
		vibroBounds= new Rectangle((fontX+stringW),rectY,size ,size );
		fontY= rectY+stringH;
	}
	
	 public void drawBtn(SpriteBatch batch){
		cam.update();
		batch.setProjectionMatrix(cam.combined);
		batch.enableBlending();
		batch.begin();
			 
		font.draw(batch, vibro,fontX,fontY);
				 
		if(Settings.getViration()){
			 batch.draw(Assets.enable,vibroBounds.x,vibroBounds.y,vibroBounds.width,vibroBounds.height);
		 }else{
			 batch.draw(Assets.disable, vibroBounds.x,vibroBounds.y,vibroBounds.width,vibroBounds.height);
		 }
		 batch.end();
		 
	 }

	public void buttonTouched(float touchX, float touchY){
		if(vibroBounds.contains( touchX, touchY)){
			if(Settings.getViration()){
				AudioManager.vibrate(AudioManager.vEnable);
				Settings.setVibration(false);	
			}else{
				AudioManager.vibrate(AudioManager.vEnable);
				Settings.setVibration(true);
			}
			AudioManager.playSound(AudioManager.click, AudioManager.sEnable,AudioManager.soundVolume);
			return;
		}
	}
	
}

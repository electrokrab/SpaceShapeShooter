package com.electrokrab.spaceshape.objects.Buttons;

import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.Input.*;
import com.electrokrab.spaceshape.settings.*;
import com.electrokrab.spaceshape.resourses.*;
import com.badlogic.gdx.graphics.glutils.*;
import com.electrokrab.spaceshape.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.*;

public class TextInputButton{

	private OrthographicCamera guiCam;
	private float nameWidth,nameHeight;
	private String userName;
	private BitmapFont font;
	private Rectangle userNameBounds;
	private ShapeRenderer shapeRender= new ShapeRenderer();
	private float xFont, yFont,fontW,fontH;
	public TextInputButton(float y, float width,float height) {
		
		guiCam = new OrthographicCamera();
        guiCam.setToOrtho(false, SpaceShape.SCREEN_WIDTH, SpaceShape.SCREEN_HEIGHT);
		font= Assets.font;
		yFont= y;
		fontW=width;
		fontH=height;
	}
	public void update(){
		font.setScale(fontW,fontH);
		userName= "Set Player: "+Settings.getUsername();
		nameWidth=font.getBounds(userName).width;
		xFont=(SpaceShape.SCREEN_WIDTH/2)-(nameWidth/2);
		nameHeight= font.getData().lineHeight;
		userNameBounds= new Rectangle(xFont-5,yFont-nameHeight-50,nameWidth+10,nameHeight+60);
		
	}
	 public void drawBtn(SpriteBatch batch){
		guiCam.update();
		 shapeRender.setProjectionMatrix(guiCam.combined);
		 shapeRender.begin(ShapeRenderer.ShapeType.Filled);
		 shapeRender.setColor(Color.GRAY);
		 shapeRender.rect(xFont-5, yFont-nameHeight,nameWidth+10,nameHeight+10);

		 shapeRender.end();

		 batch.setProjectionMatrix(guiCam.combined);
		 batch.enableBlending();
		 batch.begin();
		 font.draw(batch,userName, xFont,yFont);
		 batch.end();
		 
	 }

	public void buttonTouched(float touchX, float touchY){
		if (userNameBounds.contains(touchX, touchY)) {
			String pretext= "";

			if (Settings.getUsername().equals("") || Settings.getUsername().length() > 16) {
				pretext = "New Player";
			} else {
				pretext = Settings.getUsername();
			}
			AudioManager.playSound(AudioManager.click, AudioManager.sEnable,AudioManager.soundVolume);
			AudioManager.vibrate(AudioManager.vEnable);
			Gdx.input.getTextInput(new TextInputListener()
				{
					@Override
					public void input(String text) {
						if (text.equals("") || text.length() > 16) {
							Settings.setUsername("New Player");
						} else {
							Settings.setUsername(text);
						}
						AudioManager.vibrate(AudioManager.vEnable);
						AudioManager.playSound(AudioManager.click, AudioManager.sEnable,AudioManager.soundVolume);
					}

					@Override
					public void canceled() {
						AudioManager.vibrate(AudioManager.vEnable);
						AudioManager.playSound(AudioManager.click, AudioManager.sEnable,AudioManager.soundVolume);
					}
				}, "Enter new username",pretext);
			return;
		}
	
	}
	
}

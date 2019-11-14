package com.electrokrab.spaceshape.resourses;

import com.badlogic.gdx.*;
import com.badlogic.gdx.assets.*;
import com.badlogic.gdx.audio.*;
import com.badlogic.gdx.utils.*;
import com.electrokrab.spaceshape.settings.*;

public class AudioManager extends AssetManager
{
	public static Music music;
	public static Sound click;
	public static Sound hitEnemy;
	public static Sound shipHit;
	public static Sound shot;
	public static Sound missed;
	public static boolean mEnable, sEnable, vEnable;
	public static float musicVolume, soundVolume;
	public static long vibrotime= 0;
	
	public void AudioManager(){
		
		
	}
	public static void init(){
		update();
		music = Gdx.audio.newMusic(Gdx.files.internal("audio/electrokrab - lunar is not moon.mp3"));
		music.setLooping(true);
		playMusic(mEnable);
		setMusicVolume(musicVolume);
		click= Gdx.audio.newSound(Gdx.files.internal("audio/clicksound.wav"));
		shot= Gdx.audio.newSound(Gdx.files.internal("audio/shot.wav"));
		hitEnemy= Gdx.audio.newSound(Gdx.files.internal("audio/enemydead.wav"));
		shipHit= Gdx.audio.newSound(Gdx.files.internal("audio/shiphit.wav"));
		missed= Gdx.audio.newSound(Gdx.files.internal("audio/missed.wav"));
		}
	
	public static void setMusicVolume(float volume) {
		update();
		music.setVolume(volume);
		
	}
	
	public static void playMusic(boolean playMus){
		update();
		if(playMus){
			music.play();
			
		}else 
		if(playMus==false)
		{
			music.stop();
			
		}
	}
	
	public static void playSound (Sound sound, boolean sPlay,float volume ) {
	update();
		if (sPlay) {
			sound.play(volume);
		}
	}
	
	public static void vibrate(boolean vibro){

		update();
		if(vibro){
			if(TimeUtils.nanoTime()-vibrotime>150000000)
			Gdx.input.vibrate(150);
			vibrotime= TimeUtils.nanoTime();
		}
	}
	
	public static void update() {
		vEnable= Settings.getViration();
		mEnable= Settings.getMusicSet();
		sEnable= Settings.getSoundSet();
		musicVolume = Settings.getMusicVol() * 0.1f;
		soundVolume = Settings.getSoundVol() * 0.1f;
	}
	
	public static void dispose() {
        music.dispose();
        click.dispose();
		shot.dispose();
		hitEnemy.dispose();
		missed.dispose();
    }
}

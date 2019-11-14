package com.electrokrab.spaceshape.settings;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.*;
import java.security.acl.*;

public class Settings {
	public  static Preferences prefs;
	public static final String PREFSET= "com.electrokrab.spaceshape.PREFSET";
	public static String defName= "PLAYER";
	public static String userName= defName;
	public static int soundVol;
	public static int musicVol;
	public static boolean soundSet= false;
	public static boolean musicSet= true;
	public static boolean vibroSet= true;
	public static String lastscore[]= {"Player","0"};
	public static int lastScore= 0;
	public static String highscores[][]=  {
		{ "1.)","", "" },
		{ "2.)", "", "" },
		{ "3.)", "", "" },
		{ "4.)", "", "" },
		{ "5.)", "", "" },
		{ "6.)", "", "" },
		{ "7.)", "", "" },
		{ "8.)", "", "" },
		{ "9.)", "", "" },
		{ "10.)", "", "" }
		
	};
	public static String defString[][]={
		{"PLAYER","0"},{"PLAYER", "0"},
		{"PLAYER","0"},{"PLAYER", "0"},
		{"PLAYER","0"},{"PLAYER", "0"},
		{"PLAYER","0"},{"PLAYER", "0"},
		{"PLAYER","0"},{"PLAYER", "0"},	
		};
	
	public static void load () {
		
		prefs = Gdx.app.getPreferences(PREFSET);
		userName= prefs.getString("username",userName);
		for(int i=0; i<10;i++){
			highscores[i][1] = prefs.getString("name"+i, defString[i][0]);
			highscores[i][2] = prefs.getString("score"+i, defString[i][1] );

		}
		lastscore[0] = prefs.getString("lastname", lastscore[0]);
		lastscore[1] = prefs.getString("lastscore", lastscore[1] );
		
		soundVol = prefs.getInteger("soundVol", 5);
		musicVol = prefs.getInteger("musicVol", 5);
		soundSet= prefs.getBoolean("soundSet", soundSet);
		musicSet= prefs.getBoolean("musicSet",musicSet);
		vibroSet= prefs.getBoolean("vibroSet", vibroSet);
		save();
	}

	public static void save () {
	
		for(int i=0; i<10;i++){
		prefs.putString("name"+i,highscores[i][1]);
		prefs.putString("score"+i,highscores[i][2]);
		}
		prefs.putString("lastscore",lastscore[1]);
		prefs.putString("lastname",lastscore[0]);
		prefs.flush();
	}

	public static String getUsername() {
		return userName;
	}

	public static void setUsername(String username) {
		prefs.putString("username", username);
		prefs.flush();
		load();
	}
	
	public static boolean getViration(){
		return vibroSet;
	}

	public static void setVibration(boolean vibroSet) {
		prefs.putBoolean("vibroSet", vibroSet);
		prefs.flush();
		load();
	}
	
	
	public static boolean getSoundSet(){
		return soundSet;
	}
	
	public static void setSoundSet(boolean soundSet) {
		prefs.putBoolean("soundSet", soundSet);
		prefs.flush();
		load();
	}
	
	public static int getSoundVol() {
		return soundVol;
	}

	public static void setSoundVol(int soundVol) {
		prefs.putInteger("soundVol", soundVol);
		prefs.flush();
		load();
	}
	public static boolean getMusicSet(){
		return musicSet;
	}

	public static void setMusicSet(boolean musicSet){
		prefs.putBoolean("musicSet",musicSet);
		prefs.flush();
		load();
	}

	public static int getMusicVol() {
		return musicVol;
	}

	public static void setMusicVol(int musicVol) {
		prefs.putInteger("musicVol", musicVol);
		prefs.flush();
		load();
	}

	public static void setLastScore(String player,int lastScore) {
		lastscore[1]= String.valueOf(lastScore);
		lastscore[0]= player;
		save();
	}

	public static void addScore(String player, int score) {
		for (int i = 0; i < 10; i++) {
			if (score > Integer.parseInt(highscores[i][2])) {
				int temp = Integer.parseInt(highscores[i][2]);
				highscores[i][2] = String.valueOf(score);
				score= temp;
				String tempP = highscores[i][1];
				highscores[i][1] = player;
				player = tempP;
			}
			save();
		}
	}
}

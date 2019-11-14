package com.electrokrab.spaceshape.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.Input.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.math.*;
import com.electrokrab.spaceshape.*;
import com.electrokrab.spaceshape.objects.*;
import com.electrokrab.spaceshape.resourses.*;
import com.electrokrab.spaceshape.settings.*;
import com.electrokrab.spaceshape.worldutils.*;
import com.electrokrab.spaceshape.worldutils.World.*;
import com.electrokrab.spaceshape.objects.Buttons.*;

public class GameScreen implements Screen, InputProcessor  {
	static final int GAME_START=0;
    static final int GAME_RUNNING = 1;
    static final int GAME_PAUSED = 2;
    static final int GAME_OVER = 3;

    SpaceShape game;
    int state;
	int bullet;
    OrthographicCamera guiCam;
    Vector3 touchPoint;
	MainLaser laser;
    World world;
    WorldListener worldListener;
    WorldRenderer renderer;
	
	TextInputButton setPlayerName;
	MusicSetButton musicSetBounds;
	SoundSetButton soundSetBounds;
	VibroSetButton vibroSetBounds;
	MusicVolDButton musicVolDBounds;
	MusicVolUpButton musicVolUBounds;
	SoundVolUButton soundVolUBounds;
	SoundVolDButton soundVolDBounds;
	
	Rectangle scoreBounds;
    Rectangle pauseBounds;
    Rectangle resumeBounds;
    Rectangle quitBounds;
	Rectangle circBtn, sqBtn, trBtn;
    int lastScore;
	float exitY, resumeY;
    String scoreString;
	
    public GameScreen (SpaceShape game) {
        this.game = game;
		
        state = GAME_START;
        guiCam = new OrthographicCamera();
        guiCam.setToOrtho(false, SpaceShape.SCREEN_WIDTH, SpaceShape.SCREEN_HEIGHT);
        touchPoint = new Vector3();
        worldListener = new WorldListener()
		{
			@Override
			public void left(){
				MainShip.xPos-=150;
			}
			@Override
			public void right(){
				MainShip.xPos+=150;
			}
			
            @Override
            public void shoot () {
				MainLaser.bulletNum= bullet;
				AudioManager.playSound(AudioManager.shot,AudioManager.sEnable,AudioManager.soundVolume);
            }

            @Override
            public void hit () {
                AudioManager.playSound(AudioManager.hitEnemy,AudioManager.sEnable,AudioManager.soundVolume);
            }
		
			@Override
			public void shiphit(){
				AudioManager.playSound(AudioManager.shipHit,AudioManager.sEnable,AudioManager.soundVolume);
			}
        };
		
        world = new World(worldListener);
        renderer = new WorldRenderer(game.batch, world);
        setPlayerName= new TextInputButton(580,1.8f,1.6f);
		vibroSetBounds= new VibroSetButton(40,110,1.f,.8f);	
		musicSetBounds= new MusicSetButton(40,250,70,80);
		musicVolUBounds= new MusicVolUpButton(110,290,60,60);
		musicVolDBounds= new MusicVolDButton(110,210,60,60);

		soundSetBounds= new SoundSetButton(300,250,70,80);
		soundVolUBounds= new SoundVolUButton(380,290,60,60);
		soundVolDBounds= new SoundVolDButton(380,210,60,60);
		
		pauseBounds = new Rectangle(30, 730, 60, 60);
		scoreBounds= new Rectangle(140,300,200,70);
		circBtn= new Rectangle(SpaceShape.SCREEN_WIDTH/2-45, 40, 90, 90);
		sqBtn= new Rectangle(340, 40, 90, 90);
		trBtn = new Rectangle(40, 40, 90, 90);
        lastScore = 0;
        scoreString = "SCORE: 0";
		
		
    }

    public void update (float deltaTime) {
	
		AudioManager.update();
		AudioManager.playMusic(Settings.getMusicSet());
		AudioManager.setMusicVolume(AudioManager.musicVolume);	
		if (deltaTime > 0.1f) deltaTime = 0.1f;
        switch (state) {
			case GAME_START:
				resumeY= 450;
				exitY=370;
				updateStarting();
				break;
            case GAME_RUNNING:
                updateRunning(deltaTime);
                break;
            case GAME_PAUSED:
				resumeY=500;
                exitY=420;
				updatePaused();
                break;
            case GAME_OVER:
				resumeY=380;
				exitY=220;
				updateGameOver();
                break;
        }
		quitBounds = new Rectangle(140, exitY, 200, 70);
		resumeBounds = new Rectangle(140, resumeY, 200, 70);
		if(state==GAME_OVER){
			game.getAds().showAds(true);
		} else{
			game.getAds().showAds(false);
		}
    }
	private void updateStarting(){
		setPlayerName.update();
		if(Gdx.input.justTouched()){
			guiCam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
			
			if (resumeBounds.contains(touchPoint.x, touchPoint.y)) {
				AudioManager.vibrate(AudioManager.vEnable);
				AudioManager.playSound(AudioManager.click, AudioManager.sEnable,AudioManager.soundVolume);
				state = GAME_RUNNING;
                return;
            }
			if(quitBounds.contains(touchPoint.x, touchPoint.y)){
				AudioManager.vibrate(AudioManager.vEnable);
				AudioManager.playSound(AudioManager.click, AudioManager.sEnable,AudioManager.soundVolume);
				game.setScreen(new MainMenuScreen(game));
				return;
			}
		setPlayerName.buttonTouched(touchPoint.x,touchPoint.y);
		}
	}

    private void updateRunning (float deltaTime) {
        if (Gdx.input.justTouched()) {
            guiCam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
		
            if (pauseBounds.contains(touchPoint.x, touchPoint.y)) {
                state = GAME_PAUSED;
				AudioManager.vibrate(AudioManager.vEnable);
				AudioManager.playSound(AudioManager.click, AudioManager.sEnable,AudioManager.soundVolume);
                return;
            }
			
			if(circBtn.contains(touchPoint.x, touchPoint.y)){
				bullet= 1;
				world.addMainShipFire();
				return;
			}
			if(sqBtn.contains(touchPoint.x,touchPoint.y)){
				bullet= 2;
				world.addMainShipFire();
				return;
			}
			if(trBtn.contains(touchPoint.x,touchPoint.y)){
				bullet= 3;
				world.addMainShipFire();
				return;
			}
        }

        if(Gdx.input.justTouched()){
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            guiCam.unproject(touchPos);
			if(touchPos.y> 130&& touchPos.x> Gdx.graphics.getWidth()/2){
			worldListener.right();
			}
			if(touchPos.y> 130&& touchPos.x<Gdx.graphics.getWidth()/2){
			worldListener.left();
			}
			
            //Move Ship
            world.updateShip(deltaTime,world.ship.xPos);
			
       }
	   
        world.update(deltaTime);

        if (world.score != lastScore) {
            lastScore = world.score;
            scoreString = "SCORE: " + lastScore;
        }
       
		if (world.state == World.WORLD_STATE_GAME_OVER) {
			state = GAME_OVER;
			scoreString = "SCORE: " + lastScore;
			Settings.setLastScore(Settings.getUsername(),lastScore);
			Settings.addScore(Settings.userName, lastScore);
			
		}
	
    }

    private void updatePaused () {
		
		if (Gdx.input.justTouched()) {
            guiCam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));

            if (resumeBounds.contains(touchPoint.x, touchPoint.y)) {
				AudioManager.vibrate(AudioManager.vEnable);
				AudioManager.playSound(AudioManager.click, AudioManager.sEnable,AudioManager.soundVolume);
				state = GAME_RUNNING;
                return;
            }

            if (quitBounds.contains(touchPoint.x, touchPoint.y)) {
				AudioManager.vibrate(AudioManager.vEnable);
				AudioManager.playSound(AudioManager.click, AudioManager.sEnable,AudioManager.soundVolume);
				game.setScreen(new MainMenuScreen(game));
                return;
            }
			vibroSetBounds.buttonTouched(touchPoint.x,touchPoint.y);

			musicSetBounds.buttonTouched(touchPoint.x, touchPoint.y);
			musicVolUBounds.buttonTouched(touchPoint.x, touchPoint.y);
			musicVolDBounds.buttonTouched(touchPoint.x,touchPoint.y);

			soundSetBounds.buttonTouched(touchPoint.x,touchPoint.y);
			soundVolUBounds.buttonTouched(touchPoint.x,touchPoint.y);
			soundVolDBounds.buttonTouched(touchPoint.x,touchPoint.y);
			
        }
    }

    private void updateGameOver () {
	
		if(Gdx.input.justTouched()){
			guiCam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
        if(resumeBounds.contains(touchPoint.x, touchPoint.y)){
			AudioManager.vibrate(AudioManager.vEnable);
			AudioManager.playSound(AudioManager.click, AudioManager.sEnable,AudioManager.soundVolume);
			game.setScreen(new GameScreen(game));
			return;
		}
			if(scoreBounds.contains(touchPoint.x,touchPoint.y)){
				AudioManager.vibrate(AudioManager.vEnable);
				AudioManager.playSound(AudioManager.click, AudioManager.sEnable,AudioManager.soundVolume);
				game.setScreen(new HighscoresScreen(game));
				game.lastScreen= SpaceShape.GAME_SCREEN;
				return;
			}
		if(quitBounds.contains(touchPoint.x, touchPoint.y)){
			AudioManager.vibrate(AudioManager.vEnable);
			AudioManager.playSound(AudioManager.click, AudioManager.sEnable,AudioManager.soundVolume);
			game.setScreen(new MainMenuScreen(game));
			return;
		}
		}
    }

    public void draw () {
        GL20 gl = Gdx.gl;
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render();
        guiCam.update();
        game.batch.setProjectionMatrix(guiCam.combined);
        
        switch (state) {
			case GAME_START:
				presentStarting();
				break;
            case GAME_RUNNING:
				game.batch.enableBlending();
				game.batch.begin();
                presentRunning();
				game.batch.end();
                break;
            case GAME_PAUSED:
                presentPaused();
                break;
            case GAME_OVER:
				game.batch.enableBlending();
				game.batch.begin();
                presentGameOver();
				game.batch.end();
                break;
        }
    }
	private void presentStarting(){
		game.batch.enableBlending();
        game.batch.begin();
		game.batch.draw(Assets.start,resumeBounds.x,resumeBounds.y,resumeBounds.width,resumeBounds.height);
		game.batch.draw(Assets.exit,quitBounds.x,exitY,quitBounds.width,quitBounds.height);
		game.batch.end();
		setPlayerName.drawBtn(game.batch);
	}

    private void presentRunning () {
		float scoreW;
        game.batch.draw(Assets.pause,30, 730, 60, 60);
		Assets.font.setScale(1.6f,1.5f);
		scoreW= Assets.font.getBounds(scoreString).width/2;
		Assets.font.draw(game.batch, scoreString,SpaceShape.SCREEN_WIDTH/2-scoreW, 770);
        game.batch.draw(Assets.circBtn,SpaceShape.SCREEN_WIDTH/2-45, 40, 90, 90);
		game.batch.draw(Assets.sqBtn,340, 40, 90, 90);
		game.batch.draw(Assets.trBtn,40, 40, 90, 90);
		
		
    }

    private void presentPaused () {
		float pauseW,scoreW;
		game.batch.enableBlending();
		game.batch.begin();
        game.batch.draw(Assets.exit, quitBounds.x, exitY, quitBounds.width, quitBounds.height);
		game.batch.draw(Assets.resume, resumeBounds.x, resumeY, resumeBounds.width, resumeBounds.height);
        Assets.font.setScale(1.6f,1.5f);
		pauseW=Assets.font.getBounds("PAUSE").width/2;
		scoreW=Assets.font.getBounds(scoreString).width/2;
		Assets.font.draw(game.batch, scoreString,((SpaceShape.SCREEN_WIDTH/2)-scoreW) , 770);
		Assets.font.draw(game.batch,"PAUSE",((SpaceShape.SCREEN_WIDTH/2)-pauseW),650);
		game.batch.end();
		game.batch.enableBlending();
		game.batch.begin();
		musicSetBounds.drawBtn(game.batch);

		soundSetBounds.drawBtn(game.batch);

		musicVolDBounds.drawBtn(game.batch);
		musicVolUBounds.drawBtn(game.batch);
		soundVolUBounds.drawBtn(game.batch);
		soundVolDBounds.drawBtn(game.batch);
		Assets.font.setScale(1f,.8f);	
		Assets.font.draw(game.batch,"Volume: "+Settings.musicVol,40,200);
		Assets.font.draw(game.batch,"Volume: "+Settings.soundVol,300,200);
		game.batch.end();
		vibroSetBounds.drawBtn(game.batch);
    }

    private void presentGameOver () {
	float width;
	float nameW;
        game.batch.draw(Assets.gameOver,(SpaceShape.SCREEN_WIDTH/2)-(360/2), 580, 360,150);
		width= Assets.font.getBounds(scoreString).width;
		nameW= Assets.font.getBounds("Player: "+Settings.lastscore[0]).width;
		Assets.font.draw(game.batch,"Player: "+Settings.lastscore[0], (SpaceShape.SCREEN_WIDTH/2)-(nameW/2),550);
		Assets.font.draw(game.batch, scoreString, (SpaceShape.SCREEN_WIDTH/2)-(width/2), 500);
		game.batch.draw(Assets.retry,resumeBounds.x, resumeY,resumeBounds.width,resumeBounds.height);
		game.batch.draw(Assets.score,scoreBounds.x,scoreBounds.y, scoreBounds.width,scoreBounds.height);
		game.batch.draw(Assets.exit,quitBounds.x,exitY,quitBounds.width,quitBounds.height);
    }

    @Override
    public void render (float delta) {
        update(delta);
        draw();
    }

	@Override
	public void resume()
	{
		// TODO: Implement this method
	}
	

    @Override
    public void pause () {
        if (state == GAME_RUNNING) state = GAME_PAUSED;
    }
	
	@Override
	public void show(){
		Gdx.input.setInputProcessor(this);
		Gdx.input.setCatchBackKey(true);
	}

	@Override
	public boolean keyDown(int keycode)
	{
		return false;
	}

	@Override
	public boolean keyUp(int keycode)
	{
		if(keycode == Keys.BACK ){
			if(state== GAME_RUNNING){
			this.dispose();
			state= GAME_PAUSED;
			} 
		}
		return true;
	}

	@Override
	public boolean keyTyped(char character)
	{

		return false;
	}

	@Override
	public boolean touchDown(int x, int y, int pointer, int button)
	{
		return false;
	}

	@Override
	public boolean touchUp(int x, int y, int pointer, int button)
	{
		return false;
	}

	@Override
	public boolean touchDragged(int x, int y, int pointer)
	{

		return false;
	}

	@Override
	public boolean mouseMoved(int x, int y)
	{

		return false;
	}

	@Override
	public boolean scrolled(int amount)
	{
		return false;
	}

	@Override
	public void resize(int width, int height)
	{

	}

	@Override
	public void hide()
	{
		Gdx.input.setInputProcessor(null);
	}

	@Override
	public void dispose()
	{
		
		Gdx.input.setInputProcessor(null);
		Gdx.input.setCatchBackKey(false);
	}

}

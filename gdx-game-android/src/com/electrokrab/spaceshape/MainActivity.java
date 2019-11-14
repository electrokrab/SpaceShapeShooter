package com.electrokrab.spaceshape;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import android.widget.*;
import android.view.*;
import com.google.android.gms.ads.*;
import com.electrokrab.spaceshape.objects.*;
import android.os.*;
import android.content.*;
public class MainActivity extends AndroidApplication implements AdsController {
    
    private static final String BANNER_AD_UNIT_ID = "";	
    private final int SHOW_ADS = 1;
    private final int HIDE_ADS = 0;
    protected AdView advAds;
    protected View gameView;
	
	protected Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what) {
				case SHOW_ADS:
					advAds.setVisibility(View.VISIBLE);
					break;
				case HIDE_ADS:
					advAds.setVisibility(View.GONE);
					break;
            }
        }
    };
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
        cfg.useAccelerometer = false;
		cfg.useCompass = false;
		cfg.useWakelock = true;
	
        RelativeLayout layout = new RelativeLayout(this);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
            WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
		
		View gameView = initializeForView(new SpaceShape(this), cfg);

        layout.addView(gameView);

        advAds = new AdView(this);
		advAds.setAdUnitId(BANNER_AD_UNIT_ID);

        // Устанавливаем размер объявления

        advAds.setAdSize(AdSize.BANNER);

        AdRequest adRequest = new AdRequest.Builder()
			.addTestDevice(AdRequest.DEVICE_ID_EMULATOR) // Указываем тестовый режим на эмуляторе
			.addTestDevice("XXXXXXXXXXXXXXXXXXXXXXX") // ID устройства. Его видно в логе после первого запуска
			.build();

        advAds.loadAd(adRequest);

        RelativeLayout.LayoutParams adParams = 
            new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, 
											RelativeLayout.LayoutParams.WRAP_CONTENT);
        adParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        adParams.addRule(RelativeLayout.CENTER_HORIZONTAL);

        layout.addView(advAds, adParams);

        setContentView(layout);
		
    }

    @Override
    public void showAds(boolean show) {
        handler.sendEmptyMessage(show ? SHOW_ADS : HIDE_ADS);
    }
	
    @Override
    public void onResume() {
        super.onResume();
		if (advAds != null) {
			advAds.resume();
		}
    }

    @Override
    public void onPause() {
		super.onPause();
        if (advAds != null) {
            advAds.pause();
		}
    }

    @Override
    public void onDestroy() {
        if (advAds != null) {
            advAds.destroy();
		}
		super.onDestroy();
    }
	
}

package com.redlake.mcms.activity;

import com.redlake.mcmodeswitcher.R;
import com.redlake.mcms.util.Typefaces;
import com.testflightapp.lib.TestFlight;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class SplashActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		TestFlight.takeOff(this.getApplication(), "f9aa085d-0cc8-4525-b942-a5e2e301f402");
		
		setContentView(R.layout.activity_splash);
		
		Typefaces.setup(this);

		Thread timer = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					Thread.sleep(1500);
				} catch (InterruptedException exc) {
					exc.printStackTrace();
				}
				Intent mainActivityIntent = new Intent(SplashActivity.this,
						MainActivity.class);

				mainActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
				overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
				startActivity(mainActivityIntent);
				overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
				
				finish();
			}
		});
		timer.start();
	}
}
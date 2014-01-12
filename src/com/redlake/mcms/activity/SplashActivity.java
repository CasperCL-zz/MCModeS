package com.redlake.mcms.activity;

import com.redlake.mcmodeswitcher.R;
import com.redlake.mcms.fragment.TutorialFragment;
import com.redlake.mcms.util.Typefaces;
import com.testflightapp.lib.TestFlight;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;

public class SplashActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		TestFlight.takeOff(this.getApplication(),
				"f9aa085d-0cc8-4525-b942-a5e2e301f402");

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

				SharedPreferences settings = getSharedPreferences(
						getString(R.string.settings_file_name), 0);
				String fileString = settings.getString(
						"last_switched_level_file", null);
				boolean firstLaunch = settings.getBoolean("firstLaunch", true);
				if (fileString == null && firstLaunch) {
					launchTutorial();
				} else {
					launchMain();
				}

				finish();
			}
		});
		timer.start();
	}

	protected void launchTutorial() {
		startActivity(new Intent(this, TutorialFragment.class));
		SharedPreferences settings = getSharedPreferences(
				getString(R.string.settings_file_name), 0);
		Editor editor = settings.edit();
		editor.putBoolean("firstLaunch", false);
		editor.commit();

	}

	protected void launchMain() {
		Intent mainActivityIntent = new Intent(SplashActivity.this,
				MainActivity.class);

		mainActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		overridePendingTransition(android.R.anim.fade_in,
				android.R.anim.fade_out);
		startActivity(mainActivityIntent);
		overridePendingTransition(android.R.anim.fade_in,
				android.R.anim.fade_out);
	}
}
package com.redlake.mcms.activity;

import java.io.File;
import java.io.IOException;

import com.redlake.mcmodeswitcher.R;
import com.redlake.mcms.contoller.LevelController;
import com.redlake.mcms.fragment.TutorialFragment;
import com.redlake.mcms.util.Typefaces;
import com.testflightapp.lib.TestFlight;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_activity);

		Button button1 = (Button) findViewById(R.id.main_select_map_button);
		button1.setTypeface(Typefaces.KEEP_CALM_MEDIUM);
		Button button2 = (Button) findViewById(R.id.main_switch_last_button);
		button2.setTypeface(Typefaces.KEEP_CALM_MEDIUM);
		
	}

	public void selectMapButtonClicked(View view) {
		Intent intent = new Intent(MainActivity.this, LevelListActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		startActivity(intent);
		overridePendingTransition(android.R.anim.fade_in,
				android.R.anim.fade_out);
	}

	public void switchLastButtonClicked(View view) {

		TestFlight.passCheckpoint("Switched map by quickmode");

		// Restore preferences
		SharedPreferences settings = getSharedPreferences(
				getString(R.string.settings_file_name), 0);
		String fileString = settings
				.getString("last_switched_level_file", null);
		if (fileString != null) {
			File levelFile = new File(fileString);

			try {
				LevelController.getInstance(this).toggleMode(levelFile);

				Toast.makeText(this, getString(R.string.switch_last_success),
						Toast.LENGTH_SHORT).show();
			} catch (IOException exc) {
				exc.printStackTrace();

				Toast.makeText(this, getString(R.string.error1),
						Toast.LENGTH_SHORT).show();
			}
		} else {
			Toast.makeText(this, getString(R.string.error3), Toast.LENGTH_SHORT)
					.show();
		}
	}
}
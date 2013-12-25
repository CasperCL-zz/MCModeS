package com.redlake.mcms.activity;

import com.redlake.mcmodeswitcher.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_activity);
	}
	
	public void selectMapButtonClicked(View view) {
		Intent intent = new Intent(MainActivity.this, LevelListActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		startActivity(intent);
		overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
	}
	
	public void switchLastButtonClicked(View view) {
		Toast.makeText(this, "Switched last map", Toast.LENGTH_SHORT).show();
	}
	
	
}
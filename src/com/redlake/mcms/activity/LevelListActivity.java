package com.redlake.mcms.activity;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.zhuoweizhang.pocketinveditor.Level;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.redlake.mcmodeswitcher.R;
import com.redlake.mcms.adapter.WorldSelectAdapter;
import com.redlake.mcms.contoller.LevelController;
import com.redlake.mcms.util.Typefaces;
import com.testflightapp.lib.TestFlight;

public class LevelListActivity extends Activity implements
		AdapterView.OnItemClickListener {

	private WorldSelectAdapter adapter;
	private List<Level> levels;
	private TextView noWorldsTV;
	private ListView listview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_level_list);

		levels = new ArrayList<Level>();
		
		initView();
		initListAdapter();
	}

	public void initView(){
		noWorldsTV = (TextView) findViewById(R.id.world_select_noworldsTV);
		noWorldsTV.setTypeface(Typefaces.KEEP_CALM_MEDIUM);
		
		noWorldsTV.setPadding(pixelsToDPI(25), (int)(pixelsToDPI(25) + getActionbarHeight()), pixelsToDPI(25), 0);
	}
	
	public void initListAdapter() {
		listview = (ListView) findViewById(R.id.world_select_listview);

		adapter = new WorldSelectAdapter(this,
				android.R.layout.simple_list_item_1, levels);

		// Add the header in order to make use of the transparent action bar
		listview.addHeaderView(getHeaderView());
		listview.setAdapter(adapter);

		listview.setOnItemClickListener(this);

	}

	@Override
	protected void onResume() {
		super.onResume();
		levels.clear();

		try {
			levels.addAll(LevelController.getInstance(this).getLevels());
		} catch (FileNotFoundException exc) {
			Toast.makeText(LevelListActivity.this,
					getString(R.string.error2), Toast.LENGTH_SHORT)
					.show();
		} catch (IOException exc) {
			exc.printStackTrace();
			Toast.makeText(LevelListActivity.this,
					getString(R.string.error1), Toast.LENGTH_SHORT)
					.show();
			TestFlight.log("Exception occured: " + exc.getMessage());
		}

		if (levels.size() > 0) {
			noWorldsTV.setVisibility(View.GONE);
			listview.setVisibility(View.VISIBLE);
		} else {
			noWorldsTV.setVisibility(View.VISIBLE);
			listview.setVisibility(View.GONE);
		}

		adapter.notifyDataSetInvalidated();
	}

	/**
	 * Creates a header view with the size of the action bar.
	 * 
	 * @return View to be used as header view
	 */
	public View getHeaderView() {
		double actionBarHeight = getActionbarHeight();

		LayoutInflater inflater = getLayoutInflater();
		View headerView = inflater.inflate(R.layout.empty, null);

		View emptyView = headerView.findViewById(R.id.empty_view);
		RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) emptyView
				.getLayoutParams();
		params.height = (int) actionBarHeight;
		emptyView.setLayoutParams(params);

		return headerView;
	}
	
	public double getActionbarHeight() {
		TypedValue tv = new TypedValue();
		if (getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
			return TypedValue.complexToDimensionPixelSize(tv.data,
					getResources().getDisplayMetrics());
		}
		return 0;
	}
	
	public int pixelsToDPI(double sizeInDp){
		float scale = getResources().getDisplayMetrics().density;
		return (int)(sizeInDp*scale + 0.5f);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, final View view,
			int position, long id) {
		TestFlight.passCheckpoint("Switched map by selecting");
		
		Level level = levels.get(position - 1);
		boolean success = false;

		try {
			LevelController.getInstance(this).toggleMode(level);
			success = true; // keep code out of try/catch for performance
		} catch (IOException exc) {
			exc.printStackTrace();

			Toast.makeText(LevelListActivity.this, getString(R.string.error1),
					Toast.LENGTH_SHORT).show();
		}

		if (success) {
			Toast.makeText(LevelListActivity.this, getString(R.string.switched_map),
					Toast.LENGTH_SHORT).show();
			adapter.notifyDataSetChanged();

			SharedPreferences settings = getSharedPreferences(
					getString(R.string.settings_file_name), 0);
			SharedPreferences.Editor editor = settings.edit();
			editor.putString("last_switched_level_file", level
					.getRootDirectory().getAbsolutePath());
			editor.commit();
		}
	}
}

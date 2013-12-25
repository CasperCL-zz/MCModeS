package com.redlake.mcms.activity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.zhuoweizhang.pocketinveditor.Level;
import android.app.Activity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.redlake.mcmodeswitcher.R;
import com.redlake.mcms.adapter.WorldSelectAdapter;
import com.redlake.mcms.contoller.LevelController;

public class LevelListActivity extends Activity implements
		AdapterView.OnItemClickListener {

	private WorldSelectAdapter adapter;
	private List<Level> levels;
	private LevelController levelController;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_level_list);

		levelController = new LevelController(this);
		levels = new ArrayList<Level>();
		
		initListAdapter();
	}

	public void initListAdapter() {
		final ListView listview = (ListView) findViewById(R.id.world_select_listview);

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
			levels.addAll(levelController.getLevels());
		} catch (IOException exc) {
			exc.printStackTrace();
		}
		
		adapter.notifyDataSetInvalidated();
	}

	/**
	 * Creates a header view with the size of the action bar.
	 * 
	 * @return View to be used as header view
	 */
	public View getHeaderView() {
		double actionBarHeight = 0;
		TypedValue tv = new TypedValue();
		if (getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
			actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data,
					getResources().getDisplayMetrics());
		}

		LayoutInflater inflater = getLayoutInflater();
		View headerView = inflater.inflate(R.layout.empty, null);

		View emptyView = headerView.findViewById(R.id.empty_view);
		RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) emptyView
				.getLayoutParams();
		params.height = (int) actionBarHeight;
		emptyView.setLayoutParams(params);

		return headerView;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, final View view,
			int position, long id) {
		Level level = levels.get(position - 1);

		int newMode = -1;
		if (level.getGameType() == 0) {
			newMode = 1;
		} else {
			newMode = 0;
		}
		level.setGameType(newMode);

		try {
			levelController.switchMode(level, newMode);

			Toast.makeText(LevelListActivity.this, "Switched map",
					Toast.LENGTH_SHORT).show();
			adapter.notifyDataSetChanged();
		} catch (IOException exc) {
			exc.printStackTrace();

			Toast.makeText(LevelListActivity.this,
					"Something went wrong. Try again.", Toast.LENGTH_SHORT)
					.show();
		}
	}
}

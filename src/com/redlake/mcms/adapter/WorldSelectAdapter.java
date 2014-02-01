package com.redlake.mcms.adapter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import net.zhuoweizhang.pocketinveditor.Level;

import com.redlake.mcmodeswitcher.R;
import com.redlake.mcms.util.Typefaces;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class WorldSelectAdapter extends ArrayAdapter<Level> {

	private final List<Level> levels;
	private final Context context;
	
	public WorldSelectAdapter(Context context, int resource, List<Level> levels) {
		super(context, resource);
		this.levels = levels;
		this.context = context;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
		        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		    View rowView = inflater.inflate(R.layout.list_item_world, parent, false);
		    Level level = levels.get(position);
		    
		    ImageView modeIV = (ImageView) rowView.findViewById(R.id.list_item_world_imagemode);
		    TextView worldTitleTV = (TextView) rowView.findViewById(R.id.list_item_world_worldname);
		    TextView lastPlayedTV = (TextView) rowView.findViewById(R.id.list_item_world_lastplayed); 
		    
		    if(level.getGameType() == 0)
		    	modeIV.setImageResource(R.drawable.survival);
		    else 
		    	modeIV.setImageResource(R.drawable.pickaxe);
		    
		    worldTitleTV.setText(level.getLevelName());
		    
		    
		    Date lastPlayedDate = new Date(level.getLastPlayed() * 1000);
		    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

		    worldTitleTV.setTypeface(Typefaces.KEEP_CALM_MEDIUM);
		    lastPlayedTV.setTypeface(Typefaces.KEEP_CALM_MEDIUM);
		    
		    lastPlayedTV.setText(context.getString(R.string.last_played) + ": " + formatter.format(lastPlayedDate));
		    
		    
		    return rowView;
	}
	
	@Override
	public int getCount() {
		return levels.size();
	}

}

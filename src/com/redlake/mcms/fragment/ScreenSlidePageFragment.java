package com.redlake.mcms.fragment;

import com.redlake.mcmodeswitcher.R;
import com.redlake.mcms.activity.LevelListActivity;
import com.redlake.mcms.util.Typefaces;

import android.content.Intent;
import android.graphics.Typeface;
import android.opengl.Visibility;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class ScreenSlidePageFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		ViewGroup rootView = (ViewGroup) inflater.inflate(
				R.layout.fragment_screen_slide_page, container, false);

		initView(rootView);
		return rootView;
	}

	public void initView(View view) {
		TextView titleTV = (TextView) view.findViewById(R.id.tutorial_title_tv);
		titleTV.setTypeface(Typefaces.KEEP_CALM_MEDIUM);
		String text = getArguments().getString("text");
		titleTV.setText(text);

		TextView tv = (TextView) view.findViewById(R.id.tutorial_swipeTV);
		tv.setTypeface(Typefaces.KEEP_CALM_MEDIUM);

		Button startButton = (Button) view.findViewById(R.id.tutorial_startBT);

		if (!getArguments().getBoolean("image")) {
			ImageView imageView = (ImageView) view
					.findViewById(R.id.tutorial_imageIV);
			imageView.setVisibility(View.INVISIBLE);
		}
		
		if (getArguments().getBoolean("last")) {
			ImageButton button = (ImageButton) view
					.findViewById(R.id.tutorial_nextBT);
			button.setVisibility(View.INVISIBLE);
			tv.setVisibility(View.INVISIBLE);
			startButton.setTypeface(Typefaces.KEEP_CALM_MEDIUM);
			startButton.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent listActivity = new Intent(getActivity(), LevelListActivity.class);
					getActivity().startActivity(listActivity);
					getActivity().finish();
				}
			});
		} else {
			startButton.setVisibility(View.INVISIBLE);
		}

	}

}

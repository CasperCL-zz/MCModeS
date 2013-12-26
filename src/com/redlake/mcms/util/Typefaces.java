package com.redlake.mcms.util;

import android.content.Context;
import android.graphics.Typeface;

public class Typefaces {

	public static Typeface KEEP_CALM_MEDIUM;
	private static Context context;
	
	public static void setup(Context _context) {
		context = _context;

		KEEP_CALM_MEDIUM = Typeface.createFromAsset(context.getAssets(), "fonts/KeepCalm-Medium.ttf");
	}
}

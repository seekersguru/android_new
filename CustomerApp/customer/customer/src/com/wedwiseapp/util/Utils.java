package com.wedwiseapp.util;

import android.content.Context;
import android.graphics.Typeface;

public class Utils {
	public static Typeface getNormal(Context c) {
		return Typeface.createFromAsset(c.getAssets(), "GothamRnd-Light.otf");
	}

	public static Typeface getBold(Context c) {
		return Typeface.createFromAsset(c.getAssets(),
				"ufonts.com_gotham-book.ttf");
	}
	public static String getMonth(int c) {

		String month[]={"Jan","Feb","Mar","Apr","May","Jun","July","Aug","Sep","Oct","Nov","Dec"};
		
		return month[c-1];
	}
}

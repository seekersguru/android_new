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
}

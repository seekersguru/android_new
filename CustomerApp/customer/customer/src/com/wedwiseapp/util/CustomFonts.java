package com.wedwiseapp.util;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class CustomFonts {

	public static void setFontOfTextView(Context mContext,TextView textView,String fontPath)
	{
		Typeface face= Typeface.createFromAsset(mContext.getAssets(),fontPath);
		textView.setTypeface(face);
	}
	
	public static void setFontOfButton(Context mContext,Button button,String fontPath)
	{
		Typeface face= Typeface.createFromAsset(mContext.getAssets(),fontPath);
		button.setTypeface(face);
	}
	
	public static void setFontOfEditText(Context mContext,EditText editText,String fontPath)
	{
		Typeface face= Typeface.createFromAsset(mContext.getAssets(),fontPath);
		editText.setTypeface(face);
	}
}

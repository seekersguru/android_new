package com.wedwiseapp.views;

import com.wedwiseapp.util.Utils;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * 
 * @author VaViAn Labs.
 * 
 */
public class CTextView extends TextView {

	public CTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public CTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public CTextView(Context context) {
		super(context);
		init();
	}

	public void init() {
		if (!isInEditMode()) {
			try {
				setTypeface(Utils.getNormal(getContext()));
			} catch (Exception e) {
				// TODO: handle exception
			}
			
		}
	}

};
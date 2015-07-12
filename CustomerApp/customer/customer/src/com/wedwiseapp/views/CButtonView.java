package com.wedwiseapp.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * 
 * @author VaViAn Labs.
 * 
 */
public class CButtonView extends Button {

	public CButtonView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public CButtonView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public CButtonView(Context context) {
		super(context);
		init();
	}

	public void init() {
		if (!isInEditMode()) {
			// setTypeface(Utils.getNormal(getContext()));
		}
	}

};
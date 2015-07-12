package com.wedwiseapp.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * 
 * @author VaViAn Labs.
 * 
 */
public class CBButtonView extends Button {

	public CBButtonView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public CBButtonView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public CBButtonView(Context context) {
		super(context);
		init();
	}

	public void init() {
		if (!isInEditMode()) {
			// setTypeface(Utils.getBold(getContext()));
		}
	}

};
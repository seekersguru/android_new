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
public class CBTextView extends TextView {

	public CBTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public CBTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public CBTextView(Context context) {
		super(context);
		init();
	}

	public void init() {
		if (!isInEditMode()) {
			try {
				setTypeface(Utils.getBold(getContext()));
			} catch (Exception e) {
				// TODO: handle exception
			}

		}
	}

};
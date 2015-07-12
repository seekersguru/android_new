package com.wedwiseapp.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * 
 * @author VaViAn Labs.
 * 
 */
public class CBEditText extends EditText {

	public CBEditText(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public CBEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public CBEditText(Context context) {
		super(context);
		init();
	}

	public void init() {
		if (!isInEditMode()) {
			// setTypeface(Utils.getBold(getContext()));
		}
	}

};
package com.wedwiseapp.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.Gravity;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

public class ProgressDialogCustom extends ProgressDialog{

	ImageView iv;

	public ProgressDialogCustom(Context mContext,int resourceIdofImage)
	{
		super(mContext,ProgressDialog.STYLE_SPINNER);
		WindowManager.LayoutParams wimp=getWindow().getAttributes();
		wimp.gravity=Gravity.CENTER_HORIZONTAL;
		getWindow().setAttributes(wimp);
		setTitle(null);
		setCancelable(false);
		LinearLayout layout=new LinearLayout(mContext);
		layout.setOrientation(LinearLayout.VERTICAL);
		LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
		iv=new ImageView(mContext);
		iv.setImageResource(resourceIdofImage);
		layout.addView(iv);
		addContentView(layout, params);
	}

	@Override
	public void show() {
		super.show();
		RotateAnimation anim=new RotateAnimation(0.0f,360.0f,Animation.RELATIVE_TO_SELF,.9f,Animation.RELATIVE_TO_SELF,.9f);
		anim.setInterpolator(new LinearInterpolator());
		anim.setRepeatCount(Animation.INFINITE);
		anim.setDuration(3000);
		iv.setAnimation(anim);
		iv.startAnimation(anim);
	}
}

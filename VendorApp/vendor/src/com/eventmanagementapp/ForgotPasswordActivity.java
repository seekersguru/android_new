package com.eventmanagementapp;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ForgotPasswordActivity extends FragmentActivity{

	Button btnSignIn,btnBack;
	TextView tvToolBar,tvBottomBar;
	Toolbar toolbar;
	EditText etEmailAddress,etPassword;

//	@Override
//	public void onWindowFocusChanged(boolean hasFocus) {
//		super.onWindowFocusChanged(hasFocus);
//		if (hasFocus) {
//			getWindow().getDecorView().setSystemUiVisibility(
//					View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//					| View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//					| View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//					| View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//					| View.SYSTEM_UI_FLAG_FULLSCREEN
//					| View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
//		}
//	}

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
//		setTheme(android.R.style.Theme_Holo_Light_NoActionBar_TranslucentDecor);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.forgotpassword);	
		toolbar=(Toolbar) findViewById(R.id.toolbar);
		btnBack=(Button) toolbar.findViewById(R.id.btnBack);
		tvToolBar=(TextView)toolbar.findViewById(R.id.tvToolBar);
		tvToolBar.setText("Sign In");
		etEmailAddress=(EditText) findViewById(R.id.etEmailAddress);
		etPassword=(EditText) findViewById(R.id.etPassword);
		tvBottomBar=(TextView) findViewById(R.id.tvBottomBar);
		tvBottomBar.setText(Html.fromHtml("By signing up,I agree to terms of<br>services,privacy policies,guest policies,<br>and host guarantee terms.").toString());
		etEmailAddress.setHintTextColor(Color.parseColor("#5C5858"));
		etPassword.setHintTextColor(Color.parseColor("#5C5858"));
		btnSignIn=(Button) findViewById(R.id.btnSignIn);
		btnBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();	
			}
		});
		btnSignIn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

			}
		});
	}
}

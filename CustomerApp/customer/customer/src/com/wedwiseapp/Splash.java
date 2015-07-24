package com.wedwiseapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

import com.facebook.FacebookSdk;
import com.wedwiseapp.login.LoginSignUpActivity;
import com.wedwiseapp.util.PreferenceUtil;

public class Splash extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_splash);
		FacebookSdk.sdkInitialize(this.getApplicationContext());
		PreferenceUtil.init(this);
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		launch(this);
	}

	private void launch(final Context context) {
		Handler handler = new Handler();
		
		boolean isLogin = PreferenceUtil.getInstance().isLogin();
		if(isLogin){
			handler.postDelayed(new Runnable() {
				@Override
				public void run() {
					Intent intent = new Intent(context,
							NavigationDrawerHomeActivity.class);
					startActivity(intent);
					overridePendingTransition(R.anim.right_in, R.anim.left_out);
					finish();
				}
			}, 500);
		}else{
			handler.postDelayed(new Runnable() {
				@Override
				public void run() {
					Intent intent = new Intent(context,
							LoginSignUpActivity.class);
					startActivity(intent);
					overridePendingTransition(R.anim.right_in, R.anim.left_out);
					finish();
				}
			}, 500);
		}
		
		
	}
}

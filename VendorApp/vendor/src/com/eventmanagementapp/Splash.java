package com.eventmanagementapp;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.view.Window;

import com.eventmanagementapp.calendar.CalendarActivity;
import com.eventmanagementapp.util.PreferenceUtil;

public class Splash extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_splash);
		getHashKey();
		PreferenceUtil.init(this);
		
	}
	
	
	private void getHashKey() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.e("MY_KEY_HASH:",
                        Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
        } catch (NoSuchAlgorithmException e) {
        } }

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
		boolean isRegistered = PreferenceUtil.getInstance().isRegistered();
		if (isRegistered == true) {
			handler.postDelayed(new Runnable() {
				@Override
				public void run() {
					startActivity(new Intent(Splash.this,CalendarActivity.class));
					overridePendingTransition(R.anim.right_in, R.anim.left_out);
					finish();
				}
			}, 2000);

		} else {
			handler.postDelayed(new Runnable() {
				@Override
				public void run() {
					Intent myIntent=new Intent(context,LoginSignUpActivity.class);
					startActivity(myIntent);
					overridePendingTransition(R.anim.right_in, R.anim.left_out);
					finish();
				}
			}, 2000);
		}
	}
}

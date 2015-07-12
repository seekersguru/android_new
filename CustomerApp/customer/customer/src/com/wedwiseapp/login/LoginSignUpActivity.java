package com.wedwiseapp.login;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.wedwiseapp.R;
import com.wedwiseapp.util.PreferenceUtil;

//import com.google.android.gms.common.ConnectionResult;
//import com.google.android.gms.common.api.GoogleApiClient;
//import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
//import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
//import com.google.android.gms.plus.Plus;
//import com.google.android.gms.plus.model.people.Person;

@TargetApi(Build.VERSION_CODES.KITKAT)
public class LoginSignUpActivity extends FragmentActivity  /*implements ConnectionCallbacks,
OnConnectionFailedListener*/ {

	//	 Request code used to invoke sign in user interactions. 
	private static final int RC_SIGN_IN = 0;

	//	 Client used to interact with Google APIs. 
	//	private GoogleApiClient mGoogleApiClient;
	private boolean mIntentInProgress;
	//	com.google.android.gms.common.SignInButton signInBtn;

	Button btnFBLogin,btnGoogleLogin,btnSignUp,btnBack,btnLogin;
	//	EditText  etEmailAddress,etPassword;
	TextView tvBottomBar,tvToolBar;
	Toolbar toolbar;
	Context mContext;

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
		//		getWindow().setStatusBarColor(Color.TRANSPARENT);
		setTheme(android.R.style.Theme_Holo_Light_NoActionBar_TranslucentDecor);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		mContext=LoginSignUpActivity.this;
		setContentView(R.layout.loginsignupactivity);
		btnLogin=(Button) findViewById(R.id.btnLogin);
		btnFBLogin=(Button) findViewById(R.id.btnFBLogin);
		btnGoogleLogin=(Button) findViewById(R.id.btnGoogleLogin);
		//		etEmailAddress=(EditText) findViewById(R.id.etEmailAddress);
		//		etPassword=(EditText) findViewById(R.id.etPassword);
		//		etEmailAddress.setHintTextColor(Color.parseColor("#ffffff"));
		//		etPassword.setHintTextColor(Color.parseColor("#ffffff"));
		btnSignUp=(Button) findViewById(R.id.btnSignUp);

//		boolean isRegistered = PreferenceUtil.getInstance().isRegistered();
//		if(isRegistered)
//		{
//			btnSignUp.setVisibility(View.GONE);
//		}
//		else if (!isRegistered)
//		{
//			btnSignUp.setVisibility(View.VISIBLE);
//		}

		tvBottomBar=(TextView) findViewById(R.id.tvBottomBar);
		tvBottomBar.setText(Html.fromHtml("By signing up,I agree to terms of services,privacy policies,guest policies,and host guarantee terms.").toString());
		toolbar=(Toolbar) findViewById(R.id.toolbar);
		btnBack=(Button) toolbar.findViewById(R.id.btnBack);
		tvToolBar=(TextView)toolbar.findViewById(R.id.tvToolBar);
		tvToolBar.setText("Log In or Sign Up");
		btnBack.setVisibility(View.VISIBLE);
		//		CustomFonts.setFontOfButton(mContext, btnSignUp,"fonts/GothamRnd-Light.otf");
		//		CustomFonts.setFontOfButton(mContext, btnLogin,"fonts/GothamRnd-Light.otf");
		//		CustomFonts.setFontOfTextView(mContext, tvToolBar,"fonts/GothamRnd-Light.otf");
		//		CustomFonts.setFontOfTextView(mContext, tvBottomBar,"fonts/GothamRnd-Light.otf");
		btnBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();		
				overridePendingTransition(R.anim.right_in, R.anim.right_out);
			}
		});
		btnSignUp.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent myIntent=new Intent(LoginSignUpActivity.this,RegisterActivity.class);
				myIntent.putExtra("type","registration");
				startActivity(myIntent);	
				overridePendingTransition(R.anim.right_in, R.anim.left_out);
			}
		});
		btnLogin.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				//				startActivity(new Intent(LoginActivity.this,HomeActivity.class));
				//				startActivity(new Intent(LoginSignUpActivity.this,LoginActivity.class));
				Intent myIntent=new Intent(LoginSignUpActivity.this,RegisterActivity.class);
				myIntent.putExtra("type","login");
				startActivity(myIntent);
				overridePendingTransition(R.anim.right_in, R.anim.left_out);
			}
		});

		btnGoogleLogin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				//				Intent myIntent=new Intent(LoginSignUpActivity.this,VendorCategoryHome.class);
				//				startActivity(myIntent);
				//				overridePendingTransition(R.anim.right_in, R.anim.left_out);
			}
		});

		//		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
		//		.permitAll().build();
		//		StrictMode.setThreadPolicy(policy);

		/*		mGoogleApiClient = new GoogleApiClient.Builder(this)
		.addConnectionCallbacks(this)
		.addOnConnectionFailedListener(this)
		.addApi(Plus.API)
		.addScope(Plus.SCOPE_PLUS_LOGIN).build();*/
		//		btnLogin=(Button) findViewById(R.id.btnLogin);
		//		btnFBLogin=(Button) findViewById(R.id.btnFBLogin);
		//		etEmailAddress=(EditText) findViewById(R.id.etEmailAddress);
		//		etPassword=(EditText) findViewById(R.id.etPassword);
		//		signInBtn = (com.google.android.gms.common.SignInButton) findViewById(R.id.btnGoogleLogin);

		//		etEmailAddress.setHintTextColor(Color.parseColor("#ffffff"));
		//		etPassword.setHintTextColor(Color.parseColor("#ffffff"));
		//		tvSignUp=(TextView) findViewById(R.id.tvSignUp);
		//		tvSignUp.setOnClickListener(new OnClickListener() {
		//
		//			@Override
		//			public void onClick(View v) {
		//				startActivity(new Intent(LoginActivity.this,RegistrationActivity.class));				
		//			}
		//		});

		/*signInBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				mGoogleApiClient.connect();
			}
		});*/
	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onStop() {
		super.onStop();
		//		if (mGoogleApiClient.isConnected()) {
		//			mGoogleApiClient.disconnect();
		//		}
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(R.anim.right_in, R.anim.right_out);
	}

	/*@Override
	public void onConnectionFailed(ConnectionResult arg0) {

		if (!mIntentInProgress && arg0.hasResolution()) {
			try {
				mIntentInProgress = true;
				arg0.startResolutionForResult(this, RC_SIGN_IN);
			} catch (SendIntentException e) {
				// The intent was canceled before it was sent. Return to the
				// default
				// state and attempt to connect to get an updated
				// ConnectionResult.
				mIntentInProgress = false;
				mGoogleApiClient.connect();
			}
		}
		// Log.e("error", "error code" + arg0.getResolution());
		Toast.makeText(this, "User is onConnectionFailed!", Toast.LENGTH_LONG)
		.show();
	}

	@Override
	public void onConnected(Bundle arg0) {

		//		Toast.makeText(this, "User is connected!", Toast.LENGTH_LONG).show();
		signInBtn.setVisibility(View.GONE);
		if (Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null) {
			Person person = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);
			Toast.makeText(this, "User is connected!"+ person.getDisplayName(), Toast.LENGTH_LONG).show();
			//			textView.setText("Welcome : " + person.getDisplayName());
			try {
				JSONObject jsonObject = new JSONObject(person.getImage()
						.toString());
				String imageUrl = jsonObject.getString("url");

				try {
					URL url = new URL(imageUrl);
					Bitmap bmp;
					bmp = BitmapFactory.decodeStream(url.openConnection()
							.getInputStream());
					//					profileImage.setImageBitmap(bmp);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	@Override
	public void onConnectionSuspended(int arg0) {
		Toast.makeText(this, "User is onConnectionSuspended!",
				Toast.LENGTH_LONG).show();
	}
	@Override
	protected void onActivityResult(int requestCode, int responseCode,
			Intent intent) {
		if (requestCode == RC_SIGN_IN) {
			mIntentInProgress = false;
			if (!mGoogleApiClient.isConnecting()) {
				mGoogleApiClient.connect();
			}
		}
	}*/
}

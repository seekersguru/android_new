/*package com.eventmanagementapp;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.eventmanagementapp.util.CustomFonts;
import com.eventmanagementapp.util.SystemBarTintManager;

public class LoginActivity extends FragmentActivity implements TextWatcher{

	Button btnSignIn,btnBack;
	TextView tvToolBar,tvForgotPassword,tvLogin;//,tvBottomBar;
	Toolbar toolbar;
	EditText etEmailAddress,etPassword,etBrideName,etGroomName,etArea;
	Context mContext;
//	LinearLayout llSignupFields;

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
		setContentView(R.layout.loginactivity);	
		mContext=LoginActivity.this;
		// create our manager instance after the content view is set
		SystemBarTintManager tintManager = new SystemBarTintManager(this);
		// enable status bar tint
		tintManager.setStatusBarTintEnabled(true);
		// enable navigation bar tint
		tintManager.setNavigationBarTintEnabled(true);
		tintManager.setStatusBarAlpha(0.0f);
		tintManager.setNavigationBarAlpha(0.0f);
		toolbar=(Toolbar) findViewById(R.id.toolbar);
		btnBack=(Button) toolbar.findViewById(R.id.btnBack);
		tvToolBar=(TextView)toolbar.findViewById(R.id.tvToolBar);
//		llSignupFields=(LinearLayout) findViewById(R.id.llSignupFields);
		etEmailAddress=(EditText) findViewById(R.id.etEmailAddress);
		etPassword=(EditText) findViewById(R.id.etPassword);
		etBrideName=(EditText) findViewById(R.id.etBrideName);
		etGroomName=(EditText) findViewById(R.id.etGroomName);
		etArea=(EditText) findViewById(R.id.etGroomName);
		//		tvBottomBar=(TextView) findViewById(R.id.tvBottomBar);
		//		tvBottomBar.setText(Html.fromHtml("By signing up,I agree to terms of<br>services,privacy policies,guest policies,<br>and host guarantee terms.").toString());
		etEmailAddress.setHintTextColor(Color.parseColor("#5C5858"));
		etPassword.setHintTextColor(Color.parseColor("#5C5858"));
		etBrideName.setHintTextColor(Color.parseColor("#5C5858"));
		etGroomName.setHintTextColor(Color.parseColor("#5C5858"));
		btnSignIn=(Button) findViewById(R.id.btnSignIn);
		tvForgotPassword=(TextView) findViewById(R.id.tvForgotPassword);
//		llSignupFields.setVisibility(View.GONE);
		tvForgotPassword.setVisibility(View.VISIBLE);
		tvLogin=(TextView) findViewById(R.id.tvLogin);
		tvLogin.setText("Sign up");
		tvToolBar.setText("Log In with Email");
		btnSignIn.setText("Log In");
		etEmailAddress.addTextChangedListener(this);
		etPassword.addTextChangedListener(this);
		etBrideName.addTextChangedListener(this);
		etGroomName.addTextChangedListener(this);
		btnSignIn.setBackgroundColor(Color.parseColor("#F9B9BA"));
		btnSignIn.setEnabled(false);
		CustomFonts.setFontOfEditText(mContext, etEmailAddress,"fonts/GothamRnd-Book_0.otf");
		CustomFonts.setFontOfEditText(mContext, etPassword,"fonts/GothamRnd-Book_0.otf");
		CustomFonts.setFontOfEditText(mContext, etBrideName,"fonts/GothamRnd-Book_0.otf");
		CustomFonts.setFontOfEditText(mContext, etGroomName,"fonts/GothamRnd-Book_0.otf");
		//		CustomFonts.setFontOfTextView(mContext, tvToolBar,"fonts/GothamRnd-Light.otf");
		CustomFonts.setFontOfButton(mContext,btnSignIn,"fonts/GothamRnd-Book_0.otf");
		CustomFonts.setFontOfTextView(mContext, tvToolBar,"fonts/GothamRnd-Book_0.otf");
		CustomFonts.setFontOfTextView(mContext, tvForgotPassword,"fonts/GothamRnd-Book_0.otf");
		CustomFonts.setFontOfTextView(mContext, tvLogin,"fonts/GothamRnd-Book_0.otf");
		btnBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();	
				overridePendingTransition(R.anim.right_in, R.anim.right_out);
			}
		});
		btnSignIn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(btnSignIn.getText().toString().equalsIgnoreCase("Log In"))
				{

				}
				else if(btnSignIn.getText().toString().equalsIgnoreCase("Sign In"))
				{

				}
				//				startActivity(new Intent(LoginActivity.this,CalendarActivity.class));
				//				overridePendingTransition(R.anim.right_in, R.anim.left_out);
			}
		});
		tvForgotPassword.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

			}
		});
		tvLogin.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(tvLogin.getText().toString().equalsIgnoreCase("Login"))
				{//In Case Of Login Screen
					tvForgotPassword.setVisibility(View.VISIBLE);
					tvLogin.setText("Sign Up");
					tvToolBar.setText("Log In with Email");
//					llSignupFields.setVisibility(View.GONE);
					btnSignIn.setText("Log In");
					btnSignIn.setEnabled(false);
					etEmailAddress.setText("");
					etPassword.setText("");
					btnSignIn.setBackgroundColor(Color.parseColor("#F9B9BA"));
					etEmailAddress.requestFocus();
				}
				else if(tvLogin.getText().toString().equalsIgnoreCase("Sign Up")){
					//In Case Of Sign Up Screen
					tvForgotPassword.setVisibility(View.GONE);
					tvLogin.setText("Login");
					tvToolBar.setText("Sign Up with Email");
//					llSignupFields.setVisibility(View.VISIBLE);
					btnSignIn.setText("Sign Up");
					btnSignIn.setEnabled(false);
					etEmailAddress.setText("");
					etPassword.setText("");
					etBrideName.setText("");
					etGroomName.setText("");
					btnSignIn.setBackgroundColor(Color.parseColor("#F9B9BA"));
					etEmailAddress.requestFocus();
				}
			}
		});
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count, int after) {

	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {

	}

	@Override
	public void afterTextChanged(Editable s) {
		if(btnSignIn.getText().toString().equalsIgnoreCase("Log In"))
		{
			if(etEmailAddress.getText().toString().trim().equals("") || etPassword.getText().toString().trim().equals(""))
			{
				btnSignIn.setEnabled(false);
				btnSignIn.setBackgroundColor(Color.parseColor("#F9B9BA"));
			}
			else if(!etEmailAddress.getText().toString().trim().equals("") && !etPassword.getText().toString().trim().equals(""))
			{
				btnSignIn.setEnabled(true);
				btnSignIn.setBackgroundColor(Color.parseColor("#E4484B"));
			}
		}
		else if(btnSignIn.getText().toString().equalsIgnoreCase("Sign Up"))
		{
			if(etEmailAddress.getText().toString().trim().equals("") || etPassword.getText().toString().trim().equals("") || etBrideName.getText().toString().trim().equals("") || etGroomName.getText().toString().trim().equals(""))
			{
				btnSignIn.setEnabled(false);
				btnSignIn.setBackgroundColor(Color.parseColor("#F9B9BA"));
			}
			else if(!etEmailAddress.getText().toString().trim().equals("") && !etPassword.getText().toString().trim().equals("") && !etBrideName.getText().toString().trim().equals("") && !etGroomName.getText().toString().trim().equals(""))
			{
				btnSignIn.setEnabled(true);
				btnSignIn.setBackgroundColor(Color.parseColor("#E4484B"));
			}
		}
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(R.anim.right_in, R.anim.right_out);
	}
}
*/
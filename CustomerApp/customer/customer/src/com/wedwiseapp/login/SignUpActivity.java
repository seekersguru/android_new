/*package com.wedwiseapp.login;

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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wedwiseapp.R;
import com.wedwiseapp.util.CustomFonts;
import com.wedwiseapp.util.SystemBarTintManager;

public class SignUpActivity extends FragmentActivity implements TextWatcher{

	EditText etEmailAddress,etPassword,etBrideName,etGroomName,etArea,etPasswordReset;
	Button btnSignIn,btnBack,btnPasswordReset;
	TextView tvToolBar,tvForgotPassword,tvLogin;//,tvBottomBar;
	Toolbar toolbar;
	Context mContext;
	LinearLayout llFields,llForgotpassword;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.registration);
		mContext=SignUpActivity.this;
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
		llFields=(LinearLayout) findViewById(R.id.llFields);
		llForgotpassword=(LinearLayout) findViewById(R.id.llForgotpassword);
		etEmailAddress=(EditText) findViewById(R.id.etEmailAddress);
		etPassword=(EditText) findViewById(R.id.etPassword);
		etBrideName=(EditText) findViewById(R.id.etBrideName);
		etGroomName=(EditText) findViewById(R.id.etGroomName);
		etArea=(EditText) findViewById(R.id.etArea);
		tvForgotPassword=(TextView) findViewById(R.id.tvForgotPassword);
		tvLogin=(TextView) findViewById(R.id.tvLogin);
		//		tvBottomBar=(TextView) findViewById(R.id.tvBottomBar);
		//		tvBottomBar.setText(Html.fromHtml("By signing up,I agree to terms of<br>services,privacy policies,guest policies,<br>and host guarantee terms.").toString());
		etEmailAddress.setHintTextColor(Color.parseColor("#5C5858"));
		etPassword.setHintTextColor(Color.parseColor("#5C5858"));
		etBrideName.setHintTextColor(Color.parseColor("#5C5858"));
		etGroomName.setHintTextColor(Color.parseColor("#5C5858"));
		etArea.setHintTextColor(Color.parseColor("#5C5858"));
		etPasswordReset=(EditText) findViewById(R.id.etPasswordReset);
		etPasswordReset.setHintTextColor(Color.parseColor("#5C5858"));
		btnSignIn=(Button) findViewById(R.id.btnSignIn);
		btnPasswordReset=(Button) findViewById(R.id.btnPasswordReset);

		if(getIntent().getExtras().getString("type").equals("registration"))
		{
			//In case Of registration Screen
			btnSignIn.setText("Sign Up");
			tvToolBar.setText("Sign Up with Email");
			tvForgotPassword.setVisibility(View.GONE);
			tvLogin.setText("Login");
			llFields.setVisibility(View.VISIBLE);
			llForgotpassword.setVisibility(View.GONE);
			etEmailAddress.setVisibility(View.VISIBLE);
			etPassword.setVisibility(View.VISIBLE);
			etBrideName.setVisibility(View.VISIBLE);
			etGroomName.setVisibility(View.VISIBLE);
			etArea.setVisibility(View.VISIBLE);
		}
		else if(getIntent().getExtras().getString("type").equals("login"))
		{ 
			//In case Of login Screen
			btnSignIn.setText("Log In");
			tvForgotPassword.setVisibility(View.VISIBLE);
			tvToolBar.setText("Log In with Email");
			tvForgotPassword.setText("Forgot Password?");
			tvLogin.setText("Sign Up");
			llFields.setVisibility(View.VISIBLE);
			llForgotpassword.setVisibility(View.GONE);
			etEmailAddress.setVisibility(View.VISIBLE);
			etPassword.setVisibility(View.VISIBLE);
			etBrideName.setVisibility(View.GONE);
			etGroomName.setVisibility(View.GONE);
			etArea.setVisibility(View.GONE);
			//			etBrideName.setVisibility(View.VISIBLE);
			//			etGroomName.setVisibility(View.VISIBLE);
			//			etArea.setVisibility(View.VISIBLE);
		}
		CustomFonts.setFontOfEditText(mContext, etEmailAddress,"fonts/GothamRnd-Light.otf");
		CustomFonts.setFontOfEditText(mContext, etPassword,"fonts/GothamRnd-Light.otf");
		CustomFonts.setFontOfEditText(mContext, etBrideName,"fonts/GothamRnd-Light.otf");
		CustomFonts.setFontOfEditText(mContext, etGroomName,"fonts/GothamRnd-Light.otf");
		CustomFonts.setFontOfEditText(mContext, etArea,"fonts/GothamRnd-Light.otf");
		CustomFonts.setFontOfEditText(mContext, etPasswordReset,"fonts/GothamRnd-Light.otf");
		CustomFonts.setFontOfButton(mContext,btnSignIn,"fonts/GothamRnd-Light.otf");
		CustomFonts.setFontOfButton(mContext,btnPasswordReset,"fonts/GothamRnd-Light.otf");
		CustomFonts.setFontOfTextView(mContext,tvLogin,"fonts/GothamRnd-Light.otf");
		CustomFonts.setFontOfTextView(mContext,tvForgotPassword,"fonts/GothamRnd-Light.otf");
		etPassword.addTextChangedListener(this);
		etBrideName.addTextChangedListener(this);
		etGroomName.addTextChangedListener(this);
		etArea.addTextChangedListener(this);
		etPasswordReset.addTextChangedListener(this);
		btnSignIn.setBackgroundColor(Color.parseColor("#F9B9BA"));
		btnSignIn.setEnabled(false);
		btnBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(llFields.getVisibility()==View.VISIBLE)
				{
					finish();	
					overridePendingTransition(R.anim.right_in, R.anim.right_out);
				}
				else if(llForgotpassword.getVisibility()==View.VISIBLE)
				{
					llForgotpassword.setVisibility(View.GONE);
					llFields.setVisibility(View.VISIBLE);
					btnSignIn.setVisibility(View.VISIBLE);
					tvToolBar.setText("Log In with Email");
				}
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
//				startActivity(new Intent(SignUpActivity.this, CategoryActivity.class));
				overridePendingTransition(R.anim.right_in, R.anim.left_out);
			}
		});
		tvForgotPassword.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				btnPasswordReset.setEnabled(false);
				btnPasswordReset.setBackgroundColor(Color.parseColor("#F9B9BA"));
				llFields.setVisibility(View.GONE);
				llForgotpassword.setVisibility(View.VISIBLE);
				btnSignIn.setVisibility(View.GONE);
				tvToolBar.setText("Reset your password");
			}
		});
		tvLogin.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(tvLogin.getText().toString().equalsIgnoreCase("Login"))
				{//In Case Of Login Screen
					tvForgotPassword.setVisibility(View.VISIBLE);
					tvForgotPassword.setText("Forgot Password?");
					tvLogin.setText("Sign Up");
					tvToolBar.setText("Log In with Email");
					//					llSignupFields.setVisibility(View.GONE);
					btnSignIn.setText("Log In");
					btnSignIn.setEnabled(false);
					etEmailAddress.setText("");
					etPassword.setText("");
					etBrideName.setText("");
					etGroomName.setText("");
					etArea.setText("");

					etEmailAddress.setVisibility(View.VISIBLE);
					etPassword.setVisibility(View.VISIBLE);
					etBrideName.setVisibility(View.GONE);
					etGroomName.setVisibility(View.GONE);
					etArea.setVisibility(View.GONE);
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
					etEmailAddress.setVisibility(View.VISIBLE);
					etPassword.setVisibility(View.VISIBLE);
					etBrideName.setVisibility(View.VISIBLE);
					etGroomName.setVisibility(View.VISIBLE);
					etArea.setVisibility(View.VISIBLE);
					etEmailAddress.setText("");
					etPassword.setText("");
					etBrideName.setText("");
					etGroomName.setText("");
					etArea.setText("");
					btnSignIn.setBackgroundColor(Color.parseColor("#F9B9BA"));
					etEmailAddress.requestFocus();
				}
			}
		});
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		if(llFields.getVisibility()==View.VISIBLE)
		{
			finish();	
			overridePendingTransition(R.anim.right_in, R.anim.right_out);
		}
		else if(llForgotpassword.getVisibility()==View.VISIBLE)
		{
			llForgotpassword.setVisibility(View.GONE);
			llFields.setVisibility(View.VISIBLE);
			btnSignIn.setVisibility(View.VISIBLE);
		}
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
	}

	@Override
	public void afterTextChanged(Editable s) {
		if(llFields.getVisibility()==View.VISIBLE)
		{

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
				if(etEmailAddress.getText().toString().trim().equals("") || etPassword.getText().toString().trim().equals("") || etBrideName.getText().toString().trim().equals("") || etGroomName.getText().toString().trim().equals("") || etArea.getText().toString().trim().equals(""))
				{
					btnSignIn.setEnabled(false);
					btnSignIn.setBackgroundColor(Color.parseColor("#F9B9BA"));
				}
				else if(!etEmailAddress.getText().toString().trim().equals("") && !etPassword.getText().toString().trim().equals("") && !etBrideName.getText().toString().trim().equals("") && !etGroomName.getText().toString().trim().equals("") && !etArea.getText().toString().trim().equals(""))
				{
					btnSignIn.setEnabled(true);
					btnSignIn.setBackgroundColor(Color.parseColor("#E4484B"));
				}
			}
		}
		else if(llForgotpassword.getVisibility()==View.VISIBLE)
		{
			if(etPasswordReset.getText().toString().trim().equals(""))
			{
				btnSignIn.setEnabled(false);
				btnSignIn.setBackgroundColor(Color.parseColor("#F9B9BA"));
			}
			else if(!etPasswordReset.getText().toString().trim().equals("")){
				btnSignIn.setEnabled(true);
				btnSignIn.setBackgroundColor(Color.parseColor("#E4484B"));
			}
		}
	}
}
*/
package com.eventmanagementapp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.json.JSONObject;

import com.eventmanagementapp.calendar.CalendarActivity;
import com.eventmanagementapp.common.GlobalCommonMethods;
import com.eventmanagementapp.common.GlobalCommonValues;
import com.eventmanagementapp.dialogs.ErrorDialog;
import com.eventmanagementapp.interfaces.IAction;
import com.eventmanagementapp.util.CustomFonts;
import com.eventmanagementapp.util.PreferenceUtil;
import com.eventmanagementapp.util.ShowDialog;
import com.eventmanagementapp.util.SystemBarTintManager;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;


public class RegistrationSignUpActivity extends FragmentActivity implements
TextWatcher{

	EditText etEmailAddress,etPassword,etName,etNumber,etAddress,etPasswordReset;
	Button btnSignIn,btnBack,btnPasswordReset;
	TextView tvToolBar,tvForgotPassword,tvLogin;//,tvBottomBar;
	Toolbar toolbar;
	Context mContext;
	LinearLayout llFields,llForgotpassword;
	Spinner spVendorType;
	String vendorType="Banquets",emailUser,passwordUser,name,mobileNumber,address;
	boolean isRecentRegistered=false;;
	String response="",url="";
	ProgressDialog progress;
	String emailLogin,passwordLogin;

	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		//		setTheme(android.R.style.Theme_Holo_Light_NoActionBar_TranslucentDecor);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.registration);
		mContext=RegistrationSignUpActivity.this;

		// create our manager instance after the content view is set
		SystemBarTintManager tintManager = new SystemBarTintManager(this);
		// enable status bar tint
		tintManager.setStatusBarTintEnabled(true);
		// enable navigation bar tint
		tintManager.setNavigationBarTintEnabled(true);
		tintManager.setStatusBarAlpha(0.0f);
		tintManager.setNavigationBarAlpha(0.0f);
		toolbar=(Toolbar) findViewById(R.id.toolbar);
		toolbar.findViewById(R.id.refresh_button).setVisibility(View.GONE);
		btnBack=(Button) toolbar.findViewById(R.id.btnBack);
		tvToolBar=(TextView)toolbar.findViewById(R.id.tvToolBar);
		llFields=(LinearLayout) findViewById(R.id.llFields);
		llForgotpassword=(LinearLayout) findViewById(R.id.llForgotpassword);
		etEmailAddress=(EditText) findViewById(R.id.etEmailAddress);
		etPassword=(EditText) findViewById(R.id.etPassword);
		etName=(EditText) findViewById(R.id.etName);
		etNumber=(EditText) findViewById(R.id.etNumber);
		etAddress=(EditText) findViewById(R.id.etAddress);
		tvForgotPassword=(TextView) findViewById(R.id.tvForgotPassword);
		tvLogin=(TextView) findViewById(R.id.tvLogin);
		spVendorType=(Spinner) findViewById(R.id.spVendorType);
		ArrayList<String> spinnerArray = new ArrayList<String>();
		tvToolBar.setVisibility(View.GONE);
		spinnerArray.add("Banquets");
		spinnerArray.add("Caterers");
		spinnerArray.add("Photographers");
		spinnerArray.add("Others");

		@SuppressWarnings("rawtypes")
		ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(this,
				android.R.layout.simple_spinner_dropdown_item,
				spinnerArray);
		spVendorType.setAdapter(spinnerArrayAdapter);
		spVendorType.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				vendorType=String.valueOf(spVendorType.getItemAtPosition(position));				
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});

		//		tvBottomBar=(TextView) findViewById(R.id.tvBottomBar);
		//		tvBottomBar.setText(Html.fromHtml("By signing up,I agree to terms of<br>services,privacy policies,guest policies,<br>and host guarantee terms.").toString());
		etEmailAddress.setHintTextColor(Color.parseColor("#5C5858"));
		etPassword.setHintTextColor(Color.parseColor("#5C5858"));
		etName.setHintTextColor(Color.parseColor("#5C5858"));
		etNumber.setHintTextColor(Color.parseColor("#5C5858"));
		etAddress.setHintTextColor(Color.parseColor("#5C5858"));
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
			etName.setVisibility(View.VISIBLE);
			etNumber.setVisibility(View.VISIBLE);
			etAddress.setVisibility(View.VISIBLE);
			spVendorType.setVisibility(View.VISIBLE);

			if(!TextUtils.isEmpty(getIntent().getStringExtra("userEmail"))){
				etEmailAddress.setText(getIntent().getStringExtra("userEmail").trim());
			}

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
			etName.setVisibility(View.GONE);
			etNumber.setVisibility(View.GONE);
			etAddress.setVisibility(View.GONE);
			spVendorType.setVisibility(View.GONE);
			//			etName.setVisibility(View.VISIBLE);
			//			etNumber.setVisibility(View.VISIBLE);
			//			etAddress.setVisibility(View.VISIBLE);
		}

		CustomFonts.setFontOfEditText(mContext, etEmailAddress,"fonts/GothamRoundedBook.ttf");
		CustomFonts.setFontOfEditText(mContext, etPassword,"fonts/GothamRoundedBook.ttf");
		CustomFonts.setFontOfEditText(mContext, etName,"fonts/GothamRoundedBook.ttf");
		CustomFonts.setFontOfEditText(mContext, etNumber,"fonts/GothamRoundedBook.ttf");
		CustomFonts.setFontOfEditText(mContext, etAddress,"fonts/GothamRoundedBook.ttf");
		CustomFonts.setFontOfEditText(mContext, etPasswordReset,"fonts/GothamRoundedBook.ttf");
		CustomFonts.setFontOfButton(mContext,btnSignIn,"fonts/GothamRoundedBook.ttf");
		CustomFonts.setFontOfButton(mContext,btnPasswordReset,"fonts/GothamRoundedBook.ttf");
		CustomFonts.setFontOfTextView(mContext,tvLogin,"fonts/GothamRoundedBook.ttf");
		CustomFonts.setFontOfTextView(mContext,tvForgotPassword,"fonts/GothamRoundedBook.ttf");
		CustomFonts.setFontOfTextView(mContext,tvToolBar,"fonts/GothamRoundedBook.ttf");
		etEmailAddress.addTextChangedListener(this);
		etPassword.addTextChangedListener(this);
		etName.addTextChangedListener(this);
		etNumber.addTextChangedListener(this);
		etAddress.addTextChangedListener(this);
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
					isRecentRegistered=false;
					checkInternetConnection("login");
				}
				else if(btnSignIn.getText().toString().equalsIgnoreCase("Sign Up"))
				{
					isRecentRegistered=false;
					checkInternetConnection("registration");
				}
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
					spVendorType.setVisibility(View.GONE);
					tvForgotPassword.setText("Forgot Password?");
					tvLogin.setText("Sign Up");
					tvToolBar.setText("Log In with Email");
					//					llSignupFields.setVisibility(View.GONE);
					btnSignIn.setText("Log In");
					btnSignIn.setEnabled(false);
					etEmailAddress.setText("");
					etPassword.setText("");
					etName.setText("");
					etNumber.setText("");
					etAddress.setText("");

					etEmailAddress.setVisibility(View.VISIBLE);
					etPassword.setVisibility(View.VISIBLE);
					etName.setVisibility(View.GONE);
					etNumber.setVisibility(View.GONE);
					etAddress.setVisibility(View.GONE);
					btnSignIn.setBackgroundColor(Color.parseColor("#F9B9BA"));
					etEmailAddress.requestFocus();
				}
				else if(tvLogin.getText().toString().equalsIgnoreCase("Sign Up")){
					//In Case Of Sign Up Screen
					tvForgotPassword.setVisibility(View.GONE);
					spVendorType.setVisibility(View.VISIBLE);
					tvLogin.setText("Login");
					tvToolBar.setText("Sign Up with Email");
					//					llSignupFields.setVisibility(View.VISIBLE);
					btnSignIn.setText("Sign Up");
					btnSignIn.setEnabled(false);
					etEmailAddress.setVisibility(View.VISIBLE);
					etPassword.setVisibility(View.VISIBLE);
					etName.setVisibility(View.VISIBLE);
					etNumber.setVisibility(View.VISIBLE);
					etAddress.setVisibility(View.VISIBLE);
					etEmailAddress.setText("");
					etPassword.setText("");
					etName.setText("");
					etNumber.setText("");
					etAddress.setText("");
					btnSignIn.setBackgroundColor(Color.parseColor("#F9B9BA"));
					etEmailAddress.requestFocus();
				}
			}
		});
	}

	private void checkInternetConnection(String serviceType)
	{
		if(GlobalCommonMethods.isNetworkAvailable(mContext))
		{
			if(serviceType.equalsIgnoreCase("registration"))
			{
				url=GlobalCommonValues.USERREGISTRATION;
				new HttpAsyncTask().execute(url);
			}
			if(serviceType.equalsIgnoreCase("login"))
			{
				url=GlobalCommonValues.LOGIN;
				new HttpAsyncTask().execute(url);
			}
		}
		else{
			ShowDialog.displayDialog(mContext,"Connection error:","No Internet Connection");
		}
	}

	private class HttpAsyncTask extends AsyncTask<String, Void, Void> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			if(progress==null)
			{
				progress=new ProgressDialog(mContext);
				progress.show();		
			}
		}

		@Override
		protected Void doInBackground(String... params) {
			try {
				// Calling method for setting to be sent to the server
				SetData();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
		// onPostExecute displays the results of the AsyncTask.
		@SuppressLint("DefaultLocale")
		@Override
		protected void onPostExecute(Void result) {
			//			Toast.makeText(getBaseContext(), "Data Sent!"+response, Toast.LENGTH_LONG).show();
			if(progress!=null && progress.isShowing())
			{
				progress.dismiss();
				progress=null;
			}

			if(!TextUtils.isEmpty(response) && GlobalCommonMethods.isJSONValid(response))
			{
				if(url.equals(GlobalCommonValues.USERREGISTRATION))
				{
					try {
						String identifier="";
						JSONObject jsonObj = new JSONObject(response);
						//JSONObject jsonMainNode = jsonObj.getJSONObject("request_data");
						JSONObject request_data = jsonObj.getJSONObject("request_data");
						String contact_number = request_data.getString("contact_number");
						String password = request_data.getString("password");
						String name = request_data.getString("name");
						String email = request_data.getString("email");
						String address = request_data.getString("address");
						String _result = jsonObj.getString("result");
						String message = jsonObj.getString("message");//{"identifier":"aattyy@aa.com:cKrNpFhEm4DogRm1a8E6Lhc9YBg"}
						if(!jsonObj.getString("json").equals("0"))
							identifier =new JSONObject( jsonObj.getString("json")).getString("identifier");
						PreferenceUtil.getInstance().setEmail(email);
						PreferenceUtil.getInstance().setIdentifier(identifier);
						if(message.equals("0"))
							message="Registered Successfully";
						if(!message.toLowerCase().equalsIgnoreCase("registered successfully"))
						{
							isRecentRegistered=false;
							ErrorDialog dialog=new ErrorDialog();
							dialog.newInstance(mContext, _result.toUpperCase(), message, iActionObj);
							dialog.setCancelable(false);
							dialog.show(getFragmentManager(), "test");
						}
						else if(message.toLowerCase().equalsIgnoreCase("registered successfully"))
						{
							PreferenceUtil.getInstance().setRegister(true);
							String vendor_type = request_data.getString("vendor_type");
							PreferenceUtil.getInstance().setVendorType(vendor_type);
							isRecentRegistered=true;
							emailLogin=email;
							passwordLogin=password;
							checkInternetConnection("login");
						}
					} catch (Exception e) {
						e.getMessage();
					}
				}
				else if(url.equals(GlobalCommonValues.LOGIN))
				{
					try {
						JSONObject jsonObj = new JSONObject(response);
						String _result = jsonObj.getString("result");
						String message = jsonObj.getString("message");
						if(message.equals("0"))
						{
							message="Logged In Successfully";
						}
						if(!message.toLowerCase().contains("logged in successfully"))
						{
							ErrorDialog dialog=new ErrorDialog();
							dialog.newInstance(mContext, _result.toUpperCase(), message, iActionObj);
							dialog.setCancelable(false);
							dialog.show(getFragmentManager(), "test");
						}
						else if(message.toLowerCase().contains("logged in successfully"))
						{
							PreferenceUtil.getInstance().setLogin(true);
							PreferenceUtil.getInstance().setIdentifier(jsonObj.getJSONObject("json").getString("identifier"));
							iActionObj.setAction("navigate");
						}
					} catch (Exception e) {
						e.getMessage();
					}
				}
			}
		}
	}

	IAction iActionObj = new IAction() {

		@Override
		public void setAction(String action) {
			if(action.equals("dismiss"))
			{
			}
			else if(action.equals("navigate"))
			{
				PreferenceUtil.getInstance().setRegister(true);
				Intent intent = new Intent(mContext, CalendarActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);
				LoginSignUpActivity.isLoggedIn=true;
				overridePendingTransition(R.anim.right_in, R.anim.left_out);
				finish();
			}
		}
	};

	// Create GetData Metod
	public  void  SetData()  throws  UnsupportedEncodingException
	{
		// Create data variable for sent values to server  
		String data="";
		if(url.equals(GlobalCommonValues.USERREGISTRATION))
		{
			emailUser=etEmailAddress.getText().toString();
			passwordUser=etPassword.getText().toString();
			mobileNumber=etNumber.getText().toString();
			name=etName.getText().toString();
			address=etEmailAddress.getText().toString();

			data= URLEncoder.encode("email", "UTF-8") 
					+ "=" + URLEncoder.encode(emailUser, "UTF-8"); 

			data += "&" + URLEncoder.encode("password", "UTF-8") + "="
					+ URLEncoder.encode(passwordUser, "UTF-8"); 

			data += "&" + URLEncoder.encode("vendor_type", "UTF-8") + "="
					+ URLEncoder.encode(vendorType, "UTF-8"); 

			data += "&" + URLEncoder.encode("name", "UTF-8") 
			+ "=" + URLEncoder.encode(name, "UTF-8");

			data += "&" + URLEncoder.encode("contact_number", "UTF-8") 
			+ "=" + URLEncoder.encode(mobileNumber, "UTF-8");

			data += "&" + URLEncoder.encode("address", "UTF-8") 
			+ "=" + URLEncoder.encode(address, "UTF-8");
		}
		else if(url.equals(GlobalCommonValues.LOGIN)){
			if(!isRecentRegistered)
			{
				emailUser=etEmailAddress.getText().toString();
				passwordUser=etPassword.getText().toString();

				data = URLEncoder.encode("email", "UTF-8") 
						+ "=" + URLEncoder.encode(emailUser, "UTF-8"); 

				data += "&" + URLEncoder.encode("password", "UTF-8") + "="
						+ URLEncoder.encode(passwordUser, "UTF-8"); 
			}
			else if(isRecentRegistered)
			{
				data = URLEncoder.encode("email", "UTF-8") 
						+ "=" + URLEncoder.encode(emailLogin, "UTF-8"); 

				data += "&" + URLEncoder.encode("password", "UTF-8") + "="
						+ URLEncoder.encode(passwordLogin, "UTF-8");
			}
		}
		BufferedReader reader=null;

		// Send data 
		try
		{ 
			URL _url=null;
			// Defined URL  where to send data
			if(url.equals(GlobalCommonValues.USERREGISTRATION))
			{
				_url= new URL(GlobalCommonValues.USERREGISTRATION);
			}
			else if(url.equals(GlobalCommonValues.LOGIN))
			{
				_url= new URL(GlobalCommonValues.LOGIN);
			}
			// Send POST data request

			URLConnection conn = _url.openConnection(); 
			conn.setDoOutput(true); 
			OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream()); 
			wr.write( data ); 
			wr.flush(); 

			// Get the server response 
			reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			StringBuilder sb = new StringBuilder();
			String line = null;

			// Read Server Response
			while((line = reader.readLine()) != null)
			{
				// Append server response in string
				sb.append(line + "\n");
			}
			response = sb.toString();
		}
		catch(Exception ex)
		{
		}
		finally
		{
			try
			{
				reader.close();
			}
			catch(Exception ex) {}
		}
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
				if(etEmailAddress.getText().toString().trim().equals("") || etPassword.getText().toString().trim().equals("") || etName.getText().toString().trim().equals("") || etNumber.getText().toString().trim().equals("") || etAddress.getText().toString().trim().equals(""))
				{
					btnSignIn.setEnabled(false);
					btnSignIn.setBackgroundColor(Color.parseColor("#F9B9BA"));
				}
				else if(!etEmailAddress.getText().toString().trim().equals("") && !etPassword.getText().toString().trim().equals("") && !etName.getText().toString().trim().equals("") && !etNumber.getText().toString().trim().equals("") && !etAddress.getText().toString().trim().equals(""))
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

package com.wedwiseapp.login;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONArray;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.wedwise.adapter.ViewPagerAdapter;
import com.wedwise.bean.UserRegistrationBean;
import com.wedwise.common.GlobalCommonMethods;
import com.wedwise.common.GlobalCommonValues;
import com.wedwise.dialogs.ErrorDialog;
import com.wedwise.interfaces.IAction;
import com.wedwiseapp.NavigationDrawerHomeActivity;
import com.wedwiseapp.R;
import com.wedwiseapp.util.PreferenceUtil;
import com.wedwiseapp.util.ShowDialog;

public class RegisterActivity extends FragmentActivity implements
TextWatcher{

	EditText etEmailAddress,etPassword,etBrideName,etGroomName,etArea,etPasswordReset,etContactNumber;
	Button btnSignIn,btnBack,btnPasswordReset;
	TextView tvToolBar,tvForgotPassword,tvLogin;//,tvBottomBar;
	Toolbar toolbar;
	Context mContext;
	LinearLayout llFields,llForgotpassword;
	Gson gson;
	ProgressDialog progress;
	UserRegistrationBean objUserRegistration;
	String response="",responseImages="";
	ArrayList<HashMap<String, String>> listData=new ArrayList<HashMap<String,String>>();
	String url="";
	boolean isRecentRegistered=false;
	String emailLogin,passwordLogin;
	ViewPager pager;
	ViewPagerAdapter adapterPager;
	ArrayList<String> listImages=new ArrayList<String>();
	int currentimageindex = 0;
	Handler mHandler;
	Timer timer;
	int delay = 1000; // delay for 1 sec.
	int period = 6000; // repeat every 6 sec.
	Runnable mUpdateResults;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		//		setTheme(android.R.style.Theme_Holo_Light_NoActionBar_TranslucentDecor);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.registration);
		mContext=RegisterActivity.this;
		// create our manager instance after the content view is set
		//		SystemBarTintManager tintManager = new SystemBarTintManager(this);
		//		// enable status bar tint
		//		tintManager.setStatusBarTintEnabled(true);
		//		// enable navigation bar tint
		//		tintManager.setNavigationBarTintEnabled(true);
		//		tintManager.setStatusBarAlpha(1.0f);
		//		tintManager.setNavigationBarAlpha(1.0f);
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
		etArea.setVisibility(View.VISIBLE);
		etPasswordReset=(EditText) findViewById(R.id.etPasswordReset);
		etPasswordReset.setHintTextColor(Color.parseColor("#5C5858"));
		etContactNumber=(EditText) findViewById(R.id.etContactNumber);
		etContactNumber.setHintTextColor(Color.parseColor("#5C5858"));
		btnSignIn=(Button) findViewById(R.id.btnSignIn);
		btnPasswordReset=(Button) findViewById(R.id.btnPasswordReset);
		pager = (ViewPager) findViewById(R.id.pager);
		// Pass results to ViewPagerAdapter Class
		adapterPager = new ViewPagerAdapter(RegisterActivity.this,listImages);
		// Binds the Adapter to the ViewPager
		pager.setAdapter(adapterPager);
		checkInternetConnectionBGImages();
		try {
			mHandler = new Handler();
			timer = new Timer();
			mUpdateResults = new Runnable() {
				public void run() {
					AnimateandSlideShow();
				}
			};
			timer.scheduleAtFixedRate(new TimerTask() {
				public void run() {
					mHandler.post(mUpdateResults);
				}
			}, delay, period);
		} catch (Exception e) {
			e.getMessage();
		}

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
			//			etArea.setVisibility(View.VISIBLE);
			etArea.setVisibility(View.GONE);
			etContactNumber.setVisibility(View.VISIBLE);

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
			etContactNumber.setVisibility(View.GONE);
			//			etBrideName.setVisibility(View.VISIBLE);
			//			etGroomName.setVisibility(View.VISIBLE);
			//			etArea.setVisibility(View.VISIBLE);
		}
		//		CustomFonts.setFontOfEditText(mContext, etEmailAddress,"fonts/GothamRnd-Light.otf");
		//		CustomFonts.setFontOfEditText(mContext, etPassword,"fonts/GothamRnd-Light.otf");
		//		CustomFonts.setFontOfEditText(mContext, etBrideName,"fonts/GothamRnd-Light.otf");
		//		CustomFonts.setFontOfEditText(mContext, etGroomName,"fonts/GothamRnd-Light.otf");
		//		CustomFonts.setFontOfEditText(mContext, etArea,"fonts/GothamRnd-Light.otf");
		//		CustomFonts.setFontOfEditText(mContext, etPasswordReset,"fonts/GothamRnd-Light.otf");
		//		CustomFonts.setFontOfEditText(mContext, etContactNumber,"fonts/GothamRnd-Light.otf");
		//		CustomFonts.setFontOfButton(mContext,btnSignIn,"fonts/GothamRnd-Light.otf");
		//		CustomFonts.setFontOfButton(mContext,btnPasswordReset,"fonts/GothamRnd-Light.otf");
		//		CustomFonts.setFontOfTextView(mContext,tvLogin,"fonts/GothamRnd-Light.otf");
		//		CustomFonts.setFontOfTextView(mContext,tvForgotPassword,"fonts/GothamRnd-Light.otf");
		//		CustomFonts.setFontOfTextView(mContext,tvToolBar,"fonts/GothamRnd-Light.otf");
		etEmailAddress.addTextChangedListener(this);
		etPassword.addTextChangedListener(this);
		etBrideName.addTextChangedListener(this);
		etGroomName.addTextChangedListener(this);
		etArea.addTextChangedListener(this);
		etPasswordReset.addTextChangedListener(this);
		etContactNumber.addTextChangedListener(this);
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
				//				startActivity(new Intent(RegisterActivity.this,MessageTabActivity.class));
				//				overridePendingTransition(R.anim.right_in, R.anim.left_out);
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
					//					etArea.setText("");
					etContactNumber.setText("");
					etContactNumber.setVisibility(View.GONE);
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
					//					etArea.setVisibility(View.VISIBLE);
					etEmailAddress.setText("");
					etPassword.setText("");
					etBrideName.setText("");
					etGroomName.setText("");
					//					etArea.setText("");
					etContactNumber.setVisibility(View.VISIBLE);
					btnSignIn.setBackgroundColor(Color.parseColor("#F9B9BA"));
					etEmailAddress.requestFocus();
				}
			}
		});
	}

	private void AnimateandSlideShow() {
		if (!listImages.isEmpty()) {
			if (listImages.size() == currentimageindex) {
				currentimageindex = 0;
			}
			pager.setCurrentItem(currentimageindex);
			currentimageindex++;
			Animation rotateimage = AnimationUtils.loadAnimation(this,
					R.anim.right_in);
			pager.startAnimation(rotateimage);
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		ViewPagerAdapter.AnimateFirstDisplayListener.displayedImages.clear();
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
				if(etEmailAddress.getText().toString().trim().equals("") || 
						etPassword.getText().toString().trim().equals("") || 
						etBrideName.getText().toString().trim().equals("") || 
						etGroomName.getText().toString().trim().equals("") || 
						//etArea.getText().toString().trim().equals("")  ||
						etContactNumber.getText().toString().trim().equals(""))
				{        
					btnSignIn.setEnabled(false);
					btnSignIn.setBackgroundColor(Color.parseColor("#F9B9BA"));
				}
				else if(!etEmailAddress.getText().toString().trim().equals("") && 
						!etPassword.getText().toString().trim().equals("") && 
						!etBrideName.getText().toString().trim().equals("") && 
						!etGroomName.getText().toString().trim().equals("") && 
						//						!etArea.getText().toString().trim().equals("") && 
						!etContactNumber.getText().toString().trim().equals(""))
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

	private void checkInternetConnectionBGImages()
	{
		if(GlobalCommonMethods.isNetworkAvailable(mContext))
		{
			url=GlobalCommonValues.CUSTOMER_BG_IMAGE_LOGIN_REGISTRATION;
			new HttpAsyncTaskBGImages().execute(url);
		}
		else{
			ShowDialog.displayDialog(mContext,"Connection error:","No Internet Connection");
		}
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

	private class HttpAsyncTaskBGImages extends AsyncTask<String, Void, Void> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected Void doInBackground(String... params) {
			try {
				// Calling method for setting to be sent to the server
				GetImagesData();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
		// onPostExecute displays the results of the AsyncTask.
		@SuppressLint("DefaultLocale")
		@Override
		protected void onPostExecute(Void result) {
			if(!TextUtils.isEmpty(responseImages) && GlobalCommonMethods.isJSONValid(responseImages))
			{
				try {
					JSONObject jsonObj = new JSONObject(responseImages);
					JSONArray jsonArray=new JSONObject(jsonObj.getString("json")).getJSONArray("data");
					for (int i = 0; i < jsonArray.length(); i++)
					{
						String itemFirst=String.valueOf(jsonArray.get(i));
						listImages.add(itemFirst);
					}
					adapterPager.listImages=listImages;
					adapterPager.notifyDataSetChanged();
				} catch (Exception e) {
					e.getMessage();
				}
			}
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
						String bride_name = request_data.getString("bride_name");
						String email = request_data.getString("email");
						String groom_name = request_data.getString("groom_name");
						String _result = jsonObj.getString("result");
						String message = jsonObj.getString("message");
						if(!jsonObj.getString("json").equals("0"))
							identifier =new JSONObject(jsonObj.getString("json")).getString("identifier");
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
							message="Logged In Successfully";
						ErrorDialog dialog=new ErrorDialog();
						dialog.newInstance(mContext, _result.toUpperCase(), message, iActionObj);
						dialog.setCancelable(false);
						dialog.show(getFragmentManager(), "test");
						if(message.toLowerCase().contains("logged in successfully"))
						{
							PreferenceUtil.getInstance().setLogin(true);
							PreferenceUtil.getInstance().setIdentifier(jsonObj.getJSONObject("json").getString("identifier"));
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
				Intent intent = new Intent(mContext, NavigationDrawerHomeActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);
				overridePendingTransition(R.anim.right_in, R.anim.left_out);
				finish();
			}
		}
	};


	// Create GetImagesData Metod
	public  void   GetImagesData()  throws  UnsupportedEncodingException
	{
		// Create data variable for sent values to server  
		String data="";
		String image_type="";
		int density = getResources().getDisplayMetrics().densityDpi;

		if(density==DisplayMetrics.DENSITY_MEDIUM)
		{
			image_type="drawable-hdpi";
		}
		else if(density==DisplayMetrics.DENSITY_HIGH)
		{
			image_type="drawable-xhdpi";
		}
		else if(density==DisplayMetrics.DENSITY_XHIGH)
		{
			image_type="drawable-xhdpi";
		}
		else if(density==DisplayMetrics.DENSITY_XXHIGH)
		{
			image_type="drawable-xxhdpi";
		}
		else if(density==DisplayMetrics.DENSITY_XXXHIGH)
		{
			image_type="drawable-xxxhdpi";
		}

		data= URLEncoder.encode("mode", "UTF-8") 
				+ "=" + URLEncoder.encode("android", "UTF-8"); 

		data += "&" + URLEncoder.encode("image_type", "UTF-8") + "="
				+ URLEncoder.encode(image_type, "UTF-8"); 
		BufferedReader reader=null;

		// Send data 
		try
		{ 
			URL _url=null;
			// Defined URL  where to send data
			_url= new URL(GlobalCommonValues.CUSTOMER_BG_IMAGE_LOGIN_REGISTRATION);
			// Send POST data request

			HttpURLConnection  conn = (HttpURLConnection)_url.openConnection(); 
			conn.setRequestMethod("POST");
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
			responseImages = sb.toString();
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

	// Create GetData Metod
	public  void  SetData()  throws  UnsupportedEncodingException
	{
		// Create data variable for sent values to server  

		String data="";
		if(url.equals(GlobalCommonValues.USERREGISTRATION))
		{
			data= URLEncoder.encode("email", "UTF-8") 
					+ "=" + URLEncoder.encode(etEmailAddress.getText().toString(), "UTF-8"); 

			data += "&" + URLEncoder.encode("password", "UTF-8") + "="
					+ URLEncoder.encode(etPassword.getText().toString(), "UTF-8"); 

			data += "&" + URLEncoder.encode("groom_name", "UTF-8") 
					+ "=" + URLEncoder.encode(etBrideName.getText().toString(), "UTF-8");

			data += "&" + URLEncoder.encode("bride_name", "UTF-8") 
					+ "=" + URLEncoder.encode(etBrideName.getText().toString(), "UTF-8");

			data += "&" + URLEncoder.encode("contact_number", "UTF-8") 
					+ "=" + URLEncoder.encode(etContactNumber.getText().toString(), "UTF-8");
		}
		else if(url.equals(GlobalCommonValues.LOGIN)){

			if(!isRecentRegistered)
			{
				data = URLEncoder.encode("email", "UTF-8") 
						+ "=" + URLEncoder.encode(etEmailAddress.getText().toString(), "UTF-8"); 

				data += "&" + URLEncoder.encode("password", "UTF-8") + "="
						+ URLEncoder.encode(etPassword.getText().toString(), "UTF-8"); 
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

	/*private class HttpAsyncTask extends AsyncTask<String, Void, String> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			if(progress==null)
			{
				progress=new ProgressDialog(mContext);
				progress.show();		
			}
		}

		@Override
		protected String doInBackground(String... urls) {
			return POST(urls[0],objUserRegistration);
		}
		// onPostExecute displays the results of the AsyncTask.
		@Override
		protected void onPostExecute(String result) {
			response=result;
			Toast.makeText(getBaseContext(), "Data Sent!"+response, Toast.LENGTH_LONG).show();
			if(progress!=null && progress.isShowing())
			{
				progress.dismiss();
				progress=null;
			}
		}
	}

	public static String POST(String url, UserRegistrationBean objUserRegistrationBean){
		InputStream inputStream = null;
		String result = "";
		try {

			// 1. create HttpClient
			HttpClient httpclient = new DefaultHttpClient();

			// 2. make POST request to the given URL
			HttpPost httpPost = new HttpPost(url);

			String json = "";

			// 3. build jsonObject
			JSONObject jsonObject = new JSONObject();
			jsonObject.accumulate("email", objUserRegistrationBean.getEmail());
			jsonObject.accumulate("password", objUserRegistrationBean.getPassword());
			jsonObject.accumulate("groom_name", objUserRegistrationBean.getGroom_name());
			jsonObject.accumulate("bride_name", objUserRegistrationBean.getBride_name());
			jsonObject.accumulate("contact_number", objUserRegistrationBean.getContact_number());

			// 4. convert JSONObject to JSON to String
			json = jsonObject.toString();

			// ** Alternative way to convert Bean object to JSON string usin Jackson Lib 
			// ObjectMapper mapper = new ObjectMapper();
			// json = mapper.writeValueAsString(person); 

			// 5. set json to StringEntity
			StringEntity se = new StringEntity(json);

			// 6. set httpPost Entity
			httpPost.setEntity(se);

			// 7. Set some headers to inform server about the type of the content   
			httpPost.setHeader("Accept", "application/json");
			httpPost.setHeader("Content-type", "application/json");

			// 8. Execute POST request to the given URL
			HttpResponse httpResponse = httpclient.execute(httpPost);

			// 9. receive response as inputStream
			inputStream = httpResponse.getEntity().getContent();

			// 10. convert inputstream to string
			if(inputStream != null)
				result = GlobalCommonMethods.convertInputStreamToString(inputStream);
			else
				result = "Did not work!";

		} catch (Exception e) {
			Log.d("InputStream", e.getLocalizedMessage());
		}
		// 11. return result
		return result;
	}*/

	/*private void userRegistration(UserRegistrationBean objUserRegistration)
	{
		gson=new Gson();
		try {
			String stringGson=gson.toJson(objUserRegistration);
			StringEntity stringEntity=new StringEntity(stringGson);
			stringEntity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded"));
			MyHttpConnection.postWithJsonEntity(mContext, GlobalCommonValues.USERREGISTRATION,stringEntity,userRegistrationResponseHandler);
		} catch (Exception e) {
			e.getMessage();
		}
	}*/

	/*AsyncHttpResponseHandler userRegistrationResponseHandler=new AsyncHttpResponseHandler(){

		public void onStart() {
			if(progress==null)
			{
				progress=new ProgressDialog(mContext);
				progress.show();
			}
		};

		public void onSuccess(String arg0) {
			System.out.println(arg0);
		};

		public void onFailure(Throwable arg0, String arg1) {
			System.out.println(arg1);
		};

		public void onFinish() {
			if(progress!=null && progress.isShowing())
				progress.dismiss();
		};
	};

	private void getResponseRegistration(String response)
	{
		if(!TextUtils.isEmpty(response) && GlobalCommonMethods.isJSONValid(response))
		{

		}
	}*/
}

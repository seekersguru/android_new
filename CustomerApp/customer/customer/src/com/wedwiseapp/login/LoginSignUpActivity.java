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
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONArray;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookAuthorizationException;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.ProfileTracker;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;
import com.wedwise.adapter.ViewPagerAdapter;
import com.wedwise.calendar.Util;
import com.wedwise.common.GlobalCommonMethods;
import com.wedwise.common.GlobalCommonValues;
import com.wedwise.dialogs.ErrorDialog;
import com.wedwise.interfaces.IAction;
import com.wedwiseapp.NavigationDrawerHomeActivity;
import com.wedwiseapp.R;
import com.wedwiseapp.util.CustomFonts;
import com.wedwiseapp.util.PreferenceUtil;
import com.wedwiseapp.util.ShowDialog;

public class LoginSignUpActivity extends FragmentActivity implements OnClickListener, ConnectionCallbacks, OnConnectionFailedListener  {

	/*Declaration for facebook connectivity*/
	AccessToken accessToken;
	private CallbackManager callbackManager;
	private PendingAction pendingAction = PendingAction.NONE;
	private ProfileTracker profileTracker;
	private enum PendingAction {
		NONE,
	}
	public static final String PENDING_ACTION_BUNDLE_KEY ="com.eventmanagementapp:PendingAction";
	ProgressDialog progress;
	String response = "";
	String responseImages="";
	String url="";
	ViewPager pager;
	ViewPagerAdapter adapterPager;
	// Google client to communicate with Google
	private GoogleApiClient mGoogleApiClient;
	private boolean mIntentInProgress;
	private boolean signedInUser;
	private ConnectionResult mConnectionResult;
	private SignInButton signinButton;
	private static final int RC_SIGN_IN = 0;
	ArrayList<String> listImages=new ArrayList<String>();

	String userEmail = "";
	//	LoginButton btnFBLogin;
	Button btnFBLogin;
	Button btnGoogleLogin,btnSignUp,btnLogin;
	//	EditText  etEmailAddress,etPassword;
	TextView tvBottomBar,tvToolBar;
	Toolbar toolbar;
	Handler mHandler;
	Timer timer;
	int delay = 5000; // delay for 1 sec.
	int period = 6000; // repeat every 6 sec.
	Runnable mUpdateResults;
	int currentimageindex = 0;
	Context mContext;
	public static boolean isLoggedIn=false;
	private String email;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);

		setTheme(android.R.style.Theme_Holo_Light_NoActionBar_TranslucentDecor);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		mContext=LoginSignUpActivity.this;
		setContentView(R.layout.loginsignupactivity);
		

		btnLogin=(Button) findViewById(R.id.btnLogin);
		btnFBLogin=(Button) findViewById(R.id.btnFBLogin);


		pager = (ViewPager) findViewById(R.id.vPager);
		btnGoogleLogin=(Button) findViewById(R.id.btnGoogleLogin);
		btnSignUp=(Button) findViewById(R.id.btnSignUp);
		tvBottomBar=(TextView) findViewById(R.id.tvBottomBar);
		tvBottomBar.setText(Html.fromHtml("By signing up I agree to terms and policies of Wedwise").toString());
		toolbar=(Toolbar) findViewById(R.id.toolbar);
		
		CustomFonts.setFontOfButton(LoginSignUpActivity.this, btnGoogleLogin, "fonts/GothamRoundedBook.ttf");		
		CustomFonts.setFontOfButton(LoginSignUpActivity.this, btnSignUp, "fonts/GothamRoundedBook.ttf");		
		CustomFonts.setFontOfButton(LoginSignUpActivity.this, btnLogin, "fonts/GothamRoundedBook.ttf");		
		CustomFonts.setFontOfButton(LoginSignUpActivity.this, btnFBLogin, "fonts/GothamRoundedBook.ttf");		
		CustomFonts.setFontOfTextView(LoginSignUpActivity.this, tvBottomBar, "fonts/GothamRoundedBook.ttf");		
		
		
		adapterPager = new ViewPagerAdapter(LoginSignUpActivity.this,listImages);
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

				Intent myIntent=new Intent(LoginSignUpActivity.this,RegisterActivity.class);
				myIntent.putExtra("type","login");
				startActivity(myIntent);
				overridePendingTransition(R.anim.right_in, R.anim.left_out);

			}
		});

		btnFBLogin.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				LoginManager.getInstance().logInWithReadPermissions(LoginSignUpActivity.this, Arrays.asList("basic_info", "email"));
			}
		});
		callbackManager = CallbackManager.Factory.create();
		LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
			@Override
			public void onSuccess(LoginResult loginResult) {
				handlePendingAction();
				updateUI();
			}

			@Override
			public void onCancel() {
				if (pendingAction != PendingAction.NONE) {
					showAlert();
					pendingAction = PendingAction.NONE;
				}
			}

			@Override
			public void onError(FacebookException exception) {
				if (pendingAction != PendingAction.NONE
						&& exception instanceof FacebookAuthorizationException) {
					showAlert();
					pendingAction = PendingAction.NONE;
				}

			}

			private void showAlert() {
				Toast.makeText(getApplicationContext(), "Please check internet connectivity", Toast.LENGTH_SHORT).show();
			}
		});
		if (arg0 != null) {
			String name = arg0.getString(PENDING_ACTION_BUNDLE_KEY);
			pendingAction = PendingAction.valueOf(name);
		}


		mGoogleApiClient = new GoogleApiClient.Builder(this).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(Plus.API, Plus.PlusOptions.builder().build()).addScope(Plus.SCOPE_PLUS_LOGIN).build();
		btnGoogleLogin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				try {
					if (!mGoogleApiClient.isConnecting()) {
						signedInUser = true;
						resolveSignInError();
						mGoogleApiClient.connect();
					}

				} catch (Exception e) {
					e.printStackTrace();
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
//			Animation rotateimage = AnimationUtils.loadAnimation(this,
//					R.anim.right_in);
//			pager.startAnimation(rotateimage);
		}
	}

	private Bundle getRequestParameters() 
	{
		Bundle parameters = new Bundle(1);
		parameters.putString("fields", "email");
		return parameters;
	}

	private void updateUI() {
		accessToken = AccessToken.getCurrentAccessToken();

		if(GlobalCommonMethods.isNetworkAvailable(mContext)){
			new GraphRequest(
					accessToken,
					"/me",
					getRequestParameters(),
					HttpMethod.GET,
					new GraphRequest.Callback() {
						public void onCompleted(GraphResponse response) {
							/* handle the result */
							try {
								String resp = response.toString().substring(response.toString().indexOf("graphObject:")+12,response.toString().indexOf("}")+1);
								JSONObject jobject = new JSONObject(resp);
								email = jobject.getString("email");
								getFBLogin(email);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
					).executeAsync();
		}else{
			ShowDialog.displayDialog(mContext,"Connection error:","No Internet Connection");
		}


	}

	private void handlePendingAction() {
		PendingAction previouslyPendingAction = pendingAction;
		// These actions may re-set pendingAction if they are still pending, but we assume they
		// will succeed.
		pendingAction = PendingAction.NONE;
		switch (previouslyPendingAction) {
		case NONE:
			break;
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putString(PENDING_ACTION_BUNDLE_KEY, pendingAction.name());
	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	public void onPause() {
		super.onPause();
		AppEventsLogger.deactivateApp(this);
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (profileTracker != null && profileTracker.isTracking()) {
			profileTracker.stopTracking();
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		if(isLoggedIn)
		{
			finish();
		}
		AppEventsLogger.activateApp(this);
		LoginManager.getInstance().logOut();
	}

	@Override
	protected void onStop() {
		super.onStop();
		if (mGoogleApiClient.isConnected()) {
			mGoogleApiClient.disconnect();
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


	@Override
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(R.anim.right_in, R.anim.right_out);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case RC_SIGN_IN:
			if (resultCode == RESULT_OK) {
				signedInUser = false;
			}
			mIntentInProgress = false;
			if (!mGoogleApiClient.isConnecting()) {
				mGoogleApiClient.connect();
			}
			break;

		default:
			callbackManager.onActivityResult(requestCode, resultCode, data);
		}

	}

	@Override
	public void onConnected(Bundle arg0) {
		signedInUser = false;
		Toast.makeText(this, "Connected", Toast.LENGTH_LONG).show();
		getProfileInformation();
	}


	private void getProfileInformation() {
		try {
			if (Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null) {
				Person currentPerson = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);
				email = Plus.AccountApi.getAccountName(mGoogleApiClient);
				if(!TextUtils.isEmpty(email)){
					if(GlobalCommonMethods.isNetworkAvailable(mContext))
					{
						String url=GlobalCommonValues.FB_LOGIN;
						new HttpAsyncTask(email).execute(url);
					}
				}else{
					ShowDialog.displayDialog(mContext,"Login error:","Unable to fetch email address.");
				}
			}else{
				googlePlusLogin();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	private void getFBLogin(String email){
		if(GlobalCommonMethods.isNetworkAvailable(mContext))
		{
			String url=GlobalCommonValues.FB_LOGIN;
			new HttpAsyncTask(email).execute(url);
		}
	}

	private class HttpAsyncTask extends AsyncTask<String, Void, Void> {

		String url = "";

		public HttpAsyncTask(String email){
			userEmail = email;
		}

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
				url = params[0];
				SetData(userEmail);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
		@SuppressLint("DefaultLocale")
		@Override
		protected void onPostExecute(Void result) {
			if(progress!=null && progress.isShowing())
			{
				progress.dismiss();
				progress=null;
			}

			if(!TextUtils.isEmpty(response) && GlobalCommonMethods.isJSONValid(response))
			{
				if(url.equals(GlobalCommonValues.FB_LOGIN))
				{
					try {
						JSONObject jsonObj = new JSONObject(response);
						String _result = jsonObj.getString("result");
						String message = jsonObj.getString("message");
						if(message.equals("0")){
							message="Logged In Successfully";
						}

						if(message.toLowerCase().contains("logged in successfully"))
						{
							PreferenceUtil.getInstance().setLogin(true);
							PreferenceUtil.getInstance().setIdentifier(jsonObj.getJSONObject("json").getString("identifier"));
							Log.d("identifier", jsonObj.getJSONObject("json").getString("identifier"));
//							if(GlobalCommonMethods.isNetworkAvailable(mContext))
//							{
//								String url=GlobalCommonValues.USERREGISTRATION;
//								new HttpAsyncTaskData().execute(url);
//							}
						}

						if(message.contains("User not exist"))
						{
							Intent myIntent=new Intent(LoginSignUpActivity.this,RegisterActivity.class);
							myIntent.putExtra("type","registration");
							myIntent.putExtra("userEmail",userEmail);
							myIntent.putExtra("loginfrom",GlobalCommonValues.FB_LOGIN);
							startActivity(myIntent);	
							overridePendingTransition(R.anim.right_in, R.anim.left_out);
							finish();
							PreferenceUtil.getInstance().setLogin(true);
						}
						else if(message.toLowerCase().contains("logged in successfully"))
						{
							PreferenceUtil.getInstance().setRegister(true);
							Intent intent = new Intent(mContext, NavigationDrawerHomeActivity.class);
							intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
							startActivity(intent);
							LoginSignUpActivity.isLoggedIn=true;
							overridePendingTransition(R.anim.right_in, R.anim.left_out);
							finish();
						}

					} catch (Exception e) {
						e.getMessage();
					}
				}
			}
		}
	}

	// Create GetData Metod
	public  void  SetData(String email)  throws  UnsupportedEncodingException
	{
		String data="";
		data = URLEncoder.encode("email", "UTF-8") 
				+ "=" + URLEncoder.encode(email, "UTF-8"); 

		BufferedReader reader=null;
		// Send data 
		try
		{ 
			URL _url=null;
			_url= new URL(GlobalCommonValues.FB_LOGIN);

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
			System.out.println("server response:"+response);
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

	IAction iActionObj = new IAction() {

		@Override
		public void setAction(String action) {
			if(action.equals("register"))
			{
				Intent myIntent=new Intent(LoginSignUpActivity.this,RegisterActivity.class);
				myIntent.putExtra("type","registration");
				myIntent.putExtra("userEmail",userEmail);
				startActivity(myIntent);	
				overridePendingTransition(R.anim.right_in, R.anim.left_out);
				
			}
			else if(action.equals("navigate"))
			{
				PreferenceUtil.getInstance().setRegister(true);
				Intent intent = new Intent(mContext, NavigationDrawerHomeActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);
				LoginSignUpActivity.isLoggedIn=true;
				overridePendingTransition(R.anim.right_in, R.anim.left_out);
				finish();
				
			}
		}
	};

	private void resolveSignInError() {
		if (mConnectionResult!=null && mConnectionResult.hasResolution()) {
			try {
				mIntentInProgress = true;
				mConnectionResult.startResolutionForResult(this, RC_SIGN_IN);
			} catch (SendIntentException e) {
				mIntentInProgress = false;
				mGoogleApiClient.connect();
			}
		}
	}

	@Override
	public void onConnectionFailed(ConnectionResult result) {
		if (!result.hasResolution()) {
			GooglePlayServicesUtil.getErrorDialog(result.getErrorCode(), this, 0).show();
			return;
		}
		if (!mIntentInProgress) {
			
			mConnectionResult = result;

			if (signedInUser) {
				resolveSignInError();
			}
		}
	}

	public void signIn(View v) {
		googlePlusLogin();
	}

	public void logout(View v) {
		googlePlusLogout();
	}


	private void googlePlusLogin() {
		if (!mGoogleApiClient.isConnecting()) {
			signedInUser = true;
			resolveSignInError();
		}
	}

	private void googlePlusLogout() {
		if (mGoogleApiClient.isConnected()) {
			Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
			mGoogleApiClient.disconnect();
			mGoogleApiClient.connect();
		}
	}

	@Override
	public void onConnectionSuspended(int arg0) {
		mGoogleApiClient.connect();
	}

	@Override
	public void onClick(View v) {

	}
	
	private class HttpAsyncTaskData extends AsyncTask<String, Void, Void> {
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
				SetDataRegistration();
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
						PreferenceUtil.getInstance().setBrideName(bride_name);
						PreferenceUtil.getInstance().setGroomName(groom_name);
						PreferenceUtil.getInstance().setIdentifier(identifier);
						if(message.equals("0"))
							message="Registered Successfully";
						if(!message.toLowerCase().equalsIgnoreCase("registered successfully"))
						{
//							ErrorDialog dialog=new ErrorDialog();
//							dialog.newInstance(mContext, _result.toUpperCase(), message, iActionObj);
//							dialog.setCancelable(false);
//							dialog.show(getFragmentManager(), "test");
						}
						else if(message.toLowerCase().equalsIgnoreCase("registered successfully"))
						{
//							isRecentRegistered=true;
//							emailLogin=email;
//							passwordLogin=password;
//							checkInternetConnection("login");
						}
					} catch (Exception e) {
						e.getMessage();
					}
				}
			}
		}
	}
	
	private void SetDataRegistration() throws  UnsupportedEncodingException{
		String data="";
			data= URLEncoder.encode("identifier", "UTF-8") 
					+ "=" + URLEncoder.encode(PreferenceUtil.getInstance().getIdentifier(), "UTF-8"); 

			data += "&" + URLEncoder.encode("operation", "UTF-8") + "="
					+ URLEncoder.encode("get", "UTF-8"); 
			BufferedReader reader=null;

			// Send data 
			try
			{ 
				URL _url=null;
				// Defined URL  where to send data
					_url= new URL(GlobalCommonValues.USERREGISTRATION);
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

}

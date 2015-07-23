package com.eventmanagementapp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Arrays;

import org.json.JSONObject;

import com.eventmanagementapp.calendar.CalendarActivity;
import com.eventmanagementapp.common.GlobalCommonMethods;
import com.eventmanagementapp.common.GlobalCommonValues;
import com.eventmanagementapp.dialogs.ErrorDialog;
import com.eventmanagementapp.interfaces.IAction;
import com.eventmanagementapp.util.CustomFonts;
import com.eventmanagementapp.util.PreferenceUtil;
import com.eventmanagementapp.util.ShowDialog;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookAuthorizationException;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.ProfileTracker;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;

import android.R.color;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
	// Google client to communicate with Google
	private GoogleApiClient mGoogleApiClient;
	private boolean mIntentInProgress;
	private boolean signedInUser;
	private ConnectionResult mConnectionResult;
	private SignInButton signinButton;
	private static final int RC_SIGN_IN = 0;

	String userEmail = "";
	LoginButton btnFBLogin;
	Button btnGoogleLogin,btnSignUp,btnBack,btnLogin;
	//	EditText  etEmailAddress,etPassword;
	TextView tvBottomBar,tvToolBar;
	Toolbar toolbar;
	Context mContext;
	public static boolean isLoggedIn=false;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		FacebookSdk.sdkInitialize(this.getApplicationContext());
		//		getWindow().setStatusBarColor(Color.TRANSPARENT);
		setTheme(android.R.style.Theme_Holo_Light_NoActionBar_TranslucentDecor);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		mContext=LoginSignUpActivity.this;
		setContentView(R.layout.loginsignupactivity);
		btnLogin=(Button) findViewById(R.id.btnLogin);
		btnFBLogin=(LoginButton) findViewById(R.id.btnFBLogin);
		btnFBLogin.setBackgroundResource(R.drawable.sign_up_fb);
		btnFBLogin.setReadPermissions(Arrays.asList("user_friends"));		
		btnGoogleLogin=(Button) findViewById(R.id.btnGoogleLogin);
		btnSignUp=(Button) findViewById(R.id.btnSignUp);
		tvBottomBar=(TextView) findViewById(R.id.tvBottomBar);
		tvBottomBar.setText(Html.fromHtml("By signing up,I agree to terms of services,privacy policies,guest policies,and host guarantee terms.").toString());
		toolbar=(Toolbar) findViewById(R.id.toolbar);
		btnBack=(Button) toolbar.findViewById(R.id.btnBack);
		tvToolBar=(TextView)toolbar.findViewById(R.id.tvToolBar);
		toolbar.findViewById(R.id.refresh_button).setVisibility(View.GONE);
		tvToolBar.setText("Log In or Sign Up");
		btnBack.setVisibility(View.GONE);
		CustomFonts.setFontOfButton(mContext, btnSignUp,"fonts/GothamRoundedBook.ttf");
		//		CustomFonts.setFontOfButton(mContext, btnLogin,"fonts/GothamRoundedBook.ttf");
		CustomFonts.setFontOfTextView(mContext, tvToolBar,"fonts/GothamRoundedBook.ttf");
		CustomFonts.setFontOfTextView(mContext, tvBottomBar,"fonts/GothamRoundedBook.ttf");
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
				Intent myIntent=new Intent(LoginSignUpActivity.this,RegistrationSignUpActivity.class);
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
				Intent myIntent=new Intent(LoginSignUpActivity.this,RegistrationSignUpActivity.class);
				myIntent.putExtra("type","login");
				startActivity(myIntent);
				overridePendingTransition(R.anim.right_in, R.anim.left_out);
			}
		});

		//btnFBLogin.setReadPermissions(Arrays.asList("email"));
		//btnFBLogin.setReadPermissions("email");
		btnFBLogin.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
		btnFBLogin.setText(" ");
		btnFBLogin.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				//Toast.makeText(getApplicationContext(), "Please check internet connectivity", Toast.LENGTH_SHORT).show();
			}
		});
		callbackManager = CallbackManager.Factory.create();
		btnFBLogin.registerCallback(callbackManager,
				new FacebookCallback<LoginResult>() {
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

				//showPopups(getString(R.string.permissionNotGranted),false,getString(R.string.okayText),getString(R.string.facebookError),getString(R.string.errorTitleText));
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
					if (Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null) {
						Person currentPerson = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);
						//String personName = currentPerson.getDisplayName();
						//String personPhotoUrl = currentPerson.getImage().getUrl();
						String email = Plus.AccountApi.getAccountName(mGoogleApiClient);
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


				/*Intent myIntent=new Intent(LoginSignUpActivity.this,BidBookDetailsScreenActivity.class);
				startActivity(myIntent);
				overridePendingTransition(R.anim.right_in, R.anim.left_out);*/
			}
		});

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
								String email = jobject.getString("email");
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


		/*GraphRequestAsyncTask request = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
			@Override
			public void onCompleted(JSONObject user, GraphResponse response) {
				Log.d("email", "in on complete");

				System.out.println("user:"+response.toString());
				if (user != null) {
					try {
						if (BuildConfig.DEBUG)
							Log.d("email", (String) user.get("email"));
						Log.d("name", (String) user.get("name"));
						////Toast.makeText(getApplicationContext(), (String) user.get("email"), Toast.LENGTH_SHORT).show();
						// Email received calling the web services
						// getRegister((String) user.get("email"), (String) user.get("name"), (String) user.get("gender"));
					} catch (JSONException e) {
						Log.d("exception", e.toString());
						Toast.makeText(getApplicationContext(), "Please check internet connectivity", Toast.LENGTH_SHORT).show();
						//   showPopups(getString(R.string.fbError), false, getString(R.string.okayText), getString(R.string.errorTitleText), getString(R.string.errorTitleText));
						e.printStackTrace();
					}
				} else {
					Toast.makeText(getApplicationContext(), "Please check internet connectivity", Toast.LENGTH_SHORT).show();
					// showPopups(getString(R.string.fbError), false, getString(R.string.okayText), getString(R.string.errorTitleText), getString(R.string.errorTitleText));
				}
			}
		}).executeAsync();*/
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
		mGoogleApiClient.connect();
	}

	@Override
	public void onPause() {
		super.onPause();
		// Call the 'deactivateApp' method to log an app event for use in analytics and advertising
		// reporting.  Do so in the onPause methods of the primary Activities that an app may be
		// launched into.
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
				//String personName = currentPerson.getDisplayName();
				//String personPhotoUrl = currentPerson.getImage().getUrl();
				String email = Plus.AccountApi.getAccountName(mGoogleApiClient);
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
				// Calling method for setting to be sent to the server
				url = params[0];
				SetData(userEmail);
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
				if(url.equals(GlobalCommonValues.FB_LOGIN))
				{
					try {
						JSONObject jsonObj = new JSONObject(response);
						String _result = jsonObj.getString("result");
						String message = jsonObj.getString("message");
						if(message.equals("0")){
							message="Logged In Successfully";
						}

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
				Intent myIntent=new Intent(LoginSignUpActivity.this,RegistrationSignUpActivity.class);
				myIntent.putExtra("type","registration");
				myIntent.putExtra("userEmail",userEmail);
				startActivity(myIntent);	
				overridePendingTransition(R.anim.right_in, R.anim.left_out);
				//ShowDialog.displayDialog(mContext,"Login error:","User not exist");
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
			// store mConnectionResult
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
			//  updateProfile(false);
		}
	}

	@Override
	public void onConnectionSuspended(int arg0) {
		mGoogleApiClient.connect();
	}

	@Override
	public void onClick(View v) {

	}
}

package com.wedwise.Activities;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import com.wedwise.adapter.MessagesListAdapter;
import com.wedwise.chat.MessageChatActivity;
import com.wedwise.common.GlobalCommonMethods;
import com.wedwise.common.GlobalCommonValues;
import com.wedwise.dialogs.ErrorDialog;
import com.wedwise.tab.MessageTabActivity;
import com.wedwiseapp.FavListActivity;
import com.wedwiseapp.NavigationDrawerHomeActivity;
import com.wedwiseapp.R;
import com.wedwiseapp.util.CustomFonts;
import com.wedwiseapp.util.PreferenceUtil;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class MessageListActivity extends FragmentActivity{

	ListView lvMessages;
	ArrayList<HashMap<String, String>>listMessages;
	MessagesListAdapter adapterMessageList;
	Button btnBack;
	Context mContext;
	View viewTopbar;
	TextView tvTitle;
	String response,url,responseMessageList;
	ProgressDialog progress;
	String page_count="1";
	Button btnMail,btnHome,btnLeads,btnMenu;
	LinearLayout llMail, llHome, llLeads, llMenu;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.messagetab);
		mContext=MessageListActivity.this;
		/*ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setDisplayShowTitleEnabled(false);
		LayoutInflater mInflater = LayoutInflater.from(this);
		View mCustomView = mInflater.inflate(R.layout.customactionbarview, null);
		actionBar.setCustomView(mCustomView);
		btnBack=(Button) mCustomView.findViewById(R.id.btnBack);
		actionBar.setDisplayShowCustomEnabled(true);
		viewTopbar=findViewById(R.id.viewTopbar);
		viewTopbar.setVisibility(View.VISIBLE);*/
		tvTitle=(TextView) findViewById(R.id.tvTitle);
		btnBack=(Button) findViewById(R.id.btnBack);
		lvMessages=(ListView)findViewById(R.id.lvMessages);
		listMessages=new ArrayList<HashMap<String, String>>();
		
		CustomFonts.setFontOfTextView(MessageListActivity.this, tvTitle, "fonts/GothamRoundedBook.ttf");
		
		llMail = (LinearLayout) findViewById(R.id.llMail);
		llHome = (LinearLayout) findViewById(R.id.llHome);
		llLeads = (LinearLayout) findViewById(R.id.llLeads);
		llMenu = (LinearLayout) findViewById(R.id.llMenu);
		btnMail = (Button) findViewById(R.id.btnMail);
		btnHome=(Button) findViewById(R.id.btnHome);
		btnLeads=(Button) findViewById(R.id.btnLeads);
		btnMenu = (Button) findViewById(R.id.btnMenu);
		
		llHome.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent myIntent = new Intent(MessageListActivity.this,
						NavigationDrawerHomeActivity.class);
				startActivity(myIntent);
				overridePendingTransition(R.anim.left_in, R.anim.right_out);
			}
		});

		llLeads.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent myIntent = new Intent(MessageListActivity.this,
						MessageTabActivity.class);
				startActivity(myIntent);
				overridePendingTransition(R.anim.right_in,
						R.anim.left_out);
			}
		});

		llMail.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
//				if (!PreferenceUtil.getInstance().isRegistered()) {
//					ErrorDialog dialog = new ErrorDialog();
//					dialog.newInstance(mContext, "Alert!",
//							"Please register on Wedwise to use this feature",
//							null);
//					dialog.setCancelable(false);
//					dialog.show(getFragmentManager(), "test");
//				} else if (PreferenceUtil.getInstance().isRegistered()) {
//					Intent myIntent = new Intent(MessageListActivity.this,
//							MessageListActivity.class);
//					startActivity(myIntent);
//					overridePendingTransition(R.anim.right_in,
//							R.anim.left_out);
//				}
			}
		});

		llMenu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent myIntent = new Intent(MessageListActivity.this,
						MenuListActivity.class);
				startActivity(myIntent);
				overridePendingTransition(R.anim.right_in,
						R.anim.left_out);
				// boolean isLogin = PreferenceUtil.getInstance().isLogin();
				// if(isLogin)
				// {
				// LogoutConfirmationDialog dialogLogout=new
				// LogoutConfirmationDialog();
				// dialogLogout.setCancelable(false);
				// dialogLogout.newInstance(getActivity(), "",
				// "You are logged in.Do you want to logout?", iNotifyLogout);
				// dialogLogout.show(getFragmentManager(), "test");
				// }
				// else if (!isLogin)
				// {
				// Intent myIntent = new Intent(getActivity(),
				// LoginSignUpActivity.class);
				//  startActivity(myIntent);
				//  overridePendingTransition(R.anim.right_in,
				// R.anim.left_out);
				// }
			}
		});
		
		btnMenu.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent myIntent = new Intent(MessageListActivity.this,
						MenuListActivity.class);
				startActivity(myIntent);
				overridePendingTransition(R.anim.right_in,
						R.anim.left_out);
			}
		});

		
		btnMail.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
//				if (!PreferenceUtil.getInstance().isRegistered()) {
//					ErrorDialog dialog = new ErrorDialog();
//					dialog.newInstance(mContext, "Alert!",
//							"Please register on Wedwise to use this feature",
//							null);
//					dialog.setCancelable(false);
//					dialog.show(getFragmentManager(), "test");
//				} else if (PreferenceUtil.getInstance().isRegistered()) {
//					Intent myIntent = new Intent(MessageListActivity.this,
//							MessageListActivity.class);
//					startActivity(myIntent);
//					overridePendingTransition(R.anim.right_in,
//							R.anim.left_out);
//				}
			}
		});
		
		btnLeads.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent myIntent = new Intent(MessageListActivity.this,
						MessageTabActivity.class);
				startActivity(myIntent);
				overridePendingTransition(R.anim.right_in,
						R.anim.left_out);					
			}
		});


		//		listMessages.add("Andy Lau");
		//		listMessages.add("James Moore");
		//		listMessages.add("Jorgen Flood");
		//		listMessages.add("Claude");
		//		listMessages.add("Stefanos Fanidis");
		//		listMessages.add("James Moore");
		//		listMessages.add("James Moore");
		//		listMessages.add("James Moore");
		//		listMessages.add("James Moore");
		//		listMessages.add("James Moore");
		adapterMessageList=new MessagesListAdapter(mContext, listMessages);
		lvMessages.setAdapter(adapterMessageList);
		lvMessages.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent myIntent=new Intent(mContext,MessageChatActivity.class);
				myIntent.putExtra("receiver_email", listMessages.get(position).get("receiver_email"));
				startActivity(myIntent);
				overridePendingTransition(R.anim.right_in, R.anim.left_out);
			}
		});
		btnBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();	
				overridePendingTransition(R.anim.left_in, R.anim.right_out);				
			}
		});

		Button refreshButton = (Button)findViewById(R.id.refresh_button);
		refreshButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				url=GlobalCommonValues.CUSTOMER_VENDOR_MESSAGE_LIST;
				new HttpAsyncTask().execute(url);
			}
		});
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		url=GlobalCommonValues.CUSTOMER_VENDOR_MESSAGE_LIST;
		new HttpAsyncTask().execute(url);
	}

	private class HttpAsyncTask extends AsyncTask<String, Void, Void> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			if(url.equals(GlobalCommonValues.CUSTOMER_VENDOR_MESSAGE_LIST))
			{
				if(progress==null)
				{
					progress=new ProgressDialog(mContext);
					progress.show();		
				}
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
		@Override
		protected void onPostExecute(Void result) {
			if(url.equals(GlobalCommonValues.CUSTOMER_VENDOR_MESSAGE_LIST))
			{
				if(progress!=null && progress.isShowing())
				{
					progress.dismiss();
					progress=null;
				}
			}
			if(!TextUtils.isEmpty(response) && GlobalCommonMethods.isJSONValid(response))
			{
				// In case of fetching message listing
				try {
					JSONObject jsonObj = new JSONObject(response);
					String _result = jsonObj.getString("result");
					if(_result.equalsIgnoreCase("error"))
					{
						String errorMessage=jsonObj.getString("message");
						ErrorDialog dialog=new ErrorDialog();
						dialog.newInstance(mContext, _result.toUpperCase(), errorMessage,null);
						dialog.setCancelable(false);
						dialog.show(getFragmentManager(), "test");
					}
					else if(_result.equalsIgnoreCase("success")){
						JSONArray jsonArray = jsonObj.getJSONArray("json");
						adapterMessageList.listChat.clear();
						for(int i=0;i<jsonArray.length();i++)
						{
							String receiver_name=new JSONObject(jsonArray.getString(i)).getString("receiver_name");
							String message=new JSONObject(jsonArray.getString(i)).getString("message");
							String msg_time=new JSONObject(jsonArray.getString(i)).getString("msg_time");	
							String receiver_email=new JSONObject(jsonArray.getString(i)).getString("receiver_email");
							HashMap<String, String> hashMap=new HashMap<String,String>();
							hashMap.put("receiver_name",receiver_name);
							hashMap.put("message",message);
							hashMap.put("msg_time", msg_time);
							hashMap.put("receiver_email", receiver_email);
							adapterMessageList.listChat.add(hashMap);
							adapterMessageList.notifyDataSetChanged();
						}
					}
				} catch (Exception e) {
					e.getMessage();
				}

				if(adapterMessageList.getCount()==0){
					TextView empty_view = (TextView)findViewById(R.id.empty_view);
					empty_view.setVisibility(View.VISIBLE);
				}
			}
		}
	}

	// Create GetData Method
	public  void  SetData()  throws  UnsupportedEncodingException
	{
		// Create data variable for sent values to server  
		String data="";
		String identifier=PreferenceUtil.getInstance().getIdentifier();
		String from_to="c2v";
		if(url.equals(GlobalCommonValues.CUSTOMER_VENDOR_MESSAGE_LIST))
		{
			String page_no=page_count;
			data= URLEncoder.encode("identifier", "UTF-8") 
					+ "=" + URLEncoder.encode(identifier, "UTF-8"); 

			data += "&" + URLEncoder.encode("page_no", "UTF-8") + "="
					+ URLEncoder.encode(page_no, "UTF-8"); 

			data += "&" + URLEncoder.encode("from_to", "UTF-8") 
			+ "=" + URLEncoder.encode(from_to,"UTF-8");

			data += "&" + URLEncoder.encode("msg_type", "UTF-8") 
			+ "=" + URLEncoder.encode("message","UTF-8");

		}
		BufferedReader reader=null;
		// Send data 
		try
		{ 
			URL _url=null;
			// Defined URL  where to send data
			_url= new URL(GlobalCommonValues.CUSTOMER_VENDOR_MESSAGE_LIST);
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
		overridePendingTransition(R.anim.left_in, R.anim.right_out);
	}
}

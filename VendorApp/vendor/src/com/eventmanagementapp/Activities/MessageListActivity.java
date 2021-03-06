package com.eventmanagementapp.Activities;

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
import android.widget.ListView;
import android.widget.TextView;

import com.eventmanagementapp.R;
import com.eventmanagementapp.adapter.MessagesListAdapter;
import com.eventmanagementapp.common.GlobalCommonMethods;
import com.eventmanagementapp.common.GlobalCommonValues;
import com.eventmanagementapp.dialogs.ErrorDialog;
import com.eventmanagementapp.util.PreferenceUtil;

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
		url=GlobalCommonValues.CUSTOMER_VENDOR_MESSAGE_LIST;
		new HttpAsyncTask().execute(url);
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
				startActivity(myIntent);
				overridePendingTransition(R.anim.right_in, R.anim.left_out);
			}
		});
		btnBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();	
				overridePendingTransition(R.anim.right_in, R.anim.right_out);				
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
								HashMap<String, String> hashMap=new HashMap<String,String>();
								hashMap.put("receiver_name",receiver_name);
								hashMap.put("message",message);
								hashMap.put("msg_time", msg_time);
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
			String from_to="v2c";
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
		overridePendingTransition(R.anim.right_in, R.anim.right_out);
	}
}

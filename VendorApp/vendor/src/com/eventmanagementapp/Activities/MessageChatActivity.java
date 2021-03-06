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

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.eventmanagementapp.R;
import com.eventmanagementapp.adapter.ChatAdapter;
import com.eventmanagementapp.common.GlobalCommonMethods;
import com.eventmanagementapp.common.GlobalCommonValues;
import com.eventmanagementapp.dialogs.ErrorDialog;
import com.eventmanagementapp.util.CustomFonts;
import com.eventmanagementapp.util.PreferenceUtil;

public class MessageChatActivity extends FragmentActivity{

	Toolbar toolbar;
	Context mContext;
	ImageView imViewOverflowMenuicon,imViewAttachment;
	Button btnBack,btnSendMessage;
	TextView tvToolBar;
	EditText etMessage;
	ListView lvChatMessages;
	ChatAdapter adapterChat;
	ArrayList<HashMap<String, String>> listChat;
	String response,url;
	ProgressDialog progress;
	String page_count="1";

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.messagechatactivity);
		mContext=MessageChatActivity.this;
		toolbar=(Toolbar) findViewById(R.id.toolbar);
		toolbar.setBackgroundColor(Color.parseColor("#ffffff"));
		lvChatMessages=(ListView) findViewById(R.id.lvChatMessages);
		btnBack=(Button) toolbar.findViewById(R.id.btnBack);
		tvToolBar=(TextView)toolbar.findViewById(R.id.tvToolBar);
		etMessage=(EditText) findViewById(R.id.etMessage);
		btnSendMessage=(Button) findViewById(R.id.btnSendMessage);
		imViewOverflowMenuicon=(ImageView)toolbar.findViewById(R.id.imViewOverFlow);
		imViewAttachment=(ImageView) toolbar.findViewById(R.id.imViewAttachment);
		imViewOverflowMenuicon.setBackgroundResource(R.drawable.overflow_menu);
		//imViewAttachment.setVisibility(View.VISIBLE);
		//imViewOverflowMenuicon.setVisibility(View.VISIBLE);
		//		tvToolBar.setText("Sujata Weds Rajesh");
		tvToolBar.setTextColor(Color.parseColor("#555555"));
		btnBack.setBackground(MessageChatActivity.this.getResources().getDrawable(R.drawable.back_orange));
		listChat=new ArrayList<HashMap<String, String>>();
		CustomFonts.setFontOfTextView(mContext, tvToolBar, "fonts/GothamRnd-Light.otf");
		adapterChat=new ChatAdapter(MessageChatActivity.this,listChat);
		lvChatMessages.setAdapter(adapterChat);
		
		url=GlobalCommonValues.CUSTOMER_VENDOR_MESSAGE_DETAIL;
		new HttpAsyncTask().execute(url);
		
		btnSendMessage.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				url=GlobalCommonValues.CUSTOMER_VENDOR_MESSAGE_CREATE;
				new HttpAsyncTask().execute(url);
			}
		});

		Button refreshButton = (Button)findViewById(R.id.refresh_button);
		refreshButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				url=GlobalCommonValues.CUSTOMER_VENDOR_MESSAGE_DETAIL;
				new HttpAsyncTask().execute(url);
			}
		});
		btnBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();		
				overridePendingTransition(R.anim.right_in, R.anim.right_out);
			}
		});
		etMessage.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if(s.length()==0)
				{
					btnSendMessage.setEnabled(false);
				}
				else{
					btnSendMessage.setEnabled(true);
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {

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
		@SuppressLint("DefaultLocale")
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
				if(url.equals(GlobalCommonValues.CUSTOMER_VENDOR_MESSAGE_CREATE))
				{
					// In case of sending message
					try {
						JSONObject jsonObj = new JSONObject(response);
						String _result = jsonObj.getString("result");
						String json = jsonObj.getString("json");
						if(_result.equalsIgnoreCase("error"))
						{
							String errorMessage=jsonObj.getString("message");
							ErrorDialog dialog=new ErrorDialog();
							dialog.newInstance(mContext, _result.toUpperCase(), errorMessage,null);
							dialog.setCancelable(false);
							dialog.show(getFragmentManager(), "test");
						}
						else if(_result.equalsIgnoreCase("success")){
							etMessage.setText("");
							String message=new JSONObject(json).getString("message");
							String msg_time=new JSONObject(json).getString("msg_time");
							tvToolBar.setText(new JSONObject(json).getString("receiver_name"));
							HashMap<String, String> hashMap=new HashMap<String,String>();
							hashMap.put("message",message);
							hashMap.put("msg_time", msg_time);
							adapterChat.listChat.add(hashMap);
							adapterChat.notifyDataSetChanged();
						}
					}
					catch(Exception e)
					{
						e.getMessage();
					}
				}
				else if(url.equals(GlobalCommonValues.CUSTOMER_VENDOR_MESSAGE_DETAIL))
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
							etMessage.setText("");
							JSONArray jsonArray = jsonObj.getJSONArray("json");
							adapterChat.listChat.clear();
							for(int i=0;i<jsonArray.length();i++)
							{
								tvToolBar.setText(new JSONObject(jsonArray.getString(i)).getString("vendor_name"));
								String message=new JSONObject(jsonArray.getString(i)).getString("message");
								String msg_time=new JSONObject(jsonArray.getString(i)).getString("msg_time");	
								HashMap<String, String> hashMap=new HashMap<String,String>();
								hashMap.put("message",message);
								hashMap.put("msg_time", msg_time);
								adapterChat.listChat.add(hashMap);
								adapterChat.notifyDataSetChanged();
							}
						}
					} catch (Exception e) {
						e.getMessage();
					}
				}
				
				if(adapterChat.getCount()==0){
					TextView empty_view = (TextView)findViewById(R.id.empty_view);
					empty_view.setVisibility(View.VISIBLE);
				}
				/*
				try {
					JSONObject jsonObj = new JSONObject(response);
					String _result = jsonObj.getString("result");
					String json = jsonObj.getString("json");
					if(_result.equalsIgnoreCase("error"))
					{
						String errorMessage=jsonObj.getString("code_string");
						ErrorDialog dialog=new ErrorDialog();
						dialog.newInstance(mContext, _result.toUpperCase(), errorMessage,null);
						dialog.setCancelable(false);
						dialog.show(getFragmentManager(), "test");
					}
					else if(_result.equalsIgnoreCase("success")){
						etMessage.setText("");
						String message=new JSONObject(json).getString("message");
						String msg_time=new JSONObject(json).getString("msg_time");
						tvToolBar.setText(new JSONObject(json).getString("receiver_name"));
						HashMap<String, String> hashMap=new HashMap<String,String>();
						hashMap.put("message",message);
						hashMap.put("msg_time", msg_time);
						adapterChat.listChat.add(hashMap);
						adapterChat.notifyDataSetChanged();
					}
				} catch (Exception e) {
					e.getMessage();
				}*/
			}
		}
	}

	// Create GetData Metod
	public  void  SetData()  throws  UnsupportedEncodingException
	{
		// Create data variable for sent values to server  
		String data="";
		String identifier=PreferenceUtil.getInstance().getIdentifier();
		// Send POST data request
		String from_to="v2c";
		String receiver_email="customer@wedwise.in";
		if(url.equals(GlobalCommonValues.CUSTOMER_VENDOR_MESSAGE_CREATE))
		{
			String message=etMessage.getText().toString();

			data= URLEncoder.encode("identifier", "UTF-8") 
					+ "=" + URLEncoder.encode(identifier, "UTF-8"); 

			data += "&" + URLEncoder.encode("receiver_email", "UTF-8") + "="
					+ URLEncoder.encode(receiver_email, "UTF-8"); 

			data += "&" + URLEncoder.encode("message", "UTF-8") + "="
					+ URLEncoder.encode(message, "UTF-8"); 

			data += "&" + URLEncoder.encode("from_to", "UTF-8") 
					+ "=" + URLEncoder.encode(from_to,"UTF-8");
			
			data += "&" + URLEncoder.encode("msg_type", "UTF-8") 
					+ "=" + URLEncoder.encode("message","UTF-8");
			
		}
		else if(url.equals(GlobalCommonValues.CUSTOMER_VENDOR_MESSAGE_DETAIL))
		{
			String page_no=page_count;
			data= URLEncoder.encode("identifier", "UTF-8") 
					+ "=" + URLEncoder.encode(identifier, "UTF-8"); 

			data += "&" + URLEncoder.encode("receiver_email", "UTF-8") + "="
					+ URLEncoder.encode(receiver_email, "UTF-8"); 

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
			if(url.equals(GlobalCommonValues.CUSTOMER_VENDOR_MESSAGE_CREATE)){
				_url= new URL(GlobalCommonValues.CUSTOMER_VENDOR_MESSAGE_CREATE);
			}
			else if(url.equals(GlobalCommonValues.CUSTOMER_VENDOR_MESSAGE_DETAIL)){
				_url= new URL(GlobalCommonValues.CUSTOMER_VENDOR_MESSAGE_DETAIL);
			}
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
	public boolean onPrepareOptionsMenu(Menu menu) {
		MenuItem item= menu.findItem(R.id.action_settings);
		item.setVisible(false);
		return true;
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(R.anim.right_in, R.anim.right_out);
	}
}

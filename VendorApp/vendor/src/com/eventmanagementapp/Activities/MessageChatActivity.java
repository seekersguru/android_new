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
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONArray;
import org.json.JSONObject;

import com.eventmanagementapp.R;
import com.eventmanagementapp.adapter.ChatAdapter;
import com.eventmanagementapp.common.GlobalCommonMethods;
import com.eventmanagementapp.common.GlobalCommonValues;
import com.eventmanagementapp.dialogs.ErrorDialog;
import com.eventmanagementapp.util.CustomFonts;
import com.eventmanagementapp.util.PreferenceUtil;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class MessageChatActivity extends FragmentActivity{

	Toolbar toolbar;
	Context mContext;
	ImageView imViewOverflowMenuicon,imViewAttachment;
	Button btnBack,btnSendMessage;
	TextView tvToolBar;
	EditText etMessage;
	ListView lvChatMessages;
	ChatAdapter adapterChat;
	//ArrayList<HashMap<String, String>> listChat;
	String response,url;
	ProgressDialog progress;
	String page_count="1";
	String _receiver_email;
	boolean isMinId=false,isMaxId=false;
	String min="0",max="-1";
	boolean isWebServiceCalled=true;
	private String chatId = "";
	ArrayList<HashMap<String, String>> listData= new ArrayList<HashMap<String, String>>();
	//	static boolean isFirstTime=true;
	int count=0;

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
		btnBack.setVisibility(View.GONE);
		tvToolBar=(TextView)toolbar.findViewById(R.id.tvToolBar);
		etMessage=(EditText) findViewById(R.id.etMessage);
		btnSendMessage=(Button) findViewById(R.id.btnSendMessage);
		imViewOverflowMenuicon=(ImageView)toolbar.findViewById(R.id.imViewOverFlow);
		imViewAttachment=(ImageView) toolbar.findViewById(R.id.imViewAttachment);
		imViewOverflowMenuicon.setBackgroundResource(R.drawable.overflow_menu);
		CustomFonts.setFontOfTextView(mContext,tvToolBar,"fonts/GothamRoundedBook.ttf");
		//imViewAttachment.setVisibility(View.VISIBLE);
		//imViewOverflowMenuicon.setVisibility(View.VISIBLE);
		//		tvToolBar.setText("Sujata Weds Rajesh");
		tvToolBar.setTextColor(Color.parseColor("#555555"));
		btnBack.setBackground(MessageChatActivity.this.getResources().getDrawable(R.drawable.back_orange));
		//		listChat=new ArrayList<HashMap<String, String>>();
		//		CustomFonts.setFontOfTextView(mContext, tvToolBar, "fonts/GothamRnd-Light.otf");
		adapterChat=new ChatAdapter(MessageChatActivity.this,listData);
		lvChatMessages.setAdapter(adapterChat);
		if(getIntent()!=null && getIntent().getExtras()!=null)
		{
			_receiver_email=getIntent().getExtras().getString("receiver_email");
		}
		/*url=GlobalCommonValues.CUSTOMER_VENDOR_MESSAGE_DETAIL;
		new HttpAsyncTask().execute(url);
		isWebServiceCalled=false;*/

		btnSendMessage.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				url=GlobalCommonValues.CUSTOMER_VENDOR_MESSAGE_CREATE;
				new HttpAsyncTask().execute(url);
			}
		});

		lvChatMessages.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				isWebServiceCalled=true;
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
				//				Toast.makeText(getApplicationContext(), "2", 1000).show();
				if(isWebServiceCalled)
				{
					isWebServiceCalled=false;
					if (firstVisibleItem == 0) {
						// check if we reached the top or bottom of the list
						View v = lvChatMessages.getChildAt(0);
						int offset = (v == null) ? 0 : v.getTop();
						if (offset == 0) {
							isMinId=true;
							isMaxId=false;
							if(adapterChat.listChat!=null && !adapterChat.listChat.isEmpty())
							{
								min = String.valueOf(adapterChat.listChat.get(0).get("id"));
								max="-1";
							}
							url=GlobalCommonValues.CUSTOMER_VENDOR_MESSAGE_DETAIL;
							new HttpAsyncTask().execute(url);
							// reached the top:
							return;
						} 
					} else if (totalItemCount - visibleItemCount == firstVisibleItem){
						View v =  lvChatMessages.getChildAt(totalItemCount-1);
						int offset = (v == null) ? 0 : v.getTop();
						if (offset == 0) {
							isMinId=false;
							isMaxId=true;
							if(adapterChat.listChat!=null && !adapterChat.listChat.isEmpty())
							{
								max = String.valueOf(adapterChat.listChat.get(adapterChat.listChat.size()-1).get("id"));
								min = "-1";
							}
							url=GlobalCommonValues.CUSTOMER_VENDOR_MESSAGE_DETAIL;
							new HttpAsyncTask().execute(url);
							// reached the top:
							return;
						}
					}
				}
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

	protected void onDestroy() {
		super.onDestroy();
		//		isFirstTime=false;
	};

	@Override
	protected void onResume() {
		super.onResume();
		callAsynchronousTask();
	}

	public void callAsynchronousTask() {
		final Handler handler = new Handler();
		Timer timer = new Timer();
		TimerTask doAsynchronousTask = new TimerTask() {       
			@Override
			public void run() {
				handler.post(new Runnable() {
					public void run() {       
						try {
							isMinId=false;
							isMaxId=true;
							if(adapterChat.listChat!=null && !adapterChat.listChat.isEmpty())
							{
								max = String.valueOf(adapterChat.listChat.get(adapterChat.listChat.size()-1).get("id"));
								min = "-1";
							}
							url=GlobalCommonValues.CUSTOMER_VENDOR_MESSAGE_DETAIL;
							new HttpAsyncTask().execute(url);
							//HttpAsyncTask performBackgroundTask = new HttpAsyncTask();
							// PerformBackgroundTask this class is the class that extends AsynchTask 
							new HttpAsyncTask().execute();
						} catch (Exception e) {
							// TODO Auto-generated catch block
						}
					}
				});
			}
		};
		timer.schedule(doAsynchronousTask, 0,30000); //execute in every 50000 ms
	}

	ArrayList<HashMap<String, String>> _listChat=new ArrayList<HashMap<String,String>>();
	private class HttpAsyncTask extends AsyncTask<String, Void, Void> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			if(url.equals(GlobalCommonValues.CUSTOMER_VENDOR_MESSAGE_DETAIL))
			{
				/*if(progress==null)
				{
					progress=new ProgressDialog(mContext);
					progress.show();		
				}*/
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
			/*if(url.equals(GlobalCommonValues.CUSTOMER_VENDOR_MESSAGE_DETAIL))
			{
				if(progress!=null && progress.isShowing())
				{
					progress.dismiss();
					progress=null;
				}
			}*/
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
							//							String id=new JSONObject(json).getString("id");
							tvToolBar.setText(new JSONObject(json).getString("receiver_name"));
							String from_to=new JSONObject(response).getJSONObject("request_data").getString("from_to");
							HashMap<String, String> hashMap=new HashMap<String,String>();
							hashMap.put("message",message);
							hashMap.put("msg_time", msg_time);
							hashMap.put("from_to", from_to);
							//							hashMap.put("id", id);
							_listChat.add(hashMap);
						}
						for(int i=0;i<_listChat.size();i++)
						{
							adapterChat.listChat.add(_listChat.get(i));	
						}
						adapterChat.notifyDataSetChanged();
						lvChatMessages.setSelection(adapterChat.listChat.size()-1);
					}
					catch(Exception e)
					{
						e.getMessage();
					}
				}
				else if(url.equals(GlobalCommonValues.CUSTOMER_VENDOR_MESSAGE_DETAIL))
				{
					// In case of fetching message listing
					boolean isChanged = false;
					try {

						Log.d("MessageChat", "Response :"+response);
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
							ArrayList<HashMap<String,String>> listChatLocal = new ArrayList<HashMap<String,String>>();
							//listChatLocal=adapterChat.listChat;
							JSONArray jsonArray = jsonObj.getJSONArray("json");
							//adapterChat.listChat.clear();
							for(int i=0;i<jsonArray.length();i++)
							{
								tvToolBar.setText(new JSONObject(jsonArray.getString(i)).getString("vendor_name"));
								String message=new JSONObject(jsonArray.getString(i)).getString("message");
								String msg_time=new JSONObject(jsonArray.getString(i)).getString("msg_time");	
								String id=new JSONObject(jsonArray.getString(i)).getString("id");
								String from_to=new JSONObject(jsonArray.getString(i)).getString("from_to");
								HashMap<String, String> hashMap=new HashMap<String,String>();
								hashMap.put("message",message);
								hashMap.put("msg_time", msg_time);
								hashMap.put("from_to",from_to);
								hashMap.put("id", id);
								listData.add(hashMap);
								listChatLocal.add(hashMap);

								if(chatId.equalsIgnoreCase("") || !chatId.equalsIgnoreCase(adapterChat.listChat.get(adapterChat.listChat.size()-1).get("id").toString())){
									chatId = hashMap.get("id");
									adapterChat.listChat.add(hashMap);
									isChanged=true;
									//									isChanged = true;
									//									chatId = hashMap.get("id");
									//									adapterChat.listChat.add(hashMap);
								}
							}

							adapterChat.notifyDataSetChanged();
							if(isChanged)
								lvChatMessages.setSelection(adapterChat.listChat.size()-1);
						}
					} catch (Exception e) {
						e.getMessage();
					}
				}

				if(adapterChat.getCount()==0){
					TextView empty_view = (TextView)findViewById(R.id.empty_view);
					empty_view.setVisibility(View.VISIBLE);
				}
			}
		}
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		isWebServiceCalled=true;
	}

	// Create GetData Metod
	public  void  SetData()  throws  UnsupportedEncodingException
	{
		// Create data variable for sent values to server  
		String data="";
		String identifier=PreferenceUtil.getInstance().getIdentifier();
		// Send POST data request
		String from_to="v2c";
		String receiver_email=_receiver_email;//"customer@wedwise.in";
		if(url.equals(GlobalCommonValues.CUSTOMER_VENDOR_MESSAGE_CREATE))
		{
			String message=etMessage.getText().toString();
			// customer@wedwise.in:fRITsf8kb60QSc6r3eAJqz0rqZA
			data= URLEncoder.encode("identifier", "UTF-8") 
					+ "=" + URLEncoder.encode(identifier, "UTF-8"); 

			data += "&" + URLEncoder.encode("receiver_email", "UTF-8") + "="
					+ URLEncoder.encode(receiver_email, "UTF-8"); 

			data += "&" + URLEncoder.encode("message", "UTF-8") + "="
					+ URLEncoder.encode(message, "UTF-8"); 

			data += "&" + URLEncoder.encode("from_to", "UTF-8") 
			+ "=" + URLEncoder.encode(from_to,"UTF-8");

			data += "&" + URLEncoder.encode("action", "UTF-8") 
			+ "=" + URLEncoder.encode("customer_vendor_message_create","UTF-8");

			data += "&" + URLEncoder.encode("mode", "UTF-8") 
			+ "=" + URLEncoder.encode("android","UTF-8");

			data += "&" + URLEncoder.encode("device_id", "UTF-8") 
			+ "=" + URLEncoder.encode("123456","UTF-8");

			data += "&" + URLEncoder.encode("push_data", "UTF-8") 
			+ "=" + URLEncoder.encode("message push","UTF-8");

			data += "&" + URLEncoder.encode("msg_type", "UTF-8") 
			+ "=" + URLEncoder.encode("message","UTF-8");
		}
		else if(url.equals(GlobalCommonValues.CUSTOMER_VENDOR_MESSAGE_DETAIL))
		{
			String page_no=page_count;
			data= URLEncoder.encode("identifier", "UTF-8") 
					+ "=" + URLEncoder.encode(identifier, "UTF-8"); 


			data += "&" + URLEncoder.encode("min", "UTF-8") + "="
					+ URLEncoder.encode(min, "UTF-8");

			data += "&" + URLEncoder.encode("max", "UTF-8") + "="
					+ URLEncoder.encode(max, "UTF-8"); 


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

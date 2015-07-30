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

import com.eventmanagementapp.R;
import com.eventmanagementapp.adapter.MessagesListAdapter;
import com.eventmanagementapp.common.GlobalCommonMethods;
import com.eventmanagementapp.common.GlobalCommonValues;
import com.eventmanagementapp.dialogs.ErrorDialog;
import com.eventmanagementapp.util.CustomFonts;
import com.eventmanagementapp.util.PreferenceUtil;

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
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
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
	boolean isMinId=false,isMaxId=false;
	String min="0",max="-1";
	boolean isWebServiceCalled=true;
	ArrayList<HashMap<String, String>> listData= new ArrayList<HashMap<String, String>>();
//	static boolean isFirstTime=true;

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
		btnBack.setVisibility(View.GONE);
		lvMessages=(ListView)findViewById(R.id.lvMessages);
		listMessages=new ArrayList<HashMap<String, String>>();
		CustomFonts.setFontOfTextView(mContext,tvTitle,"fonts/GothamRoundedBook.ttf");
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
				overridePendingTransition(R.anim.right_in, R.anim.right_out);				
			}
		});

		lvMessages.setOnScrollListener(new OnScrollListener() {
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
						View v = lvMessages.getChildAt(0);
						int offset = (v == null) ? 0 : v.getTop();
						if (offset == 0) {
							isMinId=true;
							isMaxId=false;

							if(adapterMessageList.listChat!=null && !adapterMessageList.listChat.isEmpty())
							{
								min = String.valueOf(adapterMessageList.listChat.get(0).get("id"));
								max="-1";
							}
							url=GlobalCommonValues.CUSTOMER_VENDOR_MESSAGE_LIST;
							new HttpAsyncTask().execute(url);
							// reached the top:
							return;
						} 
					} else if (totalItemCount - visibleItemCount == firstVisibleItem){
						View v =  lvMessages.getChildAt(totalItemCount-1);
						int offset = (v == null) ? 0 : v.getTop();
						if (offset == 0) {
							isMinId=false;
							isMaxId=true;
							if(adapterMessageList.listChat!=null && !adapterMessageList.listChat.isEmpty())
							{
								max = String.valueOf(adapterMessageList.listChat.get(adapterMessageList.listChat.size()-1).get("id"));
								min = "-1";
							}
							url=GlobalCommonValues.CUSTOMER_VENDOR_MESSAGE_LIST;
							new HttpAsyncTask().execute(url);
							// reached the top:
							return;
						}
					}
				}
			}
		});

		Button refreshButton = (Button)findViewById(R.id.refresh_button);
		refreshButton.setVisibility(View.GONE);
		refreshButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				url=GlobalCommonValues.CUSTOMER_VENDOR_MESSAGE_LIST;
				new HttpAsyncTask().execute(url);
			}
		});
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
//		isFirstTime=false;
	}

	//	@Override
	//	protected void onResume() {
	//		// TODO Auto-generated method stub
	//		super.onResume();
	//		url=GlobalCommonValues.CUSTOMER_VENDOR_MESSAGE_LIST;
	//		new HttpAsyncTask().execute(url);
	//		isWebServiceCalled=false;
	//	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		isWebServiceCalled=true;
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
			{  //{"request_data":{"msg_type":"message","min":"0","max":"-1","from_to
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
						//						adapterMessageList.listChat.clear();
						for(int i=0;i<jsonArray.length();i++)
						{
							String receiver_name=new JSONObject(jsonArray.getString(i)).getString("receiver_name");
							String message=new JSONObject(jsonArray.getString(i)).getString("message");
							String msg_time=new JSONObject(jsonArray.getString(i)).getString("msg_time");	
							String receiver_email=new JSONObject(jsonArray.getString(i)).getString("receiver_email");
							String id=new JSONObject(jsonArray.getString(i)).getString("id");
							HashMap<String, String> hashMap=new HashMap<String,String>();
							hashMap.put("receiver_name",receiver_name);
							hashMap.put("message",message);
							hashMap.put("msg_time", msg_time);
							hashMap.put("receiver_email", receiver_email);
							hashMap.put("id", id);
							listData.add(hashMap);
							adapterMessageList.listChat.add(hashMap);
							
						}
						adapterMessageList.notifyDataSetChanged();
						/*if(!isFirstTime)
						{
							if(new JSONObject(response).getJSONObject("request_data").getString("min").equals("-1"))
							{
								//Post Append
								for(int i=0;i<listData.size();i++)
									adapterMessageList.listChat.add(listData.get(i));
								adapterMessageList.notifyDataSetChanged();
							}
							else if(new JSONObject(response).getJSONObject("request_data").getString("max").equals("-1"))
							{
								ArrayList<HashMap<String, String>> listDataTemp= new ArrayList<>();
								//Pre Append
								if(adapterMessageList.listChat!=null && !adapterMessageList.listChat.isEmpty())
								{
									for(int i=0;i<adapterMessageList.listChat.size();i++)
									{
										listDataTemp.add(adapterMessageList.listChat.get(i));
									}
									adapterMessageList.listChat.clear();
									for(int i=0;i<listData.size();i++)
									{
										adapterMessageList.listChat.add(listData.get(i));
									}
									for(int i=0;i<listDataTemp.size();i++)
									{
										adapterMessageList.listChat.add(listDataTemp.get(i));
									}
								}
								adapterMessageList.notifyDataSetChanged();
							}
						}*/
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
			data= URLEncoder.encode("identifier", "UTF-8") //customer@wedwise.in:fRITsf8kb60QSc6r3eAJqz0rqZA
					+ "=" + URLEncoder.encode(identifier, "UTF-8"); 

			data += "&" + URLEncoder.encode("min", "UTF-8") + "="
					+ URLEncoder.encode(min, "UTF-8");

			data += "&" + URLEncoder.encode("max", "UTF-8") + "="
					+ URLEncoder.encode(max, "UTF-8"); 


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

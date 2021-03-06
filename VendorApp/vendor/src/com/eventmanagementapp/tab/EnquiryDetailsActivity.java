package com.eventmanagementapp.tab;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.eventmanagementapp.R;
import com.eventmanagementapp.adapter.EnquiryDetailsAdapter;
import com.eventmanagementapp.bean.ButtonBean;
import com.eventmanagementapp.bean.EnquiryDetailsDataBean;
import com.eventmanagementapp.common.GlobalCommonMethods;
import com.eventmanagementapp.common.GlobalCommonValues;
import com.eventmanagementapp.util.PreferenceUtil;
import com.eventmanagementapp.util.ShowDialog;

public class EnquiryDetailsActivity extends FragmentActivity implements OnClickListener{

	Button btnBack;
	TextView tvTitle;
	ListView lvPrice;
	Button btnAccept,btnReject;
	EnquiryDetailsAdapter adapter;
	ArrayList<EnquiryDetailsDataBean> listData=new ArrayList<EnquiryDetailsDataBean>();
	ArrayList<ButtonBean> buttonList = new ArrayList<ButtonBean>();
	ProgressDialog progress;
	private String response;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.enquirydetails);
		btnBack=(Button) findViewById(R.id.btnBack);
		tvTitle=(TextView) findViewById(R.id.tvTitle);
		lvPrice=(ListView) findViewById(R.id.lvPrice);
		btnAccept=(Button) findViewById(R.id.btnAccept);
		btnReject=(Button) findViewById(R.id.btnReject);
		btnAccept.setOnClickListener(this);
		btnReject.setOnClickListener(this);
		btnBack.setOnClickListener(this);

		EnquiryDetailsDataBean enquiryDataBean=new EnquiryDetailsDataBean("Date", "From","Fax","Rate");
		listData.add(enquiryDataBean);
		EnquiryDetailsDataBean enquiryDataBean2=new EnquiryDetailsDataBean("18/6/2015", "Us","350","1800+");
		listData.add(enquiryDataBean2);
		listData.add(enquiryDataBean2);
		listData.add(enquiryDataBean2);
		listData.add(enquiryDataBean2);
		listData.add(enquiryDataBean2);
		listData.add(enquiryDataBean2);
		listData.add(enquiryDataBean2);

		adapter=new EnquiryDetailsAdapter(EnquiryDetailsActivity.this, listData);
		lvPrice.setAdapter(adapter);

		checkInternetConnection(getIntent().getIntExtra("id", 0),"data");
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnBack:
			finish();		
			overridePendingTransition(R.anim.right_in, R.anim.right_out);
			break;
		case R.id.btnAccept:
			int id = (int) btnAccept.getTag();
			checkInternetConnection(id,"button");
			break;
		case R.id.btnReject:
			int id1 = (int) btnReject.getTag();
			checkInternetConnection(id1,"button");
			break;
		default:
			break;
		}
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		finish();		
		overridePendingTransition(R.anim.right_in, R.anim.right_out);
	}

	private void checkInternetConnection(int id,String type)
	{
		if(GlobalCommonMethods.isNetworkAvailable(getApplicationContext()))
		{
			if(type.equalsIgnoreCase("data")){
				String url=GlobalCommonValues.CUSTOMER_VENDOR_MESSAGE_LIST;
				new HttpAsyncTask(id,"data").execute(url);
			}else{
				// request for buttons
				String url=GlobalCommonValues.VENDOR_BID_BOOK_RESPONSE;
				new HttpAsyncTask(id,"button").execute(url);
			}
			
		}
		else{
			ShowDialog.displayDialog(EnquiryDetailsActivity.this,"Connection error:","No Internet Connection");
		}
	}

	private class HttpAsyncTask extends AsyncTask<String, Void, Void> {
		int id = 0;
		String type = "";
		public HttpAsyncTask(int id,String type){
			this.id = id;
			this.type = type;
		}
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			if(progress==null)
			{
				progress=new ProgressDialog(EnquiryDetailsActivity.this);
				progress.show();		
			}
		}

		@Override
		protected Void doInBackground(String... params) {
			try {
				// Calling method for setting to be sent to the server
				SetData(id,type);
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
				try {
					if(type.equalsIgnoreCase("data")){
					JSONObject jsonObj = new JSONObject(response);
					String _result = jsonObj.getString("result");
					if(_result.equalsIgnoreCase("success")){
						JSONObject jObject = jsonObj.getJSONObject("json");
						tvTitle.setText(jObject.getString("label"));

						LinearLayout parent_layout = (LinearLayout)findViewById(R.id.parent_layout);
						JSONArray jArray = jObject.getJSONArray("table");
						for(int i=0;i<jArray.length();i++){
							View child = getLayoutInflater().inflate(R.layout.enquiry_item_layout, null);
							String key = jArray.getJSONObject(i).keys().next().trim();
							((TextView)child.findViewById(R.id.key_label)).setText(key);
							((TextView)child.findViewById(R.id.value_label)).setText(jArray.getJSONObject(i).getString(key));
							parent_layout.addView(child);
						}
						
						//get buttons here
						JSONArray buttonArray = jObject.getJSONArray("buttons");
						if(buttonArray.length()==0){
						 ((LinearLayout)findViewById(R.id.button_layout)).setVisibility(View.GONE);
						}
						for(int i=0;i<buttonArray.length();i++){
							ButtonBean buttonDetail = new ButtonBean();
							buttonDetail.buttonCode = Integer.parseInt(buttonArray.getJSONArray(i).getString(0));
							buttonDetail.buttonName = buttonArray.getJSONArray(i).getString(1);
							
							if(buttonDetail.buttonName.equalsIgnoreCase("accept")){
								btnAccept.setVisibility(View.VISIBLE);
								btnAccept.setTag(buttonDetail.buttonCode);
							}else if(buttonDetail.buttonName.equalsIgnoreCase("reject")){
								btnReject.setVisibility(View.VISIBLE);
								btnReject.setTag(buttonDetail.buttonCode);
							}
							
							//buttonList.add(buttonDetail);
						}
						
						
					}
					}else{
						// parsing response of Accept/Reject button
						JSONObject jsonObj = new JSONObject(response);
						String _result = jsonObj.getString("result");
						if(_result.equalsIgnoreCase("success")){
							String message = jsonObj.getJSONObject("json").getString("label");
							ShowDialog.displayDialog(EnquiryDetailsActivity.this,"Status",message);
						}
					}
				} catch (Exception e) {
					e.getMessage();
				}
			}
				
		}
	}

	// Create GetData Metod
	public  void  SetData(int id,String requestType)  throws  UnsupportedEncodingException
	{
		// Create data variable for sent values to server  
		String data="";
		if(requestType.equalsIgnoreCase("data")){
			data= URLEncoder.encode("identifier", "UTF-8") 
					+ "=" + URLEncoder.encode(PreferenceUtil.getInstance().getIdentifier(), "UTF-8"); 

			data += "&" + URLEncoder.encode("msg_id", "UTF-8") + "="
					+ URLEncoder.encode(""+id, "UTF-8"); 

			data += "&" + URLEncoder.encode("msg_type", "UTF-8") 
					+ "=" + URLEncoder.encode(getIntent().getStringExtra("type"), "UTF-8");
		}else{
			data= URLEncoder.encode("identifier", "UTF-8") 
					+ "=" + URLEncoder.encode(PreferenceUtil.getInstance().getIdentifier(), "UTF-8"); 

			data += "&" + URLEncoder.encode("msg_id", "UTF-8") + "="
					+ URLEncoder.encode(""+getIntent().getIntExtra("id", 0), "UTF-8"); 

			data += "&" + URLEncoder.encode("status", "UTF-8") 
					+ "=" + URLEncoder.encode(""+id, "UTF-8");
		}
		

		BufferedReader reader=null;

		// Send data 
		try
		{ 
			URL _url=null;
			// Defined URL  where to send data
			if(requestType.equalsIgnoreCase("data")){
				_url= new URL(GlobalCommonValues.VENDOR_BID_BOOK_DETAIL);
			}else{
				_url= new URL(GlobalCommonValues.VENDOR_BID_BOOK_RESPONSE);
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
			System.out.println("response is bidv:"+response);
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

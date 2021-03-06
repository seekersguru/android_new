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
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.eventmanagementapp.R;
import com.eventmanagementapp.adapter.BookListAdapter;
import com.eventmanagementapp.bean.BookingDataBean;
import com.eventmanagementapp.common.GlobalCommonMethods;
import com.eventmanagementapp.common.GlobalCommonValues;
import com.eventmanagementapp.util.PreferenceUtil;
import com.eventmanagementapp.util.ShowDialog;

/**
 */
public class BookTab extends Fragment {

	ListView lvBook;
	ArrayList<BookingDataBean> listMessages;
	BookListAdapter adapterMessageList;
	ProgressDialog progress;
	private String response;
	TextView empty_view;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.booktab,container,false);
		idInitialization(v);
		return v;
	}

	private void idInitialization(View view)
	{
		lvBook=(ListView) view.findViewById(R.id.lvBook);
		empty_view = (TextView)view.findViewById(R.id.empty_view);
		
		listMessages=new ArrayList<BookingDataBean>();
		checkInternetConnection();
		/*listMessages.add("Andy Lau");
		listMessages.add("James Moore");
		listMessages.add("Jorgen Flood");
		listMessages.add("Claude");
		listMessages.add("Stefanos Fanidis");
		listMessages.add("James Moore");
		listMessages.add("James Moore");
		listMessages.add("James Moore");
		listMessages.add("James Moore");
		listMessages.add("James Moore");*/
		adapterMessageList=new BookListAdapter(getActivity(), listMessages);
		lvBook.setAdapter(adapterMessageList);
		lvBook.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				BookingDataBean	bidDetail = listMessages.get(position);
				Intent myIntent=new Intent(getActivity(),EnquiryDetailsActivity.class);
				myIntent.putExtra("id", bidDetail.id);
				myIntent.putExtra("type","book");
				startActivity(myIntent);
				getActivity().overridePendingTransition(R.anim.right_in, R.anim.left_out);	
			}
		});
	}

	private void checkInternetConnection()
	{
		if(GlobalCommonMethods.isNetworkAvailable(getActivity().getApplicationContext()))
		{
			String url=GlobalCommonValues.CUSTOMER_VENDOR_MESSAGE_LIST;
			new HttpAsyncTask().execute(url);
		}
		else{
			ShowDialog.displayDialog(getActivity().getApplicationContext(),"Connection error:","No Internet Connection");
		}
	}

	private class HttpAsyncTask extends AsyncTask<String, Void, Void> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			if(progress==null)
			{
				progress=new ProgressDialog(getActivity());
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
				try {
					JSONObject jsonObj = new JSONObject(response);
					String _result = jsonObj.getString("result");
					if(_result.equalsIgnoreCase("success")){
						JSONArray jArray = jsonObj.getJSONArray("json");
						for(int i=0;i<jArray.length();i++){
							BookingDataBean bean = new BookingDataBean();
							bean.msg_type = jArray.getJSONObject(i).getString("msg_type");
							bean.receiver_email = jArray.getJSONObject(i).getString("receiver_email");
							bean.msg_time= jArray.getJSONObject(i).getString("msg_time");
							bean.message= jArray.getJSONObject(i).getString("message");
							bean.id= jArray.getJSONObject(i).getInt("id");
							bean.line2= jArray.getJSONObject(i).getString("line2");
							bean.line1= jArray.getJSONObject(i).getString("line1");
							bean.from_to= jArray.getJSONObject(i).getString("from_to");
							bean.event_date= jArray.getJSONObject(i).getString("event_date");
							bean.receiver_name= jArray.getJSONObject(i).getString("receiver_name");
							bean.identifier= jArray.getJSONObject(i).getString("identifier");
							
							listMessages.add(bean);
						}
					}
					adapterMessageList.notifyDataSetChanged();
					if(listMessages.size()==0){
						empty_view.setVisibility(View.VISIBLE);
					}
				} catch (Exception e) {
					e.getMessage();
				}
			}
		}
	}

	// Create GetData Metod
	public  void  SetData()  throws  UnsupportedEncodingException
	{
		// Create data variable for sent values to server  
		String data="";
		data= URLEncoder.encode("identifier", "UTF-8") 
				+ "=" + URLEncoder.encode(PreferenceUtil.getInstance().getIdentifier(), "UTF-8"); 

		data += "&" + URLEncoder.encode("page_no", "UTF-8") + "="
				+ URLEncoder.encode("1", "UTF-8"); 

		data += "&" + URLEncoder.encode("from_to", "UTF-8") + "="
				+ URLEncoder.encode("v2c", "UTF-8"); 

		data += "&" + URLEncoder.encode("msg_type", "UTF-8") 
				+ "=" + URLEncoder.encode("book", "UTF-8");

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
			System.out.println("response is:"+response);
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
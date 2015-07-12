/*package com.eventmanagementapp.tab;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
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

import com.eventmanagementapp.R;
import com.eventmanagementapp.Activities.MessageChatActivity;
import com.eventmanagementapp.adapter.MessagesListAdapter;
import com.eventmanagementapp.common.GlobalCommonMethods;
import com.eventmanagementapp.common.GlobalCommonValues;

*//**
 *//*
public class MessageTab extends Fragment {

	ListView lvMessages;
	ArrayList<String> listMessages;
	Context mContext;
	MessagesListAdapter adapterMessageList;
	ProgressDialog progress;
	String response;
	String url;

	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.messagetab,container,false);
		mContext=getActivity();
		idInitialization(v);
		return v;
	}

	private void idInitialization(View view)
	{
		lvMessages=(ListView) view.findViewById(R.id.lvMessages);
		listMessages=new ArrayList<String>();
		listMessages.add("Hi");
		listMessages.add("Hi");
		listMessages.add("Hi");
		listMessages.add("Hi");
		listMessages.add("Hi");
		listMessages.add("Hi");
		adapterMessageList=new MessagesListAdapter(getActivity(), listMessages);
		lvMessages.setAdapter(adapterMessageList);

		lvMessages.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent myIntent=new Intent(getActivity(),MessageChatActivity.class);
				getActivity().startActivity(myIntent);
				getActivity().overridePendingTransition(R.anim.right_in, R.anim.left_out);
			}
		});
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
					String message = jsonObj.getString("message");
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
		String identifier="";
		String receiver_email="test@test.com";	
		String message="";
		String from_to="";

		data= URLEncoder.encode("identifier", "UTF-8") 
				+ "=" + URLEncoder.encode(identifier, "UTF-8"); 

		data += "&" + URLEncoder.encode("receiver_email", "UTF-8") + "="
				+ URLEncoder.encode(receiver_email, "UTF-8"); 

		data += "&" + URLEncoder.encode("message", "UTF-8") + "="
				+ URLEncoder.encode(message, "UTF-8"); 

		data += "&" + URLEncoder.encode("from_to", "UTF-8") 
				+ "=" + URLEncoder.encode("c2v", "UTF-8");

		BufferedReader reader=null;
		// Send data 
		try
		{ 
			URL _url=null;
			// Defined URL  where to send data
			_url= new URL(GlobalCommonValues.CUSTOMER_VENDOR_MESSAGE_CREATE);
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
}*/
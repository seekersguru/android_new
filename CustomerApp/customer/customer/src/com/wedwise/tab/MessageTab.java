package com.wedwise.tab;

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
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import com.wedwise.adapter.MessagesListAdapter;
import com.wedwise.chat.MessageChatActivity;
import com.wedwise.common.GlobalCommonMethods;
import com.wedwise.common.GlobalCommonValues;
import com.wedwise.dialogs.ErrorDialog;
import com.wedwiseapp.R;
import com.wedwiseapp.util.PreferenceUtil;

/**
 */
public class MessageTab extends Fragment {

	ListView lvMessages;
//	ArrayList<String> listMessages;
	MessagesListAdapter adapterMessageList;
	Context mContext;
	ArrayList<HashMap<String, String>> listChat;
	String response,url,responseMessageList;
	ProgressDialog progress;
	String page_count="1";

	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.messagetab,container,false);
		idInitialization(v);
		mContext=getActivity();
		return v;
	}

	private void idInitialization(View view)
	{
		lvMessages=(ListView) view.findViewById(R.id.lvMessages);
		listChat=new ArrayList<HashMap<String,String>>();
		url=GlobalCommonValues.CUSTOMER_VENDOR_MESSAGE_LIST;
		new HttpAsyncTask().execute(url);
		adapterMessageList=new MessagesListAdapter(getActivity(), listChat);
		lvMessages.setAdapter(adapterMessageList);
		lvMessages.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent myIntent=new Intent(getActivity(),MessageChatActivity.class);
				/*getActivity().*/startActivity(myIntent);
				getActivity().overridePendingTransition(R.anim.right_in, R.anim.left_out);
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
					progress=new ProgressDialog(getActivity());
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
							dialog.newInstance(getActivity(), _result.toUpperCase(), errorMessage,null);
							dialog.setCancelable(false);
							dialog.show(getActivity().getFragmentManager(), "test");
						}
						else if(_result.equalsIgnoreCase("success")){
							JSONArray jsonArray = jsonObj.getJSONArray("json");
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
}
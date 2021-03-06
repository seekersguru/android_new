package com.wedwiseapp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Window;

import com.google.gson.Gson;
import com.wedwise.common.GlobalCommonMethods;
import com.wedwise.common.GlobalCommonValues;
import com.wedwise.gson.VendorDetail;

public class VendorDetailsActivity2 extends FragmentActivity{

	Context mContext;
	String response="",url="";
	private final String TAG = "VendorDetailsActivity2";
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.vendor_details);
		mContext = this;
		new HttpAsyncTask().execute();
	}
	
	private class HttpAsyncTask extends AsyncTask<String, Void, Void> {
		
		ProgressDialog progress;
		
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
			//			Toast.makeText(getBaseContext(), "Data Sent!"+response, Toast.LENGTH_LONG).show();
			if(progress!=null && progress.isShowing())
			{
				progress.dismiss();
				progress=null;
			}

			if(!TextUtils.isEmpty(response) && GlobalCommonMethods.isJSONValid(response))
			{
//				Log.d(TAG, "response= "+response);
				Gson gson = new Gson();
				VendorDetail vendorDetail  = gson.fromJson(response, VendorDetail.class);
				Log.d(TAG, "vendorDetail= "+vendorDetail);
			}
		}
	}

	// Create GetData Metod
		public  void  SetData()  throws  UnsupportedEncodingException
		{
			// Create data variable for sent values to server  
			String data="";
			String image_type="";
			int density = getResources().getDisplayMetrics().densityDpi;

			if(density==DisplayMetrics.DENSITY_MEDIUM)
			{
				image_type="drawable-hdpi";
			}
			else if(density==DisplayMetrics.DENSITY_HIGH)
			{
				image_type="drawable-xhdpi";
			}
			else if(density==DisplayMetrics.DENSITY_XHIGH)
			{
				image_type="drawable-xhdpi";
			}
			else if(density==DisplayMetrics.DENSITY_XXHIGH)
			{
				image_type="drawable-xxhdpi";
			}
			else if(density==DisplayMetrics.DENSITY_XXXHIGH)
			{
				image_type="drawable-xxxhdpi";
			}

			data= URLEncoder.encode("mode", "UTF-8") 
					+ "=" + URLEncoder.encode("android", "UTF-8"); 

			data += "&" + URLEncoder.encode("image_type", "UTF-8") + "="
					+ URLEncoder.encode(image_type, "UTF-8"); 
			
			data += "&" + URLEncoder.encode("vendor_email", "UTF-8") + "="
					+ URLEncoder.encode("vendor@test.com", "UTF-8"); 

			BufferedReader reader=null;

			// Send data 
			try
			{ 
				URL _url=null;
				// Defined URL  where to send data
				_url= new URL(GlobalCommonValues.CUSTOMER_VENDOR_DETAIL);
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

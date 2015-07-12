package com.wedwise.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class GlobalCommonMethods {
	
	public static boolean isJSONValid(String response) {
	    try {
	        new JSONObject(response);
	    } catch (JSONException ex) {
	        try {
	            new JSONArray(response);
	        } catch (JSONException ex1) {
	            return false;
	        }
	    }
	    return true;
	}
	
	public static boolean isNetworkAvailable(Context mContext) {
	     ConnectivityManager connectivity =(ConnectivityManager)mContext.getSystemService(Context.CONNECTIVITY_SERVICE);

	    if (connectivity == null) {
	        return false;
	    } else {
	        NetworkInfo[] info = connectivity.getAllNetworkInfo();
	        if (info != null) {
	            for (int i = 0; i < info.length; i++) {
	                if (info[i].getState() == NetworkInfo.State.CONNECTED) {
	                    return true;
	                }
	            }
	        }
	    }
	    return false;
	}
	
	public static String convertInputStreamToString(InputStream inputStream) throws IOException{
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;
 
        inputStream.close();
        return result;
 
    } 
	
}

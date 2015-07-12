package com.wedwise.httpconnection;

import java.io.File;

import org.apache.http.entity.StringEntity;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class MyHttpConnection {

	static int timeout=600000;

	private static AsyncHttpClient client=new AsyncHttpClient();

	public static void get(String url,RequestParams params,AsyncHttpResponseHandler responseHandler)
	{
		client.setTimeout(timeout);
		client.get(url, params,responseHandler);
	}
	public static void getWithoutPara(Context mContext,String url,
			AsyncHttpResponseHandler responseHandler)
	{
		client.setTimeout(timeout);
		client.get(mContext,url,responseHandler);
	}

	public static void postWithJsonEntity(Context mContext,String url,StringEntity stringEntity,
			AsyncHttpResponseHandler responseHandler)
	{
		client.setTimeout(timeout);
		client.post(mContext,url, stringEntity,"application/json",responseHandler);
	}

	public static void postWithJsonEntityFromUrlEncoded(Context mContext,String url,StringEntity stringEntity,
			AsyncHttpResponseHandler responseHandler)
	{
		client.setTimeout(timeout);
		client.post(mContext,url, stringEntity,"application/json",responseHandler);
	}
	
	public static void postWithoutJsonEntity(Context mContext,String url,StringEntity stringEntity,
			AsyncHttpResponseHandler responseHandler)
	{
		client.setTimeout(timeout);
		client.post(mContext,url,null,responseHandler);
	}

	public static void postFileWithJsonEntity(Context mContext,String url,File file,
			StringEntity stringEntity,AsyncHttpResponseHandler responseHandler)
	{
		RequestParams requestParams=new RequestParams();
		try {
			requestParams.put("file",file);
		} catch (Exception e) {
			e.getMessage();
		}
		client.setTimeout(timeout);
		client.post(mContext,url,requestParams,responseHandler);
	}

}

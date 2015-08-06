package com.wedwiseapp.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;

public class PreferenceUtil {

	private SharedPreferences sp;
	private SharedPreferences.Editor editor;	
	private static PreferenceUtil instance;

	private PreferenceUtil(Context context) {
		sp = PreferenceManager.getDefaultSharedPreferences(context);
		editor = sp.edit();
	}
	
	public static void init(Context context) {
		instance = new PreferenceUtil(context);
	}
	
	public static PreferenceUtil getInstance(){
		return instance;
	}

	public boolean getWifiMode() {
		boolean wifiMode;
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
			wifiMode = sp.getBoolean("checkWifiMode", false);
		} else {
			wifiMode = sp.getBoolean("switchWifiMode", false);
		}
		return wifiMode;
	}

	public Boolean isRegistered() {
		Boolean isRegistered = sp.getBoolean("isRegistered", false);
		return isRegistered;
	}

	public void setRegister(boolean value) {
		editor.putBoolean("isRegistered", value);
		editor.commit();
	}
	
	public Boolean isLogin() {
		Boolean isLogin = sp.getBoolean("isLogin", false);
		return isLogin;
	}

	public void setLogin(boolean value) {
		editor.putBoolean("isLogin", value);
		editor.commit();
	}
	
	public String getUid() {
		String uid = sp.getString("uid", null);
		return uid;
	}
	
	public void setUid(String uid) {
		editor.putString("uid", uid);
		editor.commit();
	}
	
	public String getIdentifier() {
		String uid = sp.getString("identifier", null);
		return uid;
	}
	
	public void setIdentifier(String identifier) {
		editor.putString("identifier", identifier);
		editor.commit();
	}
	
	public String getEmail() {
		String uid = sp.getString("email", null);
		return uid;
	}
	
	public void setEmail(String email) {
		editor.putString("email", email);
		editor.commit();
	}
	public String getBrideName() {
		String uid = sp.getString("BrideName", null);
		return uid;
	}
	
	public void setBrideName(String email) {
		editor.putString("BrideName", email);
		editor.commit();
	}
	public String getGroomName() {
		String uid = sp.getString("GroomName", null);
		return uid;
	}
	
	public void setGroomName(String groomname) {
		editor.putString("GroomName", groomname);
		editor.commit();
	}
}

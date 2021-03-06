package com.wedwise.gsonmodels;

import org.json.JSONObject;

import com.google.android.gms.maps.model.LatLng;

public class Map_Model extends TypeModel {

	String key_values = "key_values";
	String lattitude = "android_lat";
	String longitude = "android_lang";
	LatLng point;

	public LatLng getPoint() {
		return point;
	}

	public void setPoint(LatLng point) {
		this.point = point;
	}

	public Map_Model(String reponse) {
		super();
		try {
			init(new JSONObject(reponse));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void init(JSONObject JSONObject) throws Exception {
		double latt = 28.613939;
		double lon = 77.209021;
		if(JSONObject.has(lattitude)){
			 point = new
					 LatLng(Double.parseDouble(JSONObject.getString(lattitude)),
					 Double.parseDouble(JSONObject.getString(longitude)));
		}else{
			point = new LatLng(latt, lon);
		}
		
//		point = new LatLng(latt, lon);
	}
}

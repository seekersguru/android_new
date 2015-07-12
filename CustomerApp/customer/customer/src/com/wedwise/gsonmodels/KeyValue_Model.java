package com.wedwise.gsonmodels;

import java.util.HashMap;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONObject;




public class KeyValue_Model extends TypeModel{

	String key_values ="key_values";
	private HashMap<String, String> pairs= new HashMap<String, String>();

	public HashMap<String, String> getPairs() {
		return pairs;
	}

	public KeyValue_Model(String reponse) {
		super();
		try {
			init(new JSONObject(reponse).getJSONArray(key_values));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void init(JSONArray jsonArray) throws Exception{
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);
			Iterator<String> iterator  = jsonObject.keys();
			while(iterator.hasNext()){
				String key = iterator.next();
				pairs.put(key, jsonObject.getString(key));
			}
		}
	}
	
}

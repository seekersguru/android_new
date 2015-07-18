package com.wedwise.gsonmodels;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.jar.Pack200.Packer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wedwise.gsonmodels.KeyValueHeader.KeyValue;
import com.wedwise.json.PackagesModelNew;
import com.wedwise.json.TopPackege;

public class PackagesModel extends TypeModel {

	String key_values = "key_values";
	ArrayList<TopPackege> package_Values = new ArrayList<TopPackege>();

	public ArrayList<TopPackege> getPackage_Values() {
		return package_Values;
	}

	public PackagesModel(String reponse) {
		super();
		try {
			//			init(new JSONObject(reponse).getJSONArray(key_values));
			init(new JSONObject(reponse));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void init(JSONObject jsonObject)  throws Exception{
		Iterator<String> iterator = jsonObject.keys();
		while (iterator.hasNext()) {
			String key = iterator.next();
			if(!key.equals("type")){

				TopPackege tp=new TopPackege();

				String str=jsonObject.get(key).toString();
				String str1=new JSONObject(str).getJSONArray("package_values").toString();
				
				tp.setPackegeType(key);
				tp.setPackegeItems((ArrayList<PackagesModelNew>) new Gson().fromJson(str1, new TypeToken<List<PackagesModelNew>>(){}.getType()));
				//init(jsonArray);
				initarra(new JSONArray(str1),tp.getPackegeItems());
				package_Values.add(tp);

			}

		}
	}

	private void initarra(JSONArray jsonArray,ArrayList<PackagesModelNew> listData){
		try {
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject;

				jsonObject = jsonArray.getJSONObject(i);


				Iterator<String> iterator = jsonObject.keys();
				while (iterator.hasNext()) {
					String key = iterator.next();
					if(key.equals("minimum")){
						listData.get(i).setMinimum(getKeyValueObject(new JSONObject(jsonObject.getJSONObject(key).toString())));
					}else if(key.equals("quoted")){
						listData.get(i).setQuoted(getKeyValueObject(new JSONObject(jsonObject.getJSONObject(key).toString())));
					}else if(key.equals("options")){
						JSONArray arropt=jsonObject.getJSONArray(key);
						ArrayList<KeyValue> keyval=new ArrayList<KeyValueHeader.KeyValue>();
						for (int j = 0; j < arropt.length(); j++) {
							JSONObject jobjoption;

							jsonObject = arropt.getJSONObject(j);
							keyval.add(getKeyValueObject(jsonObject));
						}
						listData.get(i).setOptions(keyval);
					}

				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private KeyValue getKeyValueObject(JSONObject jobj){
		Iterator<String> iterator = jobj.keys();
		while (iterator.hasNext()) {
			String key = iterator.next();
			try {
				KeyValue keyval=new KeyValue(key, jobj.getString(key));
				return keyval;
			} catch (JSONException e) {
				e.printStackTrace();
			}

		}
		return null;
	}

	public static class Package_Values implements Serializable{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		String package_header;
		HashMap<SubSection, HashMap<String, String>> subsection_info = new HashMap<PackagesModel.Package_Values.SubSection, HashMap<String, String>>();

		public String getPackage_header() {
			return package_header;
		}

		public HashMap<SubSection, HashMap<String, String>> getSubsection_info() {
			return subsection_info;
		}

		private Package_Values(String key, JSONObject jsonObject)
				throws Exception {
			package_header = key;
			manageSubSection(jsonObject.getJSONArray("package_values"));
		}

		public static enum SubSection {
			minimum, options, quoted, label;

			public static SubSection getSubSection(String key) throws Exception {

				for (SubSection subSection : SubSection.values()) {
					if (subSection.name().equals(key)) {
						return subSection;
					}
				}
				return null;
			}
		}

		public void manageSubSection(JSONArray jsonArray) throws Exception {
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				Iterator<String> iterator = jsonObject.keys();
				while (iterator.hasNext()) {
					String key = iterator.next();
					SubSection subSection = SubSection.getSubSection(key);

					switch (subSection) {
					case minimum:
					case quoted:
						HashMap<String, String> hashMap = new HashMap<String, String>();
						JSONObject object = jsonObject.getJSONObject(subSection
								.name());
						Iterator<String> iterator2 = object.keys();
						while (iterator2.hasNext()) {
							String key2 = iterator2.next();
							hashMap.put(key2, object.getString(key2));
						}
						subsection_info.put(subSection, hashMap);
						break;
					case options:
						hashMap = new HashMap<String, String>();
						JSONArray array = jsonObject.getJSONArray(subSection
								.name());

						for (int j = 0; j < array.length(); j++) {
							JSONObject jsonObject3 = array.getJSONObject(j);
							Iterator<String> iterator3 = jsonObject3.keys();
							while (iterator3.hasNext()) {
								String key3 = iterator3.next();
								hashMap.put(key3, jsonObject3.getString(key3));
							}
						}
						subsection_info.put(subSection, hashMap);
						break;

					case label:
						hashMap = new HashMap<String, String>();
						hashMap.put(key,
								jsonObject.getString(subSection.name()));
						subsection_info.put(subSection, hashMap);
						break;

					default:
						break;
					}
				}
			}
		}

	}
}

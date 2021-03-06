package com.wedwise.gsonmodels;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONObject;

public class PackagesModel extends TypeModel {

	String key_values = "key_values";
	ArrayList<Package_Values> package_Values = new ArrayList<PackagesModel.Package_Values>();

	public ArrayList<Package_Values> getPackage_Values() {
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
			package_Values.add(new Package_Values(key, jsonObject
					.getJSONObject(key)));
		}
	}

//	private void init(JSONArray jsonArray) throws Exception {
//		for (int i = 0; i < jsonArray.length(); i++) {
//			JSONObject jsonObject = jsonArray.getJSONObject(i);
//			Iterator<String> iterator = jsonObject.keys();
//			while (iterator.hasNext()) {
//				String key = iterator.next();
//				package_Values.add(new Package_Values(key, jsonObject
//						.getJSONObject(key)));
//			}
//		}
//	}

	public static class Package_Values implements Serializable{

		String package_header;
		HashMap<SubSection, HashMap<String, String>> subsection_info = new HashMap<PackagesModel.Package_Values.SubSection, HashMap<String, String>>();

		public String getPackage_header() {
			return package_header;
		}

		public HashMap<SubSection, HashMap<String, String>> getSubsection_info() {
			return subsection_info;
		}

		public Package_Values(String key, JSONObject jsonObject)
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

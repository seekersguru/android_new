package com.wedwise.gsonmodels;

import org.json.JSONObject;

public class ParaModel extends TypeModel{
	
	String key_text = "text";
	String text;

	public String getText() {
		return text;
	}

	public ParaModel(String reponse) {
		super();
		try {
			text = new JSONObject(reponse).getString(key_text);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

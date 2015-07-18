package com.wedwise.common;

import org.json.JSONObject;

public enum WidgetsType {

	key_value("key_value"),
	map("map"),
	para("para"),
	packages("packages"),
	heading("heading");
	
	public String widgettype;
	
	WidgetsType(String type){
		this.widgettype = type;
	}
	
	public static WidgetsType getWidgetsType(JSONObject jsonObject) throws Exception{
		
		String type = jsonObject.getString("type");
		for (WidgetsType widgetsType : WidgetsType.values()) {
			if(widgetsType.widgettype.equals(type)){
				return widgetsType;
			}
		}
		return null;
	}
	
	
}

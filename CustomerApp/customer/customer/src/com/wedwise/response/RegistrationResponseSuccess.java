package com.wedwise.response;

import com.google.gson.annotations.SerializedName;

public class RegistrationResponseSuccess {

	@SerializedName("request_type")
	public String request_type;
	
	@SerializedName("json")
	public String json;
	
	@SerializedName("result")
	public String result;
	
	@SerializedName("error_fields")
	public String error_fields;
	
	@SerializedName("message")
	public String message;
	
	@SerializedName("request_data")
	public RegistrationResponseSuccessDataBean request_data;
	
}

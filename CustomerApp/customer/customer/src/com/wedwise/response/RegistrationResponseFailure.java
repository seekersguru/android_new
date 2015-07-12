package com.wedwise.response;

import com.google.gson.annotations.SerializedName;

public class RegistrationResponseFailure {

	@SerializedName("request_type")
	public String request_type;
	
	@SerializedName("json")
	public int json;
	
	@SerializedName("result")
	public String result;
	
	@SerializedName("error_fields")
	public RegistrationResponseFailureDataBean error_fields;
	
	@SerializedName("message")
	public String message;
	
	@SerializedName("request_data")
	public RegistrationResponseSuccessDataBean request_data;
	
}

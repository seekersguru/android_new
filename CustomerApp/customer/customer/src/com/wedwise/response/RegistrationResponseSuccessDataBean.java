package com.wedwise.response;

import com.google.gson.annotations.SerializedName;

public class RegistrationResponseSuccessDataBean {

	@SerializedName("contact_number")
	public String contact_number;
	
	@SerializedName("password")
	public String password;
	
	@SerializedName("bride_name")
	public String bride_name;
	
	@SerializedName("email")
	public String email;
	
	@SerializedName("groom_name")
	public String groom_name;
	
}

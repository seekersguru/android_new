package com.wedwise.bean;

public class UserRegistrationBean {

	public String email;
	public String password;
	public String groom_name;
	public String bride_name;
	public String contact_number;
	
	public UserRegistrationBean(String email, String password,
			String groom_name, String bride_name, String contact_number) {
		super();
		this.email = email;
		this.password = password;
		this.groom_name = groom_name;
		this.bride_name = bride_name;
		this.contact_number = contact_number;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getGroom_name() {
		return groom_name;
	}

	public void setGroom_name(String groom_name) {
		this.groom_name = groom_name;
	}

	public String getBride_name() {
		return bride_name;
	}

	public void setBride_name(String bride_name) {
		this.bride_name = bride_name;
	}

	public String getContact_number() {
		return contact_number;
	}

	public void setContact_number(String contact_number) {
		this.contact_number = contact_number;
	}
}

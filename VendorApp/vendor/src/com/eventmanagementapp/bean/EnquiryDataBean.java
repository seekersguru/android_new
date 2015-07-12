package com.eventmanagementapp.bean;

public class EnquiryDataBean {

	public String Name;
	public String DateFirst;
	public String DateSecond;
	public String packageDetails;

	public EnquiryDataBean(String name, String dateFirst,
			String dateSecond,String packageDetails) {
		super();
		Name = name;
		DateFirst = dateFirst;
		DateSecond = dateSecond;
		this.packageDetails=packageDetails;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getDateFirst() {
		return DateFirst;
	}

	public void setDateFirst(String dateFirst) {
		DateFirst = dateFirst;
	}

	public String getDateSecond() {
		return DateSecond;
	}

	public void setDateSecond(String dateSecond) {
		DateSecond = dateSecond;
	}

	public String getPackageDetails() {
		return packageDetails;
	}

	public void setPackageDetails(String packageDetails) {
		this.packageDetails = packageDetails;
	}
}

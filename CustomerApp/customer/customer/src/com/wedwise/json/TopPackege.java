package com.wedwise.json;

import java.io.Serializable;
import java.util.ArrayList;

public class TopPackege implements Serializable{
	
	
	private static final long serialVersionUID = 2286496692413651473L;
	private String packegeType;
	private ArrayList<PackagesModelNew> packegeItems;
	
	public String getPackegeType() {
		return packegeType;
	}
	public void setPackegeType(String packegeType) {
		this.packegeType = packegeType;
	}
	public ArrayList<PackagesModelNew> getPackegeItems() {
		return packegeItems;
	}
	public void setPackegeItems(ArrayList<PackagesModelNew> packegeItems) {
		this.packegeItems = packegeItems;
	}
	
}

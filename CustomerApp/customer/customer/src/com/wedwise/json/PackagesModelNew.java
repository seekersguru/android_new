package com.wedwise.json;

import java.io.Serializable;
import java.util.ArrayList;

import com.wedwise.gsonmodels.KeyValueHeader.KeyValue;

public class PackagesModelNew implements Serializable{
	
	private static final long serialVersionUID = 6645351987744187741L;
	private KeyValue  minimum;
	private KeyValue  quoted;
	private String  label;
	private ArrayList<KeyValue>  options;
	
	public KeyValue getMinimum() {
		return minimum;
	}
	public void setMinimum(KeyValue minimum) {
		this.minimum = minimum;
	}
	public KeyValue getQuoted() {
		return quoted;
	}
	public void setQuoted(KeyValue quoted) {
		this.quoted = quoted;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public ArrayList<KeyValue> getOptions() {
		return options;
	}
	public void setOptions(ArrayList<KeyValue> options) {
		this.options = options;
	}
}

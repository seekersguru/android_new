package com.wedwise.gsonmodels;

import java.io.Serializable;
import java.util.ArrayList;

public class KeyValueHeader implements Serializable{

	private static final long serialVersionUID = -1801818956200455042L;

	String header;

	ArrayList<KeyValue> keyValues;

	public ArrayList<KeyValue> getKeyValues() {
		return keyValues;
	}

	public static class KeyValue implements Serializable{

		private static final long serialVersionUID = -6000763268922074631L;
		public KeyValue(String key, String value) {
			this.key = key;
			this.value = value;
		}
		String key;
		public String getKey() {
			return key;
		}
		public String getValue() {
			return value;
		}
		String value;
	}

	public KeyValueHeader(String header) {
		super();
		this.header = header;
		keyValues = new ArrayList<KeyValueHeader.KeyValue>();
	}

	public void addKeyValue(String key, String value) {
		keyValues.add(new KeyValue(key, value));
	}

	public String getHeader() {
		return header;
	}

}

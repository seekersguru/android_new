package com.wedwise.gsonmodels;

import java.io.Serializable;

import com.wedwise.common.WidgetsType;

public class SectionModel implements Serializable {

	TypeModel typeModel;
	WidgetsType widgetsType;
	WidgetsType read_widgetsType;
	TypeModel readTypeModel;
	String header;
	String read_header;
	//
	TypeModel extraTypeModel;
	WidgetsType extraWidgetsType;
	public static String intent_key = "sectionmodel";

	public TypeModel getExtraTypeModel() {
		return extraTypeModel;
	}

	public void setExtraTypeModel(TypeModel extraTypeModel) {
		this.extraTypeModel = extraTypeModel;
	}

	public WidgetsType getExtraWidgetsType() {
		return extraWidgetsType;
	}

	public void setExtraWidgetsType(WidgetsType extraWidgetsType) {
		this.extraWidgetsType = extraWidgetsType;
	}

	public TypeModel getTypeModel() {
		return typeModel;
	}

	public void setTypeModel(TypeModel typeModel) {
		this.typeModel = typeModel;
	}

	public WidgetsType getWidgetsType() {
		return widgetsType;
	}

	public void setWidgetsType(WidgetsType widgetsType) {
		this.widgetsType = widgetsType;
	}

	public WidgetsType getRead_widgetsType() {
		return read_widgetsType;
	}

	public void setRead_widgetsType(WidgetsType read_widgetsType) {
		this.read_widgetsType = read_widgetsType;
	}

	public TypeModel getReadTypeModel() {
		return readTypeModel;
	}

	public void setReadTypeModel(TypeModel readTypeModel) {
		this.readTypeModel = readTypeModel;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getRead_header() {
		return read_header;
	}

	public void setRead_header(String read_header) {
		this.read_header = read_header;
	}

}

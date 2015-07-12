package com.wedwise.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.wedwiseapp.R;

public class VendorListByCategory extends BaseAdapter{

	Context mContext;
	ArrayList<String> listItems;
	private static LayoutInflater inflater;

	public VendorListByCategory(Context mContext,ArrayList<String> listItems)
	{
		this.mContext=mContext;
		this.listItems=new ArrayList<String>();
		this.listItems=listItems;
		inflater = ((Activity) mContext).getLayoutInflater();
	}

	@Override
	public int getCount() {
		return listItems.size();
	}

	@Override
	public Object getItem(int arg0) {
		return arg0;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}


	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = inflater.inflate(R.layout.vendor_details_adapter, parent, false);
		
		return convertView;
	}
}

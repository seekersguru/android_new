package com.wedwise.adapter;

import java.util.ArrayList;

import com.wedwise.gsonmodels.PackegeBidBook;
import com.wedwiseapp.R;
import com.wedwiseapp.util.CustomFonts;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class PackageSpinnerAapter extends BaseAdapter{
	Context mContext;
	ArrayList<PackegeBidBook> listMessages;

	public PackageSpinnerAapter(Context mContext,ArrayList<PackegeBidBook> listMessages)
	{
		this.mContext=mContext;	
		this.listMessages=listMessages;
	}

	@Override
	public int getCount() {
		return listMessages.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressLint("ViewHolder")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View listItem = convertView;
		LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
		listItem = inflater.inflate(R.layout.spinneradapter, parent, false);
		TextView tvContactName=(TextView) listItem.findViewById(R.id.tvItemName);
		CustomFonts.setFontOfTextView(mContext, tvContactName, "fonts/GothamRoundedBook.ttf");
		tvContactName.setText(listMessages.get(position).getLabel());
		return listItem;
	}

}

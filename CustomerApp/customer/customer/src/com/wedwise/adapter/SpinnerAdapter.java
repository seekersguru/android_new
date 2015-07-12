package com.wedwise.adapter;

import java.util.ArrayList;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wedwiseapp.R;
import com.wedwiseapp.util.CustomFonts;

public class SpinnerAdapter extends BaseAdapter{

	Context mContext;
	ArrayList<String> listMessages;

	public SpinnerAdapter(Context mContext,ArrayList<String> listMessages)
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
//		CustomFonts.setFontOfTextView(mContext, tvContactName, "fonts/GothamRnd-Light.otf");
		tvContactName.setText(listMessages.get(position));
		return listItem;
	}

}

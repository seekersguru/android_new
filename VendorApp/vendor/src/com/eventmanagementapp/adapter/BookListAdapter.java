package com.eventmanagementapp.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.eventmanagementapp.R;
import com.eventmanagementapp.bean.BookingDataBean;
import com.eventmanagementapp.util.CustomFonts;

public class BookListAdapter extends BaseAdapter{

	Context mContext;
	public ArrayList<BookingDataBean> listMessages;

	public BookListAdapter(Context mContext,ArrayList<BookingDataBean> listMessages)
	{
		this.mContext=mContext;	
		this.listMessages=listMessages;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
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

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View listItem = convertView;
		LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
		listItem = inflater.inflate(R.layout.booklistadapter, parent, false);
		TextView tvName=(TextView) listItem.findViewById(R.id.tvName);
		TextView tvDateFirst=(TextView) listItem.findViewById(R.id.tvDateFirst);
		TextView tvDateSecond=(TextView) listItem.findViewById(R.id.tvDateSecond);
		TextView tvPackageDedtails=(TextView) listItem.findViewById(R.id.tvPackageDedtails);
		CustomFonts.setFontOfTextView(mContext,tvName,"fonts/GothamRoundedBook.ttf");
		CustomFonts.setFontOfTextView(mContext,tvDateFirst,"fonts/GothamRoundedBook.ttf");
		CustomFonts.setFontOfTextView(mContext,tvDateSecond,"fonts/GothamRoundedBook.ttf");
		CustomFonts.setFontOfTextView(mContext,tvPackageDedtails,"fonts/GothamRoundedBook.ttf");
		tvName.setText(listMessages.get(position).receiver_name);
		tvDateFirst.setText(listMessages.get(position).event_date);
		String msg_time = (listMessages.get(position).msg_time).substring(0,listMessages.get(position).msg_time.indexOf(" "));
		tvDateSecond.setText(msg_time);
		tvPackageDedtails.setText(listMessages.get(position).line1+"\n"+listMessages.get(position).line2+"--"+listMessages.get(position).status);

		return listItem;
	}

}

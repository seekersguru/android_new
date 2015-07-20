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

public class EnquiryDataAdapter extends BaseAdapter{

	Context mContext;
	public ArrayList<BookingDataBean> listEnquiryDataBean=new ArrayList<BookingDataBean>();

	public EnquiryDataAdapter(Context mContext,ArrayList<BookingDataBean> listEnquiryDataBean)
	{
		this.mContext=mContext;	
		this.listEnquiryDataBean=listEnquiryDataBean;
	}

	@Override
	public int getCount() {
		return listEnquiryDataBean.size();
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
		listItem = inflater.inflate(R.layout.enquiryactivityviewadapter, parent, false);
		TextView tvName=(TextView) listItem.findViewById(R.id.tvName);
		TextView tvDateFirst=(TextView) listItem.findViewById(R.id.tvDateFirst);
		TextView tvDateSecond=(TextView) listItem.findViewById(R.id.tvDateSecond);
		TextView tvPackageDedtails=(TextView) listItem.findViewById(R.id.tvPackageDedtails);

		tvName.setText(listEnquiryDataBean.get(position).receiver_name);
		tvDateFirst.setText(listEnquiryDataBean.get(position).event_date);
		String msg_time = (listEnquiryDataBean.get(position).msg_time).substring(0,listEnquiryDataBean.get(position).msg_time.indexOf(" "));
		tvDateSecond.setText(msg_time);
		tvPackageDedtails.setText(listEnquiryDataBean.get(position).line1+"\n"+listEnquiryDataBean.get(position).line2+"--"+listEnquiryDataBean.get(position).status);
		return listItem;
	}
}

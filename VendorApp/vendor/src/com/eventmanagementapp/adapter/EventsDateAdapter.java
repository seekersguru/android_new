package com.eventmanagementapp.adapter;

import java.util.ArrayList;
import com.eventmanagementapp.R;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class EventsDateAdapter extends BaseAdapter{

	Context mContext;
	ArrayList<String> listDates = new ArrayList<String>();

	public EventsDateAdapter(Context mContext,ArrayList<String> listDates)
	{
		this.mContext=mContext;
		this.listDates=listDates;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listDates.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View listItem = convertView;
		LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
		listItem = inflater.inflate(R.layout.list_events_date_item, parent, false);
		TextView tvDate=(TextView) listItem.findViewById(R.id.tvDate);
		tvDate.setText(listDates.get(position));
		return listItem;
	}

}
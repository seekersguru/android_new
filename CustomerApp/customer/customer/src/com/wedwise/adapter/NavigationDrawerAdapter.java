package com.wedwise.adapter;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wedwise.model.ObjectDrawerItem;
import com.wedwiseapp.R;
import com.wedwiseapp.util.CustomFonts;


/**
 */
public class NavigationDrawerAdapter extends BaseAdapter{
	//	List<NavDrawerItem> data = Collections.emptyList();
	private LayoutInflater inflater;
	private Context mContext;
	ArrayList<ObjectDrawerItem> listItems;

	public NavigationDrawerAdapter(Context mContext,ArrayList<ObjectDrawerItem> listItems)
	{
		this.mContext = mContext;
		inflater = LayoutInflater.from(mContext);
		//		this.data = data;
		this.listItems=new ArrayList<ObjectDrawerItem>();
		this.listItems=listItems;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listItems.size();
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

	@SuppressLint("ViewHolder")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View albumView = convertView;
		LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
		albumView = inflater.inflate(R.layout.nav_drawer_list_item, parent, false);
		ImageView imViewMenuIcon=(ImageView)albumView.findViewById(R.id.imViewMenuIcon);
		TextView tvCategoryName=(TextView)albumView.findViewById(R.id.tvCategoryName);
		tvCategoryName.setText(listItems.get(position).name);
		imViewMenuIcon.setBackgroundResource(listItems.get(position).icon);
//		CustomFonts.setFontOfTextView(mContext, tvCategoryName,"fonts/GothamRnd-Light.otf");

		return albumView;
	}


}

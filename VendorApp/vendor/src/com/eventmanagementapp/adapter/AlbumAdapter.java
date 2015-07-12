package com.eventmanagementapp.adapter;

import java.util.ArrayList;

import com.eventmanagementapp.R;
import com.eventmanagementapp.model.ObjectDrawerItem;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AlbumAdapter extends BaseAdapter
{
	Context mContext;
	ArrayList<String> listItems;

	public AlbumAdapter(Context mContext,ArrayList<String> listItems)
	{
		this.mContext=mContext;
		this.listItems=listItems;
	}

	@Override
	public int getCount() {
		return listItems.size();
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
		View albumView = convertView;
		LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
		albumView = inflater.inflate(R.layout.album_row, parent, false);
		ImageView imViewMenuImage=(ImageView)albumView.findViewById(R.id.imViewMenuImage);
		TextView tvCategoryName=(TextView)albumView.findViewById(R.id.tvCategoryName);
		tvCategoryName.setText(listItems.get(position));
		if(position==0)
			imViewMenuImage.setBackground(mContext.getResources().getDrawable(R.drawable.img1));	
		else if(position==1)
		{
			imViewMenuImage.setBackground(mContext.getResources().getDrawable(R.drawable.img2));	
		}
		else if(position==2)
		{
			imViewMenuImage.setBackground(mContext.getResources().getDrawable(R.drawable.img3));	
		}
		else if(position==3)
		{
			imViewMenuImage.setBackground(mContext.getResources().getDrawable(R.drawable.img4));	
		}
		else if(position==4)
		{
			imViewMenuImage.setBackground(mContext.getResources().getDrawable(R.drawable.img5));	
		}

		return albumView;
	}

}

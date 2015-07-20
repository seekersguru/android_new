package com.wedwise.adapter;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wedwiseapp.FavData;
import com.wedwiseapp.R;
import com.wedwiseapp.util.CustomFonts;

public class FavAdapter extends BaseAdapter {

	private Context mContext;
	private LayoutInflater infalter;
	private ArrayList<FavData> data = new ArrayList<FavData>();

	public FavAdapter(Context c,ArrayList<FavData> data) {
		infalter = (LayoutInflater) c
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mContext = c;
		this.data=data;
	}

	@Override
	public int getCount() {
		// return data.size();
		return 20;
	}

	@Override
	public FavData getItem(int position) {
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public void addAll(ArrayList<FavData> files) {

		this.data.clear();
		this.data.addAll(files);
		notifyDataSetChanged();
	}

	public void add(FavData files) {

		this.data.add(files);
		notifyDataSetChanged();
	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();

			convertView = infalter.inflate(R.layout.favorite_item, null);
			holder.tvVenue = (TextView) convertView.findViewById(R.id.tvVenue);
			holder.tvVeg_NonVeg=(TextView) convertView.findViewById(R.id.tvVeg_NonVeg);
			holder.tvCapacity=(TextView) convertView.findViewById(R.id.tvCapacity);
			holder.tvStartingPrice=(TextView) convertView.findViewById(R.id.tvStartingPrice);
			holder.imViewBackground=(ImageView) convertView.findViewById(R.id.imViewBackground);
			holder.imViewCar=(ImageView) convertView.findViewById(R.id.imViewCar);
			holder.imViewGlass=(ImageView) convertView.findViewById(R.id.imViewGlass);
			holder.imViewLocation=(ImageView) convertView.findViewById(R.id.imViewLocation);
			holder.imViewHeart=(ImageView) convertView.findViewById(R.id.imViewHeart);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
//		CustomFonts.setFontOfTextView(mContext,holder.tvVenue,"fonts/GothamRnd-Light.otf");
//		CustomFonts.setFontOfTextView(mContext,holder.tvVeg_NonVeg,"fonts/GothamRnd-Light.otf");
//		CustomFonts.setFontOfTextView(mContext,holder.tvCapacity,"fonts/GothamRnd-Light.otf");
//		CustomFonts.setFontOfTextView(mContext,holder.tvStartingPrice,"fonts/GothamRnd-Light.otf");
//		CustomFonts.setFontOfTextView(mContext,holder.tvVenue,"fonts/GothamRnd-Light.otf");

		return convertView;
	}

	public class ViewHolder {
		ImageView imViewBackground,imViewCar,imViewGlass,imViewLocation,imViewHeart;
		TextView tvVenue,tvVeg_NonVeg,tvCapacity,tvStartingPrice;
	}

}

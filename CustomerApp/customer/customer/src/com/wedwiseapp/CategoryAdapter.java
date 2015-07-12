
package com.wedwiseapp;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CategoryAdapter extends BaseAdapter {
	private Activity activity;
	private ArrayList<CategoryData> data;
	private static LayoutInflater inflater = null;
	
	public CategoryAdapter(Activity activity, ArrayList<CategoryData> contacts) {		
		this.activity = activity;
		data = contacts;
		inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	@Override
	public int getCount() {
		return data.size();
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
	public View getView(int position, View convertView, final ViewGroup parent) {
		final CategoryData pair = data.get(position);
		convertView = inflater.inflate(R.layout.category_list_row, null);		
		ImageView categoryView = (ImageView) convertView.findViewById(R.id.category);
		try {
			InputStream ims = activity.getAssets().open(pair.imageSrc);
			Drawable drawable = Drawable.createFromStream(ims, null);
			categoryView.setImageDrawable(drawable);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		TextView title = (TextView) convertView.findViewById(R.id.category_title);
		title.setText(pair.categoryName);
		return convertView;
	}
}
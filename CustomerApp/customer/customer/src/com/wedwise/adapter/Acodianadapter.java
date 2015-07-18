package com.wedwise.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wedwise.gsonmodels.Acodianmodal;
import com.wedwise.gsonmodels.KeyValueHeader;
import com.wedwise.gsonmodels.KeyValueHeader.KeyValue;
import com.wedwiseapp.R;
import com.wedwiseapp.views.CTextView;

public class Acodianadapter extends BaseAdapter {

	List<Acodianmodal> list;
	ArrayList<ArrayList<KeyValue>> listDataChild;
	Context c;

	public Acodianadapter(Context ctx, List<Acodianmodal> _listDataHeader, ArrayList<ArrayList<KeyValue>> _listDataChild) {
		// TODO Auto-generated constructor stub
		c = ctx;
		list = _listDataHeader;
		listDataChild = _listDataChild;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Acodianmodal getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		if (convertView == null) {
			LayoutInflater inflate = (LayoutInflater) c
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflate.inflate(R.layout.package_item, parent,
					false);
			holder = new ViewHolder();

			holder.maintext = (TextView) convertView
					.findViewById(R.id.maintext);
			holder.linearLayout = (LinearLayout) convertView
					.findViewById(R.id.key_value_layout);
			
			
			
//			for(int i = 0; i < listDataChild.size(); i++){
////				for (Entry<String, List<KeyValueHeader>> e : listDataChild.entrySet()) {
//
//					ArrayList<KeyValueHeader> list=(ArrayList<KeyValueHeader>)e.getValue();
//
					View blank_line_view = null;
						
						for(KeyValueHeader.KeyValue val:listDataChild.get(position)){
							View child = inflate.inflate(
									R.layout.keyvaluerow, null);
							CTextView key_layout = (CTextView) child
									.findViewById(R.id.key_layout);
							CTextView value_layout = (CTextView) child
									.findViewById(R.id.value_layout);
							if(val.getKey().equals("Minimum Price")){
								key_layout.setTypeface(null, Typeface.BOLD);
								value_layout.setTypeface(null, Typeface.BOLD);
							}else if(val.getKey().equals("Quoted Price")){
								key_layout.setTypeface(null, Typeface.BOLD);
								value_layout.setTypeface(null, Typeface.BOLD);
							}
							
							key_layout.setText(val.getKey());
							value_layout.setText(val.getValue());
							holder.linearLayout.addView(child);
							blank_line_view = inflate.inflate(
									R.layout.blank_line, null);
							holder.linearLayout.addView(blank_line_view);
						}
//						
//						
//						
//					// this is the last balnk view
//					if (blank_line_view != null)
//						blank_line_view.setVisibility(View.GONE);
//				}
//			}

			//			holder.subitemlay = (LinearLayout) convertView
			//					.findViewById(R.id.subitemlay);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		Acodianmodal modal = getItem(position);
		holder.maintext.setText(list.get(position).getName());
				if (modal.isOpen()) {
					holder.linearLayout.setVisibility(View.VISIBLE);
				} else {
					holder.linearLayout.setVisibility(View.GONE);
				}

		return convertView;
	}

	class ViewHolder {
		TextView maintext;
		LinearLayout linearLayout;
	}

}

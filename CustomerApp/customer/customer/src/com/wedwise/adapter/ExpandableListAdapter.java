package com.wedwise.adapter;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.LinearLayout;

import com.wedwise.gsonmodels.KeyValueHeader;
import com.wedwiseapp.R;
import com.wedwiseapp.views.CTextView;

public class ExpandableListAdapter extends BaseExpandableListAdapter {

	private Context _context;
	private List<String> _listDataHeader; // header titles
	// child data in format of header title, child title
	private HashMap<String, List<KeyValueHeader>> _listDataChild;
	LayoutInflater infalInflater;

	public ExpandableListAdapter(Context context, List<String> listDataHeader,
			HashMap<String, List<KeyValueHeader>> listChildData) {
		this._context = context;
		this._listDataHeader = listDataHeader;
		this._listDataChild = listChildData;
		infalInflater = (LayoutInflater) this._context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public Object getChild(int groupPosition, int childPosititon) {
		return this._listDataChild.get(this._listDataHeader.get(groupPosition))
				.get(childPosititon);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, final int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {

		final KeyValueHeader childText = (KeyValueHeader) getChild(
				groupPosition, childPosition);

//		if (convertView == null) {

			convertView = infalInflater.inflate(R.layout.package_item, null);
//		}
		LinearLayout key_value_layout = (LinearLayout) convertView
				.findViewById(R.id.key_value_layout);

		for (int i = 0; i < childText.getKeyValues().size(); i++) {
			KeyValueHeader.KeyValue keyValue = childText.getKeyValues().get(i);
			View child = infalInflater.inflate(R.layout.package_item_row, null);

			CTextView header_layout = (CTextView) child
					.findViewById(R.id.header_layout);
			CTextView key_layout = (CTextView) child
					.findViewById(R.id.key_layout);
			CTextView value_layout = (CTextView) child
					.findViewById(R.id.value_layout);
			header_layout.setText(childText.getHeader());

			key_layout.setText(keyValue.getKey());
			value_layout.setText(keyValue.getValue());
			if (i == 0) {
				header_layout.setVisibility(View.VISIBLE);
			} else {
				header_layout.setVisibility(View.GONE);

			}

			key_value_layout.addView(child);
		}
		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return this._listDataChild.get(this._listDataHeader.get(groupPosition))
				.size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return this._listDataHeader.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		return this._listDataHeader.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		String headerTitle = (String) getGroup(groupPosition);
		if (convertView == null) {
			LayoutInflater infalInflater = (LayoutInflater) this._context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(R.layout.package_header, null);
		}

		CTextView tvDescription = (CTextView) convertView
				.findViewById(R.id.tvDescription);
		tvDescription.setTypeface(null, Typeface.BOLD);
		tvDescription.setText(headerTitle);

		return convertView;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}
}
package com.eventmanagementapp.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.eventmanagementapp.R;
import com.eventmanagementapp.model.ObjectDrawerItem;


/**
 */
public class NavigationDrawerAdapter extends RecyclerView.Adapter<NavigationDrawerAdapter.MyViewHolder> {
//	List<NavDrawerItem> data = Collections.emptyList();
	private LayoutInflater inflater;
	private Context context;
	ArrayList<ObjectDrawerItem> listItems;

	public NavigationDrawerAdapter(Context context,ArrayList<ObjectDrawerItem> listItems)
	{
		this.context = context;
		inflater = LayoutInflater.from(context);
//		this.data = data;
		this.listItems=new ArrayList<ObjectDrawerItem>();
		this.listItems=listItems;
	}

	public void delete(int position) {
		listItems.remove(position);
		notifyItemRemoved(position);
	}

	@Override
	public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = inflater.inflate(R.layout.nav_drawer_row, parent, false);
		MyViewHolder holder = new MyViewHolder(view);
		return holder;
	}

	@Override
	public void onBindViewHolder(MyViewHolder holder, int position) {
		ObjectDrawerItem current = listItems.get(position);
		holder.title.setText(current.name);
		holder.imViewMenuIcon.setImageResource(current.icon);
	}

	@Override
	public int getItemCount() {
		return listItems.size();
	}

	class MyViewHolder extends RecyclerView.ViewHolder {
		TextView title;
		ImageView imViewMenuIcon;

		public MyViewHolder(View itemView) {
			super(itemView);
			title = (TextView) itemView.findViewById(R.id.tvCategoryName);
			imViewMenuIcon=(ImageView) itemView.findViewById(R.id.imViewMenuIcon);
		}
	}
}

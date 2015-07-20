package com.wedwise.adapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.wedwiseapp.R;

public class ChatAdapter extends BaseAdapter {

	public ArrayList<HashMap<String, String>> listChat;
	Context mContext;

	public ChatAdapter(Context mContext, ArrayList<HashMap<String, String>> listChat) {
		this.mContext = mContext;
		this.listChat = listChat;
	}

	@Override
	public int getCount() {
		return listChat.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressLint("ViewHolder")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View chatView = convertView;
		LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
		chatView = inflater.inflate(R.layout.chatadapter, parent, false);
		RelativeLayout rlRight = (RelativeLayout) chatView.findViewById(R.id.rlRight);
		TextView tvMessageRight = (TextView) chatView.findViewById(R.id.tvMessageRight);
		TextView tvDateRight = (TextView) chatView.findViewById(R.id.tvDateRight);
		RelativeLayout rlLeft = (RelativeLayout) chatView.findViewById(R.id.rlLeft);
		TextView tvMessageLeft = (TextView) chatView.findViewById(R.id.tvMessageLeft);
		TextView tvDateLeft = (TextView) chatView.findViewById(R.id.tvDateLeft);
		rlRight.setVisibility(View.GONE);
		rlLeft.setVisibility(View.GONE);

		//		CustomFonts.setFontOfTextView(mContext,tvMessageLeft,"fonts/GothamRnd-Light.otf");
		//		CustomFonts.setFontOfTextView(mContext,tvMessageRight,"fonts/GothamRnd-Light.otf");
		//		CustomFonts.setFontOfTextView(mContext,tvDateLeft,"fonts/GothamRnd-Light.otf");
		//		CustomFonts.setFontOfTextView(mContext,tvDateRight,"fonts/GothamRnd-Light.otf");

		if (position % 2 == 0) {
			rlRight.setVisibility(View.GONE);
			rlLeft.setVisibility(View.VISIBLE);
			tvMessageLeft.setText(listChat.get(position).get("message"));
			tvDateLeft.setVisibility(View.VISIBLE);
			try {
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
				Date myDate = simpleDateFormat.parse(listChat.get(position).get("msg_time"));
				simpleDateFormat.setTimeZone(TimeZone.getDefault());
				String formattedDate = simpleDateFormat.format(myDate);
				tvDateLeft.setText(formattedDate);
			} catch (Exception e) {
				e.getMessage();
			}
		} else {
			rlRight.setVisibility(View.VISIBLE);
			rlLeft.setVisibility(View.GONE);
			tvMessageRight.setText(listChat.get(position).get("message"));
			tvDateRight.setVisibility(View.VISIBLE);
			try {
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
				Date myDate = simpleDateFormat.parse(listChat.get(position).get("msg_time"));
				simpleDateFormat.setTimeZone(TimeZone.getDefault());
				String formattedDate = simpleDateFormat.format(myDate);
				tvDateRight.setText(formattedDate);
			} catch (Exception e) {
				e.getMessage();
			}
		}
		return chatView;
	}
}

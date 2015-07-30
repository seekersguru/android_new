package com.eventmanagementapp.dialogs;

import java.util.ArrayList;

import com.eventmanagementapp.R;
import com.eventmanagementapp.adapter.EventsDateAdapter;
import com.eventmanagementapp.interfaces.IAction;

import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;


public class DisplayEventDatesDialog extends DialogFragment{

	Context mContext;
	IAction iNotify;
	TextView tvTitle;
	String title,message;
	Button btnOk;
	ListView lvDates;
	EventsDateAdapter adapter;
	ArrayList<String> listDates;
	IAction iNotifyAction;

	public void newInstance(Context mContext,String title,ArrayList<String> listDates,IAction iNotifyAction)
	{
		this.mContext=mContext;
		this.title=title;
		this.listDates=listDates;
		this.iNotifyAction=iNotifyAction;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setStyle(STYLE_NO_TITLE, getTheme());
	}

	@Override
	public void onStart() {
		super.onStart();
		Window window=getDialog().getWindow();
		WindowManager.LayoutParams params=window.getAttributes();
		params.dimAmount=0.6f;
		window.setAttributes(params);
		window.setBackgroundDrawableResource(android.R.color.transparent);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view=inflater.inflate(R.layout.display_events_date_dialog,container);
		tvTitle=(TextView) view.findViewById(R.id.tvTitle);
		btnOk=(Button) view.findViewById(R.id.btnOk);
		lvDates=(ListView) view.findViewById(R.id.lvDates);
		adapter=new EventsDateAdapter(mContext, listDates);
		lvDates.setAdapter(adapter);
		if(!title.equals(""))
			tvTitle.setText(title);
		else 
			tvTitle.setVisibility(View.GONE);
		btnOk.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dismiss();
				if(iNotifyAction!=null)
					iNotifyAction.setAction("senddata"+"");
			}
		});
		return view;
	}

}

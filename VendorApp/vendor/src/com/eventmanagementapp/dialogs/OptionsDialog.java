package com.eventmanagementapp.dialogs;

import java.util.ArrayList;

import com.eventmanagementapp.R;
import com.eventmanagementapp.adapter.EventsDateAdapter;
import com.eventmanagementapp.interfaces.IAction;

import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class OptionsDialog extends DialogFragment{

	Context mContext;
	IAction iNotify;
	TextView tvTitle;
	String title,message;
	Button btnOk;
	ListView lvDates;
	EventsDateAdapter adapter;
	ArrayList<String> listOptions;
	IAction iNotifyAction;

	public void newInstance(Context mContext,String title,ArrayList<String> listOptions,IAction iNotifyAction)
	{
		this.mContext=mContext;
		this.title=title;
		this.listOptions=listOptions;
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
		window.setGravity(Gravity.BOTTOM);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view=inflater.inflate(R.layout.optionsdialog,container);
		tvTitle=(TextView) view.findViewById(R.id.tvTitle);
		btnOk=(Button) view.findViewById(R.id.btnOk);
		btnOk.setVisibility(View.GONE);
		lvDates=(ListView) view.findViewById(R.id.lvDates);
		adapter=new EventsDateAdapter(mContext, listOptions);
		lvDates.setAdapter(adapter);
		if(!title.equals(""))
			tvTitle.setText(title);
		else 
			tvTitle.setVisibility(View.GONE);


		lvDates.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				dismiss();
				if(iNotifyAction!=null)
					iNotifyAction.setAction(listOptions.get(position));
			}
		});

		btnOk.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dismiss();
				if(iNotifyAction!=null)
					iNotifyAction.setAction("senddata");
			}
		});
		return view;
	}

}

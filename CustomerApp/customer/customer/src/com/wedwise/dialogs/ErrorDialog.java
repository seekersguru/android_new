package com.wedwise.dialogs;

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
import android.widget.TextView;

import com.wedwise.interfaces.IAction;
import com.wedwiseapp.R;

public class ErrorDialog extends DialogFragment{

	Context mContext;
	IAction iNotify;
	TextView tvTitle,tvMessage;
	String title,message;
	Button btnOk;

	public void newInstance(Context mContext,String title,String message,IAction iNotify)
	{
		this.mContext=mContext;
		this.title=title;
		this.message=message;
		this.iNotify=iNotify;
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
		View view=inflater.inflate(R.layout.error_dialog,container);
		tvTitle=(TextView) view.findViewById(R.id.tvTitle);
		tvMessage=(TextView) view.findViewById(R.id.tvMessage);
		btnOk=(Button) view.findViewById(R.id.btnOk);
		if(!title.equals(""))
			tvTitle.setText(title);
		else 
			tvTitle.setVisibility(View.GONE);
		tvMessage.setText(message);
		btnOk.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dismiss();
				if(iNotify!=null)
				{
					//					if(message.contains("email already exists") || message.contains("mobile number should be of 10/11 digits") ||
					//							message.contains("invalid email"))
					//					{
					//						iNotify.setAction("dismiss");
					//					}           //Logged In Successfully
					if (message.toLowerCase().contains("registered successfully") || 
							message.toLowerCase().contains("logged in successfully"))
					{
						iNotify.setAction("navigate");
					}
				}
			}
		});
		return view;
	}

}

package com.wedwise.dialogs;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.wedwise.interfaces.INotify;
import com.wedwiseapp.R;


public class FacilitiesDialog extends DialogFragment{

	Context mContext;
	INotify iNotify;
	Button btnSubmit;

	public void newInstance(Context mContext,INotify iNotify)
	{
		this.mContext=mContext;
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
		View view=inflater.inflate(R.layout.facilitiesdialog,container);

		btnSubmit=(Button) view.findViewById(R.id.btnSubmit);
//		CustomFonts.setFontOfButton(mContext, btnSubmit, "fonts/GothamRnd-Light.otf");
		btnSubmit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dismiss();
				if(iNotify!=null)
					iNotify.yes();
			}
		});
		return view;
	}
}

package com.eventmanagementapp.Activities;

import com.eventmanagementapp.LoginSignUpActivity;
import com.eventmanagementapp.R;
import com.eventmanagementapp.calendar.CalendarActivityMultipleSelection;
import com.eventmanagementapp.util.CustomFonts;
import com.eventmanagementapp.util.PreferenceUtil;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class MenuListActivity extends Activity {

	private String[] menuitem = {"Availability","Logout"};
	private ListView listView;
	TextView tvTitle,txtMenurow;
	Button 	btnBack;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_menulist);
		listView = (ListView) findViewById(R.id.list_item);
		btnBack = (Button) findViewById(R.id.btnBack);
		btnBack.setVisibility(View.GONE);
		tvTitle= (TextView) findViewById(R.id.tvTitle);
		CustomFonts.setFontOfTextView(MenuListActivity.this,tvTitle,"fonts/GothamRoundedBook.ttf");
		btnBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
				overridePendingTransition(R.anim.left_in, R.anim.right_out);
			}
		});

		MenuListAdapter adapter = new MenuListAdapter(MenuListActivity.this, R.layout.menu_list_row, menuitem);
		listView.setAdapter(adapter);
	}

	private class MenuListAdapter extends ArrayAdapter<String> {

		private String[] listdata;
		private Context context;
		int _resource;

		public MenuListAdapter(Context context, int resource, String[] objects) {
			super(context, resource, objects);
			this.context  = context;
			listdata = objects;
			_resource = resource;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			View view = convertView;

			if (convertView == null) {

				LayoutInflater mLayoutInflater = ((Activity) context)
						.getLayoutInflater();
				view = mLayoutInflater.inflate(_resource, parent, false);
				txtMenurow = (TextView) view.findViewById(R.id.txtMenurow);
			}
			CustomFonts.setFontOfTextView(context,txtMenurow,"fonts/GothamRoundedBook.ttf");
			txtMenurow.setText(listdata[position]);
			txtMenurow.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					if(listdata[position].equals("Logout")){
						PreferenceUtil.getInstance().setEmail("");
						PreferenceUtil.getInstance().setIdentifier("");
						PreferenceUtil.getInstance().setLogin(false);
						PreferenceUtil.getInstance().setRegister(false);
						Intent intent = new Intent(context, LoginSignUpActivity.class);
						startActivity(intent);
						LoginSignUpActivity.isLoggedIn=false;
						finish();
					}else if(listdata[position].equals("Availability")){
						Intent myIntent=new Intent(context,CalendarActivityMultipleSelection.class);
						startActivity(myIntent);	
						overridePendingTransition(R.anim.right_in, R.anim.left_out);	
					}
				}
			});
			return view;
		}
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		overridePendingTransition(R.anim.left_in, R.anim.right_out);
	}

}

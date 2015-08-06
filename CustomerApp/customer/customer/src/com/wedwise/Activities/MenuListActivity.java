package com.wedwise.Activities;

import java.util.ArrayList;

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

import com.wedwiseapp.R;
import com.wedwiseapp.login.LoginSignUpActivity;
import com.wedwiseapp.util.CustomFonts;
import com.wedwiseapp.util.PreferenceUtil;

public class MenuListActivity extends Activity {

	private String[] menuitem = {"Profile","Favourites","Logout"};
	private ListView listView;
	TextView txtMenurow, tvTitle;
	Button 	btnBack;
	ArrayList<String> menuItem = new ArrayList<String>();
	ArrayList<String> menuItemFull = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_menulist);
		listView = (ListView) findViewById(R.id.list_item);
		btnBack = (Button) findViewById(R.id.btnBack);
		tvTitle = (TextView) findViewById(R.id.tvTitle);
		CustomFonts.setFontOfTextView(MenuListActivity.this, tvTitle, "fonts/GothamRoundedBook.ttf");
//		menuItem = getIntent().getStringArrayListExtra("menuArray");
		menuItem.add("Profile");
		menuItem.add("Favourites");
		menuItem.add("Logout");
//		menuItemFull.addAll(menuItem);
		btnBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
				overridePendingTransition(R.anim.left_in, R.anim.right_out);
			}
		});
		
		MenuListAdapter adapter = new MenuListAdapter(MenuListActivity.this, R.layout.menu_list_row, menuItem);
		listView.setAdapter(adapter);
	}

	private class MenuListAdapter extends ArrayAdapter<String> {

		private  ArrayList<String> _menu_item;
		private Context context;
		int _resource;

		public MenuListAdapter(Context context, int resource, ArrayList<String> menu_item) {
			super(context, resource, menu_item);
			this.context  = context;
			_menu_item = menu_item;
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
			CustomFonts.setFontOfTextView(context, txtMenurow, "fonts/GothamRoundedBook.ttf");
			txtMenurow.setText(_menu_item.get(position));
			
			txtMenurow.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					if(_menu_item.get(position).equals("Logout")){
						PreferenceUtil.getInstance().setEmail("");
						PreferenceUtil.getInstance().setIdentifier("");
						PreferenceUtil.getInstance().setLogin(false);
						PreferenceUtil.getInstance().setRegister(false);
						Intent intent = new Intent(context, LoginSignUpActivity.class);
						startActivity(intent);
						finish();
					}else if(_menu_item.get(position).equals("Profile")){
						
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

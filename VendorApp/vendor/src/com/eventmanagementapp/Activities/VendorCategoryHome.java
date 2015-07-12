package com.eventmanagementapp.Activities;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import com.eventmanagementapp.R;
import com.eventmanagementapp.adapter.AlbumAdapter;

public class VendorCategoryHome extends Activity{

	Button btnBack;
//	RecyclerView listRecyclerView;
	ListView lvAlbums;
	AlbumAdapter adapterAlbum;
	ArrayList<String> listItems;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.albumactivity);
		btnBack=(Button) findViewById(R.id.btnBack);
		lvAlbums=(ListView) findViewById(R.id.lvAlbums);
		/*listRecyclerView.setHasFixedSize(true);
		LinearLayoutManager llm = new LinearLayoutManager(this);
		llm.setOrientation(LinearLayoutManager.VERTICAL);
		listRecyclerView.setLayoutManager(llm);*/
//		listRecyclerView.setItemAnimator(new DefaultItemAnimator());
		/*
		 mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
		 */
		listItems=new ArrayList<String>();
		listItems.add("BANQUITE");
		listItems.add("DECORATORS");
		listItems.add("CATERERS");
		listItems.add("PHOTOGRAPHY");
		listItems.add("OTHERS");
		/*ObjectDrawerItem item1=new ObjectDrawerItem(R.drawable.img1, "BANQUITE");
		listItems.add(item1);
		ObjectDrawerItem item2=new
				ObjectDrawerItem(R.drawable.img2, "DECORATORS");
		listItems.add(item2); ObjectDrawerItem item3=new
				ObjectDrawerItem(R.drawable.img3, "CATERERS");
		listItems.add(item3); ObjectDrawerItem item4=new
				ObjectDrawerItem(R.drawable.img4, "PHOTOGRAPHY");
		listItems.add(item4); ObjectDrawerItem item5=new
				ObjectDrawerItem(R.drawable.img5, "OTHERS");
		listItems.add(item5);*/

		adapterAlbum=new AlbumAdapter(VendorCategoryHome.this, listItems);
		lvAlbums.setAdapter(adapterAlbum);

		//		listRecyclerView.seto
		btnBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
				overridePendingTransition(R.anim.right_in, R.anim.right_out);
			}
		});
	}
}

package com.eventmanagementapp.fragments;


import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.eventmanagementapp.R;
import com.eventmanagementapp.adapter.NavigationDrawerAdapter;
import com.eventmanagementapp.model.ObjectDrawerItem;


public class FragmentDrawer extends Fragment {

	//	private static String TAG = FragmentDrawer.class.getSimpleName();
	private RecyclerView recyclerView;
	private ActionBarDrawerToggle mDrawerToggle;
	public static DrawerLayout mDrawerLayout;
	private NavigationDrawerAdapter adapter;
	private View containerView;
	//	private static String[] titles = null;
	private FragmentDrawerListener drawerListener;
	ArrayList<ObjectDrawerItem> listItems;

	public FragmentDrawer() {
	}

	public void setDrawerListener(FragmentDrawerListener listener) {
		this.drawerListener = listener;
	}

	public ArrayList<ObjectDrawerItem> getData() {
		ArrayList<ObjectDrawerItem> data = new ArrayList<>();
		listItems=new ArrayList<>();
		ObjectDrawerItem item1=new ObjectDrawerItem(R.drawable.weeding_venu_icon, "Wedding Venues");
//		listItems.add(item1);
//		ObjectDrawerItem item2=new ObjectDrawerItem(R.drawable.bridal_fashion_icon, "Bridal Fashion");
//		listItems.add(item2);
//		ObjectDrawerItem item3=new ObjectDrawerItem(R.drawable.photogrphers_icon, "Photographers");
//		listItems.add(item3);
//		ObjectDrawerItem item4=new ObjectDrawerItem(R.drawable.makeup_icon, "Makeup");
//		listItems.add(item4);
//		ObjectDrawerItem item5=new ObjectDrawerItem(R.drawable.caters_icon, "Caterers");
//		listItems.add(item5);
//		ObjectDrawerItem item6=new ObjectDrawerItem(R.drawable.flowers_icon, "Flowers");
//		listItems.add(item6);
//		ObjectDrawerItem item7=new ObjectDrawerItem(R.drawable.discjokey, "Disc jockeys");
//		listItems.add(item7);
//		ObjectDrawerItem item8=new ObjectDrawerItem(R.drawable.invitation_icon, "Invitations");
//		listItems.add(item8);

		//		// preparing navigation drawer items
		//		for (int i = 0; i < titles.length; i++) {
		//			NavDrawerItem navItem = new NavDrawerItem();
		//			navItem.setTitle(titles[i]);
		//			data.add(navItem);
		//		}
		return data;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		mDrawerLayout.setBackgroundColor(Color.parseColor("cc00cc"));
		getData();
		// drawer labels
		/*titles = new String[]{"Wedding Venues","Bridal Fashion","Photographers","Makeup",
				"Caterers","Flowers","Disc jockeys","invitations","Wedding Venues",
				"Bridal Fashion","Photographers","Makeup","Caterers","Flowers","Disc jockeys",
				"invitations"};*///getActivity().getResources().getStringArray(R.array.nav_drawer_labels);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflating view layout
		View layout = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
		recyclerView = (RecyclerView) layout.findViewById(R.id.drawerList);

		adapter = new NavigationDrawerAdapter(getActivity(), listItems);
		recyclerView.setAdapter(adapter);
		recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
		recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new ClickListener() {
			@Override
			public void onClick(View view, int position) {
				drawerListener.onDrawerItemSelected(view, position);
				mDrawerLayout.closeDrawer(containerView);
			}

			@Override
			public void onLongClick(View view, int position) {

			}
		}));

		return layout;
	}


	public void setUp(int fragmentId, DrawerLayout drawerLayout, final Toolbar toolbar) {
		containerView = getActivity().findViewById(fragmentId);
		mDrawerLayout = drawerLayout;
		mDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.open, R.string.close) {
			@Override
			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
				getActivity().invalidateOptionsMenu();
			}

			@Override
			public void onDrawerClosed(View drawerView) {
				super.onDrawerClosed(drawerView);
				getActivity().invalidateOptionsMenu();
			}

			@Override
			public void onDrawerSlide(View drawerView, float slideOffset) {
				super.onDrawerSlide(drawerView, slideOffset);
				toolbar.setAlpha(1 - slideOffset / 2);
			}
		};
		//		mDrawerLayout.openDrawer(Gravity.RIGHT);
		mDrawerLayout.setDrawerListener(mDrawerToggle);
		mDrawerLayout.post(new Runnable() {
			@Override
			public void run() {
				mDrawerToggle.syncState();
			}
		});

	}

	public static interface ClickListener {
		public void onClick(View view, int position);

		public void onLongClick(View view, int position);
	}

	static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

		private GestureDetector gestureDetector;
		private ClickListener clickListener;

		public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener) {
			this.clickListener = clickListener;
			gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
				@Override
				public boolean onSingleTapUp(MotionEvent e) {
					return true;
				}

				@Override
				public void onLongPress(MotionEvent e) {
					View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
					if (child != null && clickListener != null) {
						clickListener.onLongClick(child, recyclerView.getChildPosition(child));
					}
				}
			});
		}

		@Override
		public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

			View child = rv.findChildViewUnder(e.getX(), e.getY());
			if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
				clickListener.onClick(child, rv.getChildPosition(child));
			}
			return false;
		}

		@Override
		public void onTouchEvent(RecyclerView rv, MotionEvent e) {
		}
	}

	public interface FragmentDrawerListener {
		public void onDrawerItemSelected(View view, int position);
	}
}

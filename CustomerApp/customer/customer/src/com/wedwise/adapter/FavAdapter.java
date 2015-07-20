package com.wedwise.adapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.wedwise.adapter.AlbumAdapter.AnimateFirstDisplayListener;
import com.wedwiseapp.R;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class FavAdapter extends BaseAdapter {

	private Context mContext;
	private LayoutInflater infalter;
	public ArrayList<HashMap<String,String>> listData = new ArrayList<HashMap<String,String>>();
	DisplayImageOptions options;
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();


	public FavAdapter(Context c,ArrayList<HashMap<String,String>> listData ) {
		infalter = (LayoutInflater) c
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mContext = c;
		this.listData=listData;
		options = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.ic_stub)
				.showImageForEmptyUri(R.drawable.ic_empty)
				.showImageOnFail(R.drawable.ic_error)
				.cacheInMemory(true)
				.cacheOnDisk(true)
				.considerExifParams(true)
				.displayer(new RoundedBitmapDisplayer(20)).build();
	}

	@Override
	public int getCount() {
		// return data.size();
		return listData.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	/*public void addAll(ArrayList<FavData> files) {

		this.data.clear();
		this.data.addAll(files);
		notifyDataSetChanged();
	}
	 */
	/*public void add(FavData files) {

		this.data.add(files);
		notifyDataSetChanged();
	}*/

	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();

			convertView = infalter.inflate(R.layout.favorite_item, null);
			holder.tvVenue = (TextView) convertView.findViewById(R.id.tvVenue);
			holder.tvVeg_NonVeg=(TextView) convertView.findViewById(R.id.tvVeg_NonVeg);
			holder.tvCapacity=(TextView) convertView.findViewById(R.id.tvCapacity);
			holder.tvStartingPrice=(TextView) convertView.findViewById(R.id.tvStartingPrice);
			holder.imViewBackground=(ImageView) convertView.findViewById(R.id.imViewBackground);
			holder.imViewCar=(ImageView) convertView.findViewById(R.id.imViewCar);
			holder.imViewGlass=(ImageView) convertView.findViewById(R.id.imViewGlass);
			holder.imViewLocation=(ImageView) convertView.findViewById(R.id.imViewLocation);
			holder.imViewHeart=(ImageView) convertView.findViewById(R.id.imViewHeart);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.tvVenue.setText(listData.get(position).get("name") +" -- " + listData.get(position).get("email"));
		holder.tvVeg_NonVeg.setVisibility(View.GONE);
		holder.tvCapacity.setVisibility(View.GONE);
		holder.tvStartingPrice.setVisibility(View.GONE);

		String imagePath="http://52.11.207.26"+listData.get(position).get("image"); //"http://0.tqn.com/d/webclipart/1/0/5/l/4/floral-icon-5.jpg";
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(mContext)
				.defaultDisplayImageOptions(options)
				.build();
		ImageLoader.getInstance().init(config);
		ImageLoader.getInstance().displayImage(imagePath, holder.imViewBackground, options, animateFirstListener);
		//		CustomFonts.setFontOfTextView(mContext,holder.tvVenue,"fonts/GothamRnd-Light.otf");
		//		CustomFonts.setFontOfTextView(mContext,holder.tvVeg_NonVeg,"fonts/GothamRnd-Light.otf");
		//		CustomFonts.setFontOfTextView(mContext,holder.tvCapacity,"fonts/GothamRnd-Light.otf");
		//		CustomFonts.setFontOfTextView(mContext,holder.tvStartingPrice,"fonts/GothamRnd-Light.otf");
		//		CustomFonts.setFontOfTextView(mContext,holder.tvVenue,"fonts/GothamRnd-Light.otf");

		return convertView;
	}

	public class ViewHolder {
		ImageView imViewBackground,imViewCar,imViewGlass,imViewLocation,imViewHeart;
		TextView tvVenue,tvVeg_NonVeg,tvCapacity,tvStartingPrice;
	}

	public static class AnimateFirstDisplayListener extends SimpleImageLoadingListener {

		public static final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());

		@Override
		public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
			if (loadedImage != null) {
				ImageView imageView = (ImageView) view;
				boolean firstDisplay = !displayedImages.contains(imageUri);
				if (firstDisplay) {
					FadeInBitmapDisplayer.animate(imageView, 500);
					displayedImages.add(imageUri);
				}
			}
		}
	}

}

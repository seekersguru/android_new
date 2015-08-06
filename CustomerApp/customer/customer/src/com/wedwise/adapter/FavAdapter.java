package com.wedwise.adapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.wedwiseapp.R;
import com.wedwiseapp.util.CustomFonts;

public class FavAdapter extends BaseAdapter {

	private Context mContext;
	private LayoutInflater infalter;
	public ArrayList<HashMap<String,String>> listData = new ArrayList<HashMap<String,String>>();
	public ArrayList<ArrayList<HashMap<String,String>>> listImages = new ArrayList<ArrayList<HashMap<String,String>>>();
	DisplayImageOptions options;
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();


	public FavAdapter(Context c,ArrayList<HashMap<String,String>> listData, ArrayList<ArrayList<HashMap<String,String>>> listImages ) {
		infalter = (LayoutInflater) c
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mContext = c;
		this.listData=listData;
		this.listImages=listImages;
		options = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.ic_stub)
				.showImageForEmptyUri(R.drawable.ic_empty)
				.showImageOnFail(R.drawable.ic_error)
				.cacheInMemory(true)
				.cacheOnDisk(true)
				.considerExifParams(true)
				.displayer(new RoundedBitmapDisplayer(0)).build();
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
			holder.tvtextview1=(TextView) convertView.findViewById(R.id.tvtextview1);
			holder.tvtextview2=(TextView) convertView.findViewById(R.id.tvtextview2);
			holder.tvtextview3=(TextView) convertView.findViewById(R.id.tvtextview3);
			holder.tvtextview4=(TextView) convertView.findViewById(R.id.tvtextview4);
//			holder.tvStartingPrice=(TextView) convertView.findViewById(R.id.tvStartingPrice);
			holder.tvSearchRate=(TextView) convertView.findViewById(R.id.tvSearchRate);
			holder.imViewBackground=(ImageView) convertView.findViewById(R.id.imViewBackground);
			holder.imv1=(ImageView) convertView.findViewById(R.id.imv1);
			holder.imv2=(ImageView) convertView.findViewById(R.id.imv2);
			holder.imv3=(ImageView) convertView.findViewById(R.id.imv3);
			holder.imv4=(ImageView) convertView.findViewById(R.id.imv4);
//			holder.imViewCar=(ImageView) convertView.findViewById(R.id.imViewCar);
//			holder.imViewGlass=(ImageView) convertView.findViewById(R.id.imViewGlass);
//			holder.imViewLocation=(ImageView) convertView.findViewById(R.id.imViewLocation);
//			holder.imViewHeart=(ImageView) convertView.findViewById(R.id.imViewHeart);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();  
		}
		CustomFonts.setFontOfTextView(mContext, holder.tvSearchRate, "fonts/GothamRoundedBook.ttf");
		CustomFonts.setFontOfTextView(mContext, holder.tvVenue, "fonts/GothamRoundedBook.ttf");
		CustomFonts.setFontOfTextView(mContext, holder.tvtextview1, "fonts/GothamRoundedBook.ttf");
		CustomFonts.setFontOfTextView(mContext, holder.tvtextview2, "fonts/GothamRoundedBook.ttf");
		CustomFonts.setFontOfTextView(mContext, holder.tvtextview3, "fonts/GothamRoundedBook.ttf");
		CustomFonts.setFontOfTextView(mContext, holder.tvtextview4, "fonts/GothamRoundedBook.ttf");
		holder.tvVenue.setText(listData.get(position).get("name"));
//		holder.tvVenue.setText(listData.get(position).get("name") +" -- " + listData.get(position).get("email"));
		holder.tvSearchRate.setText(listData.get(position).get("price")+"/-");
//		holder.tvVeg_NonVeg.setVisibility(View.GONE);
//		holder.tvCapacity.setVisibility(View.GONE);
//		holder.tvStartingPrice.setVisibility(View.GONE);
		for(int i=0; i<listImages.get(position).size(); i++){
			if(i == 0)
			holder.tvtextview1.setText(listImages.get(position).get(i).get("name10"));
			if(i == 1)
			holder.tvtextview2.setText(listImages.get(position).get(i).get("name11"));
			if(i == 2)
			holder.tvtextview3.setText(listImages.get(position).get(i).get("name20"));
			if(i == 3)
			holder.tvtextview4.setText(listImages.get(position).get(i).get("name21"));
		}
		
		String imagePath="http://52.11.207.26"+listData.get(position).get("image"); //"http://0.tqn.com/d/webclipart/1/0/5/l/4/floral-icon-5.jpg";
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(mContext)
		.defaultDisplayImageOptions(options)
		.build();
		ImageLoader.getInstance().init(config);
		ImageLoader.getInstance().displayImage(imagePath, holder.imViewBackground, options, animateFirstListener);
		
		
		String imagePath1;
		String imagePath2;
		String imagePath3;
		String imagePath4;
		for(int i=0; i<listImages.get(position).size(); i++){
			if(i == 0){
				imagePath1="http://52.11.207.26"+listImages.get(position).get(i).get("image10"); 
				ImageLoader.getInstance().displayImage(imagePath1, holder.imv1, options, animateFirstListener);
			}
			if(i == 1){
				imagePath2="http://52.11.207.26"+listImages.get(position).get(i).get("image11"); 
				ImageLoader.getInstance().displayImage(imagePath2, holder.imv2, options, animateFirstListener);
			}
			if(i == 2){
				imagePath3="http://52.11.207.26"+listImages.get(position).get(i).get("image20"); 
				ImageLoader.getInstance().displayImage(imagePath3, holder.imv3, options, animateFirstListener);
			}
			if(i == 3){
				imagePath4="http://52.11.207.26"+listImages.get(position).get(i).get("image21");
				ImageLoader.getInstance().displayImage(imagePath4, holder.imv4, options, animateFirstListener);
			}
		}

		
//		String imagePath1="http://52.11.207.26"+listImages.get(position).get(position).get("image10"); //"http://0.tqn.com/d/webclipart/1/0/5/l/4/floral-icon-5.jpg";
//		String imagePath2="http://52.11.207.26"+listImages.get(position).get(position).get("image11"); //"http://0.tqn.com/d/webclipart/1/0/5/l/4/floral-icon-5.jpg";
//		String imagePath3="http://52.11.207.26"+listImages.get(position).get(position).get("image20"); //"http://0.tqn.com/d/webclipart/1/0/5/l/4/floral-icon-5.jpg";
//		String imagePath4="http://52.11.207.26"+listImages.get(position).get(position).get("image21"); //"http://0.tqn.com/d/webclipart/1/0/5/l/4/floral-icon-5.jpg";
		
//		ImageLoader.getInstance().displayImage(imagePath1, holder.imv1, options, animateFirstListener);
//		ImageLoader.getInstance().displayImage(imagePath2, holder.imv2, options, animateFirstListener);
//		ImageLoader.getInstance().displayImage(imagePath3, holder.imv3, options, animateFirstListener);
//		ImageLoader.getInstance().displayImage(imagePath4, holder.imv4, options, animateFirstListener);
		//		CustomFonts.setFontOfTextView(mContext,holder.tvVenue,"fonts/GothamRnd-Light.otf");
		//		CustomFonts.setFontOfTextView(mContext,holder.tvVeg_NonVeg,"fonts/GothamRnd-Light.otf");
		//		CustomFonts.setFontOfTextView(mContext,holder.tvCapacity,"fonts/GothamRnd-Light.otf");
		//		CustomFonts.setFontOfTextView(mContext,holder.tvStartingPrice,"fonts/GothamRnd-Light.otf");
		//		CustomFonts.setFontOfTextView(mContext,holder.tvVenue,"fonts/GothamRnd-Light.otf");

		return convertView;
	}

	public class ViewHolder {
		ImageView imViewBackground,imViewCar,imViewGlass,imViewLocation,imViewHeart, imv1,imv2,imv3,imv4;
		TextView tvVenue,tvtextview1,tvtextview2,tvStartingPrice, tvSearchRate,tvtextview3,tvtextview4;
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

package com.wedwise.adapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
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

@SuppressLint("ViewHolder")
public class AlbumAdapter extends BaseAdapter
{
	Context mContext;
	public ArrayList<HashMap<String,String>> listItems;
	DisplayImageOptions options;
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

	public AlbumAdapter(Context mContext,ArrayList<HashMap<String,String>> listItems)
	{
		this.mContext=mContext;
		this.listItems=listItems;
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
		return listItems.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View albumView = convertView;
		LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
		albumView = inflater.inflate(R.layout.album_row, parent, false);
		ImageView imViewMenuImage=(ImageView)albumView.findViewById(R.id.imViewMenuImage);
		TextView tvCategoryName=(TextView)albumView.findViewById(R.id.tvCategoryName);
		tvCategoryName.setText(listItems.get(position).get("name"));
		String imagePath="http://wedwise.work"+listItems.get(position).get("image_path"); //"http://0.tqn.com/d/webclipart/1/0/5/l/4/floral-icon-5.jpg";
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(mContext)
		.defaultDisplayImageOptions(options)
		.build();
		ImageLoader.getInstance().init(config);
		ImageLoader.getInstance().displayImage(imagePath, imViewMenuImage, options, animateFirstListener);
		//		CustomFonts.setFontOfTextView(mContext, tvCategoryName,"fonts/GothamRnd-Light.otf");
		tvCategoryName.setShadowLayer(1, 2, 2, Color.BLACK);
		/*if(position==0)
			imViewMenuImage.setBackground(mContext.getResources().getDrawable(R.drawable.img1));	
		else if(position==1)
		{
			imViewMenuImage.setBackground(mContext.getResources().getDrawable(R.drawable.img2));	
		}
		else if(position==2)
		{
			imViewMenuImage.setBackground(mContext.getResources().getDrawable(R.drawable.img3));	
		}
		else if(position==3)
		{
			imViewMenuImage.setBackground(mContext.getResources().getDrawable(R.drawable.img4));	
		}
		else if(position==4)
		{
			imViewMenuImage.setBackground(mContext.getResources().getDrawable(R.drawable.img5));	
		}
*/
		return albumView;
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

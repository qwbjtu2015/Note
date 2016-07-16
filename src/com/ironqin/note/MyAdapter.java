package com.ironqin.note;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

@SuppressLint("ViewHolder")
public class MyAdapter extends BaseAdapter{

	private Context context;
	private Cursor cursor;
	private LinearLayout layout;
	private TextView tv_content;
	private TextView tv_time;
	
	public MyAdapter(Context context,Cursor cursor) {
		this.context=context;
		this.cursor=cursor;
	}
	
	@Override
	public int getCount() {
		return cursor.getCount();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return cursor.getPosition();
	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater=LayoutInflater.from(context);//加载视图的权限
		layout=(LinearLayout)inflater.inflate(R.layout.cell, null);//视图
		tv_content=(TextView)layout.findViewById(R.id.content);
		tv_time=(TextView)layout.findViewById(R.id.time);
		ImageView img_photo=(ImageView)layout.findViewById(R.id.img_photo);
		ImageView img_video=(ImageView)layout.findViewById(R.id.img_video);
		cursor.moveToPosition(position);
		String content=cursor.getString(cursor.getColumnIndex("content"));
		String time=cursor.getString(cursor.getColumnIndex("time"));
		String photo=cursor.getString(cursor.getColumnIndex("photo"));
		String video=cursor.getString(cursor.getColumnIndex("video"));
		tv_content.setText(content);
		tv_time.setText(time);
		img_photo.setImageBitmap(getImageThumbnail(photo, 200, 200));
		img_video.setImageBitmap(getVideoThumpnail(video, 200, 200, 
				MediaStore.Images.Thumbnails.MICRO_KIND));
		return layout;
	}

	
	//获取图片的缩略图
	public Bitmap getImageThumbnail(String uri,int width,int height){
		Bitmap bitmap=null;
		BitmapFactory.Options options=new BitmapFactory.Options();
		options.inJustDecodeBounds=true;
		bitmap=BitmapFactory.decodeFile(uri, options);
		options.inJustDecodeBounds=false;
		int beWidth=options.outWidth/width;
		int beHeight=options.outHeight/height;
		int be = 1;
		if(beWidth<beHeight)
			be=beWidth;
		else
			be=beHeight;
		if(be<=0)
			be=1;
		options.inSampleSize=be;
		bitmap=BitmapFactory.decodeFile(uri, options);
		bitmap=ThumbnailUtils.extractThumbnail(bitmap, width, height
				,ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
		return bitmap;
	}
	
	private Bitmap getVideoThumpnail(String uri,int width,int height,int kind){
		Bitmap bitmap=null;
		bitmap=ThumbnailUtils.createVideoThumbnail(uri, kind);
		bitmap=ThumbnailUtils.extractThumbnail(bitmap, width, height, 
				ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
		return bitmap;
	}
}

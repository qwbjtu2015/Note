package com.ironqin.note;

import java.io.File;
import java.sql.Date;
import java.text.SimpleDateFormat;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.VideoView;

@SuppressLint("SimpleDateFormat")
public class AddContent extends Activity implements OnClickListener {

	private String val;
	private Button btn_save,btn_giveup;
	private ImageView img_photo;
	private VideoView v_video;
	private EditText edit_content;
	private File photo_file;
	private File video_file;
	
	private NotesDB notesDB;
	private SQLiteDatabase dbWriter;
	

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_content);
		
		val=getIntent().getStringExtra("flag");
		btn_save=(Button)findViewById(R.id.button_save);
		btn_giveup=(Button)findViewById(R.id.button_giveup);
		img_photo=(ImageView)findViewById(R.id.img_photo);
		v_video=(VideoView)findViewById(R.id.v_video);
		edit_content=(EditText)findViewById(R.id.edit_content);
		
		btn_save.setOnClickListener(this);
		btn_giveup.setOnClickListener(this);
		v_video.setOnClickListener(this);
		
		notesDB=new NotesDB(this);
		dbWriter=notesDB.getWritableDatabase();
		
		initView();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button_save:
			addDB();
			finish();
			break;
		case R.id.button_giveup:
			finish();
			break;
		case R.id.v_video:
			v_video.start();
			break;
		}
	}
	
	public void addDB(){
		ContentValues contentValues=new ContentValues();
		contentValues.put(NotesDB.CONTENT, edit_content.getText().toString());
		contentValues.put(NotesDB.TIME, getTime());
		if(img_photo.getVisibility()== View.GONE)
			contentValues.put(NotesDB.PHOTO,"null");
		else
			contentValues.put(NotesDB.PHOTO,photo_file+"");
		if(v_video.getVisibility()== View.GONE)
			contentValues.put(NotesDB.VIDEO,"null");
		else
			contentValues.put(NotesDB.VIDEO, video_file+"");
		dbWriter.insert(NotesDB.TABLE_NAME, null, contentValues);
	}
	
	private String getTime(){
		SimpleDateFormat format=new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
		java.util.Date date=new java.util.Date();
		String string=format.format(date);
		return string;
	}
	private String getDocumentName(){
		SimpleDateFormat format=new SimpleDateFormat("yyyy-mm-dd_HHmmss");
		java.util.Date date=new java.util.Date();
		String string=format.format(date);
		return string;
	}
	public void initView(){
		switch (val) {
		case "1"://文字
			img_photo.setVisibility(View.GONE);
			v_video.setVisibility(View.GONE);
			break;
		case "2"://图片+文字
			img_photo.setVisibility(View.VISIBLE);
			v_video.setVisibility(View.GONE);
			Intent camera=new Intent(
					MediaStore.ACTION_IMAGE_CAPTURE);//开启系统相机
			photo_file=new File(Environment.getExternalStorageDirectory()
					.getAbsoluteFile()+"/"+getDocumentName()+".jpg");//保存照片，只在数据库中存储地址
			camera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photo_file));
			startActivityForResult(camera, 1);
			break;
		case "3"://视频+文字
			img_photo.setVisibility(View.GONE);
			v_video.setVisibility(View.VISIBLE);
			Intent videointent=new Intent(
					MediaStore.ACTION_VIDEO_CAPTURE);
			video_file=new File(Environment.getExternalStorageDirectory()
					.getAbsoluteFile()+"/"+getDocumentName()+".mp4");
			videointent.putExtra(MediaStore.EXTRA_OUTPUT, 
					Uri.fromFile(video_file));
			startActivityForResult(videointent, 2);
			break;
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO 自动生成的方法存根
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 1) {
			Bitmap bitmap=BitmapFactory.
					decodeFile(photo_file.getAbsolutePath());
			img_photo.setImageBitmap(bitmap);
		}
		if (requestCode == 2) {
			v_video.setVideoURI(Uri.fromFile(video_file));
			//v_video.start();//播放
		}
	}
}

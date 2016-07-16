package com.ironqin.note;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

public class SelectAct extends Activity implements OnClickListener{

	private Button btn_delete,btn_back;
	private ImageView detail_photo;
	private VideoView detail_video;
	private TextView detail_text;
	private NotesDB notes;
	private SQLiteDatabase dbWriter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select);
		
		btn_delete=(Button) findViewById(R.id.button_delete);
		btn_back=(Button) findViewById(R.id.button_back);
		detail_photo=(ImageView)findViewById(R.id.detail_photo);
		detail_video=(VideoView)findViewById(R.id.detail_video);
		detail_text=(TextView)findViewById(R.id.detail_text);
		
		btn_delete.setOnClickListener(this);
		btn_back.setOnClickListener(this);
		detail_video.setOnClickListener(this);
		
		notes=new NotesDB(this);
		dbWriter=notes.getWritableDatabase();//修改权限
		
		if (getIntent().getStringExtra(NotesDB.PHOTO).equals("null")) {//没有图片
			detail_photo.setVisibility(View.GONE);
		}
		else {
			detail_photo.setVisibility(View.VISIBLE);
		}
		
		if (getIntent().getStringExtra(NotesDB.VIDEO).equals("null")) {//没有视频
			detail_video.setVisibility(View.GONE);
		}
		else {
			detail_video.setVisibility(View.VISIBLE);
		}
		
		detail_text.setText(getIntent().getStringExtra(NotesDB.CONTENT));
		Bitmap bitmap=BitmapFactory.decodeFile(getIntent().getStringExtra(NotesDB.PHOTO));
		detail_photo.setImageBitmap(bitmap);
		detail_video.setVideoURI(Uri.parse(getIntent().getStringExtra(NotesDB.VIDEO)));
		//detail_video.start();
		
		
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button_delete:
			deleteListView();
			finish();
			break;
		case R.id.button_back:
			finish();
			break;
		case R.id.detail_video:
			detail_video.start();
			break;
		}
	}
	
	public void deleteListView(){
		dbWriter.delete(NotesDB.TABLE_NAME, 
				"_id="+getIntent().getIntExtra(NotesDB.ID, 0), null);
		
	}
}

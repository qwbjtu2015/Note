package com.ironqin.note;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;



public class MainActivity extends Activity implements OnClickListener, OnItemClickListener{

	
	private Button btn_text,btn_photo,btn_video;
	private ListView listView;
	private Intent i;
	private MyAdapter myAdapter;
	private NotesDB notesDB;
	private SQLiteDatabase dbReader;
	private SQLiteDatabase dbWriter;
	private Cursor cursor;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		initView();
		//clearDatabase();//初始化，将原有数据库内容清空
		
		
		
	}
	
	public void initView(){
		listView=(ListView)findViewById(R.id.list);
		btn_text=(Button)findViewById(R.id.button_text);
		btn_photo=(Button)findViewById(R.id.button_photo);
		btn_video=(Button)findViewById(R.id.button_video);
		
		btn_text.setOnClickListener(this);
		btn_photo.setOnClickListener(this);
		btn_video.setOnClickListener(this);
		listView.setOnItemClickListener(this);
		

		notesDB=new NotesDB(this);
		dbReader=notesDB.getReadableDatabase();
	}

	@Override
	public void onClick(View v) {
		i=new Intent(this,AddContent.class);
		switch (v.getId()) {
			case R.id.button_text:
				i.putExtra("flag", "1");
				startActivity(i);
				break;
			case R.id.button_photo:
				i.putExtra("flag", "2");
				startActivity(i);
				break;
			case R.id.button_video:
				i.putExtra("flag", "3");
				startActivity(i);
				break;
		}
	}
	
	public void selectDB(){
		cursor=dbReader.query(NotesDB.TABLE_NAME, null, null, null, null, null, null);
		myAdapter=new MyAdapter(this, cursor);
		listView.setAdapter(myAdapter);
	}

	@Override
	protected void onResume() {
		super.onResume();
		selectDB();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		cursor.moveToPosition(position);
		Intent intent=new Intent(MainActivity.this,SelectAct.class);
		intent.putExtra(NotesDB.ID, cursor.getInt(cursor.getColumnIndex(NotesDB.ID)));
		intent.putExtra(NotesDB.CONTENT, cursor.getString(cursor.getColumnIndex(NotesDB.CONTENT)));
		intent.putExtra(NotesDB.TIME, cursor.getString(cursor.getColumnIndex(NotesDB.TIME)));
		intent.putExtra(NotesDB.PHOTO, cursor.getString(cursor.getColumnIndex(NotesDB.PHOTO)));
		intent.putExtra(NotesDB.VIDEO, cursor.getString(cursor.getColumnIndex(NotesDB.VIDEO)));
		startActivity(intent);
	}
	
	//清空数据库操作
	public void clearDatabase(){
		dbWriter=notesDB.getWritableDatabase();
		dbWriter.delete(NotesDB.TABLE_NAME, null, null);
	}
	
}

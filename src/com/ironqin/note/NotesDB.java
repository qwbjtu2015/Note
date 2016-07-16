package com.ironqin.note;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class NotesDB extends SQLiteOpenHelper{

	public NotesDB(Context context) {
		super(context, "notes", null, 1);
		// TODO �Զ����ɵĹ��캯�����
		//�ĸ������ֱ�Ϊcontext�����ݿ������汾��
	}

	public static final String TABLE_NAME = "notes";
	public static final String ID = "_id";
	public static final String CONTENT = "content";
	public static final String TIME = "time";
	public static final String PHOTO = "photo";
	public static final String VIDEO = "video";
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO �Զ����ɵķ������
		db.execSQL("create table "+TABLE_NAME+"("
				+ID+" integer primary key autoincrement,"
				+CONTENT+" text not null,"
				+PHOTO+" text ,"
				+VIDEO+" text ,"
				+TIME+" text not null)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO �Զ����ɵķ������
		
	}
	
}

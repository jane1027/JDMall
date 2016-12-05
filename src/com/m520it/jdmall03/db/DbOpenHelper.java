package com.m520it.jdmall03.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.m520it.jdmall03.cons.DbConst;

public class DbOpenHelper extends SQLiteOpenHelper {

	public DbOpenHelper(Context context) {
		super(context, DbConst.DB_NAME, null, DbConst.DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("create table " +DbConst.USER_TABLE+"(" +
				DbConst._ID+" integer primary key autoincrement," +
				DbConst._NAME+" text, " +
				DbConst._PWD+" text);");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}


}

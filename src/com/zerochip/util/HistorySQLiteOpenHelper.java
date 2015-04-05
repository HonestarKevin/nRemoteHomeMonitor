package com.zerochip.util;

import com.zerochip.remotehomemonitor.RootActivity;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class HistorySQLiteOpenHelper extends SQLiteOpenHelper {
	
	public static final boolean DEBUG = RootActivity.DEBUG;
	private static final String TAG = "com.zerochip.remotehomemonitor.HistorySQLiteOpenHelper";
	private static  String DatabaseName = "History.db";
	private static  int DatabaseVersion = 1;
	
	public static String _index_ = "_index_";
	public static String Time = "Time";
	public static String DevicesId = "DevicesId";
	public static String DevicesName = "DevicesName";
	public static String WarningContext = "WarningContext";
	
	public static String ColumnsInfo = 
			"[_index_] INT PRIMARY KEY DEFAULT 0," +
			"[Time] TIME  DEFAULT '00:00:00',"  +
			"[DevicesId] INT DEFAULT 0," +
			"[DevicesName] TEXT DEFAULT none, " +
			"[WarningContext] TEXT DEFAULT safety";
			
	
	public HistorySQLiteOpenHelper(WorkContext mWorkContext) {
		this(mWorkContext.mContext, DatabaseName, null, DatabaseVersion);
	}
	
	public HistorySQLiteOpenHelper(WorkContext mWorkContext, String name,
			CursorFactory factory, int version) {
		super(mWorkContext.mContext, name, factory, version);
	}
	
	public HistorySQLiteOpenHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.e(TAG, "onCreate db = "+db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.e(TAG, "onUpgrade db = "+db);
	}

}

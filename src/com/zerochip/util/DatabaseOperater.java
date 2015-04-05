package com.zerochip.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.zerochip.remotehomemonitor.RootActivity;

/**
 * @Function : 数据库操作类
 * @author Kevin.Xu
 * 
 */
public class DatabaseOperater {

	public static final boolean DEBUG = RootActivity.DEBUG;
	private static final String TAG = "com.zerochip.util.DatabaseOperater";

	private WorkContext mWorkContext = null;
	private HistorySQLiteOpenHelper mSqLiteOpenHelper = null;
	public SQLiteDatabase mSqLiteDatabase = null;

	public DatabaseOperater(WorkContext mWorkContext) {
		super();
		this.mWorkContext = mWorkContext;
	}

	/**
	 * @function : 打开数据库
	 * @param context
	 */
	public void OpenDB(Context context) {
		if (mSqLiteOpenHelper == null) {
			mSqLiteOpenHelper = new HistorySQLiteOpenHelper(mWorkContext);
			mSqLiteDatabase = mSqLiteOpenHelper.getWritableDatabase();
		}
	}

	/**
	 * @function : 只读方式打开数据库
	 * @param context
	 */
	public void ReadOnlyOpenDB(Context context) {
		if (mSqLiteOpenHelper == null) {
			mSqLiteOpenHelper = new HistorySQLiteOpenHelper(mWorkContext);
			mSqLiteDatabase = mSqLiteOpenHelper.getReadableDatabase();
		}
	}

	/**
	 * @function : 关闭数据库
	 * @param context
	 */
	public void CloseDB(Context context) {
		if (mSqLiteDatabase.isOpen()) {
			mSqLiteDatabase.close();
		}
		mSqLiteOpenHelper = null;
		mSqLiteDatabase = null;
	}

	/**
	 * @function 创建新的数据表
	 * @param tableName
	 *            表名称
	 */
	public void CreateTable(String tableName, String columnsInfo) {
		String SQLCreate = "CREATE TABLE IF NOT EXISTS " + tableName + "("
				+ columnsInfo + ")";
		mSqLiteDatabase.execSQL(SQLCreate);
	}

	/**
	 * @Function: 插入表数据
	 * @param table_name
	 *            表名称
	 * @param values
	 *            ContentValues对象
	 */
	public void InsertData(String TableName, ContentValues values) {
		// 调用insert方法，就可以将数据插入到数据库当中
		// 第一个参数:表名称
		// 第二个参数：SQl不允许一个空列，如果ContentValues是空的，那么这一列被明确的指明为NULL值
		// 第三个参数：ContentValues对象
		mSqLiteDatabase.insert(TableName, null, values);
	}

	/**
	 * @Function: 更新表数据
	 * @param TableName
	 *            表名
	 * @param values
	 *            ContentValues对象
	 * @param whereClause
	 *            where字句，相当于sql语句where后面的语句，？号是占位符
	 * @param whereArgs
	 *            占位符的值
	 */
	public void UpdateData(String TableName, ContentValues values,
			String whereClause, String[] whereArgs) {
		mSqLiteDatabase.update(TableName, values, whereClause, whereArgs);
	}

	/**
	 * @Function: 删除表数据
	 * @param TableName
	 *            表名
	 * @param whereClause
	 *            条件语句
	 * @param whereArgs
	 *            条件值
	 */
	public void DeleteData(String TableName, String whereClause,
			String[] whereArgs) {
		mSqLiteDatabase.delete(TableName, whereClause, whereArgs);
	}

	/**
	 * @Function : 查询表数据
	 * @param table
	 *            表名
	 * @param columns
	 *            要查询的列名
	 * @param selection
	 *            查询条件
	 * @param selectionArgs
	 *            查询条件的参数
	 * @param groupBy
	 *            对查询的结果进行分组
	 * @param having
	 *            对分组的结果进行限制
	 * @param orderBy
	 *            对查询的结果进行排序
	 */
	public Cursor QueryData(String table, String[] columns, String selection,
			String[] selectionArgs, String groupBy, String having,
			String orderBy) {
		return mSqLiteDatabase.query(table, columns, selection, selectionArgs,
				groupBy, having, orderBy);
	}

	/**
	 * @Function: 判断数据表是否存在
	 * @param table
	 * @return
	 */
	public boolean TabelIsExist(String table) {
		if (table == null)
			return false;
		try {
			String sqlExecString = "select count(*) as c from sqlite_master where type ='table' and name ='"
					+ table.trim() + "' ";
			Cursor cursor = mSqLiteDatabase.rawQuery(sqlExecString, null);
			if (cursor.moveToNext()) {
				int count = cursor.getInt(0);
				if (count > 0) {
					return true;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return false;
	}
}

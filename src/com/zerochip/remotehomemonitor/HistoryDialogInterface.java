package com.zerochip.remotehomemonitor;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.zerochip.util.DateUtil;
import com.zerochip.util.HistorySQLiteOpenHelper;
import com.zerochip.util.WorkContext;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;

/**
 * @Function : 历史界面
 * @author xu
 * 
 */
public class HistoryDialogInterface extends Dialog {

	public static final boolean DEBUG = RootActivity.DEBUG;
	private static final String TAG = "com.zerochip.remotehomemonitor.HistoryDialogInterface";
	private WorkContext mWorkContext = null;
	private Context mContext = null;
	private ListView mHistoriesListView = null;
	private List<HistoriesData> mHistoriesDatasList = null;

	private DatePicker mDatePicker = null;

	/**
	 * @function 历史数据类
	 * @author xu
	 * 
	 */
	class HistoriesData {
		public String Index;
		public String Time;
		public String Id;
		public String Name;
		public String Context;
	}

	/**
	 * @function 历史显示Listview 显示数据适配器
	 * @author xu
	 * 
	 */
	class HistoriesAdapter extends BaseAdapter {
		private WorkContext mWorkContext = null;
		private List<HistoriesData> mHistoriesDatas = null;
		private LayoutInflater mListLayoutInflater = null;

		public HistoriesAdapter(WorkContext mWorkContext,
				List<HistoriesData> mHistoriesDatas) {
			super();
			this.mWorkContext = mWorkContext;
			this.mHistoriesDatas = mHistoriesDatas;
			this.mListLayoutInflater = LayoutInflater
					.from(mWorkContext.mContext);
		}

		@Override
		public int getCount() {
			if (DEBUG)
				Log.e(TAG, "mContactsList.size = " + mHistoriesDatas.size());
			return mHistoriesDatas.size();
		}

		@Override
		public Object getItem(int arg0) {
			return arg0;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			TextView NameTextView = null;
			TextView TimeTextView = null;
			TextView IdTextView = null;
			TextView ContextTextView = null;
			if (convertView == null) {
				convertView = mListLayoutInflater.inflate(
						R.layout.history_item, null);
			}
			NameTextView = (TextView) convertView
					.findViewById(R.id.tx_histories_dialog_name);
			TimeTextView = (TextView) convertView
					.findViewById(R.id.tx_histories_dialog_time);
			IdTextView = (TextView) convertView
					.findViewById(R.id.tx_histories_dialog_id);
			ContextTextView = (TextView) convertView
					.findViewById(R.id.tx_histories_dialog_context);
			ContextTextView.setGravity(Gravity.LEFT);

			NameTextView.setText(mHistoriesDatas.get(position).Name);
			TimeTextView.setText(mHistoriesDatas.get(position).Time);
			IdTextView.setText(mHistoriesDatas.get(position).Id);
			ContextTextView.setText(mHistoriesDatas.get(position).Context);
			return convertView;
		}

	}

	/**
	 * @Funciton
	 * @param context
	 * @param theme
	 * @param workContext
	 */
	public HistoryDialogInterface(Context context, int theme,
			WorkContext workContext) {
		super(workContext.mActivity, theme);
		this.mWorkContext = workContext;
		this.mContext = workContext.mContext;
		// 打开数据库；
		mWorkContext.mDatabaseOperater.OpenDB(mWorkContext.mContext);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_histories);
		mHistoriesListView = (ListView) findViewById(R.id.lt_histories_dialog);
		mHistoriesDatasList = new ArrayList<HistoriesData>();
		mDatePicker = (DatePicker) findViewById(R.id.dp_histories_dialog);

		int year = DateUtil.getInstance().getYear();
		int monthOfYear = DateUtil.getInstance().getMonthOfYear();
		int dayOfMonth = DateUtil.getInstance().getDayOfMonth();

		if (DEBUG)
			Log.e(TAG, "year = " + year + " monthOfYear =  " + monthOfYear
					+ " dayOfMonth = " + dayOfMonth);
		mDatePicker.init(year, monthOfYear, dayOfMonth,
				new DatePicker.OnDateChangedListener() {

					@Override
					public void onDateChanged(DatePicker view, int year,
							int monthOfYear, int dayOfMonth) {
						if (DEBUG)
							Log.e(TAG, "onDateChanged year = " + year
									+ " monthOfYear =  " + monthOfYear
									+ " dayOfMonth = " + dayOfMonth);
						//InsertTestDataToDataBase();
						LoadDataToListview(year, monthOfYear + 1, dayOfMonth);
					}

				});
		LoadDataToListview(year, monthOfYear, dayOfMonth);

	}

	private void InsertTestDataToDataBase() {
		String TableName = "tbl_" + 2015 + "_" + 3 + "_" + 27;
		int index = 0;

		mWorkContext.mDatabaseOperater.CreateTable(TableName,
				HistorySQLiteOpenHelper.ColumnsInfo);
		String columns[] = { HistorySQLiteOpenHelper._index_,
				HistorySQLiteOpenHelper.Time,
				HistorySQLiteOpenHelper.DevicesId,
				HistorySQLiteOpenHelper.DevicesName,
				HistorySQLiteOpenHelper.WarningContext };
		Cursor mCursor = mWorkContext.mDatabaseOperater.QueryData(TableName,
				columns, null, null, null, null, null);
		
		index = mCursor.getCount();
		ContentValues values = new ContentValues();
		for (int j = 0; j < 5; j++) {
			int _index_ = index +j;
			Log.e(TAG, "index = "+ _index_);
			values.put(HistorySQLiteOpenHelper._index_, _index_);
			values.put(HistorySQLiteOpenHelper.Time, "21:03:0" + j);
			values.put(HistorySQLiteOpenHelper.DevicesId, j);
			values.put(HistorySQLiteOpenHelper.DevicesName, "" + j + index
					+ "号门");
			values.put(HistorySQLiteOpenHelper.WarningContext, "" + j + index
					+ "号测试内容");
			mWorkContext.mDatabaseOperater.InsertData(TableName, values);
		}
	}

	private void LoadDataToListview(int year, int monthOfYear, int dayOfMonth) {
		mHistoriesDatasList.removeAll(mHistoriesDatasList);
		String TableName = "tbl_" + year + "_" + monthOfYear + "_" + dayOfMonth;
		Log.e(TAG, "TableName = " + TableName);
		if (mWorkContext.mDatabaseOperater.TabelIsExist(TableName)) {
			String columns[] = { HistorySQLiteOpenHelper._index_,
					HistorySQLiteOpenHelper.Time,
					HistorySQLiteOpenHelper.DevicesId,
					HistorySQLiteOpenHelper.DevicesName,
					HistorySQLiteOpenHelper.WarningContext };
			Cursor mCursor = mWorkContext.mDatabaseOperater.QueryData(
					TableName, columns, null, null, null, null, null);
			
			if (DEBUG) {
				Log.e(TAG,
						"INDEX LAST="
								+ mCursor.getColumnCount());
			}
			while (mCursor.moveToNext()) {
				HistoriesData mData = new HistoriesData();
				mData.Time = mCursor.getString(mCursor
						.getColumnIndex(HistorySQLiteOpenHelper.Time));
				mData.Id = String.valueOf(mCursor.getInt(mCursor
						.getColumnIndex(HistorySQLiteOpenHelper.DevicesId)));
				mData.Name = mCursor.getString(mCursor
						.getColumnIndex(HistorySQLiteOpenHelper.DevicesName));
				mData.Context = mCursor
						.getString(mCursor
								.getColumnIndex(HistorySQLiteOpenHelper.WarningContext));
				mHistoriesDatasList.add(mData);
			}
			mHistoriesListView.setAdapter(new HistoriesAdapter(mWorkContext,
					mHistoriesDatasList));
		} else {
			HistoriesData mData = new HistoriesData();
			mData.Time = mWorkContext.mResources
					.getString(R.string.str_main_none);
			mData.Id = mWorkContext.mResources
					.getString(R.string.str_main_none);
			mData.Name = mWorkContext.mResources
					.getString(R.string.str_main_none);
			mData.Context = mWorkContext.mResources
					.getString(R.string.str_main_none);
			mHistoriesDatasList.add(mData);
			mHistoriesListView.setAdapter(new HistoriesAdapter(mWorkContext,
					mHistoriesDatasList));
		}
	}

}

package com.zerochip.remotehomemonitor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.content.res.AssetManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.zerochip.remotehomemonitor.HistoryDialogInterface.HistoriesData;
import com.zerochip.util.WorkContext;
import com.zerochip.util.WorkContext.TONE_TYPE;

public class ToneSettingsDialogViewHolder {
	public static final boolean DEBUG = RootActivity.DEBUG;
	private static final String TAG = "com.zerochip.remotehomemonitor.ToneSettingsDialogViewHolder";
	private WorkContext mWorkContext = null;
	private ToneSettingsDialogInterface mToneSettingsDialogInterface = null;
	private WorkContext.TONE_TYPE mToneType = TONE_TYPE.SOME_ONE_GO_HOME;
	public TextView ToneSubtitleTextView = null;
	public ListView ToneSelectListView = null;
	public String ToneFileList[] = null;
	public ArrayList<ToneFileInfo> mToneFileInfoList = null;
	private ToneSelectAdapter mToneSelectAdapter = null;
	class ToneFileInfo
	{
		String FileName = null;
		boolean IsSelect = false;
	}

	public ToneSettingsDialogViewHolder(WorkContext mWorkContext,
			ToneSettingsDialogInterface mDialogInterface, TONE_TYPE mToneType) {
		super();
		this.mWorkContext = mWorkContext;
		this.mToneSettingsDialogInterface = mDialogInterface;
		this.mToneType = mToneType;
		FindToneFile();
		FindAllView();
		SetViewListener();
		InitViewData();
	}
	private void FindToneFile() {
		try {
			ToneFileList = mWorkContext.mAssetManager.list(mWorkContext.ToneFilePath);
			mToneFileInfoList = new ArrayList<ToneSettingsDialogViewHolder.ToneFileInfo>();
			if(DEBUG) Log.e(TAG, "file list = " + ToneFileList.length);
			for (String name : ToneFileList) {
				if(DEBUG) Log.e(TAG, "name = "+ name);
				ToneFileInfo mToneFileInfo = new ToneFileInfo();
				mToneFileInfo.FileName = name;
				mToneFileInfo.IsSelect = false;
				mToneFileInfoList.add(mToneFileInfo);
			}
			if(DEBUG) Log.e(TAG, "mToneFileInfoList  = " + mToneFileInfoList);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * @function Listview 显示数据适配器
	 * @author xu
	 * 
	 */
	class ToneSelectAdapter extends BaseAdapter {
		private WorkContext mWorkContext = null;
		private ArrayList<ToneFileInfo>mToneInfoList = null;
		private LayoutInflater mListLayoutInflater = null;

		public ToneSelectAdapter(WorkContext mWorkContext,
				ArrayList<ToneFileInfo>mToneInfoList) {
			super();
			this.mWorkContext = mWorkContext;
			this.mToneInfoList = mToneInfoList;
			this.mListLayoutInflater = LayoutInflater
					.from(mWorkContext.mContext);
		}

		@Override
		public int getCount() {
			if (DEBUG)
				Log.e(TAG, "ToneInfoList.size = " + mToneInfoList.size());
			return mToneInfoList.size();
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
			TextView FileNameTextView = null;
			RadioButton  IsSelectRadioButton= null;

			if (convertView == null) {
				convertView = mListLayoutInflater.inflate(
						R.layout.dialog_tone_item, null);
			}
			FileNameTextView = (TextView) convertView
					.findViewById(R.id.textview_dialog_tone_file_name);
			IsSelectRadioButton = (RadioButton) convertView
					.findViewById(R.id.radiabutton_dialog_tone_select);
			
			FileNameTextView.setText(this.mToneInfoList.get(position).FileName);
			IsSelectRadioButton.setSelected(this.mToneInfoList.get(position).IsSelect);
			return convertView;
		}

	}
	private void InitViewData() {
		//根据选择的类型来读取对应的设置铃声的名称，并做初始化设置
		//1.设置radio button select state
		mToneSelectAdapter = new ToneSelectAdapter(mWorkContext, mToneFileInfoList);
		ToneSelectListView.setAdapter(mToneSelectAdapter);
	}

	private void FindAllView() {
		ToneSubtitleTextView = (TextView) mToneSettingsDialogInterface
				.findViewById(R.id.textview_tone_dialog_subtitle);
		ToneSelectListView = (ListView) mToneSettingsDialogInterface
				.findViewById(R.id.listview_tone_dialog_select_tone);
	}

	private void SetViewListener() {
		ToneSelectListView.setOnItemClickListener(new ListView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				mToneSelectAdapter.notifyDataSetChanged();
			}
		});
	}
}

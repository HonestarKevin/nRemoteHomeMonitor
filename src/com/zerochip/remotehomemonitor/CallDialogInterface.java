package com.zerochip.remotehomemonitor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.zerochip.remotehomemonitor.R.string;
import com.zerochip.util.WorkContext;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
/**
 * @Function :呼叫界面
 * @author xu
 *
 */
public class CallDialogInterface extends Dialog {

	public static final boolean DEBUG = RootActivity.DEBUG;
	private static final String TAG = "com.zerochip.remotehomemonitor.CallDialogInterface";

	private WorkContext mWorkContext = null;
	private Context mContext = null;
	
	//联系人列表
	private ListView ContactsListView = null;
	private HashMap<String, String> mContactsHashMap = null;
	private List<String> NameList = null;

	public CallDialogInterface(Context context, int theme, WorkContext mWorkContext, Activity mActivity) {
		super(mActivity, theme);
		this.mWorkContext = mWorkContext;
		this.mContext = context;
	}
	/**
	 * @Function：呼叫界面增加数据
	 * @author xu
	 *
	 */
	private static class ContactsAdapter extends BaseAdapter {

		private WorkContext  mWorkContext = null;
		private HashMap<String, String> mContactsList = null;
		private LayoutInflater mListLayoutInflater = null;
		private List<String> mContactsNameList = null;
		
		public ContactsAdapter(WorkContext mWorkContext, HashMap<String, String> ContactsList, List<String> nameList){
			this.mWorkContext = mWorkContext;
			this.mContactsList= ContactsList;
			this.mContactsNameList = nameList;
			mListLayoutInflater = LayoutInflater.from(mWorkContext.mContext);
		}
		@Override
		public int getCount() {
			if(DEBUG) Log.e(TAG, "mContactsList.size = "+  mContactsList.size());
			if(DEBUG) Log.e(TAG, "mContactsNameList.size() = "+  mContactsNameList.size());
			return mContactsNameList.size();
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
			TextView PhoneTextView = null;
			if(convertView == null){
				convertView = mListLayoutInflater.inflate(R.layout.call_contacts_item, null);
			}
			NameTextView = (TextView)convertView.findViewById(R.id.tx_call_contacts_name);
			PhoneTextView = (TextView)convertView.findViewById(R.id.tx_call_contacts_phone);
			NameTextView.setText(mContactsNameList.get(position)+":");
			PhoneTextView.setText(mContactsList.get(mContactsNameList.get(position)));
			
			Log.e(TAG, "mContactsNameList.get(position)" +mContactsNameList.get(position));
			Log.e(TAG, "mContactsList.get(mContactsNameList.get(position)) " + mContactsList.get(mContactsNameList.get(position)));
			return convertView;
		}
		
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_call_contacts);
		ContactsListView = (ListView)findViewById(R.id.lt_call_contacts_dialog);
		
		mContactsHashMap = new HashMap<String, String>();
		NameList = new ArrayList<String>();
		//NOTE :
			//需要读取用户设定的号码；
		
		NameList.add("police");
		NameList.add("kv");
		NameList.add("kv1");
		NameList.add("kv2");
		
		mContactsHashMap.put("police", "123");
		mContactsHashMap.put("kv", "669325");
		mContactsHashMap.put("kv1", "662114");
		mContactsHashMap.put("kv2", "13570805860");
		if(DEBUG) Log.e(TAG, "ContactsListView "+ ContactsListView);
		ContactsListView.setAdapter(new ContactsAdapter(mWorkContext, mContactsHashMap, NameList));
		ContactsListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				String PhoneNumber = mContactsHashMap.get(NameList.get(position));
				if(DEBUG) Log.e(TAG, "PhoneNumber = "+ PhoneNumber);
				Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel://" + PhoneNumber));
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				mWorkContext.mContext.startActivity(intent); 
			}
		});
	}
	
}

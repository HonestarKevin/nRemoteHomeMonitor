package com.zerochip.remotehomemonitor;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.zerochip.util.WorkContext;

/**
 * @Function: 主界面
 * @author xu
 *
 */
public class MainInterfaceViewHolder {
	public static final boolean DEBUG = RootActivity.DEBUG;
	private static final String TAG = "com.zerochip.remotehomemonitor.MainInterfaceViewHolder";

	private WorkContext mWorkContext = null;

	// exit Button
	public Button ExitButton = null;

	// Warning Button
	public ImageButton WarningImageButton = null;
	// Warning ID TextView
	public TextView WarningIDTextView = null;

	// Main Button
	// 安全状态 Button
	public Button SafetyStatuButton = null;
	// 呼叫 Button
	public Button CallButton = null;
	// 设置 Button
	public Button SettingsButton = null;
	// 历史 Button
	public Button HistoriesButton = null;

	// 用户名 TextView
	public TextView UsernameTextView = null;

	public MainInterfaceViewHolder(WorkContext mWorkContext) {
		super();
		this.mWorkContext = mWorkContext;
		FindAllView();
		SetViewListener();
		SetInitViewData();
	}

	private void FindAllView() {
		// exit Button
		ExitButton = (Button) mWorkContext.mActivity
				.findViewById(R.id.bt_main_exit);

		// Warning Button
		WarningImageButton = (ImageButton) mWorkContext.mActivity
				.findViewById(R.id.ibt_main_warning);
		// Warning ID TextView
		WarningIDTextView = (TextView) mWorkContext.mActivity
				.findViewById(R.id.tx_main_warning_id);

		// Main Button
		// 安全状态 Button
		SafetyStatuButton = (Button) mWorkContext.mActivity
				.findViewById(R.id.bt_main_safety_staus);
		// 呼叫 Button
		CallButton = (Button) mWorkContext.mActivity
				.findViewById(R.id.bt_main_call);
		// 设置 Button
		SettingsButton = (Button) mWorkContext.mActivity
				.findViewById(R.id.bt_main_settings);
		// 历史 Button
		HistoriesButton = (Button) mWorkContext.mActivity
				.findViewById(R.id.bt_main_history);

		// 用户名 TextView
		UsernameTextView = (TextView) mWorkContext.mActivity
				.findViewById(R.id.tx_main_username);
	}
	/**
	 * @function 安全状态被按下；
	 */
	Runnable SafetyStatusButtonClickRunnable = new Runnable() {

		@Override
		public void run() {
			
		}
	};
	/**
	 * @function: 呼叫按键
	 */
	Runnable CallButtonClickRunnable = new Runnable() {

		@Override
		public void run() {
			//Show Dialog and on click goto call interface
			new CallDialogInterface(mWorkContext.mContext, R.style.CallDialogInterface, mWorkContext, mWorkContext.mActivity).show();
		}
	};
	/**
	 * @function :设置页面
	 */
	Runnable SettingsButtonClickRunnable = new Runnable() {

		@Override
		public void run() {
			//goto Settings Interface
		}
	};
	/**
	 * @function ：历史页面
	 */
	Runnable HistoriesButtonClickRunnable = new Runnable() {

		@Override
		public void run() {
			//goto History Interface
		}
	};
	View.OnClickListener OnClickListener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.bt_main_exit:
				if(DEBUG)Log.e(TAG, "bt_main_exit");
				System.exit(0);
				break;
			case R.id.bt_main_safety_staus:
				mWorkContext.mHandler.post(SafetyStatusButtonClickRunnable);
				break;
			case R.id.bt_main_call:
				mWorkContext.mHandler.post(CallButtonClickRunnable);
				break;
			case R.id.bt_main_settings:
				mWorkContext.mHandler.post(SettingsButtonClickRunnable);
				break;
			case R.id.bt_main_history:
				mWorkContext.mHandler.post(HistoriesButtonClickRunnable);
				break;
			default:
				break;
			}
		}
	};
	/**
	 * @function: 给view 增加监听事件
	 */
	private void SetViewListener() {
		WarningImageButton.setOnClickListener(OnClickListener);
		SafetyStatuButton.setOnClickListener(OnClickListener);
		CallButton.setOnClickListener(OnClickListener);
		SettingsButton.setOnClickListener(OnClickListener);
		HistoriesButton.setOnClickListener(OnClickListener);
	}
	/**
	 * @function: 给有需要的View增加数据
	 */
	private void SetInitViewData() {
		WarningIDTextView.setText(mWorkContext.mResources
				.getString(R.string.str_main_warning_id)
				+ mWorkContext.mResources.getString(R.string.str_main_none));
		UsernameTextView.setText(mWorkContext.mResources
				.getString(R.string.str_user_setup_name)
				+ mWorkContext.mSharedPreferencesDataHelper.GetLocalUserName());
		SafetyStatuButton.setText(mWorkContext.mResources.getString(R.string.str_main_safety));
	}
}

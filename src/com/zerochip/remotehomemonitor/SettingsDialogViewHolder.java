package com.zerochip.remotehomemonitor;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zerochip.util.WorkContext;
import com.zerochip.util.WorkContext.TONE_TYPE;

public class SettingsDialogViewHolder {

	public static final boolean DEBUG = RootActivity.DEBUG;
	private static final String TAG = "com.zerochip.remotehomemonitor.SettingsDialogViewHolder";

	private WorkContext mWorkContext = null;
	private SettingsDialogInterface mSettingsDialogInterface = null;
	// 提示音设置
	LinearLayout SomeoneGoHomeToneLineLayout = null;
	LinearLayout SomeoneAwayHomeToneLineLayout = null;
	LinearLayout PhoneWarningToneLineLayout = null;
	LinearLayout PhoneWarningTimesLineLayout = null;
	LinearLayout MyselfGoHomeToneLineLayout = null;
	LinearLayout MyselfAwayHomeToneLineLayout = null;

	// 感应设备设置
	LinearLayout AddDevicesLineLayout = null;
	ImageView AddDevicesLineLayoutDownImageView = null;
	LinearLayout AddDeviceContainerLayout = null;
	EditText AddDevicesName = null;
	EditText AddDevicesId = null;
	Button AddDeviceButton = null;

	LinearLayout DelDevicesLineLayout = null;
	ImageView DelDevicesLineLayoutDownImageView = null;
	LinearLayout DelDeviceContainerLayout = null;
	EditText DelDevicesId = null;
	EditText DelDevicesIdVerify = null;
	Button DeleteDeviceButton = null;

	// 系统信息
	TextView TelnetName = null;
	TextView TelnetConnectionNumber = null;
	TextView TelnetAdminName = null;
	TextView TelnetAdminPhoneNumber = null;

	public SettingsDialogViewHolder(WorkContext mWorkContext,
			SettingsDialogInterface mDialogInterface) {
		super();
		this.mWorkContext = mWorkContext;
		this.mSettingsDialogInterface = mDialogInterface;
		FindAllView();
		SetViewListener();
		InitViewData();
	}

	/**
	 * @function 初始化view 的数据
	 */
	private void InitViewData() {
		TelnetName.setText(mWorkContext.mSharedPreferencesDataHelper
				.GetTelnetname());
		TelnetConnectionNumber
				.setText(mWorkContext.mSharedPreferencesDataHelper
						.GetTelnetConnectionNumber(mWorkContext.mSharedPreferencesDataHelper
								.GetTelnetname()));
		if (mWorkContext.mSharedPreferencesDataHelper.GetUserMode() == WorkContext.ADMIN_MODE) {
			TelnetAdminName.setText(mWorkContext.mSharedPreferencesDataHelper
					.GetLocalUserName());
		} else if (mWorkContext.mSharedPreferencesDataHelper.GetUserMode() == WorkContext.COMMON_USER_MODE) {
			TelnetAdminName.setText(mWorkContext.mSharedPreferencesDataHelper
					.GetRemoteAdminName());
			TelnetAdminPhoneNumber
					.setText(mWorkContext.mSharedPreferencesDataHelper
							.GetRemoteAdminPhoneNumber());
		}
	}

	Runnable AddDevicesLineLayoutOnClickRunnable = new Runnable() {

		@Override
		public void run() {
			if (DEBUG) {
				Log.e(TAG, "AddDeviceContainerLayout.getVisibility = "
						+ AddDeviceContainerLayout.getVisibility());
			}
			if (AddDeviceContainerLayout.getVisibility() == View.GONE) {
				AddDevicesLineLayoutDownImageView
						.setBackgroundResource(R.drawable.up);
				AddDeviceContainerLayout.setVisibility(View.VISIBLE);
			} else if (AddDeviceContainerLayout.getVisibility() == View.VISIBLE) {
				AddDevicesLineLayoutDownImageView
						.setBackgroundResource(R.drawable.down);
				AddDeviceContainerLayout.setVisibility(View.GONE);
			} else {
				AddDevicesLineLayoutDownImageView
						.setBackgroundResource(R.drawable.down);
				AddDeviceContainerLayout.setVisibility(View.GONE);
			}
		}
	};

	public String GetAddDevicesName() {

		if (AddDevicesName == null) {
			return null;
		} else if (AddDevicesName.getText().toString().equals("")) {
			ShowToast(mWorkContext.mResources
					.getString(R.string.str_please_input_sensor_device_name));
			return null;
		} else {
			return AddDevicesName.getText().toString();
		}
	}

	public String GetAddDevicesId() {
		if (AddDevicesId == null) {
			return null;
		} else if (AddDevicesId.getText().toString().equals("")) {
			ShowToast(mWorkContext.mResources
					.getString(R.string.str_please_input_sensor_device_number_id));
			return null;
		} else {
			return AddDevicesId.getText().toString();
		}
	}

	Runnable AddDeviceButtonOnClickRunnable = new Runnable() {

		@Override
		public void run() {
			String DeviceName = GetAddDevicesName();
			if (DeviceName == null)
				return;
			String DeviceId = GetAddDevicesId();
			if (DeviceId == null)
				return;
			if (mWorkContext.mSharedPreferencesDataHelper.AddDevice(DeviceName,
					DeviceId))
				ShowToast(mWorkContext.mResources.getString(
						R.string.str_device_add).toString()
						+ mWorkContext.mResources.getString(
								R.string.str_device_setup_name).toString()
						+ DeviceName
						+ mWorkContext.mResources.getString(
								R.string.str_device_setup_number).toString()
						+ DeviceId);
		}
	};

	Runnable DelDevicesLineLayoutOnClickRunnable = new Runnable() {

		@Override
		public void run() {
			if (DEBUG) {
				Log.e(TAG, "DelDeviceContainerLayout.getVisibility = "
						+ DelDeviceContainerLayout.getVisibility());
			}
			if (DelDeviceContainerLayout.getVisibility() == View.GONE) {
				DelDevicesLineLayoutDownImageView
						.setBackgroundResource(R.drawable.up);
				DelDeviceContainerLayout.setVisibility(View.VISIBLE);
			} else if (DelDeviceContainerLayout.getVisibility() == View.VISIBLE) {
				DelDevicesLineLayoutDownImageView
						.setBackgroundResource(R.drawable.down);
				DelDeviceContainerLayout.setVisibility(View.GONE);
			} else {
				DelDevicesLineLayoutDownImageView
						.setBackgroundResource(R.drawable.down);
				DelDeviceContainerLayout.setVisibility(View.GONE);
			}
		}
	};

	public String GetDelDevicesId() {
		if (DelDevicesId == null) {
			return null;
		} else if (DelDevicesId.getText().toString().equals("")) {
			ShowToast(mWorkContext.mResources
					.getString(R.string.str_please_input_sensor_device_number_id));
			return null;
		} else {
			return DelDevicesId.getText().toString();
		}
	}

	public String GetDelDevicesVerifyId() {
		if (DelDevicesIdVerify == null) {
			return null;
		} else if (DelDevicesIdVerify.getText().toString().equals("")) {
			ShowToast(mWorkContext.mResources
					.getString(R.string.str_please_input_sensor_device_number_id_verify));
			return null;
		} else {
			return DelDevicesIdVerify.getText().toString();
		}
	}

	Runnable DelDeviceButtonOnClickRunnable = new Runnable() {

		@Override
		public void run() {
			String DelDeviceId = GetDelDevicesId();
			if (DelDeviceId == null)
				return;
			String DelDeviceIdVerify = GetDelDevicesVerifyId();
			if (DelDeviceIdVerify == null)
				return;
			if (DelDeviceId.equals(DelDeviceIdVerify)) {
				if (mWorkContext.mSharedPreferencesDataHelper
						.DelDevice(DelDeviceId))
					ShowToast(mWorkContext.mResources
							.getString(R.string.str_device_del) + DelDeviceId);
			} else {
				ShowToast(mWorkContext.mResources
						.getString(R.string.str_input_sensor_device_number_id_verify_error));
			}
		}
	};

	View.OnClickListener mViewOnClickListener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			if (DEBUG)
				Log.e(TAG, "onClick v = " + v + " id = " + v.getId());
			switch (v.getId()) {
			case R.id.ll_someone_go_home_tone_settings_dialog:
				new ToneSettingsDialogInterface(mWorkContext.mContext,
						R.style.HistoriesDialogInterface, mWorkContext,
						TONE_TYPE.SOME_ONE_GO_HOME).show();
				break;
			case R.id.ll_someone_away_home_tone_settings_dialog:
				new ToneSettingsDialogInterface(mWorkContext.mContext,
						R.style.HistoriesDialogInterface, mWorkContext,
						TONE_TYPE.SOME_ONE_AWAY_HOME).show();
				break;
			case R.id.ll_phone_warning_tone_settings_dialog:
				new ToneSettingsDialogInterface(mWorkContext.mContext,
						R.style.HistoriesDialogInterface, mWorkContext,
						TONE_TYPE.PHONE_WARNING).show();
				break;
			case R.id.ll_phone_warning_time_settings_dialog:
				new ToneSettingsDialogInterface(mWorkContext.mContext,
						R.style.HistoriesDialogInterface, mWorkContext,
						TONE_TYPE.TONE_TIME).show();
				break;
			case R.id.ll_myself_go_home_settings_dialog:
				new ToneSettingsDialogInterface(mWorkContext.mContext,
						R.style.HistoriesDialogInterface, mWorkContext,
						TONE_TYPE.MYSELF_GO_HOME).show();
				break;
			case R.id.ll_myself_away_home_settings_dialog:
				new ToneSettingsDialogInterface(mWorkContext.mContext,
						R.style.HistoriesDialogInterface, mWorkContext,
						TONE_TYPE.MYSELF_AWAY_HOME).show();
				break;

			case R.id.ll_add_devices_settings_dialog:
				mWorkContext.mHandler.post(AddDevicesLineLayoutOnClickRunnable);
				break;
			case R.id.b_settings_add_device:
				mWorkContext.mHandler.post(AddDeviceButtonOnClickRunnable);
				break;
			case R.id.ll_del_devices_settings_dialog:
				mWorkContext.mHandler.post(DelDevicesLineLayoutOnClickRunnable);
				break;
			case R.id.b_settings_del_device:
				mWorkContext.mHandler.post(DelDeviceButtonOnClickRunnable);
				break;
			default:
				break;
			}
		}
	};

	/**
	 * @function 设置view 的触发事件
	 */
	private void SetViewListener() {
		// 设置View触发事件：
		// 提示音设置
		SomeoneGoHomeToneLineLayout.setOnClickListener(mViewOnClickListener);
		SomeoneAwayHomeToneLineLayout.setOnClickListener(mViewOnClickListener);
		PhoneWarningToneLineLayout.setOnClickListener(mViewOnClickListener);
		PhoneWarningTimesLineLayout.setOnClickListener(mViewOnClickListener);
		MyselfGoHomeToneLineLayout.setOnClickListener(mViewOnClickListener);
		MyselfAwayHomeToneLineLayout.setOnClickListener(mViewOnClickListener);

		// 感应设备设置
		AddDevicesLineLayout.setOnClickListener(mViewOnClickListener);
		DelDevicesLineLayout.setOnClickListener(mViewOnClickListener);
		AddDeviceButton.setOnClickListener(mViewOnClickListener);
		DeleteDeviceButton.setOnClickListener(mViewOnClickListener);
	}

	/**
	 * @Function: 找出所有的View;
	 */
	private void FindAllView() {
		// TODO Auto-generated method stub
		// 提示音设置
		SomeoneGoHomeToneLineLayout = (LinearLayout) mSettingsDialogInterface
				.findViewById(R.id.ll_someone_go_home_tone_settings_dialog);
		SomeoneAwayHomeToneLineLayout = (LinearLayout) mSettingsDialogInterface
				.findViewById(R.id.ll_someone_away_home_tone_settings_dialog);
		PhoneWarningToneLineLayout = (LinearLayout) mSettingsDialogInterface
				.findViewById(R.id.ll_phone_warning_tone_settings_dialog);
		PhoneWarningTimesLineLayout = (LinearLayout) mSettingsDialogInterface
				.findViewById(R.id.ll_phone_warning_time_settings_dialog);
		MyselfGoHomeToneLineLayout = (LinearLayout) mSettingsDialogInterface
				.findViewById(R.id.ll_myself_go_home_settings_dialog);
		MyselfAwayHomeToneLineLayout = (LinearLayout) mSettingsDialogInterface
				.findViewById(R.id.ll_myself_away_home_settings_dialog);

		// 感应设备设置
		AddDevicesLineLayout = (LinearLayout) mSettingsDialogInterface
				.findViewById(R.id.ll_add_devices_settings_dialog);
		AddDevicesLineLayoutDownImageView = (ImageView) mSettingsDialogInterface
				.findViewById(R.id.iv_settings_dialog_add_down);
		AddDeviceContainerLayout = (LinearLayout) mSettingsDialogInterface
				.findViewById(R.id.ll_settings_dialog_add_devices);
		AddDevicesName = (EditText) mSettingsDialogInterface
				.findViewById(R.id.e_settings_add_device_name);
		AddDevicesId = (EditText) mSettingsDialogInterface
				.findViewById(R.id.e_settings_add_device_number);
		AddDeviceButton = (Button) mSettingsDialogInterface
				.findViewById(R.id.b_settings_add_device);

		DelDevicesLineLayout = (LinearLayout) mSettingsDialogInterface
				.findViewById(R.id.ll_del_devices_settings_dialog);
		DelDevicesLineLayoutDownImageView = (ImageView) mSettingsDialogInterface
				.findViewById(R.id.iv_settings_dialog_del_down);
		DelDeviceContainerLayout = (LinearLayout) mSettingsDialogInterface
				.findViewById(R.id.ll_settings_dialog_del_devices);
		DelDevicesId = (EditText) mSettingsDialogInterface
				.findViewById(R.id.e_settings_del_device_number);
		DelDevicesIdVerify = (EditText) mSettingsDialogInterface
				.findViewById(R.id.e_settings_del_device_number_repeat);
		DeleteDeviceButton = (Button) mSettingsDialogInterface
				.findViewById(R.id.b_settings_del_device);

		// 系统信息
		TelnetName = (TextView) mSettingsDialogInterface
				.findViewById(R.id.tv_telnet_name_settings_dialog);
		TelnetConnectionNumber = (TextView) mSettingsDialogInterface
				.findViewById(R.id.tv_telnet_connection_number_settings_dialog);
		TelnetAdminName = (TextView) mSettingsDialogInterface
				.findViewById(R.id.tv_admin_register_name_settings_dialog);
		TelnetAdminPhoneNumber = (TextView) mSettingsDialogInterface
				.findViewById(R.id.tv_admin_register_telnumber_settings_dialog);
	}

	/**
	 * 用于显示错误信息
	 * 
	 * @param ShowString
	 */
	public void ShowToast(String ShowString) {
		Toast.makeText(mWorkContext.mContext, ShowString, Toast.LENGTH_SHORT)
				.show();
	}
}

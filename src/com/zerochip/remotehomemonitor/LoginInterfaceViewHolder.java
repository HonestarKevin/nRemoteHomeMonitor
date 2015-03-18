package com.zerochip.remotehomemonitor;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.zerochip.util.WorkContext;

public class LoginInterfaceViewHolder {
	public static final boolean DEBUG = RootActivity.DEBUG;
	private static final String TAG = "com.zerochip.remotehomemonitor.LoginInterfaceViewHolder";

	private WorkContext mWorkContext = null;

	// 用户名输入框
	private EditText UsernameEditText = null;
	// 密码输入框
	private EditText PaddwdEditText = null;
	// 选择用户类型
//	private Spinner UserModeSelectSpinner = null;
	// 记住密码
	private CheckBox RememberPasswdCheckBox = null;
	// 登录Button
	private Button LoginButton = null;
	// 取消Button
	private Button CancelButton = null;
	
	//记往密码是否勾选
	private static boolean RememberPasswdCheckFlag = false;

	public LoginInterfaceViewHolder(WorkContext mWorkContext) {
		super();
		this.mWorkContext = mWorkContext;
		FindAllView();
		SetViewListener();
		SetInitViewData();
	}

	private void SetInitViewData() {
		// TODO Auto-generated method stub
		UsernameEditText.setText(mWorkContext.mSharedPreferencesDataHelper.GetRememberUserName());
		if(mWorkContext.mSharedPreferencesDataHelper.GetRememberPasswdStatus()){
			RememberPasswdCheckBox.setChecked(true);
			RememberPasswdCheckFlag = true;
			PaddwdEditText.setText(mWorkContext.mSharedPreferencesDataHelper.GetRememberPasswd());
		}
	}

	public void FindAllView() {
		// 用户名输入框
		UsernameEditText = (EditText) mWorkContext.mActivity
				.findViewById(R.id.e_login_username);
		// 密码输入框
		PaddwdEditText = (EditText) mWorkContext.mActivity
				.findViewById(R.id.e_login_passwd);
		// 选择用户类型
//		UserModeSelectSpinner = (Spinner) mWorkContext.mActivity
//				.findViewById(R.id.sp_login_spinner);
		// 记住密码
		RememberPasswdCheckBox = (CheckBox) mWorkContext.mActivity
				.findViewById(R.id.ck_login_remember);
		// 登录Button
		LoginButton = (Button) mWorkContext.mActivity
				.findViewById(R.id.b_login_login);
		// 取消Button
		CancelButton = (Button) mWorkContext.mActivity
				.findViewById(R.id.b_login_cancel);
	}

	public void SetViewListener() {
		LoginButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//check username and passwd after login
				//if(DEBUG) Log.e(TAG,"SetViewListener Username  = "+GetUserInputUsername() +" Passwd = " +GetUserInputPasswd());
				if(null == GetUserInputUsername()){
					UsernameEditText.requestFocus();
					return;
				}
				if(null == GetUserInputPasswd()){
					PaddwdEditText.requestFocus();
					return;
				}
				mWorkContext.mSharedPreferencesDataHelper.SetRememberUserName(GetUserInputUsername());
				boolean CheckUserInputVerify = mWorkContext.mSharedPreferencesDataHelper.CheckLocalUserPasswd(GetUserInputUsername(), GetUserInputPasswd());
				if(CheckUserInputVerify){
					mWorkContext.mSharedPreferencesDataHelper.SetRememberPasswdStatus(RememberPasswdCheckFlag);
					if(mWorkContext.mSharedPreferencesDataHelper.GetRememberPasswdStatus()){
						mWorkContext.mSharedPreferencesDataHelper.SetRememberPasswd(GetUserInputPasswd());
					}else{
						if(mWorkContext.mSharedPreferencesDataHelper.GetRememberPasswd() != null){
							mWorkContext.mSharedPreferencesDataHelper.RemoveRememberPasswd();
						}
					}
					//Goto 主界面
					RootActivity mRootActivity = (RootActivity)mWorkContext.mActivity;
					mRootActivity.GotoMainInterface();
				}else{				
					//提示用户密码错误或用户名错误
					ShowToast(mWorkContext.mResources
							.getString(R.string.str_username_or_passwd_error));
					UsernameEditText.requestFocus();
				}
			}
		});
		CancelButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//exit
				if(DEBUG)  Log.e(TAG,"CancelButton setOnClickListener");
				System.exit(0);
			}
		});
		RememberPasswdCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				//update SharedPreferences
				RememberPasswdCheckFlag = isChecked;
			}
		});
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

	/**
	 * 获取用户输入的用户名称
	 * 
	 * @return
	 */
	public String GetUserInputUsername() {
		if (UsernameEditText == null) {
			return null;
		} else if (UsernameEditText.getText().toString().equals("")) {
			ShowToast(mWorkContext.mResources
					.getString(R.string.str_please_input_username));
			return null;
		} else {
			return UsernameEditText.getText().toString();
		}
	}

	/**
	 * 获取用户输入的密码
	 * 
	 * @return
	 */
	public String GetUserInputPasswd() {
		if (PaddwdEditText == null) {
			return null;
		} else if (PaddwdEditText.getText().toString().equals("")) {
			ShowToast(mWorkContext.mResources
					.getString(R.string.str_please_input_passwd));
			return null;
		} else {
			return PaddwdEditText.getText().toString();
		}
	}

	/**
	 * 获取用户选择的用户模式
	 * 
	 * @return
	 */
//	public long GetUserMode() {
//		if (UserModeSelectSpinner == null) {
//			return mWorkContext.UNKNOW_MODE;
//		} else {
//			if (DEBUG)
//				Log.e(TAG, "GetUserMode getSelectedItemId ="
//						+ UserModeSelectSpinner.getSelectedItemId());
//			return UserModeSelectSpinner.getSelectedItemId();
//		}
//	}

	public boolean GetUserRememberPasswd() {
		return false;
	}
}

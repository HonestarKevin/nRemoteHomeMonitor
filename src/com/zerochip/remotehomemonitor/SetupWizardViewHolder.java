package com.zerochip.remotehomemonitor;

import android.util.Log;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.zerochip.util.WorkContext;

public class SetupWizardViewHolder {

	
	public static final boolean DEBUG = RootActivity.DEBUG;
	private static final String TAG = "com.zerochip.remotehomemonitor.SetupWizardViewHolder";
	
	private WorkContext mWorkContext = null;
	
	//设置远程终端页面 pag1.
	//终端名称
	private EditText	TelnetNameEditText = null;
	//终端号码
	private EditText	TelnetNumberEditText = null;
	
	//设置用户信息页面 pag2.
	//用户名输入框
	private EditText	UsernameEditText  = null;
	//密码输入框
	private EditText	PaddwdEditText  = null;
	//密码确认输入框
	private EditText	PasswdVerifyEditText  = null;
	//选择用户类型
	private Spinner		UserModeSelectSpinner = null;
	

	//设置设备页面	pag3.
	//设备名称
	private EditText	DevicesNameEditText  = null;
	//设备ID
	private EditText	DevicesIDEditText = null;
	
	public SetupWizardViewHolder(WorkContext mWorkContext) {
		super();
		this.mWorkContext = mWorkContext;
		FindAllView();
	}
	/**
	 * @Function: 找出所有的View;
	 */
	private void FindAllView() {
		// TODO Auto-generated method stub
		//pag1.
		TelnetNameEditText = (EditText) mWorkContext.mActivity.findViewById(R.id.e_setup_telnet_name);
		TelnetNumberEditText = (EditText) mWorkContext.mActivity.findViewById(R.id.e_setup_telnet_connectipn_number);
		
		//pag2.
		UsernameEditText = (EditText) mWorkContext.mActivity.findViewById(R.id.e_setup_user_username);
		PaddwdEditText = (EditText) mWorkContext.mActivity.findViewById(R.id.e_setup_user_passwd);
		PasswdVerifyEditText = (EditText) mWorkContext.mActivity.findViewById(R.id.e_setup_user_passwd_verify);
		UserModeSelectSpinner = (Spinner) mWorkContext.mActivity.findViewById(R.id.sp_setup_spinner);
		
		//pag3.
		DevicesNameEditText = (EditText) mWorkContext.mActivity.findViewById(R.id.e_setup_device_name);
		DevicesIDEditText = (EditText) mWorkContext.mActivity.findViewById(R.id.e_setup_device_number);
	}
	
	/**
	 * 用于显示错误信息
	 * @param ShowString
	 */
	public void ShowToast(String ShowString)
    {
        Toast.makeText(mWorkContext.mContext, ShowString, Toast.LENGTH_SHORT)
                .show();
    }
    
    //pag1.
    public String GetTelnetName()
    {
        if (TelnetNameEditText == null)
        {
            return null;
        }
        else
            if (TelnetNameEditText.getText().toString().equals(""))
            {
                ShowToast(mWorkContext.mResources
                        .getString(R.string.str_please_input_telnet_name));
                return null;
            }
            else
            {
                return TelnetNameEditText.getText().toString();
            }
    }
    //pag1.
    public String GetTelnetNumber()
    {
        if (TelnetNumberEditText == null)
        {
            return null;
        }
        else
            if (TelnetNumberEditText.getText().toString().equals(""))
            {
                ShowToast(mWorkContext.mResources
                        .getString(R.string.str_please_input_telnet_connection_number));
                return null;
            }
            else
            {
                return TelnetNumberEditText.getText().toString();
            }
    }
    
    //pag2.
    public String GetUsername()
    {
        if (UsernameEditText == null)
        {
            return null;
        }
        else
            if (UsernameEditText.getText().toString().equals(""))
            {
                ShowToast(mWorkContext.mResources
                        .getString(R.string.str_please_input_sensor_device_name));
                return null;
            }
            else
            {
                return UsernameEditText.getText().toString();
            }
    }
    //pag2.
    public String GetUserPasswd()
    {
        if (PaddwdEditText == null
                || PasswdVerifyEditText == null)
        {
            return null;
        }
        else
            if (PaddwdEditText.getText().toString().equals(""))
            {
                ShowToast(mWorkContext.mResources
                        .getString(R.string.str_please_input_passwd));
                return null;
            }
            else
                if (PasswdVerifyEditText.getText().toString()
                        .equals(""))
                {
                    ShowToast(mWorkContext.mResources
                            .getString(R.string.str_please_input_passwd_verify));
                    return null;
                }
                else
                    if (!PasswdVerifyEditText
                            .getText()
                            .toString()
                            .equals(PaddwdEditText.getText().toString()))
                    {
                        ShowToast(mWorkContext.mResources
                                .getString(R.string.str_input_passwd_error_warning));
                        return null;
                    }
                    else
                    {
                        return PaddwdEditText.getText().toString();
                    }
    }
    
    public long GetUserMode()
    {
        if (UserModeSelectSpinner == null)
        {
            return -1;
        }else
        {
        	if(DEBUG) Log.e(TAG, "GetUserMode getSelectedItemId ="+UserModeSelectSpinner.getSelectedItemId());
        	return UserModeSelectSpinner.getSelectedItemId();
        }
    }
    //pag 3.
    public String GetDevicesName()
    {
        
        if (DevicesNameEditText == null)
        {
            return null;
        }
        else
            if (DevicesNameEditText.getText().toString().equals(""))
            {
                ShowToast(mWorkContext.mResources
                        .getString(R.string.str_please_input_sensor_device_name));
                return null;
            }
            else
            {
                return DevicesNameEditText.getText().toString();
            }
    }
    public String GetDevicesId()
    {
        if (DevicesIDEditText == null)
        {
            return null;
        }
        else
            if (DevicesIDEditText.getText().toString().equals(""))
            {
                ShowToast(mWorkContext.mResources
                        .getString(R.string.str_please_input_sensor_device_number_id));
                return null;
            }
            else
            {
                return DevicesIDEditText.getText().toString();
            }
    }
}

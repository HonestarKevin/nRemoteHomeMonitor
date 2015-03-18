package com.zerochip.remotehomemonitor;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import android.content.SharedPreferences;
import android.util.Log;

import com.zerochip.util.WorkContext;

public class SharedPreferencesDataHelper {
	private WorkContext mWorkContext = null;
	private SharedPreferences.Editor mSharedPreferencesEditor = null;
	private SharedPreferences mSharedPreferences = null;
	private final static String TAG = "com.zerochip.util.SharedPreferencesDataHelper";
	private final boolean DEBUG = RootActivity.DEBUG;
	/**
	 * Devices Info
	 */
	private HashSet<String> DevicesList = null;
	private HashMap<String, String> DevicesInfo = null;

	/**
	 * Telnet Info
	 */
	private String TelnetnameString = null;
	private String TelnetConnectionNumberString = null;

	/**
	 * Local User info
	 */
	private String LocalUserNameString = null;
	private String LocalUserPasswdString = null;
	private String LocalUserModeString = null;
	
	/**
	 * Remote Admin Info
	 */
	private String RemoteAdminUsernameString = null;
	private String RemoteAdminPhoneNumberString = null;

	
	/**
	 * 
	 * @param mWorkContext
	 */
	public SharedPreferencesDataHelper(WorkContext mWorkContext) {
		super();
		this.mWorkContext = mWorkContext;
		this.mSharedPreferences = mWorkContext.mPreferences;
		if (mSharedPreferences == null) {
			Log.e(TAG, "SharedPreferencesDataHelper  mPreferences = "
					+ mSharedPreferences);
		}
		this.mSharedPreferencesEditor = mWorkContext.mPreferences.edit();
		if (mSharedPreferencesEditor == null) {
			Log.e(TAG,
					"SharedPreferencesDataHelper  mSharedPreferencesEditor = "
							+ mSharedPreferencesEditor);
		}
		InitLoadData();
		if (DEBUG)
		 DumpData();
		 //TestSharePreference();
	}
    private void TestSharePreference()
    {
         Log.e(TAG, "********************Test Admin End*******************************");
         Log.e(TAG, "\n------------------old Start------------------------------\n");
         DumpData();
         Log.e(TAG, "\n------------------old End--------------------------------\n");
         Log.e(TAG, "********************Test telnet Start*******************************");
         SetTelnetname("10086");
         SetTelnetConnectionNumber("13570805860");
         Log.e(TAG, "GetTelnetConnectionNumber"+GetTelnetConnectionNumber("10086"));
         Log.e(TAG, "GetTelnetname"+GetTelnetname());
         SetTelnetInfo("10010", "18688974002");
         Log.e(TAG, "GetTelnetInfo"+GetTelnetInfo());
         Log.e(TAG, "********************Test telnet End*******************************");
         
         Log.e(TAG, "********************Test devicesInfo Start*******************************");
         HashMap<String, String> devicesInfo = new HashMap<String, String>();
         devicesInfo.put("1", "a");
         devicesInfo.put("2", "b");
         devicesInfo.put("3", "c");
         devicesInfo.put("4", "d");
         Log.e(TAG, "devicesInfo.keyset = "+ devicesInfo.keySet());
         SetDevicesInfo(devicesInfo);
         Log.e(TAG, "GetDevicesInfo=" +this.GetDevicesInfo());
         Log.e(TAG, "ID = 1  GetDeviceName=" +this.GetDeviceName("1"));
         Log.e(TAG, "GetDeviceGroup=" +this.GetDeviceGroup());
         SetDeviceName("1", "aa");
         Log.e(TAG, "ID = 1  GetDeviceName=" +this.GetDeviceName("1"));
         SetDeviceGroup(DevicesList);
         Log.e(TAG, "********************Test devicesInfo End*******************************");
         
         Log.e(TAG, "********************Test UserInfo Start*******************************");
         SetLocalUserInfo("kevin", "123456");
         Log.e(TAG, "GetLocalUserInfo=" +this.GetLocalUserInfo());
         Log.e(TAG, "GetLocalUserName=" +this.GetLocalUserName());
         Log.e(TAG, "GetLocalUserPasswd=" +this.GetLocalUserPasswd("kevin"));
         Log.e(TAG, "GetUserMode=" +this.GetUserMode());
         SetLocalUserName("xu");
         SetLocalUserPasswd("peng");
         Log.e(TAG, "GetLocalUserInfo=" +this.GetLocalUserInfo());
         SetUserMode(1);
         Log.e(TAG, "GetUserMode=" +this.GetUserMode());
         Log.e(TAG, "********************Test UserInfo End*******************************");
         
         Log.e(TAG, "\n------------------new Start------------------------------\n");
         DumpData();
         Log.e(TAG, "\n------------------new End--------------------------------\n");
         

    }
    
	private void InitLoadData() {
		LoadTelnetInfo();
		LoadLocalUserInfo();
		LoadDevicesInfo();
	}
	
	/**
	 * @Funciton: 确认是否为第一次打开些APK,如果是，就进行第一次的APK设置；
	 * @return
	 */
	public boolean CheckNeedSetup() {
		if (mWorkContext.mPreferences != null) {
			if (DEBUG)
				Log.e(TAG,
						"mWorkContext.mPreferences != null + "
								+ mWorkContext.mPreferences
										.getBoolean(
												mWorkContext.configNeedRunSetupWizardString,
												true));
			return mWorkContext.mPreferences.getBoolean(
					mWorkContext.configNeedRunSetupWizardString, true);
		} else {
			if (DEBUG)
				Log.e(TAG, "mWorkContext.mPreferences null error");
			return true;
		}
	}
	public void SetupWizardOK()
	{
		mSharedPreferencesEditor.putBoolean(
		mWorkContext.configNeedRunSetupWizardString, false);
		mSharedPreferencesEditor.commit();
	}
	
    private void DumpData()
    {
        Log.e(TAG, "\n------------------dump Start------------------------------\n");
        Log.e(TAG, "TelnetnameString = " + TelnetnameString);
        Log.e(TAG, "TelnetConnectionNumberString = "
                + TelnetConnectionNumberString);
        
        Log.e(TAG, "\n DevicesList = " + DevicesList);
        Log.e(TAG, "DevicesInfo = " + DevicesInfo);
        
        Log.e(TAG, "\n LocalUserNameString = " + LocalUserNameString);
        Log.e(TAG, "LocalUserPasswdString = " + LocalUserPasswdString);
        Log.e(TAG, "LocalUserModeString = " + LocalUserModeString);
        Log.e(TAG, "\n------------------dump End------------------------------\n");
    }
	/** -----------------------SharedPreference operator Start --------------- **/
	public String SharedPreferencesGetString(String keyString) {
		return mSharedPreferences.getString(keyString, null);
	}

	public void SharedPreferencesSetString(String keyString, String value) {
		mSharedPreferencesEditor.putString(keyString, value);
		mSharedPreferencesEditor.commit();
	}

	public void SharedPreferencesSetStringSet(String keyString,
			HashSet<String> value) {
		mSharedPreferencesEditor.putStringSet(keyString, value);
		mSharedPreferencesEditor.commit();
	}

	public HashSet<String> SharedPreferencesGetStringSet(String keyString) {
		return (HashSet<String>) mSharedPreferences.getStringSet(keyString,
				null);
	}

	public void SharedPreferencesRemoveMember(String keyString) {
		mSharedPreferencesEditor.remove(keyString);
		mSharedPreferencesEditor.commit();
	}
	public void SharedPreferencesSetBoolean(String keyString, boolean value)
	{
		mSharedPreferencesEditor.putBoolean(keyString, value);
		mSharedPreferencesEditor.commit();
	}
	public boolean SharedPreferencesGetBoolean(String keyString)
	{
		return mSharedPreferences.getBoolean(keyString,
				false);
	}
	/** -----------------------SharedPreference operator End --------------- **/

	/** 终端信息Start-----> */
	private void LoadTelnetInfo() {
		TelnetnameString = SharedPreferencesGetString(mWorkContext.configTelnetnameString);
		TelnetConnectionNumberString = SharedPreferencesGetString(mWorkContext.configTelnetConnectionNumberString);
	}

	/**
	 * @Function 设置终端通信号码
	 * @param TelnetConnectionNumber
	 */
	public void SetTelnetConnectionNumber(String TelnetConnectionNumber) {
		if (TelnetnameString == null) {
			Log.e(TAG, "SetTelnetConnectionNumber TelnetnameString = "
					+ TelnetnameString);
		} else if (TelnetConnectionNumber == null) {
			Log.e(TAG, "SetTelnetConnectionNumber TelnetConnectionNumber = "
					+ TelnetConnectionNumber);
		} else {
			TelnetConnectionNumberString = TelnetConnectionNumber;
			SharedPreferencesSetString(
					mWorkContext.configTelnetConnectionNumberString,
					TelnetConnectionNumber);
		}
	}

	/**
	 * @Function 获取终端通信号码
	 * @param Telnetname
	 * @return
	 */
	public String GetTelnetConnectionNumber(String Telnetname) {
		if (TelnetnameString == null || TelnetConnectionNumberString == null) {
			Log.e(TAG, "GetTelnetConnectionNumber TelnetnameString = "
					+ TelnetnameString + "TelnetConnectionNumberString = "
					+ TelnetConnectionNumberString);
			return null;
		} else if (Telnetname == null) {
			Log.e(TAG, "GetTelnetConnectionNumber Telnetname = " + Telnetname);
			return null;
		} else if (Telnetname.equals(TelnetnameString)) {
			return TelnetConnectionNumberString;
		} else {
			return null;
		}
	}

	/**
	 * @Function 设置终端通信名称
	 * @param Telnetname
	 */
	public void SetTelnetname(String Telnetname) {
		if (Telnetname == null) {
			Log.e(TAG, "SetTelnetname  Telnetname = " + Telnetname);
		} else {
			TelnetnameString = Telnetname;
			SharedPreferencesSetString(mWorkContext.configTelnetnameString,
					Telnetname);
		}
	}

	/**
	 * @Function 获取终端通信名称
	 * @return
	 */
	public String GetTelnetname() {
		return TelnetnameString;
	}

	/**
	 * @Function 获取终端通信信息
	 * @return
	 */
	public HashMap<String, String> GetTelnetInfo() {
		HashMap<String, String> telnetInfoHashMap = new HashMap<String, String>();
		telnetInfoHashMap.put(TelnetnameString, TelnetConnectionNumberString);
		return telnetInfoHashMap;
	}

	/**
	 * @Function 设置终端通信信息
	 * @param Telnetname
	 * @param TelnetConnectionNumber
	 */
	public boolean SetTelnetInfo(String Telnetname, String TelnetConnectionNumber) {
		if (Telnetname == null || TelnetConnectionNumber == null) {
			Log.e(TAG, "SetTelnetInfo + Telnetname = " + Telnetname
					+ " TelnetConnectionNumber = " + TelnetConnectionNumber);
			return false;
		} else {
			TelnetConnectionNumberString = TelnetConnectionNumber;
			SharedPreferencesSetString(
					mWorkContext.configTelnetConnectionNumberString,
					TelnetConnectionNumber);
			TelnetnameString = Telnetname;
			SharedPreferencesSetString(mWorkContext.configTelnetnameString,
					Telnetname);
			return true;
		}
	}

	/** <----- 终端信息End */

	/** 设备信息Start-----> */
	private void LoadDevicesInfo() {
		DevicesList = SharedPreferencesGetStringSet(mWorkContext.configDevicesIdListString);
		if(DevicesList == null){
			Log.e(TAG, "DevicesList devices info not exist!");
			return;
		}
		DevicesInfo = new HashMap<String, String>();
		for (String devicesId : DevicesList) {
			String devicesName = SharedPreferencesGetString(devicesId);
			DevicesInfo.put(devicesId, devicesName);
		}
	}

	/**
	 * @Function 获取设备的用户
	 * @return 设备组
	 */
	public HashSet<String> GetDeviceGroup() {
		if (DevicesList == null) {
			Log.e(TAG, "GetDeviceGroup + DevicesList = " + DevicesList);
			return null;
		} else {
			return DevicesList;
		}
	}

	/**
	 * @Function 设置所有的设备组
	 * @param DeviceGroup
	 */
	public void SetDeviceGroup(HashSet<String> DeviceGroup) {
		if (DeviceGroup == null) {
			Log.e(TAG, "SetDeviceGroup + DeviceGroup = " + DeviceGroup);
		} else {
			DevicesList = DeviceGroup;
			SharedPreferencesSetStringSet(
					mWorkContext.configDevicesIdListString, DeviceGroup);
		}
	}

	/**
	 * @Function 获取所有设备的信息
	 * @return
	 */
	public HashMap<String, String> GetDevicesInfo() {
		if (DevicesInfo == null) {
			Log.e(TAG, "GetDevicesInfo DevicesInfo =  " + DevicesInfo);
			return null;
		} else {
			return DevicesInfo;
		}
	}

	/**
	 * @Function 设置所有设备的信息
	 */
	public void SetDevicesInfo(HashMap<String, String> devicesInfo) {
		if (devicesInfo == null) {
			Log.e(TAG, "SetDevicesInfo devicesInfo:\n" + devicesInfo);
			return;
		} else {
			Set<String> setDevicesList = devicesInfo.keySet();
			DevicesList = new HashSet<String>(setDevicesList);
//			for(String mSetDevicesID : setDevicesList){
//				DevicesList.add(mSetDevicesID);
//			}
			for (String id : DevicesList) {
				String name = devicesInfo.get(id);
				if (name == null) {
					Log.e(TAG, "SetDevicesInfo name = " + name);
					DevicesList.remove(id);
					devicesInfo.remove(id);
				} else {
					SharedPreferencesSetString(id, name);
				}
			}
			this.DevicesInfo = devicesInfo;
			SharedPreferencesSetStringSet(
					mWorkContext.configDevicesIdListString, DevicesList);
		}
	}

	/**
	 * @Function 获取设备名称
	 * @param Username
	 * @return 设备名称
	 */
	public String GetDeviceName(String DeviceId) {
		if (DeviceId == null) {
			Log.e(TAG, "GetDeviceName DeviceId = " + DeviceId);
		} else if (DevicesInfo == null) {
			Log.e(TAG, "GetDeviceName DevicesInfo = " + DevicesInfo);
		} else
			return DevicesInfo.get(DeviceId);
		return null;
	}

	/**
	 * @Function 设置设备名称
	 * @param DeviceId
	 * @param DeviceName
	 */
	public void SetDeviceName(String DeviceId, String DeviceName) {
		if (DeviceName == null || DeviceId == null) {
			Log.e(TAG, "SetDeviceName  DeviceId = " + DeviceId
					+ "DeviceName = " + DeviceName);
		} else if (DevicesList.contains(DeviceId)) {
			DevicesInfo.put(DeviceId, DeviceName);
			SharedPreferencesSetString(DeviceId, DeviceName);
		}
	}

	/**
	 * @Function 增加设备
	 * @param DeviceName
	 * @param DeviceId
	 * @return true 增加设备成功 false 增加设备失败
	 */
	public boolean AddDevice(String DeviceName, String DeviceId) {
		if (DeviceName == null || DeviceId == null) {
			Log.e(TAG, "AddDevice  DeviceName = " + DeviceName + "DeviceId = "
					+ DeviceId);
			return false;
		} else if (DevicesList != null && DevicesList.contains(DeviceId)) {
			Log.e(TAG, " AddDevice DeviceId " + DeviceId + "have exist");
			return false;
		} else {
			if(DevicesInfo == null)
				DevicesInfo = new HashMap<String, String>();
			if(DevicesList == null)
				DevicesList = new HashSet<String>();
			DevicesInfo.put(DeviceId, DeviceName);
			SharedPreferencesSetString(DeviceId, DeviceName);
			DevicesList.add(DeviceId);
			SharedPreferencesSetStringSet(
					mWorkContext.configDevicesIdListString, DevicesList);
			return true;
		}
	}

	/**
	 * @Function 删除设备
	 * 
	 * @param DeviceId
	 * @return false 删除设备失败 treu 删除设备OK
	 */
	public boolean DelDevice(String DeviceId) {
		if (DeviceId == null) {
			Log.e(TAG, "DelDevice DeviceId =" + DeviceId);
			return false;
		} else if (!DevicesList.contains(DeviceId)) {
			Log.e(TAG, " DelDevice DeviceId = " + DeviceId
					+ "dont't exist DevicesList :\n " + DevicesList);
			return false;
		} else {
			DevicesInfo.remove(DeviceId);
			DevicesList.remove(DeviceId);
			SharedPreferencesRemoveMember(DeviceId);
			SharedPreferencesSetStringSet(
					mWorkContext.configDevicesIdListString, DevicesList);
			return true;
		}
	}

	/** <----- 设备信息End */

	/** 本地用户信息Start-----> */
	/**
	 * LoadLocalUserInfo
	 */
	private void LoadLocalUserInfo() {
		LocalUserNameString = SharedPreferencesGetString(mWorkContext.configLocalUsernameString);
		LocalUserPasswdString = SharedPreferencesGetString(mWorkContext.configLocalPasswdString);
		LocalUserModeString = SharedPreferencesGetString(mWorkContext.configLocalUserModeString);
	}

	
	/**
	 * @Function 获取本地用户信息用户名
	 * @return 获取本地用户信息用户名
	 */
	public long GetUserMode() {
		if(LocalUserModeString == null){
			Log.e(TAG, "GetUserMode LocalUserModeString = "+LocalUserModeString); 
			return -1;
		}else{
			return Integer.valueOf(LocalUserModeString).longValue();
	
		}
	}
	
	/**
	 * @Function 获取本地用户信息用户名
	 */
	public boolean SetUserMode(long UserMode) {
		if(UserMode != mWorkContext.ADMIN_MODE && UserMode != mWorkContext.COMMON_USER_MODE)
		{
			Log.e(TAG, "SetUserMode UserMode = "+ UserMode);
			return false;
		}else
		{
			LocalUserModeString = String.valueOf(UserMode);
			SharedPreferencesSetString(mWorkContext.configLocalUserModeString, String.valueOf(UserMode));
			return true;
		}
	}
	/**
	 * @Function 确认本地用户信息密码
	 * @param Username
	 * @param UserPasswd
	 * @return false 认证失败 ture 认证OK
	 */
	public boolean CheckLocalUserPasswd(String Username, String UserPasswd) {
		if (DEBUG)
			Log.e(TAG, "LocalUserNameString LocalUserNameString = " + LocalUserNameString
					+ "LocalUserPasswdString = " + LocalUserPasswdString);
		if (Username == null || UserPasswd == null) {
			Log.e(TAG, "CheckLocalUserPasswd Username = " + Username
					+ "UserPasswd = " + UserPasswd);
			return false;
		} else if (LocalUserNameString == null || LocalUserPasswdString == null) {
			Log.e(TAG, "CheckLocalUserPasswd LocalUserNameString = "
					+ LocalUserNameString + "LocalUserPasswdString = "
					+ LocalUserPasswdString);
			return false;
		} else {
			if (LocalUserNameString.equals(Username)
					&& LocalUserPasswdString.equals(UserPasswd)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @Function 设置本地用户信息密码
	 * @param UserPasswd
	 */
	public void SetLocalUserPasswd(String UserPasswd) {
		if (LocalUserNameString == null) {
			Log.e(TAG, "SetLocalUserPasswd LocalUserNameString = "
					+ LocalUserNameString);
		} else if (UserPasswd == null) {
			Log.e(TAG, "SetLocalUserPasswd UserPasswd = " + UserPasswd);
		} else {
			LocalUserPasswdString = UserPasswd;
			SharedPreferencesSetString(mWorkContext.configLocalPasswdString,
					UserPasswd);
		}
	}

	/**
	 * @Function 获取本地用户信息密码
	 * @param Username
	 * @return 本地用户信息密码
	 */
	public String GetLocalUserPasswd(String Username) {
		if (LocalUserNameString == null || LocalUserPasswdString == null) {
			Log.e(TAG, "GetLocalUserPasswd LocalUserNameString = "
					+ LocalUserNameString + "LocalUserPasswdString = "
					+ LocalUserPasswdString);
			return null;
		} else if (Username == null) {
			Log.e(TAG, "GetLocalUserPasswd Username = " + Username);
			return null;
		} else if (Username.equals(LocalUserNameString)) {
			return LocalUserPasswdString;
		} else {
			return null;
		}
	}

	/**
	 * @Function 设置本地用户信息用户名
	 * @param Username
	 */
	public void SetLocalUserName(String Username) {
		if (Username == null) {
			Log.e(TAG, "SetLocalUserName  Username = " + Username);
		} else {
			LocalUserNameString = Username;
			SharedPreferencesSetString(mWorkContext.configLocalUsernameString,
					Username);
		}
	}

	/**
	 * @Function 获取本地用户信息用户名
	 * @return 获取本地用户信息用户名
	 */
	public String GetLocalUserName() {
		return LocalUserNameString;
	}
	
	/**
	 * @Function 获取本地用户信息
	 * @return 本地用户信息
	 */
	public HashMap<String, String> GetLocalUserInfo() {
		HashMap<String, String> LocalUserInfoHashMap = new HashMap<String, String>();
		LocalUserInfoHashMap.put(LocalUserNameString, LocalUserPasswdString);
		return LocalUserInfoHashMap;
	}

	/**
	 * @Function 设置本地用户信息
	 * @param Username
	 * @param UserPasswd
	 * @return
	 */
	public boolean SetLocalUserInfo(String Username, String UserPasswd) {
		if (Username == null || UserPasswd == null) {
			Log.e(TAG, "SetLocalUserInfo + Username = " + Username
					+ " UserPasswd = " + UserPasswd);
			return false;
		} else {
			SharedPreferencesSetString(mWorkContext.configLocalUsernameString,
					Username);		
			SharedPreferencesSetString(mWorkContext.configLocalPasswdString,
					UserPasswd);
			LocalUserPasswdString = UserPasswd;
			LocalUserNameString = Username;
			return true;
		}
	}

	/** <----- 本地用户信息End */

	/**远程管理信息员信息Start-----> */
	/**
	 * @Function 设置远程管理信息员用户名
	 * @param AdminUsername
	 */
	public void SetRemoteAdminPhoneNumber(String AdminPhoneNumber) {
		if (AdminPhoneNumber == null) {
			Log.e(TAG, "SetRemoteAdminPhoneNumber  AdminPhoneNumber = " + AdminPhoneNumber);
		} else {
			RemoteAdminPhoneNumberString = AdminPhoneNumber;
			SharedPreferencesSetString(mWorkContext.configRemoteAdminPhoneNumberString,
					AdminPhoneNumber);
		}
	}

	/**
	 * @Function 获取远程管理信息员用户名
	 * @return 获取远程管理信息员用户名
	 */
	public String GetRemoteAdminPhoneNumber() {
		return RemoteAdminPhoneNumberString;
	}
	/**
	 * @Function 设置远程管理信息员用户名
	 * @param AdminUsername
	 */
	public void SetRemoteAdminName(String AdminUsername) {
		if (AdminUsername == null) {
			Log.e(TAG, "SetRemoteAdminName  AdminUsername = " + AdminUsername);
		} else {
			RemoteAdminUsernameString = AdminUsername;
			SharedPreferencesSetString(mWorkContext.configRemoteAdminUsernameString,
					AdminUsername);
		}
	}

	/**
	 * @Function 获取远程管理信息员用户名
	 * @return 获取远程管理信息员用户名
	 */
	public String GetRemoteAdminName() {
		return RemoteAdminUsernameString;
	}
	/**
	 * @Function 获取本地用户信息
	 * @return 本地用户信息
	 */
	public HashMap<String, String> GetRemoteAdminInfo() {
		HashMap<String, String> RemoteAdminInfoHashMap = new HashMap<String, String>();
		RemoteAdminInfoHashMap.put(RemoteAdminUsernameString, RemoteAdminUsernameString);
		return RemoteAdminInfoHashMap;
	}
	/**
	 * @Function 设置远程管理信息员信息
	 * @param Username
	 * @param UserPasswd
	 * @return
	 */
	public boolean SetRemoteAdminInfo(String AdminUsername, String AdminPhoneNumber) {
		if (AdminUsername == null || AdminPhoneNumber == null) {
			Log.e(TAG, "SetRemoteAdminInfo + AdminUsername = " + AdminUsername
					+ " AdminPhoneNumber = " + AdminPhoneNumber);
			return false;
		} else {
			SharedPreferencesSetString(mWorkContext.configRemoteAdminUsernameString,
					AdminUsername);
			SharedPreferencesSetString(mWorkContext.configRemoteAdminPhoneNumberString,
					AdminPhoneNumber);
			RemoteAdminUsernameString = AdminUsername;
			RemoteAdminPhoneNumberString = AdminPhoneNumber;
			return true;
		}
	}
	/** <----- 远程管理员信息End */
	
	/**登录页面Start-----> */
	/**
	 * @Function: 记录用户输入的密码
	 * @param UserInputPasswd
	 */
	public void SetRememberPasswd(String UserInputPasswd) 
	{
		SharedPreferencesSetString(mWorkContext.LoginUserInputUserPasswd, UserInputPasswd);
	}
	/**
	 * @Function: 获取记录的用户密码；
	 * @return 用户记录的密码
	 */
	public String GetRememberPasswd()
	{
		if(GetRememberPasswdStatus())
		{
			return SharedPreferencesGetString(mWorkContext.LoginUserInputUserPasswd);
		}else{
			return null;
		}
	}
	public void RemoveRememberPasswd()
	{
		SharedPreferencesRemoveMember(mWorkContext.LoginUserInputUserPasswd);
	}
	/**
	 * @Funciton: 获取当前是否为记录用户名和密码的状态
	 * @return 记录标志 true：记录，false:不记录	
	 */
	public boolean GetRememberPasswdStatus()
	{
		return SharedPreferencesGetBoolean(mWorkContext.LoginUserRememberPasswd);
	}
	/**
	 * @Funciton: 设置当前是否为记录用户名和密码的状态
	 */
	public void SetRememberPasswdStatus(boolean LoginUserRememberPasswd)
	{
		SharedPreferencesSetBoolean(mWorkContext.LoginUserRememberPasswd, LoginUserRememberPasswd);
	}

	/**
	 * @Function: 记录的用户名；
	 * @param UserInputUserName
	 */
	public void SetRememberUserName(String UserInputUserName)
	{
		SharedPreferencesSetString(mWorkContext.LoginUserInputUserName, UserInputUserName);
	}
	
	/**
	 * @Function: 获取记录的用户名；
	 * @return 记录的用户名
	 */
	public String GetRememberUserName()
	{
		return SharedPreferencesGetString(mWorkContext.LoginUserInputUserName);
	}
	/** <----- 登录页面End */
}

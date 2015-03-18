package com.zerochip.util;

import com.zerochip.remotehomemonitor.SharedPreferencesDataHelper;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Handler;
import android.text.StaticLayout;

/***
 * @category 常用变量
 * @author Kevin.Xu
 *
 */
public class WorkContext
{   
    public Context  mContext = null;
    public Activity mActivity = null;
    public Handler mHandler = null;
    public Resources mResources = null;
    public GetNetWorkState mGetNetWorkState = null;
    public SimpleTextToSpeech mSimpleTextToSpeech = null;
    public SharedPreferences mPreferences = null;
    public AnimationFactory mAnimationFactory = null;
    public SharedPreferencesDataHelper mSharedPreferencesDataHelper = null;
    //setup wizard 
    public final static String configNeedRunSetupWizardString = "NeedRunSetupWizard";
    public final static String configFileNameString = "RemoteHomeMonitor";
    public final static String configAdminUsernameString = "AdminUsername";
    public final static String configAdminPasswdString = "AdminPasswd";
    public final static String configTelnetnameString = "Telnetname";
    public final static String configTelnetConnectionNumberString = "TelnetConnectionNumber";
    public final static String configDevicesIdListString = "DevicesIdList";
    public final static String configUsernamesListString = "UsernamesList";
    
    public final static String configLocalUsernameString = "LocalUsername";
    public final static String configLocalPasswdString = "LocalPasswd";
    public final static String configLocalUserModeString = "LocalUserMode";
    
    public final static String configRemoteAdminUsernameString = "RemoteAdminUsername";
    public final static String configRemoteAdminPhoneNumberString = "RemoteAdminPhoneNumber";
    
    public final static long ADMIN_MODE = 0;
    public final static long COMMON_USER_MODE = 1;
    public final static long UNKNOW_MODE = -1;
    
    //Login 
    public final static String LoginUserRememberPasswd = "LoginUserRememberPasswd";
    public final static String LoginUserInputUserName = "LoginUserInputUserName";
    public final static String LoginUserInputUserPasswd = "LoginUserInputUserPasswd";
}
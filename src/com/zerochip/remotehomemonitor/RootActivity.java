package com.zerochip.remotehomemonitor;

import com.zerochip.util.*;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

/**
 * @Class: RootActivity
 * @author Kevin.Xu
 */
public class RootActivity extends Activity {

	public static final boolean DEBUG = true;
	private static final String TAG = "com.zerochip.remotehomemonitor.RootActivity";
	private LayoutInflater RootLayoutInflater = null;
	private Handler mHandler = new Handler();
	private WorkContext mWorkContext = null;
	private float mTtsSpeechRate = 1.0f; // 朗读速率
	// 所有布局的父容器
	public FrameLayout ViewContainerFrameLayout = null;
	private SetupWizardViewHolder  mSetupWizardViewHolder = null;

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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_root);

		RootLayoutInflater = LayoutInflater.from(this);
		mWorkContext = new WorkContext();
		mWorkContext.mContext = this;
		mWorkContext.mActivity = this;
		// TODO Auto-generated method stub
		mWorkContext.mHandler = mHandler;
		mWorkContext.mResources = getResources();
		mWorkContext.mGetNetWorkState = new GetNetWorkState(
				mWorkContext.mContext);
		mWorkContext.mSimpleTextToSpeech = new SimpleTextToSpeech(
				mWorkContext.mContext, mTtsSpeechRate);
		mWorkContext.mAnimationFactory = new AnimationFactory(mWorkContext);
		mWorkContext.mPreferences = getSharedPreferences(
				mWorkContext.configFileNameString, Activity.MODE_WORLD_READABLE
						+ Activity.MODE_WORLD_WRITEABLE);
		ViewContainerFrameLayout = (FrameLayout) findViewById(R.id.root_container);
		// 如果是第一次打开这个APK。就提示用户设置用户名，密码。增加帐号等；
		if (CheckNeedSetup()) {
			ViewContainerFrameLayout.addView(RootLayoutInflater.inflate(
					R.layout.interface_setupwizard, null));
			LinearLayout CurrentPageLinearLayout = (LinearLayout) findViewById(R.id.l_welcom_setup);
			CurrentPageLinearLayout
					.setVisibility(CurrentPageLinearLayout.VISIBLE);
			mSetupWizardViewHolder = new SetupWizardViewHolder(mWorkContext);
		} else {
			ViewContainerFrameLayout.addView(RootLayoutInflater.inflate(
					R.layout.interface_login, null));

		}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	/**
	 * @function NextButtonOnClick
	 * @note　"下一步"按键按下
	 * @param v
	 */
	public void NextButtonOnClick(View v) {
		if (DEBUG)
			Log.e(TAG, "NextButtonOnClick id =" + v.getId());
		switch (v.getId()) {
		case R.id.b_welcome_next:
			mHandler.post(new Runnable() {
				@Override
				public void run() {
					SetShowPage(R.id.l_welcom_setup, R.id.l_telnet_setup, false);
				}
			});
			break;
		case R.id.b_telnet_next:
			mHandler.post(new Runnable() {
				@Override
				public void run() {
					SetShowPage(R.id.l_telnet_setup, R.id.l_user_setup, false);
				}
			});
			break;
		case R.id.b_user_next:
			mHandler.post(new Runnable() {
				@Override
				public void run() {
					SetShowPage(R.id.l_user_setup, R.id.l_devices_setup, false);
				}
			});
			break;
//		case R.id.b_device_next:
//			mHandler.post(new Runnable() {
//				@Override
//				public void run() {
//
//				}
//			});
//			break;
		default:
			break;
		}
	}

	/**
	 * @function PreviousButtonOnClick
	 * @note　"上一步"按键按下
	 * @param v
	 */
	public void PreviousButtonOnClick(View v) {
		if (DEBUG)
			Log.e(TAG, "PreviousButtonOnClick id =" + v.getId());
		switch (v.getId()) {
		case R.id.b_telnet_previous:
			mHandler.post(new Runnable() {
				@Override
				public void run() {
					SetShowPage(R.id.l_telnet_setup, R.id.l_welcom_setup, true);
				}
			});
			break;
		case R.id.b_user_previous:
			mHandler.post(new Runnable() {
				@Override
				public void run() {
					SetShowPage(R.id.l_user_setup, R.id.l_telnet_setup, true);
					mSetupWizardViewHolder.GetUserMode();
				}
			});
			break;
		// case R.id.b_device_previous:
		// mHandler.post(new Runnable() {
		// @Override
		// public void run() {
		// SetShowPage(R.id.l_devices_setup, R.id.l_telnet_setup, true);
		// }
		// });
		// break;
		default:
			break;
		}
	}

	/**
	 * @function SetShowPage
	 * @note　两个页面之间跳转
	 * @param CurrentPageLinearLayoutId
	 *            当前页面
	 * @param GotoPageLinearLayoutId
	 *            要跳转的页面
	 * @param LeftFlage
	 */
	private void SetShowPage(int CurrentPageLinearLayoutId,
			int GotoPageLinearLayoutId, boolean LeftFlage) {
		if (DEBUG)
			Log.e(TAG, "CurrentPageLinearLayoutId = "
					+ CurrentPageLinearLayoutId + "  GotoPageLinearLayoutId= "
					+ GotoPageLinearLayoutId);
		LinearLayout CurrentPageLinearLayout = (LinearLayout) findViewById(CurrentPageLinearLayoutId);
		LinearLayout GotoPageLinearLayout = (LinearLayout) findViewById(GotoPageLinearLayoutId);
		if (LeftFlage) {
			CurrentPageLinearLayout.setAnimation(mWorkContext.mAnimationFactory
					.GetAnimationSet(R.anim.push_right_out));
			GotoPageLinearLayout.setAnimation(mWorkContext.mAnimationFactory
					.GetAnimationSet(R.anim.push_left_in));
		} else {
			CurrentPageLinearLayout.setAnimation(mWorkContext.mAnimationFactory
					.GetAnimationSet(R.anim.push_left_out));
			GotoPageLinearLayout.setAnimation(mWorkContext.mAnimationFactory
					.GetAnimationSet(R.anim.push_right_in));
		}
		CurrentPageLinearLayout.setVisibility(View.GONE);
		GotoPageLinearLayout.setVisibility(View.VISIBLE);
	}

	
    /**
     * @function AddButtonOnClick
     * @note 设置设备面"增加"按键按下
     * @param v
     */
    public void AddButtonOnClick(View v)
    {
        if (DEBUG)
            Log.e(TAG, "NextButtonOnClick id =" + v.getId());
        if (v.getId() == R.id.b_device_add)
        {
            // 增加设备
            mHandler.post(new Runnable()
            {
                @Override
                public void run()
                {
//                    String DevicesName = GetDevicesName();
//                    String DevicesId = GetDevicesId();
//                    AddDeviceInfo(DevicesName, DevicesId);
                }
            });
        }

    }

    /**
     * @function FinishButtonOnClick
     * @note 设置设备面"完成"按键按下
     * @param v
     */
    public void FinishButtonOnClick(View v)
    {
        if (DEBUG)
            Log.e(TAG, "PreviousButtonOnClick id =" + v.getId());
        if (v.getId() == R.id.b_device_finish)
        {
            // 增加设备
            mHandler.post(new Runnable()
            {
                @Override
                public void run()
                {
//                    String UserInfoName = GetUserInfoName();
//                    String UserInfoPasswd = GetUserInfoPasswd();
//                    AddUserInfo(UserInfoName, UserInfoPasswd);
//                    editor.putBoolean(
//                            mWorkContext.configNeedRunSetupWizardString, false);
//                    editor.commit();
//                    mWorkContext.mContext.startActivity(new Intent(
//                            mWorkContext.mContext, LoginActivity.class));
//                    finish();
                }
            });
            // 跳转到登录页面
        }
    }
}

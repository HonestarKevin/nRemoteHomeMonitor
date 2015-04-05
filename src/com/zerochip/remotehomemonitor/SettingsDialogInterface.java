package com.zerochip.remotehomemonitor;

import com.zerochip.util.WorkContext;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

public class SettingsDialogInterface extends Dialog {
	public static final boolean DEBUG = RootActivity.DEBUG;
	private static final String TAG = "com.zerochip.remotehomemonitor.SettingsDialogInterface";
	
	private WorkContext mWorkContext = null;
	private Context mContext = null;
	
	/**
	 * @Funciton
	 * @param context
	 * @param theme
	 * @param workContext
	 */
	public SettingsDialogInterface(Context context, int theme,
			WorkContext workContext) {
		super(workContext.mActivity, theme);
		this.mWorkContext = workContext;
		this.mContext = workContext.mContext;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_settings);
	}

}

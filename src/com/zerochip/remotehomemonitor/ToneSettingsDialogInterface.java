package com.zerochip.remotehomemonitor;

import com.zerochip.util.WorkContext;
import com.zerochip.util.WorkContext.TONE_TYPE;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

public class ToneSettingsDialogInterface extends Dialog {
	public static final boolean DEBUG = RootActivity.DEBUG;
	private static final String TAG = "com.zerochip.remotehomemonitor.ToneSettingsDialogInterface";
	
	private WorkContext mWorkContext = null;
	private Context mContext = null;
	private WorkContext.TONE_TYPE mToneType = TONE_TYPE.SOME_ONE_GO_HOME;
	private ToneSettingsDialogViewHolder mToneSettingsDialogViewHolder = null;
	
	/**
	 * @Funciton
	 * @param context
	 * @param theme
	 * @param workContext
	 */
	public ToneSettingsDialogInterface(Context context, int theme,
			WorkContext workContext, WorkContext.TONE_TYPE mType) {
		super(workContext.mActivity, theme);
		this.mWorkContext = workContext;
		this.mContext = workContext.mContext;
		this.mToneType = mType;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_settings_tone);
		mToneSettingsDialogViewHolder = new ToneSettingsDialogViewHolder(mWorkContext, this, mToneType);
	}

}

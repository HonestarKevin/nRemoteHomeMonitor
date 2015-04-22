package com.zerochip.remotehomemonitor;

import com.zerochip.util.WorkContext;
import com.zerochip.util.WorkContext.TONE_TYPE;

public class ToneSettingsDialogViewHolder {
	private WorkContext mWorkContext = null;
	private ToneSettingsDialogInterface mToneSettingsDialogInterface = null;
	private WorkContext.TONE_TYPE mToneType = TONE_TYPE.SOME_ONE_GO_HOME;
	
	public ToneSettingsDialogViewHolder(WorkContext mWorkContext,
			ToneSettingsDialogInterface mDialogInterface, TONE_TYPE mToneType) {
		super();
		this.mWorkContext  = mWorkContext;
		this.mToneSettingsDialogInterface  = mDialogInterface;
		this.mToneType = mToneType;
		FindAllView();
		SetViewListener();
		InitViewData();
	}

	private void InitViewData() {
		
	}

	private void FindAllView() {
		
	}

	private void SetViewListener() {
		
	}
}

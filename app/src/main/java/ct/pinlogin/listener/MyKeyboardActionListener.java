/*
 * Copyright (C) 2011 - Riccardo Ciovati
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ct.pinlogin.listener;

import android.inputmethodservice.KeyboardView.OnKeyboardActionListener;
import android.view.KeyEvent;

import ct.pinlogin.activity.LoginActivity;
import ct.pinlogin.model.ChartState;
import ct.pinlogin.model.KeyPress;

public class MyKeyboardActionListener implements OnKeyboardActionListener {

	private LoginActivity myActivity;


	/**
	 * activity which generates this keyboard
	 * @param targetActivity
	 */
	public MyKeyboardActionListener(LoginActivity targetActivity) {
		myActivity = targetActivity;

	}

	@Override
	public void swipeUp() {
		// TODO Auto-generated method stub

	}

	@Override
	public void swipeRight() {
		// TODO Auto-generated method stub

	}

	@Override
	public void swipeLeft() {
		// TODO Auto-generated method stub

	}

	@Override
	public void swipeDown() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onText(CharSequence text) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onRelease(int primaryCode) {
		// TODO Auto-generated method stub
		myActivity.updateChart();
		//add keypress to list and set current to null
		myActivity.setKeyPress(null);
	}

	@Override
	public void onPress(int primaryCode) {
		// TODO Auto-generated method stub
		KeyPress kp=myActivity.getKeyPress();
		kp.setKey(primaryCode);
	}



	@Override
	public void onKey(int primaryCode, int[] keyCodes) {
		long eventTime = System.currentTimeMillis();
		KeyEvent event = new KeyEvent(eventTime, eventTime,
				KeyEvent.ACTION_DOWN, primaryCode, 0, 0, 0, 0,
				KeyEvent.FLAG_SOFT_KEYBOARD | KeyEvent.FLAG_KEEP_TOUCH_MODE);
		myActivity.dispatchKeyEvent(event);
	}
}
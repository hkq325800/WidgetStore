package com.iue.pocketdoc.utilities;

import java.lang.ref.WeakReference;
import android.os.Handler;
import android.os.Message;

/**
 *销毁hander引起的持有对象activity
 * 
 * @author Administrator syc
 * 
 */
public abstract class SystemHandler extends Handler {
	private WeakReference<Object> weekPeference;

	

	public SystemHandler(Object obj) {
		weekPeference = new WeakReference<Object>(obj);
	}

	@Override
	public void dispatchMessage(Message msg) {
		// TODO Auto-generated method stub6
		super.dispatchMessage(msg);
		Object obj = weekPeference.get();
		if (null != obj)
			handlerMessage(msg);

	}

	/**
	 * 
	 * 
	 * @param msg
	 */
	public abstract void handlerMessage(Message msg);

}

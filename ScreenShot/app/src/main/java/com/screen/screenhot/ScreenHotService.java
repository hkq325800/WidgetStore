package main.java.com.screen.screenhot;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.screen.aidl.IScreenshotCallback;
import com.screen.aidl.IScreenshotProvider;
import com.screen.manager.Root;
import com.screen.manager.ScreenHotManager;
import com.screen.manager.ShakeManager;
import com.screen.manager.Root.RootPermissionListener;
import com.screen.manager.ShakeManager.ShakeChangeListener;
import com.screen.utils.Utils;

import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.IBinder;
import android.os.RemoteException;

public class ScreenHotService extends Service implements ShakeChangeListener {
	
	private static final String ASSERT_CMD = "gsnap";
	private static String EXC_CMD = "";
	private static String SAVE_FILEPATH;
	
	private Root root;
	private IScreenshotCallback callback;
	private ScreenHotManager screenHotManager;
	private ShakeManager shakeManager;
	
	private RootPermissionListener rootPermissionListener = new RootPermissionListener() {

		@Override
		public void isRoot(boolean root) {
			try {
				callback.isRooted(root);
				if(root) {
					File file = new File(EXC_CMD);
					if(!file.exists()) {
						try {
							Utils.copy(Utils.getAssertFile(ScreenHotService.this, ASSERT_CMD), openFileOutput(ASSERT_CMD, 0));
							ScreenHotService.this.root.root("chmod 777 " + EXC_CMD);
							callback.isSeccuss(true);
						} catch (FileNotFoundException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
							deleteFile(ASSERT_CMD);
							callback.isSeccuss(false);
							return ;
						}
					}
					screenHotManager = new ScreenHotManager(ScreenHotService.this);
					shakeManager = new ShakeManager(ScreenHotService.this);
					shakeManager.setOnShakeChangeListener(ScreenHotService.this);
					callback.isSeccuss(true);
				}
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			
		}
	};
	
	IScreenshotProvider.Stub stub = new IScreenshotProvider.Stub() {

		@Override
		public void root() throws RemoteException {
			root = new Root(rootPermissionListener);
		}

		@Override
		public void registRemoteCallback(IScreenshotCallback callback)
				throws RemoteException {
			ScreenHotService.this.callback = callback;
		}

		@Override
		public void unRegistRemoteCallback(IScreenshotCallback callback)
				throws RemoteException {
			
		}

	};
	
	@Override
	public IBinder onBind(Intent intent) {
		EXC_CMD = getCacheDir().getParent() + "/files/" + ASSERT_CMD;
		SAVE_FILEPATH = Environment.getExternalStorageDirectory() + "/screenhot/";
		File file = new File(SAVE_FILEPATH);
		if(!file.exists()) file.mkdirs();
		return stub;
	}

	@Override
	public void onShakeChanged() {
		takeScreenHot();
	}
	
	public synchronized void takeScreenHot() {
		try {
			screenHotManager.cancel();
			String savePath = Utils.getSavePicPath(SAVE_FILEPATH);
			String cmd = "." + EXC_CMD + " " + savePath + " /dev/graphics/fb0";
			System.out.println(cmd);
			root.root(cmd);
			shakeManager.vibrator();
			Thread.sleep(500);
			screenHotManager.showScreenHot(savePath);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		if(null != root) root.recycle();
		if(null != shakeManager) shakeManager.recycle();
	}
	
}

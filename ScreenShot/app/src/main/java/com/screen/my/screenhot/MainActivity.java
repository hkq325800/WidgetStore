package com.screen.my.screenhot;

import com.screen.my.IScreenshotCallback;
import com.screen.my.IScreenshotProvider;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;

public class MainActivity extends Activity implements OnClickListener {
	
	private static final String str = "本软件需要ROOT权限,如果您没有root权限可以点击我获取root权限,<a href='#'>点击我下载root软件</a>";
	
	private static final int ROOT = 1;
	private static final int SECCUSS = 2;
	
	private Intent service;
	private IScreenshotProvider screenshotProvider;
	private ProgressDialog progressDialog;
	private static boolean isStop = true;
	
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case ROOT:
				dismissDialog();
				Toast.makeText(MainActivity.this, R.string.activity_no_root, Toast.LENGTH_SHORT).show();
				break;
			case SECCUSS:
				dismissDialog();
				Toast.makeText(MainActivity.this, R.string.activity_start_success, Toast.LENGTH_SHORT).show();
				break;
			}
		}
		
	};
	
	private IScreenshotCallback.Stub callback = new IScreenshotCallback.Stub() {

		@Override
		public void isRooted(boolean root) throws RemoteException {
			if(!root) {
				handler.sendEmptyMessage(ROOT);
			} else {
				System.out.println("root^");
			}
		}

		@Override
		public void isSeccuss(boolean seccussed) throws RemoteException {
			if(seccussed) { 
				handler.sendEmptyMessage(SECCUSS);
			}
		}
		
	};
	
	private ServiceConnection conn = new ServiceConnection() {
		

		@Override
		public void onServiceDisconnected(ComponentName name) {
			try {
				screenshotProvider.unRegistRemoteCallback(callback);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			screenshotProvider = null;
		}
		
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			screenshotProvider = IScreenshotProvider.Stub.asInterface(service);
			try {
				screenshotProvider.registRemoteCallback(callback);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			try {
				screenshotProvider.root();
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);
		findViewById(R.id.activity_btnStart).setOnClickListener(this);
		findViewById(R.id.activity_btnStop).setOnClickListener(this);
		TextView textView = (TextView) findViewById(R.id.activity_text);
		textView.setText(Html.fromHtml(str));
		textView.setOnClickListener(this);
		service = new Intent(this, ScreenHotService.class);
	}

	@Override  
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.activity_text:
			try {
				Uri uri = Uri.parse("market://search?q=root"); 
				Intent it = new Intent(Intent.ACTION_VIEW, uri); 
				startActivity(it); 
			} catch (Exception e) {
				Toast.makeText(this, R.string.no_market, Toast.LENGTH_SHORT).show();
				e.printStackTrace();
			}
			break;
		case R.id.activity_btnStart:
			isStop = false;
			bindService(service, conn, BIND_AUTO_CREATE);
			showDialog();
			break;
		case R.id.activity_btnStop:
			if(!isStop) {
				isStop = true;
				stopService(service);
				Toast.makeText(this, R.string.service_shutdown, Toast.LENGTH_SHORT).show();
			}
			
			break;
		}
	}
	
	public void showDialog() {
		if(null == progressDialog) {
			progressDialog = new ProgressDialog(this);
			progressDialog.setMessage(getString(R.string.activity_starting));
		}
		if(!progressDialog.isShowing()) {
			progressDialog.show();
		}
		
	}
	
	private void dismissDialog() {
		if(null != progressDialog && progressDialog.isShowing()) {
			progressDialog.dismiss();
		}
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if(!isStop) startService(service);
		dismissDialog();
	}
	
}

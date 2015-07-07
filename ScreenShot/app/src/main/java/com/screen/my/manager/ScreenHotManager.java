package com.screen.my.manager;

import java.io.File;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageView;

import com.screen.my.screenhot.R;

public class ScreenHotManager implements OnClickListener {
	
	private WindowManager windowManager;
	private LayoutParams layoutParams;
	private View showView;
	private ImageView imageView;
	private String picPath;
	private boolean exist;
	
	public ScreenHotManager(Context context) {
		windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		initView(context);
		layoutParams = new LayoutParams();
		layoutParams.gravity = Gravity.CENTER;
		layoutParams.type = LayoutParams.TYPE_SYSTEM_ALERT;
		exist = false;
	}
	
	private void initView(Context context) {
		showView = LayoutInflater.from(context).inflate(R.layout.screenshot_view, null);
		imageView = (ImageView) showView.findViewById(R.id.screen_img);
		showView.findViewById(R.id.screen_btnSave).setOnClickListener(this);
		showView.findViewById(R.id.screen_btnCanel).setOnClickListener(this);
	}
	
	public void setImage() {
		if(!TextUtils.isEmpty(picPath)) {
			Drawable drawable = imageView.getDrawable();
			if(null != drawable) {
				Bitmap recycle = ((BitmapDrawable)drawable).getBitmap();
				if(null != recycle && !recycle.isRecycled()) {
					recycle.recycle();
				}
			}
			Bitmap bitmap = BitmapFactory.decodeFile(picPath);
			imageView.setImageBitmap(bitmap);
		}
	}
	
	public void showScreenHot(String picPath) {
		if(!exist) {
			this.picPath = picPath;
			setImage();
			windowManager.addView(showView, layoutParams);
			exist = true;
		}
		System.out.println("showScreenHot : " + exist);
	}
	
	private void dismissScreenHot() {
		if(exist) {
			windowManager.removeView(showView);
			exist = false;
		}
		System.out.println("dismissScreenHot : " + exist);
	}
	
	public void save() {
		dismissScreenHot();
	}
	
	public void cancel() {
		if(exist) {
			File file = new File(picPath);
			file.delete();
		}
		dismissScreenHot();
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.screen_btnSave:
			save();
			break;
		case R.id.screen_btnCanel:
			cancel();
			break;
		}
	}
	
}

package com.pps.screen.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

/**
 * 模拟实现截图功能，(当然可以借助这个功能进行扩展(例如：摇一摇进行截图,或者开启定时截图功能))
 * @author jiangqingqing
 * @time 2013/09/29
 */
public class MainActivity extends Activity {
    private Button btn;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		btn=(Button)this.findViewById(R.id.btn);
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				boolean result = ScreenShotUtils.shotBitmap(MainActivity.this);
				if(result)
				{
					Toast.makeText(MainActivity.this, "截图成功.", Toast.LENGTH_SHORT).show();
				}else {
					Toast.makeText(MainActivity.this, "截图失败.", Toast.LENGTH_SHORT).show();
				}
				
			}
		});
	}

	

}

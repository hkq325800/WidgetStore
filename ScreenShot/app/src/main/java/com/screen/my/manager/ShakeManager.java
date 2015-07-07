package com.screen.my.manager;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Vibrator;

public class ShakeManager implements SensorEventListener {
	
	private Vibrator vibrator;
	private SensorManager sensorManager;
	private ShakeChangeListener shakeChangeListener;
	
	public ShakeManager(Context context) {
		vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
		sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
		sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
	}
	
	public void setOnShakeChangeListener(ShakeChangeListener shakeChangeListener) {
		this.shakeChangeListener = shakeChangeListener;
	}
	
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		
	}
	
	public void vibrator() {
		vibrator.vibrate(200);
	}
	
	@Override
	public void onSensorChanged(SensorEvent event) {
		 // 传感器信息改变时执行该方法 
        float[] values = event.values; 
        float x = values[0]; // x轴方向的重力加速度，向右为正 
        float y = values[1]; // y轴方向的重力加速度，向前为正 
        float z = values[2]; // z轴方向的重力加速度，向上为正 
        // 一般在这三个方向的重力加速度达到40就达到了摇晃手机的状态。 
        int medumValue = 19;// 三星 i9250怎么晃都不会超过20，没办法，只设置19了 
        if (Math.abs(x) > medumValue || Math.abs(y) > medumValue || Math.abs(z) > medumValue) {
        	if(null != shakeChangeListener) shakeChangeListener.onShakeChanged();
        } 
	}
	
	public void recycle() {
		sensorManager.unregisterListener(this);
		vibrator.cancel();
	}
	
	public interface ShakeChangeListener {
		
		void onShakeChanged();
		
	}
	
}

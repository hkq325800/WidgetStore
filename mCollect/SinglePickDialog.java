package com.iue.pocketdoc.visitscheduling.widget;


import com.iue.pocketdoc.android.R;
import com.iue.pocketdoc.common.widget.PickDialog;

import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;


/**
 * @author HKQ
 * @category 新增排班界面中开始时间与持续时间的选择
 * @date 2015年12月
 */
public abstract class SinglePickDialog extends PickDialog {
	private NumberPicker mFirstPicker, mSecondPicker;
	private TextView mSingleDialogFirstTxt, mSingleDialogSecondTxt;
	public static final int type_single_start = 0;
	public static final int type_single_last = 1;
	final String[] first4last = { "0", "1", "2", "3", "4" };
	final String[] second4start = { "00", "15", "30", "45" };
	final String[] second4last = { "00", "30" };
	private int type;

	public SinglePickDialog(Context context, int layout, String title,
			boolean hasbtn, int type) {
		super(context, layout, title, hasbtn);
		this.type = type;
	}

	public void setDefault(int leftValue, int rightValue) {
		switch (type) {
		case type_single_start:
			mSingleDialogFirstTxt.setText(":");
			mFirstPicker.setMinValue(0);
			mFirstPicker.setMaxValue(23);
			mFirstPicker.setValue(leftValue);// 0-23
			mSecondPicker.setDisplayedValues(second4start);
			mSecondPicker.setMinValue(0);
			mSecondPicker.setMaxValue(second4start.length - 1);
			mSecondPicker.setValue(getTrueRightIndex(rightValue));// 0、15、30、45
			break;
		case type_single_last:
			mSingleDialogFirstTxt.setText("小时");
			mFirstPicker.setDisplayedValues(first4last);
			mFirstPicker.setMinValue(0);
			mFirstPicker.setMaxValue(first4last.length - 1);
			mFirstPicker.setValue(leftValue);// 0、1、2、3、4
			mSingleDialogSecondTxt.setText("分钟");
			mSecondPicker.setDisplayedValues(second4last);
			mSecondPicker.setMinValue(0);
			mSecondPicker.setMaxValue(second4last.length - 1);
			mSecondPicker.setValue(getTrueRightIndex(rightValue));// 0、30
			break;
		}
	}

	// private int getTrueLeftIndex(int leftValue){
	// switch(type){
	// case type_single_last:
	// for (int i = 0; i < first4last.length; i++) {
	// if (leftValue == Integer.valueOf(first4last[i]))
	// return i;
	// }
	// break;
	// }
	// return 0;
	// }

	private int getTrueRightIndex(int rightValue) {
		switch (type) {
		case type_single_start:
			for (int i = 0; i < second4start.length; i++) {
				if (rightValue == Integer.valueOf(second4start[i]))
					return i;
			}
			break;
		case type_single_last:
			for (int i = 0; i < second4last.length; i++) {
				if (rightValue == Integer.valueOf(second4last[i]))
					return i;
			}
			break;
		}
		return 0;
	}

	private String getTrueRightValue(int rightValue) {
		switch (type) {
		case type_single_start:
			for (int i = 0; i < second4start.length; i++) {
				if (rightValue == i)
					return second4start[i];
			}
			break;
		case type_single_last:
			for (int i = 0; i < second4last.length; i++) {
				if (rightValue == i)
					return second4last[i];
			}
			break;
		}
		return "";
	}

	@Override
	public void findViewByView(View view) {
		mSingleDialogFirstTxt = (TextView) view
				.findViewById(R.id.mSingleDialogFirstTxt);
		mSingleDialogSecondTxt = (TextView) view
				.findViewById(R.id.mSingleDialogSecondTxt);
		mFirstPicker = (NumberPicker) view.findViewById(R.id.mFirstPicker);
		mSecondPicker = (NumberPicker) view.findViewById(R.id.mSecondPicker);
		EditText e1 = (EditText) mFirstPicker.getChildAt(0);
		EditText e2 = (EditText) mSecondPicker.getChildAt(0);
		e1.setFocusable(false);
		e2.setFocusable(false);
	}

	@Override
	public void okClicked(DialogInterface dialog, boolean sure) {
		if (sure) {
			StringBuffer sb = new StringBuffer();
			switch (type) {
			case type_single_start:
				sb.append(mFirstPicker.getValue());
				sb.append(":");
				sb.append(getTrueRightValue(mSecondPicker.getValue()));
				callback(sb.toString(), mFirstPicker.getValue(),
						Integer.valueOf(getTrueRightValue(mSecondPicker
								.getValue())));
				break;
			case type_single_last:
				if (mFirstPicker.getValue() != 0) {
					sb.append(mFirstPicker.getValue());
					sb.append("小时");
				}
				if (mSecondPicker.getValue() != 0) {
					sb.append(getTrueRightValue(mSecondPicker.getValue()));
					sb.append("分钟");
				}
				callback(sb.toString(), mFirstPicker.getValue(),
						Integer.valueOf(getTrueRightValue(mSecondPicker
								.getValue())));
				break;
			}
		}
	}

	public double getduration() {

		return 0;
	}

	public abstract void callback(String data, int valueFirst, int valueSecond);
}

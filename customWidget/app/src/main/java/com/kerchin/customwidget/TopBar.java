package com.kerchin.customwidget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by Kerchin on 2016/1/7 0007.
 */
public class TopBar extends RelativeLayout {
    private int mLeftTextColor, mRightTextColor, mTitleTextColor;
    private float mTitleTextSize;
    private Drawable mRightBackground, mLeftBackground;
    private String mLeftText, mRightText, mTitle;
    private Context context;
    private Button mLeftButton, mRightButton;
    private TextView mTitleView;
    private topBarClickListener mListener;

    public TopBar(Context context) {
        super(context);
        this.context = context;
        setContentView(context);
        initializeView();
        initializeData(context);
    }

    public TopBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        parseAttr(context, attrs);


        //setContentView(context);
        initializeView();
        //initializeData(context);
    }

    private void parseAttr(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TopBar);
        if (typedArray.hasValue(R.styleable.TopBar_leftTextColor))
            mLeftTextColor = typedArray.getInt(R.styleable.TopBar_leftTextColor, 1);
        mLeftBackground = typedArray.getDrawable(R.styleable.TopBar_leftBackground);
        mLeftText = typedArray.getString(R.styleable.TopBar_leftText);
        mRightTextColor = typedArray.getColor(R.styleable.TopBar_rightTextColor
                , getResources().getColor(R.color.colorAccent));
        mRightBackground = typedArray.getDrawable(R.styleable.TopBar_rightBackground);
        mRightText = typedArray.getString(R.styleable.TopBar_rightText);
        mTitleTextSize = typedArray.getDimension(R.styleable.TopBar_mTitleTextSize, 10);
        mTitleTextColor = typedArray.getColor(R.styleable.TopBar_mTitleTextColor, 0);
        mTitle = typedArray.getString(R.styleable.TopBar_mTitle);
        typedArray.recycle();
    }

    private void setContentView(Context context) {
//        LayoutInflater inflater = (LayoutInflater) context
//                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        inflater.inflate(R.layout.mTopBar, this);
    }

    private void initializeView() {
        mLeftButton = new Button(context);
        mRightButton = new Button(context);
        mTitleView = new TextView(context);

        if (isValid(mLeftTextColor))
            mLeftButton.setTextColor(mLeftTextColor);
        if (isValid(mLeftText))
            mLeftButton.setText(mLeftText);
        //if (isValid(mLeftBackground))
        mLeftButton.setBackground(mLeftBackground);

        if (isValid(mRightText))
            mRightButton.setText(mRightText);
        //if (isValid(mRightBackground))
        mRightButton.setBackground(mRightBackground);
        if (isValid(mRightTextColor))
            mRightButton.setTextColor(mRightTextColor);

        if (isValid(mRightTextColor))
            mTitleView.setText(mTitle);
        if (isValid(mRightTextColor))
            mTitleView.setTextColor(mTitleTextColor);
        if (isValid(mRightTextColor))
            mTitleView.setTextSize(mTitleTextSize);
        mTitleView.setGravity(Gravity.CENTER);

        LayoutParams mLeftParams = new LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mLeftParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, TRUE);
        addView(mLeftButton, mLeftParams);

        LayoutParams mRightParams = new LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mRightParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, TRUE);
        addView(mRightButton, mRightParams);

        LayoutParams mTitleParams = new LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mTitleParams.addRule(RelativeLayout.CENTER_IN_PARENT, TRUE);
        addView(mTitleView, mTitleParams);

        mRightButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.rightClick();
            }
        });
        mLeftButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.leftClick();
            }
        });
    }

    private void initializeData(Context context) {

    }

    public void setOnTopBarClickListener(topBarClickListener mListener) {
        this.mListener = mListener;
    }

    public interface topBarClickListener {
        void leftClick();

        void rightClick();
    }

    public boolean isValid(Object o) {
        return o != null;
    }
}

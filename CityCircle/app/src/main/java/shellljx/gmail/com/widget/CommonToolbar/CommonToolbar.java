package shellljx.gmail.com.widget.CommonToolbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.media.Image;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import shellljx.gmail.com.widget.R;

/**
 * Created by Administrator on 2015/5/15.
 */
public class CommonToolbar extends LinearLayout implements View.OnClickListener {

    private boolean isLeftbtnIsvisible;
    private boolean isRightbtnIsvisible;
    private String titleString;
    private String rightBtnText;
    private int backgroundColor;
    private TextView titleView;
    private TextView rightBtn;
    private ImageView leftBtn;
    private LinearLayout leftBtn_p;
    private ImageView rightImageBtn;
    private RelativeLayout common_tool_bar;
    private int leftBtnSrc;
    private int rightBtnSrc;

    private OnToolbarClickListener onToolbarClickListener;

    public CommonToolbar(Context context) {
        this(context, null);
    }

    public CommonToolbar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CommonToolbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.common_tool_bar, this, true);
        titleView = (TextView)findViewById(R.id.title);
        rightBtn = (TextView)findViewById(R.id.btn_right);
        leftBtn_p = (LinearLayout)findViewById(R.id.btn_left_p);
        leftBtn = (ImageView)findViewById(R.id.btn_left);
        common_tool_bar = (RelativeLayout)findViewById(R.id.common_tool_bar);
        rightImageBtn = (ImageView)findViewById(R.id.btn_right_image);

        TypedArray array = context.getTheme().obtainStyledAttributes(attrs,R.styleable.CommonToolbar,0,0);
        try{
            titleString = array.getString(R.styleable.CommonToolbar_toolbarTitle);
            rightBtnText = array.getString(R.styleable.CommonToolbar_rightBtnText);
            leftBtnSrc = array.getResourceId(R.styleable.CommonToolbar_leftBtnsrc, -1);
            isLeftbtnIsvisible = array.getBoolean(R.styleable.CommonToolbar_isLeftBtnVisible, true);
            isRightbtnIsvisible = array.getBoolean(R.styleable.CommonToolbar_isRightBtnVisible,true);
            backgroundColor = array.getInt(R.styleable.CommonToolbar_backgroundColor, -1);
            rightBtnSrc = array.getResourceId(R.styleable.CommonToolbar_rightBtnsrc,-1);
        }finally {
            array.recycle();
        }

        titleView.setText((titleString!=null)?titleString:"");
        leftBtn.setVisibility(isLeftbtnIsvisible ? VISIBLE : GONE);
        rightBtn.setVisibility(isRightbtnIsvisible?VISIBLE:GONE);
        rightImageBtn.setVisibility((rightBtnSrc!=-1)?VISIBLE:GONE);
        if(rightBtnSrc!=-1){
            rightImageBtn.setImageResource(rightBtnSrc);
            rightImageBtn.setClickable(false);
        }
        rightBtn.setText((rightBtnText != null) ? rightBtnText : "");
        if(leftBtnSrc!=-1){
            leftBtn.setImageResource(leftBtnSrc);
            leftBtn.setClickable(false);
        }
        leftBtn_p.setOnClickListener(this);
        rightBtn.setOnClickListener(this);
        rightImageBtn.setOnClickListener(this);
        common_tool_bar.setBackgroundColor((backgroundColor!=-1)?backgroundColor:(getResources().getColor(R.color.basecolor)));
    }

    public String getTitle(){
        return this.titleString;
    }

    public void setTitle(String title){
        this.titleString = title;
        this.titleView.setText(titleString);
        invalidate();
        requestLayout();
    }

    public void setRightBtnText(String text){
        rightBtnText = text;
        rightBtn.setText(rightBtnText);
        invalidate();
        requestLayout();
    }

    public void setLeftBtnSrc(int res){
        leftBtnSrc = res;
        leftBtn.setImageResource(leftBtnSrc);
    }

    public void setOnToolbarClickListener(OnToolbarClickListener listener){
        this.onToolbarClickListener = listener;
    }

    public OnToolbarClickListener getOnToolbarClickListener(){
        return this.onToolbarClickListener;
    }

    @Override
    public void onClick(View view) {
        if(onToolbarClickListener!=null){
            if(view.getId()==R.id.btn_left_p)
                onToolbarClickListener.onLeftBtnClicked();
            if(view.getId()==R.id.btn_right)
                onToolbarClickListener.onRightBtnClicked();
            if(view.getId()==R.id.btn_right_image)
                onToolbarClickListener.onRightBtnClicked();
        }

    }

    public interface OnToolbarClickListener{
        public void onLeftBtnClicked();
        public void onRightBtnClicked();
    }

}

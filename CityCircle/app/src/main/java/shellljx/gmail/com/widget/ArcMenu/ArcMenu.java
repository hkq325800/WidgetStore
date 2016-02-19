package shellljx.gmail.com.widget.ArcMenu;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;

import shellljx.gmail.com.widget.R;

/**
 * Created by Administrator on 2015/5/9.
 */
public class ArcMenu extends ViewGroup implements View.OnClickListener{

    private Position mPosition = Position.BOTTOM_CENTER;
    private int mRadius;
    private Status mCurrentStatus = Status.CLOSE;
    private OnMenuItemClickListener menuItemClickListener;

    private static final int POS_LEFT_TOP = 0;
    private static final int POS_LEFT_BOTTOM = 1;
    private static final int POS_RIGHT_TOP = 2;
    private static final int POS_RIGHT_BOTTOM = 3;
    private static final int POS_BOTTOM_CENTER = 4;

    /**
     * 菜单的主按钮
     * */
    private View mCButton;

    @Override
    public void onClick(View v) {
        if(menuItemClickListener!=null){
            menuItemClickListener.onMenuItemClicked(v,v.getId());
        }
    }

    /**
     * 菜单的位置枚举
     * */
    public enum Position
    {
        LEFT_TOP,LEFT_BOTTOM,RIGHT_TOP,RIGHT_BOTTOM,BOTTOM_CENTER
    }
    /**
     * 菜单的状态
     * */
    public enum Status
    {
        OPEN,CLOSE
    }

    public ArcMenu(Context context) {
        this(context,null);
    }

    public ArcMenu(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ArcMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mRadius = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,100,getResources().getDisplayMetrics());
        //获取自定义属性的值
        TypedArray array = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ArcMenu,defStyleAttr,0);
        int pos = array.getInt(R.styleable.ArcMenu_position,POS_BOTTOM_CENTER);
        switch (pos)
        {
            case POS_LEFT_TOP:
                mPosition = Position.LEFT_TOP;
                break;
            case POS_LEFT_BOTTOM:
                mPosition = Position.LEFT_BOTTOM;
                break;
            case POS_RIGHT_TOP:
                mPosition = Position.RIGHT_TOP;
                break;
            case POS_RIGHT_BOTTOM:
                mPosition = Position.RIGHT_BOTTOM;
                break;
            case POS_BOTTOM_CENTER:
                mPosition = Position.BOTTOM_CENTER;
                break;
        }

        mRadius = (int) array.getDimension(R.styleable.ArcMenu_radius,TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,100,getResources().getDisplayMetrics()));
        Log.e("TAG ","Position = "+mPosition+",Radius "+mRadius);
        array.recycle();

    }

    /**
     * 点击子菜单项的回调接口
     * */
    public interface OnMenuItemClickListener
    {
        void onMenuItemClicked(View view, int pos);
    }

    public void setMenuItemClickListener(OnMenuItemClickListener listener)
    {
        this.menuItemClickListener = listener;
    }

    public OnMenuItemClickListener getMenuItemClickListener()
    {
        return this.menuItemClickListener;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int count= getChildCount();
        for(int i=0;i<count;i++)
        {
            measureChild(getChildAt(i),widthMeasureSpec,heightMeasureSpec);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        if(changed)
        {
            int count = getChildCount();
            for(int i=0;i<count;i++)
            {
                View child = getChildAt(i);
                //child.setVisibility(GONE);
                int cl = (int) (getMeasuredWidth()/2-mRadius*Math.cos(Math.PI/2/(count-1)*i+Math.PI/4));
                int ct = (int) (getMeasuredHeight()-mRadius*Math.sin(Math.PI/2/(count-1)*i+Math.PI/4));

                int cWidth = child.getMeasuredWidth();
                int cHight = child.getMeasuredHeight();
                child.layout(cl-cWidth/2,ct-cHight/2,cl+cWidth/2,ct+cHight/2);
                child.setOnClickListener(this);
            }
        }
    }

    public void toggleMenu(int duration)
    {
        int count = getChildCount();

        for(int i=0;i<count;i++)
        {
            AnimationSet animationSet = new AnimationSet(true);
            Animation tranAnima = null;
            int cl = (int) (getMeasuredWidth()/2-mRadius*Math.cos(Math.PI/2/(count-1)*i+Math.PI/4));
            int ct = (int) (getMeasuredHeight()-mRadius*Math.sin(Math.PI/2/(count-1)*i+Math.PI/4));
            final View childview = getChildAt(i);
            childview.setVisibility(VISIBLE);
            System.out.println("cl:"+cl+"ct:"+ct);
            if(mCurrentStatus ==Status.CLOSE)
            {
                tranAnima = new TranslateAnimation(getMeasuredWidth()/2-cl,0,getMeasuredHeight()-ct+getChildAt(i).getMeasuredHeight()/2,0);
                childview.setClickable(true);
                childview.setFocusable(true);
            }else
            {
                tranAnima = new TranslateAnimation(0,getMeasuredWidth()/2-cl,0,getMeasuredHeight()-ct+getChildAt(i).getMeasuredHeight()/2);
                childview.setClickable(false);
                childview.setFocusable(false);
            }
            tranAnima.setFillAfter(true);
            tranAnima.setDuration(duration);
            tranAnima.setStartOffset((i * 100) / count);
            RotateAnimation rotateAnimation = new RotateAnimation(0,1080,RotateAnimation.RELATIVE_TO_SELF,0.5f,RotateAnimation.RELATIVE_TO_SELF,0.5f);
            rotateAnimation.setDuration(duration);
            rotateAnimation.setFillAfter(true);
            animationSet.addAnimation(rotateAnimation);
            animationSet.addAnimation(tranAnima);
            animationSet.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    if(mCurrentStatus == Status.CLOSE)
                    {
                        childview.setVisibility(GONE);
                    }
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            childview.startAnimation(animationSet);
        }
        changeStatus();
    }

    private void changeStatus() {
        mCurrentStatus = (mCurrentStatus==Status.CLOSE?Status.OPEN:Status.CLOSE);
    }
}

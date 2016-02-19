package shellljx.gmail.com.app.fragment.main;

import shellljx.gmail.com.app.fragment.BaseFragment;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import shellljx.gmail.com.app.adapter.HomePagerAdapter;
import shellljx.gmail.com.app.fragment.BaseFragment;
import shellljx.gmail.com.widget.R;

/**
 * Created by shell on 15-10-7.
 */
public class HomeFragment extends BaseFragment implements ViewPager.OnPageChangeListener, View.OnClickListener {

    @Bind(R.id.profile_image)
    CircleImageView profile_image;
    @Bind(R.id.tv_selecter_left)
    LinearLayout tv_selecter_left;
    @Bind(R.id.tv_selecter_right)
    LinearLayout tv_selecter_right;
    @Bind(R.id.vp_content)
    ViewPager vp_content;
    @Bind(R.id.tv_home_title1) TextView tv_home_title1;
    @Bind(R.id.tv_home_title2) TextView tv_home_title2;

    private HomePagerAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mAdapter = new HomePagerAdapter(getFragmentManager());
        vp_content.setAdapter(mAdapter);
        vp_content.setOffscreenPageLimit(1);
        vp_content.setOnPageChangeListener(this);
        tv_selecter_left.setOnClickListener(this);
        tv_selecter_right.setOnClickListener(this);
        vp_content.setCurrentItem(0);
        selectItem(0);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        selectItem(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View view) {
        int vId = view.getId();
        if (vId == R.id.tv_selecter_left) {
            vp_content.setCurrentItem(0);
            selectItem(0);
        }
        if (vId == R.id.tv_selecter_right) {
            vp_content.setCurrentItem(1);
            selectItem(1);
        }
    }

    private void selectItem(int position){
        tv_selecter_left.setSelected(position==0?true:false);
        tv_selecter_right.setSelected(position==0?false:true);
        tv_home_title1.setSelected(position==0?true:false);
        tv_home_title2.setSelected(position==0?false:true);
    }
}

package shellljx.gmail.com.app.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.Hashtable;
import java.util.Map;

import shellljx.gmail.com.app.fragment.main.FirstFragment;
import shellljx.gmail.com.app.fragment.main.FourFragment;
import shellljx.gmail.com.app.fragment.main.HomeFragment;
import shellljx.gmail.com.app.fragment.main.SecondFragment;
import shellljx.gmail.com.app.fragment.main.ThirdFragment;

/**
 * Created by shell on 15-9-7.
 */
public class MainPagerAdapter extends FragmentStatePagerAdapter {

    private static final int COUNT = 4;

    public MainPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if(position==0){
            return new HomeFragment();
        }else if(position==1){
            return new SecondFragment();
        }else if(position==2){
            return new ThirdFragment();
        }else {
            return new FourFragment();
        }
    }

    @Override
    public int getCount() {
        return COUNT;
    }
}

package com.dpizarro.uipicker.library.picker;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.dpizarro.uipicker.library.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by shell on 16-1-3.
 */
public class PlacePickerUI extends RelativeLayout {

    private static final String LOG_TAG = PlacePickerUI.class.getSimpleName();

    private PlacePickerUIItemClickListener mPickerUIListener;
    private PickerUIListView mPickerProvince;
    private PickerUIListView mPickerCity;
    private PickerUIListView mPickerCounty;

    private Context mContext;
    private List<String> provinceList = new ArrayList<>();
    private HashMap<String ,List<String>> cityMap = new HashMap<>();
    private HashMap<String,List<String>> countyMap = new HashMap<>();
    private String currentProvinceName;
    private String currentCityName;
    private String currentCountyName;

    private int mColorTextCenterListView,mColorTextNoCenterListView;

    public PlacePickerUI(Context context) {
        super(context);
        mContext = context;
        if (isInEditMode()) {
            createEditModeView();
        } else {
            createView(null);
        }
    }

    public PlacePickerUI(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        if (isInEditMode()) {
            createEditModeView();
        } else {
            createView(attrs);
            getAttributes(attrs);
        }
    }

    public PlacePickerUI(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        if (isInEditMode()) {
            //如果在自定义控件绘制相关地方使用系统依赖的代码，会导致可视化编辑器提升错误
            //用isInEditMode()方法做判断
            createEditModeView();
        } else {
            createView(attrs);
            getAttributes(attrs);
        }
    }

    /**
     * This method inflates the panel to be visible from Preview Layout
     */
    private void createEditModeView() {
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.place_picker_ui, this, true);
    }

    private void createView(AttributeSet attrs) {
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.place_picker_ui, this, true);
        mPickerProvince = (PickerUIListView) view.findViewById(R.id.province_picker);
        mPickerCity = (PickerUIListView) view.findViewById(R.id.city_picker);
        mPickerCounty = (PickerUIListView) view.findViewById(R.id.county_picker);
        setItemsClickables(true);
    }

    /**
     * Method to enable the click of items
     *
     * @param itemsClickables the behaviour selected for items
     */
    public void setItemsClickables(boolean itemsClickables) {
        if (mPickerProvince != null && mPickerProvince.getPickerUIAdapter() != null) {
            mPickerProvince.getPickerUIAdapter().setItemsClickables(itemsClickables);
        }

        if (mPickerCity != null && mPickerCity.getPickerUIAdapter() != null) {
            mPickerCity.getPickerUIAdapter().setItemsClickables(itemsClickables);
        }

        if (mPickerCounty != null && mPickerCounty.getPickerUIAdapter() != null) {
            mPickerCounty.getPickerUIAdapter().setItemsClickables(itemsClickables);
        }
    }

    /**
     * Retrieve styles attributes
     */
    private void getAttributes(AttributeSet attrs) {
        mColorTextCenterListView = R.color.text_center_pickerui;
        mColorTextNoCenterListView = R.color.text_no_center_pickerui;
    }

    /**
     * Method to set items to show in panel.
     * In this method, by default, the 'which' is 0 and the position is the half of the elements.
     */
    public void setItems(Context context, List<Province> data) {
        if (data!=null) {
            for (Province province : data){
                provinceList.add(province.getName());
                List<String> citylist = new ArrayList<>();
                for (Province.City city:province.getCities()){
                    citylist.add(city.getName());
                    countyMap.put(city.getName(),city.getArea());
                }
                cityMap.put(province.getName(),citylist);
            }
            setProvince(context, provinceList, 0, provinceList.size()/2);
            currentProvinceName = provinceList.get(provinceList.size()/2);
            setCity(context, cityMap.get(currentProvinceName), 1, cityMap.get(currentProvinceName).size()/2);
            currentCityName = cityMap.get(currentProvinceName).get(0);
            setCounty(context, countyMap.get(currentCityName), 2, countyMap.get(currentCityName).size()/2);
        }
    }

    /**
     * Method to set items to show in panel.
     *
     * @param context  {@link PickerUIListView} needs a context
     * @param items    elements to show in panel
     * @param which    id of the element has been clicked（第几列）
     * @param position the position to set in the center of the panel.
     */
    public void setProvince(Context context, List<String> items, int which, int position) {
        if (items != null) {
            mPickerProvince.setItems(context, items, which, position, true);
            setTextColorsListView();
        }
    }

    public void setCity(Context context, List<String> items, int which, int position) {
        if (items != null) {
            mPickerCity.setItems(context, items, which, position, true);
            setTextColorsListView();
        }
    }

    public void setCounty(Context context, List<String> items, int which, int position) {
        if (items != null) {
            mPickerCounty.setItems(context, items, which, position, true);
            setTextColorsListView();
        }
    }

    private void setTextColorsListView() {
        setColor(mColorTextCenterListView,mColorTextNoCenterListView);
    }

    /**
     * Sets the text color for the item of the center.
     *
     */
    public void setColor(int colorCenter,int colorNoCenter) {
        if (mPickerProvince != null && mPickerProvince.getPickerUIAdapter() != null
                && mPickerCity != null && mPickerCity.getPickerUIAdapter() != null
                && mPickerCounty != null && mPickerCounty.getPickerUIAdapter() != null) {

            mPickerProvince.getPickerUIAdapter().setColorTextCenter(colorCenter);
            mPickerCity.getPickerUIAdapter().setColorTextCenter(colorCenter);
            mPickerCounty.getPickerUIAdapter().setColorTextCenter(colorCenter);

            mPickerProvince.getPickerUIAdapter().setColorTextNoCenter(colorNoCenter);
            mPickerCity.getPickerUIAdapter().setColorTextNoCenter(colorNoCenter);
            mPickerCounty.getPickerUIAdapter().setColorTextNoCenter(colorNoCenter);
        }
    }

    public void bindData(Context context,List<Province> data){
        setItems(context,data);
        setItemsClickables(true);
    }


    /**
     * Set a callback listener for the item click.
     *
     * @param listener Callback instance.
     */
    public void setOnClickItemPickerUIListener(final PlacePickerUIItemClickListener listener) {
        this.mPickerUIListener = listener;

        mPickerProvince.setOnClickItemPickerUIListener(pickerUIItemClickListener);
        mPickerCounty.setOnClickItemPickerUIListener(pickerUIItemClickListener);
        mPickerCity.setOnClickItemPickerUIListener(pickerUIItemClickListener);
    }

    //改变子集
    private PickerUIListView.PickerUIItemClickListener pickerUIItemClickListener = new PickerUIListView.PickerUIItemClickListener() {
        @Override
        public void onItemClickItemPickerUI(int which, int position,
                                            String valueResult) {

            if (mPickerUIListener == null) {
                throw new IllegalStateException(
                        "You must assign a valid PlacePickerUI.PickerUIItemClickListener first!");
            }
            mPickerUIListener.onItemClickPickerUI(which, position, valueResult);
            if (which==0){
                updateCity(valueResult);
            }else if (which == 1){
                updateCounty(valueResult);
            }
        }
    };

    private void updateCity(String province){
        setCity(mContext,cityMap.get(province),1,cityMap.get(province).size()/2);
        mPickerCity.setNewPositionCenter(cityMap.get(province).size()/2+2);
    }

    private void updateCounty(String city){
        setCounty(mContext,countyMap.get(city),2,countyMap.get(city).size()/2);
        mPickerCounty.setNewPositionCenter(countyMap.get(city).size()/2+2);
    }

    /**
     * Interface for a callback when the item has been clicked.
     */
    public interface PlacePickerUIItemClickListener {

        /**
         * Callback when the item has been clicked.
         *
         * @param which       id of the element has been clicked
         * @param position    Position of the current item.
         * @param valueResult Value of text of the current item.
         */
        public void onItemClickPickerUI(int which, int position, String valueResult);
    }
}

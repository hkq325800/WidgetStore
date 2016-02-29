package com.iue.pocketdoc.common.service;

import java.util.List;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.CityInfo;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.iue.pocketdoc.global.IUEApplication;

public class PoiSearchService implements OnGetPoiSearchResultListener {

	private PoiSearch mPoiSearch = null;
	private PoiSearchSerivceListener mPoiSearchSerivceListener;
	private String keyWords, city;
//	private int resultSize = 0;
//	private int citySize = 0;
	private boolean isFirst = true;

	private void initPoiSearch() {
		mPoiSearch = PoiSearch.newInstance();
		mPoiSearch.setOnGetPoiSearchResultListener(this);
	}

	/**
	 * 上传combox的文本内容，根据内容搜索类似地址
	 * 
	 * @param city
	 * @param keyWords
	 */

	public void searchPoi(LatLng lat, String keyWords) {
		this.keyWords = keyWords;
		initPoiSearch();

		PoiNearbySearchOption optionNear = new PoiNearbySearchOption();
		optionNear.keyword(keyWords);
		optionNear.location(lat);
		optionNear.pageNum(0);
		optionNear.pageCapacity(10);
		mPoiSearch.searchNearby(optionNear);

	}

	public void searchPoi(String city) {
		initPoiSearch();
		PoiCitySearchOption optionCity = new PoiCitySearchOption();
		optionCity.keyword(keyWords);
		optionCity.city(city);
		optionCity.pageNum(0);
		optionCity.pageCapacity(10);
		mPoiSearch.searchInCity(optionCity);
	}

	public void setPoiSearchSerivceListener(
			PoiSearchSerivceListener mPoiSearchSerivceListener) {
		this.mPoiSearchSerivceListener = mPoiSearchSerivceListener;
	}

	public interface PoiSearchSerivceListener {
		void poiSearchResult(List<PoiInfo> result);
	}

	@Override
	public void onGetPoiResult(PoiResult result) {
		
		if (result == null
				|| result.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {// 先默认全国搜索不会为空
			if (isFirst) {
				isFirst = false;
				searchPoi(IUEApplication.province);
				return;
			} else {
				isFirst = true;
				return;
			}
		}
		if (result.error == SearchResult.ERRORNO.AMBIGUOUS_KEYWORD) {
			// 当输入关键字在本市没有找到，但在其他城市找到时，返回包含该关键字信息的城市列表
			String strInfo = "在";
		//	citySize = result.getSuggestCityList().size();
			for (CityInfo cityInfo : result.getSuggestCityList()) {
				strInfo += cityInfo.city;
				strInfo += ",";
			}
			city = result.getSuggestCityList().get(0).city;
			strInfo += "找到结果";
			searchPoi(city);// 进行第一个城市内搜索
		} else {
			if (result.getAllPoi().size() != 0) {
				isFirst = true;
			//	resultSize = result.getAllPoi().size();
				mPoiSearchSerivceListener.poiSearchResult(result.getAllPoi());
			}
		}
	}

	@Override
	public void onGetPoiDetailResult(PoiDetailResult arg0) {
		

	}

}

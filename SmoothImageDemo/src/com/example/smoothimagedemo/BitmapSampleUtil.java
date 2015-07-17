package com.example.smoothimagedemo;

import java.util.Random;

public class BitmapSampleUtil {

	public static String[] IMAGES = new String[] { "http://p2008.zbjimg.com/task/2009-08/05/121253/s78zlmih.jpg",
			"http://img7.9158.com/200709/01/11/53/200709018758949.jpg", "http://t11.baidu.com/it/u=1172002465,3333042393&fm=59",
			"http://imgt4.bdstatic.com/it/u=3458459038,2718284416&fm=23&gp=0.jpg",
			"http://a1.att.hudong.com/39/46/01200000012881116174646886639.jpg",
			"http://image.rayliimg.cn/0004/2009-05-21/images/2009521114737437.jpg",
			"http://www.chinajianshen.com/upimg/allimg/070312/1131311M03.jpg", "http://www.176web.net/upload/picture/201209/s/04114912.jpg",
			"http://photo.scol.com.cn/hdp/img/attachement/jpg/site2/20140422/00219b7b064914c05a234b.jpg", };

	/**
	 * 随机产生的一个图片Url
	 * 
	 * @return
	 */
	public static String getBmpUrl() {
		int index = new Random().nextInt(IMAGES.length);
		return IMAGES[index];
	}
}

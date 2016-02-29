package com.iue.pocketdoc.utilities;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.iue.pocketdoc.visitscheduling.widget.CalendarUtil;

@SuppressLint("SimpleDateFormat")
public class DateUtil {

	/**
	 * 与现在时间比较
	 * 
	 * @param str
	 * @return
	 */
	public static boolean getCompare(String firstdata, String seconddata,
			String matchstr) {
		if (stringToLong(firstdata, matchstr) >= stringToLong(seconddata,
				matchstr)) {
			return true;
		}
		return false;
	}

	/**
	 * 得到你所需要现在时间的字符串
	 * 
	 * @return
	 */
	public static String getNowDate(String matchstr) {
		SimpleDateFormat formatter = new SimpleDateFormat(matchstr);
		Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
		return formatter.format(curDate);
	}

	/**
	 * long to string
	 * 
	 * @return
	 */
	public static String longtoString(Long time, String matchstr) {
		SimpleDateFormat formatter = new SimpleDateFormat(matchstr);
		Date curDate = new Date(time);// 获取当前时间
		return formatter.format(curDate);
	}

	public static String datetoString(Date date, String matchstr) {
		if (date != null) {
			SimpleDateFormat formatter = new SimpleDateFormat(matchstr);
			// 获取当前时间
			return formatter.format(date);
		} else {
			return "";
		}
	}

	/**
	 * 根据输入的格式把数据转化Long
	 * 
	 * @param dateString
	 * @param matchstr
	 * @return
	 */
	public static Date stringToDate(String dateString, String matchstr) {
		ParsePosition position = new ParsePosition(0);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(matchstr);
		Date dateValue = simpleDateFormat.parse(dateString, position);
		return dateValue;
	}

	public static String stringtoString(String datestring, String matchstr,
			String matchstr2) {
		ParsePosition position = new ParsePosition(0);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(matchstr);
		Date dateValue = simpleDateFormat.parse(datestring, position);
		SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat(matchstr2);
		return simpleDateFormat2.format(dateValue);
	}

	public static String intervalDay(String startTime, String endTime,
			String match) {
		SimpleDateFormat format = new SimpleDateFormat(match);
		Date startDate = null;
		Date endDate = null;
		try {
			startDate = format.parse(startTime);
			endDate = format.parse(endTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		// Logger.i(startTime, endTime);
		long delta = endDate.getTime() - startDate.getTime();
		long days = toDays(delta);
		if (days > 0) {
			return days + "天后";
		} else {
			if (days == 0) {
				return "当天";
			} else {
				return (days <= 0 ? Math.abs(days) : days) + "天前";
			}
		}

	}

	/**
	 * 根据输入的格式把数据转化Date
	 * 
	 * @param dateString
	 * @param matchstr
	 * @return
	 */
	public static Long stringToLong(String dateString, String matchstr) {
		ParsePosition position = new ParsePosition(0);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(matchstr);
		Date dateValue = simpleDateFormat.parse(dateString, position);
		return dateValue.getTime();
	}

	/********************** 获取时间差 ***************/
	public static String subData(String startTime, String endTime, int type) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date startDate = null;
		Date endDate = null;
		try {
			startDate = format.parse(startTime);
			endDate = format.parse(endTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		// Logger.i(startTime, endTime);
		long delta = endDate.getTime() - startDate.getTime();
		long days = toDays(delta);
		long months = toMonths(delta);

		if (type == 1) {
			return (days <= 0 ? 1 : days) + "天";
		} else {
			return (months <= 0 ? 0 : months) + "月";
		}

	}

	/************** 获取几秒前，几分前，等转化 ******************/

	private static final long ONE_MINUTE = 60000L;
	private static final long ONE_HOUR = 3600000L;
	private static final long ONE_DAY = 86400000L;
	// private static final long ONE_WEEK = 604800000L;
	private static final String ONE_SECOND_AGO = "秒前";
	private static final String ONE_MINUTE_AGO = "分钟前";
	private static final String ONE_HOUR_AGO = "小时前";
	private static final String ONE_DAY_AGO = "天前";

	// 根据条件将获取星期几，秒，分，几月几日（只针对matchstr格式为"yyyy-MM-dd HH:mm:ss"）
	public static String format(String strdate, String matchstr) {
		Date date = null;
		if (strdate == "" || strdate == null) {
			return "";
		}
		date = stringToDate(strdate, matchstr);
		long delta = new Date().getTime() - date.getTime();
		if (delta < 1L * ONE_MINUTE) {
			long seconds = toSeconds(delta);
			return (seconds <= 0 ? 1 : seconds) + ONE_SECOND_AGO;
		}
		if (delta < 60L * ONE_MINUTE) {
			long minutes = toMinutes(delta);
			return (minutes <= 0 ? 1 : minutes) + ONE_MINUTE_AGO;
		}
		if (delta < 24L * ONE_HOUR) {
			long hours = toHours(delta);
			return (hours <= 0 ? 1 : hours) + ONE_HOUR_AGO;
		}
		if (delta < 7L * ONE_DAY) {
			long days = toDays(delta);
			return (days <= 0 ? 1 : days) + ONE_DAY_AGO;
		} else {
			String[] date1 = strdate.split(" ");
			String[] date2 = date1[0].split("-");
			if (!strdate.substring(0, 4).equals(
					getNowDate("yyyy-mm-dd").substring(0, 4))) {
				return strdate.substring(0, 4) + "-" + date2[1] + "-"
						+ date2[2];
			}
			return date2[1] + "-" + date2[2];
		}
	}

	public static String JuLiGaoHao(String strdate, String matchstr) {
		Date date = null;
		if (strdate == "" || strdate == null) {
			return "";
		}
		date = stringToDate(strdate, matchstr);
		long delta = date.getTime() - new Date().getTime();
		if (delta < 1L * ONE_MINUTE) {
			long seconds = toSeconds(delta);
			return (seconds <= 0 ? 1 : seconds) + "秒";
		}
		if (delta < 60L * ONE_MINUTE) {
			long minutes = toMinutes(delta);
			return (minutes <= 0 ? 1 : minutes) + "分钟";
		}
		if (delta < 24L * ONE_HOUR) {
			long hours = toHours(delta);
			return (hours <= 0 ? 1 : hours) + "小时";
		}
		if (delta < 14L * ONE_DAY) {
			long days = toDays(delta);
			return (days <= 0 ? 1 : days) + "天";
		}
		return "";
	}

	private static long toSeconds(long date) {
		return date / 1000L;
	}

	private static long toMinutes(long date) {
		return toSeconds(date) / 60L;
	}

	private static long toHours(long date) {
		return toMinutes(date) / 60L;
	}

	private static long toDays(long date) {
		return toHours(date) / 24L;
	}

	private static long toMonths(long date) {
		return toDays(date) / 30L;
	}

	public static Calendar getCalender(String date, String match) {

		Calendar calendar = Calendar.getInstance();
		if (TextUtil.isValidate(date)) {
			calendar.setTime(stringToDate(date, match));
		}
		return calendar;

	}

	@SuppressWarnings("deprecation")
	public static Date[] getSevenDate(Date date) {
		Date[] dates = new Date[7];
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		for (int i = 0; i < dates.length; i++) {
		//	int s = calendar.get(Calendar.YEAR);
			dates[i] = new Date(calendar.get(Calendar.YEAR) - 1900,
					calendar.get(Calendar.MONTH),
					calendar.get(Calendar.DAY_OF_MONTH));

			calendar.add(Calendar.DAY_OF_MONTH, 1);

		}
		return dates;
	}

	public static String getWeekString(int week) {
		switch (week) {
		case 1:
			return "周日";
		case 2:
			return "周一";
		case 3:
			return "周二";
		case 4:
			return "周三";
		case 5:
			return "周四";
		case 6:
			return "周五";
		case 7:
			return "周六";
		}
		return "";
	}

	public static String getMonthString(int month) {
		switch (month) {
		case 0:
			return "一月";
		case 1:
			return "二月";
		case 2:
			return "三月";
		case 3:
			return "四月";
		case 4:
			return "五月";
		case 5:
			return "六月";
		case 6:
			return "七月";
		case 7:
			return "八月";
		case 8:
			return "九月";
		case 9:
			return "十月";
		case 10:
			return "十一月";
		case 11:
			return "十二月";
		}
		return "";
	}

	public static int getNeededPageNum() {
		int pageNum = 0;
		Calendar cNow = Calendar.getInstance();
		// 今天月份 0是一月
		//int monthNow = cNow.get(Calendar.MONTH);
		// 今天日期
		int day_of_month = cNow.get(Calendar.DAY_OF_MONTH);
		// 今天周几 1是周日7是周六
		int day_of_week = cNow.get(Calendar.DAY_OF_WEEK);
		if(day_of_week!=1)
			pageNum++;
		// 本月总天数
		int sumMonthNow = CalendarUtil.getMonthDays(cNow.get(Calendar.YEAR),
				cNow.get(Calendar.MONTH) + 1);
		// 本月剩余天数
		int sumRemainDays = sumMonthNow - day_of_month;
		// 本月页数 与今天周数有关
		// int neededPageNumNow = sumRemainDays / 7 + 1;

		Calendar cNext = Calendar.getInstance();
		cNext.set(Calendar.MONTH, cNext.get(Calendar.MONTH) + 1);
		// 下个月份
		//int monthNext = cNext.get(Calendar.MONTH);
		// 下月总天数
		int sumMonthNext = CalendarUtil.getMonthDays(cNext.get(Calendar.YEAR),
				cNext.get(Calendar.MONTH) + 1);
		// 下月页数
		pageNum += (sumRemainDays + sumMonthNext + 1) / 7;
		if ((sumRemainDays + sumMonthNext + 1) % 7 != 0)
			pageNum++;
		return pageNum;
	}
}

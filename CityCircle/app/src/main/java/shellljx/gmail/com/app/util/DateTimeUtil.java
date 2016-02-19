package shellljx.gmail.com.app.util;

import android.text.TextUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2015/6/15.
 */
public class DateTimeUtil {
    public static final String LOCAL_SHORT_DATE_FORMAT = "yyyy-MM-dd";
    public static final String LOCAL_LONG_DATE_FORMAT  = "yyyy-MM-dd HH:mm:ss";
    public static final String STANDARD_LONG_DATE_FORMAT  = "yyyy/MM/dd HH:mm:ss";
    public static final String STANDARD_SHORT_DATE_FORMAT = "yyyy/MM/dd";

    public static String getFormatString(Date date,String format){
        if(date==null|| TextUtils.isEmpty(format)){
            return null;
        }
        return new SimpleDateFormat(format).format(date);
    }

    public static String getShortFormat(Date date){
        if(date==null){
            return null;
        }
        return getFormatString(date,LOCAL_SHORT_DATE_FORMAT);
    }
}

package cn.unis.gmweb.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

	public static final String YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
	public static final String YYYY_MM_DD_HH = "yyyy-MM-dd HH";
	public static final String YYYY_MM_DD = "yyyy-MM-dd";
	public static final String YYYY_MM_DD_HH_MM_SS="yyyy-MM-dd HH:mm:ss";
	public static final String YYYY_MM_DD_HH_MM_SS_SSS="yyyy-MM-dd HH:mm:ss SSS";
	/**
	 * 获取一小时前的分级串
	 * @param datestr
	 * @return
	 * @throws ParseException
	 */
	public static String getnumHourDate(String datestr) throws ParseException {

		SimpleDateFormat format = new SimpleDateFormat(YYYY_MM_DD_HH_MM);
		Date parseDate = format.parse(datestr);
		Calendar ca = Calendar.getInstance();
		ca.setTime(parseDate);
		ca.add(Calendar.HOUR, -1);
		String addHourDate = format.format(ca.getTime());
		return addHourDate;
	}

	// 日期串（年月日时）转换为日期对象
	public static Date getDate(String datestr) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat(YYYY_MM_DD_HH);
		return format.parse(datestr);
	}

	// 日期对象转日期串（年月日时）
	public static String getstrDate(Date date) {
		SimpleDateFormat format = new SimpleDateFormat(YYYY_MM_DD_HH);
		return format.format(date);
	}

	// 日期串（年月日）转换为时间戳
	public static long getDayStamp(String datestr) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat(YYYY_MM_DD);
		return format.parse(datestr).getTime();
	}
	// 日期串（年月日时）转换为时间戳
	public static long getHourStamp(String datestr) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat(YYYY_MM_DD_HH);
		return format.parse(datestr).getTime();
	}
	// 日期串（年月日时分）转换为时间戳
	public static long getMinuteStamp(String datestr) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat(YYYY_MM_DD_HH_MM);
		return format.parse(datestr).getTime();
	}

	// 时间戳转换为（年月日时）时间格式
	public static String getHourDate(long tm) {
		SimpleDateFormat format = new SimpleDateFormat(YYYY_MM_DD_HH);
		return format.format(new Date(tm));
	}
	// 时间戳转换为（年月日时分）时间格式
	public static String getMinuteDate(long tm) {
		SimpleDateFormat format = new SimpleDateFormat(YYYY_MM_DD_HH_MM);
		return format.format(new Date(tm));
	}

	// 获取上一小时时间（年月日时）
	@SuppressWarnings("deprecation")
	public static String getlasthourDate(int hour) {
		Date mydate = new Date();
		SimpleDateFormat format = new SimpleDateFormat(YYYY_MM_DD_HH);
		mydate.setHours(mydate.getHours() - hour);
		String lasttime = format.format(mydate);
		return lasttime;
	}
	// 获取上一天时间串（年月日）
	@SuppressWarnings("deprecation")
	public static String getlastdayDate(int day) {
		Date mydate = new Date();
		SimpleDateFormat format = new SimpleDateFormat(YYYY_MM_DD);
		mydate.setDate(mydate.getDate() - day);
		String lasttime = format.format(mydate);
		return lasttime;
	}
	/**
	 * 字符串转为秒级时间戳
	 * @param DateTime
	 * @return
	 */
	public static Long apply(String DateTime){
        Long result=0L;
        DateFormat dateFormat=new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
        try {
            Date data = dateFormat.parse(DateTime);
            result=data.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }
	/**
	 * 获取当前时间秒级字符串
	 * @return
	 */
    public static String getTime(){
        SimpleDateFormat df = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
        String time =df.format(new Date());
        return time;
    }
    /**
     * 获取上一分钟秒级字符串
     * @param time
     * @return
     */
    public static String getLastTime(String time){
        Date date = null;
        SimpleDateFormat sd = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
        try {
            date = sd.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long rightTime = (long) (date.getTime() -  60 * 1000);
        String newtime = sd.format(rightTime);//把得到的新的时间戳再次格式化成时间的格式
        return newtime;
    }
    /**
     * 获取20分钟前秒级字符串
     * @param time
     * @param day
     * @return
     */
    public static String getTwenty(String time,int day){
        Date date = null;
        SimpleDateFormat sd = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
        try {
            date = sd.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long rightTime = (long) (date.getTime() -  day*60 * 1000);
        String newtime = sd.format(rightTime);//把得到的新的时间戳再次格式化成时间的格式
        return newtime;
    }
    /**
     * 获取24小时前秒级字符串
     * @param time
     * @return
     */
    public static String getToDay(String time){
        Date date = null;
        SimpleDateFormat sd = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
        try {
            date = sd.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long rightTime = (long) (date.getTime() -  24*60*60 * 1000);
        String newtime = sd.format(rightTime);//把得到的新的时间戳再次格式化成时间的格式
        return newtime;
    }


}

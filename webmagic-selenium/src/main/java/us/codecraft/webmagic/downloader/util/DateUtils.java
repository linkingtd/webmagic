package us.codecraft.webmagic.downloader.util;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.Interval;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.google.common.collect.Lists;

public class DateUtils extends org.apache.commons.lang3.time.DateUtils {

	public final static String defaultPattern = "yyyy-MM-dd";
	public final static String dateTimePattern = "yyyy-MM-dd HH:mm";
	private final static String dateMonthHourPattern = "M月d日HH:mm";
	public final static String dateMonthPattern = "yyyy年MM月dd日";
	public final static String dateTimeSecondPattern = "yyyy-MM-dd HH:mm:ss";
	public final static String dateTimeYNDPattern = "yyyy年MM月dd日HH点mm分";
	public final static String dateTimeYNDHMSPattern = "yyyyMMddHHmmss";
	public final static String dateYNDPattern = "yyyyMMdd";
	private final static ThreadLocal<HashMap<String, SimpleDateFormat>> customerMapThread = new ThreadLocal<HashMap<String, SimpleDateFormat>>();
	public static final DateTimeFormatter SHORT_FORMATTER = DateTimeFormat.forPattern("yyyy-MM-dd");
	public static final DateTimeFormatter LONG_FORMATTER = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm");
	public static final DateTimeFormatter SHORT_CHINA_FORMATTER = DateTimeFormat.forPattern("yyyy年MM月dd日");

	/**
	 * 根据传入字符串 返回yyyy年mm月dd日,月份日期为单位时显示为双位
	 */
	public static String getCurrentDateToCNWhole(String dateTime) {

		String year = dateTime.substring(0, 4);
		String month;
		String day;

		month = dateTime.substring(5, 7);
		day = dateTime.substring(8, 10);
		return year + "年" + month + "月" + day + "日";
	}

	/**
	 * 将日期字符串转换成日期型
	 */
	public static Date dateString2Date(String dateStr) {
		return dateString2Date(dateStr, defaultPattern);
	}

	/**
	 * 将日期字符串转换成指定格式日期
	 */
	public static Date dateString2Date(String dateStr, String partner) {

		try {
			SimpleDateFormat formatter = getSimpleDateFormat(partner);
			ParsePosition pos = new ParsePosition(0);
			return formatter.parse(dateStr, pos);
		} catch (NullPointerException e) {
			return null;
		}
	}

	/**
	 * 获取指定日期的年份
	 *
	 */
	public static int getYearOfDate(java.util.Date p_date) {
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTime(p_date);
		return c.get(java.util.Calendar.YEAR);
	}

	/**
	 * 获取日期字符串的年份
	 *
	 */
	public static int getYearOfDate(String p_date) {
		return getYearOfDate(dateString2Date(p_date));
	}

	/**
	 * 获取指定日期的月份
	 *
	 */
	public static int getMonthOfDate(java.util.Date p_date) {
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTime(p_date);
		return c.get(java.util.Calendar.MONTH) + 1;
	}

	/**
	 * 获取日期字符串的月份
	 *
	 */
	public static int getMonthOfDate(String date) {
		return getMonthOfDate(dateString2Date(date));
	}

	/**
	 * 获取指定日期的日份
	 *
	 */
	public static int getDayOfDate(java.util.Date p_date) {
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTime(p_date);
		return c.get(java.util.Calendar.DAY_OF_MONTH);
	}

	/**
	 * 获取指定日期的周 与 Date .getDay()兼容
	 *
	 */
	public static int getWeekOfDate(String date) {
		java.util.Date p_date = dateString2Date(date);
		return getWeekOfDate(p_date);
	}

	/**
	 * 获取指定日期的周 与 Date .getDay()兼容
	 */
	public static int getWeekOfDate(Date date) {
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTime(date);
		return c.get(java.util.Calendar.DAY_OF_WEEK) - 1;
	}

	/**
	 * 获取指定日期的小时
	 */
	public static int getHourOfDate(java.util.Date p_date) {
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTime(p_date);
		return c.get(java.util.Calendar.HOUR_OF_DAY);
	}

	/**
	 * 获取指定日期的分钟
	 */
	public static int getMinuteOfDate(java.util.Date p_date) {
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTime(p_date);
		return c.get(java.util.Calendar.MINUTE);
	}

	/**
	 * 日期转化指定格式的字符串型日期
	 */
	public static String date2String(java.util.Date p_utilDate, String p_format) {
		String l_result = "";
		if (p_utilDate != null) {
			SimpleDateFormat sdf = getSimpleDateFormat(p_format);
			l_result = sdf.format(p_utilDate);
		}
		return l_result;
	}

	/**
	 * 日期转化指定格式的字符串型日期
	 */
	public static String date2String(java.util.Date p_utilDate) {
		return date2String(p_utilDate, defaultPattern);
	}

	/**
	 * Description: 时间计算(根据时间推算另个日期)
	 *
	 * 日期 类型 y,M,d,h,m,s 年、月、日、时、分、秒 添加值
	 */
	public static Date dateAdd(Date date, String type, int value) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		if (type.toLowerCase().equals("y") || type.toLowerCase().equals("year"))
			c.add(Calendar.YEAR, value);
		else if (type.equals("M") || type.toLowerCase().equals("month"))
			c.add(Calendar.MONTH, value);
		else if (type.toLowerCase().equals("d") || type.toLowerCase().equals("date"))
			c.add(Calendar.DATE, value);
		else if (type.toLowerCase().equals("h") || type.toLowerCase().equals("hour"))
			c.add(Calendar.HOUR, value);
		else if (type.equals("m") || type.toLowerCase().equals("minute"))
			c.add(Calendar.MINUTE, value);
		else if (type.toLowerCase().equals("s") || type.toLowerCase().equals("second"))
			c.add(Calendar.SECOND, value);
		return c.getTime();
	}

	/**
	 */
	public static Date dateAdd2Date(Date date, String type, int value) {
		return dateAdd(date, type, value);
	}

	/**
	 */
	public static Date dateAdd2Date(String dateStr, String type, int value, String pattern) {
		Date date = DateUtils.dateString2Date(dateStr, pattern);
		return dateAdd(date, type, value);

	}

	/**
	 */
	public static Date dateAdd2Date(String dateStr, String type, int value) {
		Date date = DateUtils.dateString2Date(dateStr, DateUtils.defaultPattern);
		return dateAdd(date, type, value);

	}

	/**
	 */
	public static String dateAdd2String(Date date, String type, int value) {
		Date dateD = dateAdd2Date(date, type, value);
		return date2String(dateD);
	}

	/**
	 */
	public static String dateAdd2String(Date date, String type, int value, String pattern) {
		Date dateD = dateAdd2Date(date, type, value);
		return date2String(dateD, pattern);
	}

	/**
	 */
	public static String dateAdd2String(String dateStr, String type, int value, String pattern) {
		Date date = dateAdd2Date(dateStr, type, value, pattern);
		return date2String(date);
	}

	/**
	 */
	public static String dateAdd2String(String dateStr, String type, int value) {
		Date date = dateAdd2Date(dateStr, type, value);
		return date2String(date);
	}

	public static String dateAdd2String(String dateStr, int value) {
		return dateAdd2String(dateStr, "d", value);
	}

	/**
	 */
	public static String dateAdd2String(String dateStr, boolean isAddDay) {
		String returndateStr = dateStr;
		try {
			if (isAddDay) {
				dateStr = dateAdd2String(dateStr, "d", 1);
			}
			Date date = dateString2Date(dateStr);
			int month = getMonthOfDate(date);
			int day = getDayOfDate(date);
			returndateStr = month + "." + day;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return returndateStr;
	}

	/**
	 */
	public static String dateAdd2String(String dateStr) {
		return dateAdd2String(dateStr, false);
	}

	/**
	 */
	public static String dateAdd2PatternString(String dateStr, String type, int value, String pattern) {
		Date date = dateAdd2Date(dateStr, type, value, pattern);
		return date2String(date, pattern);
	}

	/**
	 * 判断是平时还是周末
	 */

	public static boolean checkWeekendDay(String p_date) {
		Calendar c = Calendar.getInstance();
		c.setTime(dateString2Date(p_date));
		int num = c.get(Calendar.DAY_OF_WEEK);

		// 如果为周六 周日则为周末 1星期天 2为星期六
		return num == 6 || num == 7 || num == 1;
	}

	/**
	 * 按时间段计算月份跨度 计算出所包含的月份
	 */
	@SuppressWarnings("static-access")
	public static int[][] getMonthsByTime(String startTime, String endTime) {
		Date st;
		Date et;

		try {
			et = getSimpleDateFormat(defaultPattern).parse(endTime);
			st = getSimpleDateFormat(defaultPattern).parse(startTime);
		} catch (ParseException e) {
			return null;
		}

		Calendar ca1 = Calendar.getInstance();
		Calendar ca2 = Calendar.getInstance();
		ca1.setTime(st);
		ca2.setTime(et);

		int ca1Year = ca1.get(Calendar.YEAR);
		int ca1Month = ca1.get(Calendar.MONTH);

		int ca2Year = ca2.get(Calendar.YEAR);
		int ca2Month = ca2.get(Calendar.MONTH);
		int countMonth;// 这个是用来计算得到有多少个月时间的一个整数,
		if (ca1Year == ca2Year) {
			countMonth = ca2Month - ca1Month;
		} else {
			countMonth = (ca2Year - ca1Year) * 12 + (ca2Month - ca1Month);
		}

		int months[][] = new int[countMonth + 1][2]; // 年月日二维数组

		for (int i = 0; i < countMonth + 1; i++) {
			// 每次在原基础上累加一个月

			months[i][0] = ca1.get(java.util.Calendar.YEAR);
			months[i][1] = ca1.get(java.util.Calendar.MONTH);
			months[i][1] += 1;
			ca1.add(ca1.MONTH, 1);
		}

		return months;
	}

	/**
	 * yyyy-MM-dd HH:mm 格式日期 转化 为 M月d日HH:mm 格式日期
	 *
	 */
	public static String string2String(String date) throws ParseException {
		return date2String(dateString2Date(date, dateTimePattern), dateMonthHourPattern);
	}

	/**
	 *
	 */
	public static String string2String(String date, String pattern) throws ParseException {
		return date2String(dateString2Date(date), pattern);
	}

	/**
	 * 得到两个时间差
	 *
	 */
	public static long getDateDiff(String startTime, String toTime, String pattern) {
		long diff = getDateDiffLong(startTime, toTime, pattern);
		diff = diff / 1000 / 60;
		return diff;
	}

	/**
	 */
	public static long getDateDiffLong(String startTime, String toTime, String pattern) {
		long diff = 0l;
		if (StringUtils.isNotBlank(startTime) && StringUtils.isNotBlank(toTime)) {
			SimpleDateFormat format = getSimpleDateFormat(pattern);
			ParsePosition pos = new ParsePosition(0);
			Date startTimeD = format.parse(startTime, pos);
			pos.setIndex(0);
			Date toTimeD = format.parse(toTime, pos);
			if (startTimeD != null && toTimeD != null) {
				diff = startTimeD.getTime() - toTimeD.getTime();
			}
		}
		return diff;
	}

	/**
	 * 得到两个时间差
	 */
	public static long getDateDiff(String startTime, String toTime) {
		return getDateDiff(startTime, toTime, dateTimePattern);
	}

	/**
	 * 得到两个时间差
	 *
	 */
	public static long getDateDiff(Date startTimeD, String toTime, String pattern) {
		long diff;
		Date toTimeD = dateString2Date(toTime, pattern);
		diff = startTimeD.getTime() - toTimeD.getTime();
		return diff;
	}

	/**
	 *
	 */
	public static Integer getMinuteTotal(String hour, String minute) {
		return getMinuteTotal(Integer.parseInt(hour), Integer.parseInt(minute));
	}

	/**
	 */
	public static Integer getMinuteTotal(Integer hour, Integer minute) {
		return hour * 60 + minute;
	}

	/**
	 */
	public static String[] getallyearMonth(Date leaseTime, int leaseDays) {
		List<String> yearList = new ArrayList<String>();
		List<String> monthList = new ArrayList<String>();
		String yearString;
		String monthString;
		StringBuilder dateString = new StringBuilder();
		StringBuilder sBuffer = new StringBuilder();
		String[] returnResult = new String[3];
		for (int i = 0; i < leaseDays; i++) {
			String correctDate = DateUtils.dateAdd2String(leaseTime, "d", i);
			String year = correctDate.split("-")[0];
			String month = correctDate.split("-")[1];
			if (!yearList.contains(year))
				yearList.add(year);
			if (!monthList.contains(month))
				monthList.add(month);
			if (i == leaseDays - 1)
				dateString.append(correctDate);
			else
				dateString.append(correctDate).append(",");

		}
		for (String month : monthList) {
			sBuffer.append(month).append(",");
		}
		monthString = sBuffer.toString();
		sBuffer.delete(0, sBuffer.length());
		for (String year : yearList) {
			sBuffer.append(year).append(",");
		}
		yearString = sBuffer.toString();
		if (monthString.lastIndexOf(",") == monthString.length() - 1)
			monthString = monthString.substring(0, monthString.length() - 1);
		if (yearString.lastIndexOf(",") == yearString.length() - 1)
			yearString = yearString.substring(0, yearString.length() - 1);
		returnResult[0] = yearString;
		returnResult[1] = monthString;
		returnResult[2] = dateString.toString();
		return returnResult;
	}

	private static SimpleDateFormat getSimpleDateFormat(String pattern) {
		SimpleDateFormat simpleDateFormat;
		HashMap<String, SimpleDateFormat> simpleDateFormatMap = customerMapThread.get();
		if (simpleDateFormatMap != null && simpleDateFormatMap.containsKey(pattern)) {
			simpleDateFormat = simpleDateFormatMap.get(pattern);
		} else {
			simpleDateFormat = new SimpleDateFormat(pattern);
			if (simpleDateFormatMap == null) {
				simpleDateFormatMap = new HashMap<String, SimpleDateFormat>();
			}
			simpleDateFormatMap.put(pattern, simpleDateFormat);
			customerMapThread.set(simpleDateFormatMap);
		}

		return simpleDateFormat;
	}

	public static List<String> getRightYearMonth(String[] year, String[] month, String fromDate, String toDate) {
		List<String> dayArray = getRightDay(year, month, fromDate, toDate);
		if (dayArray != null && dayArray.size() > 0) {
			for (int i = 0, len = dayArray.size(); i < len; i++) {
				dayArray.set(i, dayArray.get(i).substring(0, 7));
			}
		}
		return dayArray;
	}

	private static List<String> getRightDay(String[] years, String[] months, String fromDate, String toDate) {
		DateTime from = new DateTime(Integer.parseInt(fromDate.substring(0, 4)),
				Integer.parseInt(fromDate.substring(5, 7)), 1, 0, 0);
		DateTime to = new DateTime(Integer.parseInt(toDate.substring(0, 4)), Integer.parseInt(toDate.substring(5, 7)),
				2, 0, 0);
		Interval interval = new Interval(from, to);
		List<String> list = Lists.newArrayList();
		DateTime tmp;
		for (String year : years) {
			for (String month : months) {
				tmp = new DateTime(Integer.parseInt(year), Integer.parseInt(month), 1, 0, 0);
				if (interval.contains(tmp)) {
					list.add(SHORT_FORMATTER.print(tmp));
				}
			}
		}

		return list;
	}

	public static Integer getTermMaxLeng(String[] year, String[] month, String fromDate, String toDate) {
		List<String> dayArray = getRightDay(year, month, fromDate, toDate);
		Integer size = 0;
		if (dayArray != null) {
			size = dayArray.size();
		}
		return size;
	}

	/**
	 * 获得指定日期及指定天数之内的所有日期列表
	 *
	 * 指定日期 格式:yyyy-MM-dd 取指定日期后的count天
	 */
	public static Vector<String> getDatePeriodDay(String pDate, int count) throws ParseException {
		Vector<String> v = new Vector<String>();

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(DateUtils.dateString2Date(pDate));
		v.add(DateUtils.date2String(calendar.getTime()));

		for (int i = 0; i < count - 1; i++) {
			calendar.add(Calendar.DATE, 1);
			v.add(DateUtils.date2String(calendar.getTime()));
		}

		return v;
	}

	/**
	 * 获得指定日期内的所有日期列表
	 *
	 * 指定开始日期 格式:yyyy-MM-dd 指定开始日期 格式:yyyy-MM-dd
	 */
	public static String[] getDatePeriodDay(String sDate, String eDate) throws ParseException {
		if (dateCompare(sDate, eDate)) {
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		Calendar calendar_ = Calendar.getInstance();
		calendar.setTime(DateUtils.dateString2Date(sDate));
		long l1 = calendar.getTimeInMillis();
		calendar_.setTime(DateUtils.dateString2Date(eDate));
		long l2 = calendar_.getTimeInMillis();
		// 计算天数
		long days = (l2 - l1) / (24 * 60 * 60 * 1000) + 1;

		String[] dates = new String[(int) days];
		dates[0] = (DateUtils.date2String(calendar.getTime()));
		for (int i = 1; i < days; i++) {
			calendar.add(Calendar.DATE, 1);
			dates[i] = (DateUtils.date2String(calendar.getTime()));
		}
		return dates;
	}

	/**
	 * 比较日期大小
	 *
	 */

	public static boolean dateCompare(String compareDate, String toCompareDate) {
		boolean comResult = false;
		Date comDate = DateUtils.dateString2Date(compareDate);
		Date toComDate = DateUtils.dateString2Date(toCompareDate);

		if (comDate.after(toComDate)) {
			comResult = true;
		}

		return comResult;
	}

	/**
	 * Description: 判断字符串是否日期格式(yyyy-MM-dd 或者 yyyy-MM-dd HH:mm)
	 */
	public static boolean isDateFormat(String time) {
		boolean isDate = true;
		if (StringUtils.isNotBlank(time)) {
			SimpleDateFormat format = getSimpleDateFormat(defaultPattern);
			ParsePosition pos = new ParsePosition(0);
			java.util.Date timeD = format.parse(time, pos);
			if (timeD == null) {
				format = getSimpleDateFormat(dateTimePattern);
				pos.setIndex(0);
				timeD = format.parse(time, pos);
				if (timeD == null) {
					isDate = false;
				}
			}

		}
		return isDate;
	}

	public static Duration getDuration(String fromTime, String toTime, String pattern) {

		return getDuration(fromTime, toTime, DateTimeFormat.forPattern(pattern));
	}

	public static Duration getDuration(String fromTime, String toTime, DateTimeFormatter formatter) {
		Duration duration = null;
		if (StringUtils.isNotBlank(fromTime) && StringUtils.isNotBlank(toTime)) {
			final DateTime fromDateTime = formatter.parseDateTime(fromTime);
			final DateTime toDateTime = formatter.parseDateTime(toTime);
			duration = new Duration(fromDateTime, toDateTime);
		}
		return duration;
	}

	public static Duration getDuration(String fromTime, String toTime) {
		return getDuration(fromTime, toTime, SHORT_FORMATTER);
	}

	public static String getMaxDateByMonth(Date currentDate) {
		return getMaxDateByMonth(date2String(currentDate));
	}

	public static String getMinDateByMonth(Date currentDate) {
		return getMinDateByMonth(date2String(currentDate));
	}

	public static String getMaxDateByMonth(String currentDate) {
		Date sDate1 = DateUtils.dateString2Date(currentDate);
		Calendar cDay1 = Calendar.getInstance();
		cDay1.setTime(sDate1);
		final int lastDay = cDay1.getActualMaximum(Calendar.DAY_OF_MONTH);
		return currentDate.substring(0, 8) + lastDay;
	}

	public static String getMinDateByMonth(String currentDate) {
		return currentDate.substring(0, 8) + "01";
	}

}

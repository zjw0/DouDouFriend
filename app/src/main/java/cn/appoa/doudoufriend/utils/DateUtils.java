package cn.appoa.doudoufriend.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

	/**
	 * 判断2个时间大小 yyyy-MM-dd HH:mm 格式（自己可以修改成想要的时间格式） 列表添加时间
	 * 
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public static boolean getTimeCompareSize(String startTime, String endTime) {
//		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");// 年-月-日 时-分
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 年-月-日 时-分
		try {
			Date date1 = dateFormat.parse(startTime);// 开始时间
			Date date2 = dateFormat.parse(endTime);// 结束时间
			// 1 结束时间小于开始时间 2 开始时间与结束时间相同 3 结束时间大于开始时间
			if (date2.getTime() < date1.getTime()) {
				return false;
			} else if (date2.getTime() == date1.getTime()) {
				return false;
			} else if (date2.getTime() > date1.getTime()) {
				// 正常情况下的逻辑操作.
				return true;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 判断2个时间大小 yyyy-MM-dd HH:mm 格式（自己可以修改成想要的时间格式） 列表添加时间
	 * 
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public static boolean getTimeHistory(String startTime, String endTime) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");// 年-月-日 时-分
		try {
			Date date1 = dateFormat.parse(startTime);// 开始时间
			Date date2 = dateFormat.parse(endTime);// 结束时间
			// 1 结束时间小于开始时间 2 开始时间与结束时间相同 3 结束时间大于开始时间
			if (date2.getTime() < date1.getTime()) {
				return false;
			} else if (date2.getTime() == date1.getTime()) {
				return false;
			} else if (date2.getTime() > date1.getTime()) {
				// 正常情况下的逻辑操作.
				return true;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 判断2个时间大小 yyyy-MM-dd HH:mm 格式（自己可以修改成想要的时间格式） 列表添加时间
	 * 
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public static boolean getTimeForVip(String startTime, String endTime, int type) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 年-月-日 时-分
		try {
			Date date1 = dateFormat.parse(startTime);// 开始时间
			Date date2 = dateFormat.parse(endTime);// 结束时间
			// 1 结束时间小于开始时间 2 开始时间与结束时间相同 3 结束时间大于开始时间
			if (type == 1) {
				if (date2.getTime() < date1.getTime()) {
					return true;
				} else if (date2.getTime() == date1.getTime()) {
					return true;
				} else if (date2.getTime() > date1.getTime()) {
					// 正常情况下的逻辑操作.
					return false;
				}
			} else {
				if (date2.getTime() < date1.getTime()) {
					return true;
				} else if (date2.getTime() == date1.getTime()) {
					return true;
				} else if (date2.getTime() > date1.getTime()) {
					// 正常情况下的逻辑操作.
					return false;
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static String getNowDate(String format) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);// 年-月-日 时-分
		return dateFormat.format(new Date());
	}

	/**
	 * 日期转时间
	 * 
	 * @param time
	 * @return
	 */
	public static Date getTimetoDate(String time) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 年-月-日 时-分
		try {
			Date parse = dateFormat.parse(time);
			return parse;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 日期转时间
	 *
	 * @param time
	 * @return
	 */
	public static Date getTimetoDate1(String time) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");// 年-月-日 时-分
		try {
			Date parse = dateFormat.parse(time);
			return parse;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String getNowDate(Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");// 年-月-日 时-分
		return dateFormat.format(date);
	}

	public static String getNowDate1(Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 年-月-日 时-分
		return dateFormat.format(date);
	}



	/**
	 * 获得两个日期间距多少天
	 *
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public static int  getTimeDistance(Date beginDate, Date endDate) {
		Calendar fromCalendar = Calendar.getInstance();
		fromCalendar.setTime(beginDate);
		fromCalendar.set(Calendar.HOUR_OF_DAY, 0);
		fromCalendar.set(Calendar.MINUTE, 0);
		fromCalendar.set(Calendar.SECOND, 0);
		fromCalendar.set(Calendar.MILLISECOND, 0);

		Calendar toCalendar = Calendar.getInstance();
		toCalendar.setTime(endDate);
		toCalendar.set(Calendar.HOUR_OF_DAY, 0);
		toCalendar.set(Calendar.MINUTE, 0);
		toCalendar.set(Calendar.SECOND, 0);
		toCalendar.set(Calendar.MILLISECOND, 0);

		return (int) ((toCalendar.getTime().getTime() - fromCalendar.getTime().getTime()) / (1000 * 60 * 60 * 24));
	}

}

package com.soft.util;

import java.text.*;
import java.util.*;

public class Time {
	
	/**
	 * 获取当前日期
	 * @return
	 */
	public static DateUtil d(){ return new Time.DateUtil(); }
	
	/**
	 * 获取传入日期
	 * @return
	 */
	public static DateUtil d(Date date){ return new Time.DateUtil(date); }
	
	/**
	 * 获取当前日期，按入参格式化
	 * @param format
	 * @return
	 */
	public static String d(String format){ return new Time.DateUtil().p(format).toString(); }
	
	/**
	 * 智能识别当前日期，并保存当前格式
	 * @param format
	 * @return
	 */
	public static DateUtil d(String format, String date) throws Exception{ return new Time.DateUtil(format).c(date); }
	
	/**
	 * 时间工具
	 */
	public static class DateUtil{
		private Date _date = new Date(); //默认时间
		private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //默认格式
		
		public DateUtil(){}
		public DateUtil(Date date){ _date = date; }
		public DateUtil(String format){ sdf = new SimpleDateFormat(format); }
		
		/**
		 * 修改时间基础方法
		 * @param field
		 * @param amount
		 * @return
		 */
		private DateUtil set(int field, int amount){
			Calendar c = Calendar.getInstance();
			c.setTime(_date);
			c.add(field, amount);
			_date = c.getTime();
			return this;
		}
		
		/**
		 * 修改年
		 * @param year
		 * @return
		 */
		public DateUtil yy(int year){ return set(Calendar.YEAR, year); }
		
		/**
		 * 修改月
		 * @param month
		 * @return
		 */
		public DateUtil MM(int month){ return set(Calendar.MONTH, month); }
		
		/**
		 * 修改日
		 * @param day
		 * @return
		 */
		public DateUtil dd(int day){ return set(Calendar.DAY_OF_YEAR, day); }
		
		/**
		 * 修改小时
		 * @param hour
		 * @return
		 */
		public DateUtil HH(int hour){ return set(Calendar.HOUR, hour); }
		
		/**
		 * 修改分钟
		 * @param minute
		 * @return
		 */
		public DateUtil mm(int minute){ return set(Calendar.MINUTE, minute); }
		
		/**
		 * 修改秒
		 * @param second
		 * @return
		 */
		public DateUtil ss(int second){ return set(Calendar.SECOND, second); }
		
		/**
		 * 修改毫秒
		 * @param millisecond
		 * @return
		 */
		public DateUtil SSS(int millisecond){ return set(Calendar.MILLISECOND, millisecond); }
		
		/**
		 * 获取修改后的Date
		 * @return
		 */
		public Date val(){ return _date; }
		
		/**
		 * 设置格式并输出，用于修改时间后格式化
		 * @param format
		 * @return
		 */
		public String p(String format){
			sdf = new SimpleDateFormat(format);
			return sdf.format(_date);
		}
		
		/**
		 * 按传入字符串转换为日期
		 * @param date
		 * @return
		 * @throws Exception
		 */
		public DateUtil c(String date) throws Exception {
			_date = sdf.parse(date);
			return this;
		}
		
		/**
		 * toString简写
		 * @return
		 */
		public String t(){ return toString(); }
		
		public String toString() { return sdf.format(_date); }
	}
}
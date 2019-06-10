package cc.natapp4.ddaig.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUtils {

	/**
	 * 基于参数给出格里高利偏移量（Long类型数据），得到该偏移位置的形如 "yyyy-MM-dd HH:mm:ss"的日期字符串
	 * @param timeMillis  格里高利历偏移量（相对于1970年1月1日 00：00：00 的毫秒值偏移量）
	 * @return
	 */
	public static String getSimpleDateTimeFormatString(long timeMillis){
		SimpleDateFormat  formatter  =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String format = formatter.format(new Date(timeMillis));
		
		return format;
	}
}

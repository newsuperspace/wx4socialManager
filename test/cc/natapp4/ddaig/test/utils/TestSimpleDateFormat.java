package cc.natapp4.ddaig.test.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

import cc.natapp4.ddaig.json.returnMessage.ReturnMessage4StartDayAndEndDay;
import cc.natapp4.ddaig.utils.ActivityUtils;

public class TestSimpleDateFormat {

	/**
	 * 判断今天是星期几
	 * @throws ParseException 
	 */
	@Test
	public void testGetWeek() throws ParseException {
		// 获得今天的格里高利偏移量（含具体shifenmiao）
		long millis = System.currentTimeMillis();
		System.out.println(millis);
		SimpleDateFormat sdw = new SimpleDateFormat("E");
		String week = sdw.format(millis);
		/**
		 * 打印
		 * 星期一、星期二、星期三、星期四、星期五、星期六、星期日
		 */
		System.out.println(week);
		
		Date parse = sdw.parse(week);
		System.out.println(parse.getTime());
	}
	
	/**
	 * 【通过】
	 *  测试ActivityUtils工具类中的静态方法getStartDayAndEndDay()
	 */
	@Test
	public void testActivityUtils4getStartDayAndEndDay(){
		long time  =  System.currentTimeMillis();
		ReturnMessage4StartDayAndEndDay result = ActivityUtils.getStartDayAndEndDay(3);
		System.out.println("开始时间："+result.getStartDay());
		System.out.println("持续天数："+result.getEndDay());
	}
	
}

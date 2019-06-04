package cc.natapp4.ddaig.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import cc.natapp4.ddaig.json.returnMessage.ReturnMessage4StartDayAndEndDay;

public class ActivityUtils {

	public static ReturnMessage4StartDayAndEndDay getStartDayAndEndDay(int level) {

		ReturnMessage4StartDayAndEndDay result = new ReturnMessage4StartDayAndEndDay();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat format4week = new SimpleDateFormat("E");
		long currentMillion = System.currentTimeMillis();
		String week = format4week.format(new Date(currentMillion));

		String startDay;
		int endDay;
		long million;
		switch (level) {
		case -1:  // 街道层级
			// TODO 待定
			break;
		case 0:	// 社区
			if("星期一".equals(week)||"周一".equals(week)){
				// 星期二到星期日
				million = currentMillion + 1000L*60*60*24;;
				startDay = format.format(new Date(million));
				endDay  = 6;
				result.setStartDay(startDay);
				result.setEndDay(endDay);
			}else if("星期二".equals(week)||"周二".equals(week)){
				// 星期三到星期日
				million = currentMillion + 1000L*60*60*24;;
				startDay = format.format(new Date(million));
				endDay  = 5;
				result.setStartDay(startDay);
				result.setEndDay(endDay);
			}else if("星期三".equals(week)||"周三".equals(week)){
				// 社区每周三可以开启下周的预约了
				million = currentMillion + 1000L*60*60*24;;
				startDay = format.format(new Date(million));
				endDay  = 11;
				result.setStartDay(startDay);
				result.setEndDay(endDay);
			}else if("星期四".equals(week)||"周四".equals(week)){
				million = currentMillion + 1000L*60*60*24;;
				startDay = format.format(new Date(million));
				endDay  = 10;
				result.setStartDay(startDay);
				result.setEndDay(endDay);
			}else if("星期五".equals(week)||"周五".equals(week)){
				million = currentMillion + 1000L*60*60*24;;
				startDay = format.format(new Date(million));
				endDay  = 9;
				result.setStartDay(startDay);
				result.setEndDay(endDay);
			}else if("星期六".equals(week)||"周六".equals(week)){
				million = currentMillion + 1000L*60*60*24;;
				startDay = format.format(new Date(million));
				endDay  = 8;
				result.setStartDay(startDay);
				result.setEndDay(endDay);
			}else if("星期日".equals(week)||"周日".equals(week)){
				million = currentMillion + 1000L*60*60*24;;
				startDay = format.format(new Date(million));
				endDay  = 7;
				result.setStartDay(startDay);
				result.setEndDay(endDay);
			}
			break;
		case 1:	// 第一
			if("星期一".equals(week)||"周一".equals(week)){
				// 星期二到星期日
				million = currentMillion + 1000L*60*60*24;;
				startDay = format.format(new Date(million));
				endDay  = 6;
				result.setStartDay(startDay);
				result.setEndDay(endDay);
			}else if("星期二".equals(week)||"周二".equals(week)){
				// 星期三到星期日
				million = currentMillion + 1000L*60*60*24;;
				startDay = format.format(new Date(million));
				endDay  = 5;
				result.setStartDay(startDay);
				result.setEndDay(endDay);
			}else if("星期三".equals(week)||"周三".equals(week)){
				// 星期四到星期日
				million = currentMillion + 1000L*60*60*24;;
				startDay = format.format(new Date(million));
				endDay  = 4;
				result.setStartDay(startDay);
				result.setEndDay(endDay);
			}else if("星期四".equals(week)||"周四".equals(week)){
				// 第一层级可在周四开启下周预约
				million = currentMillion + 1000L*60*60*24;;
				startDay = format.format(new Date(million));
				endDay  = 10;
				result.setStartDay(startDay);
				result.setEndDay(endDay);
			}else if("星期五".equals(week)||"周五".equals(week)){
				million = currentMillion + 1000L*60*60*24;;
				startDay = format.format(new Date(million));
				endDay  = 9;
				result.setStartDay(startDay);
				result.setEndDay(endDay);
			}else if("星期六".equals(week)||"周六".equals(week)){
				million = currentMillion + 1000L*60*60*24;;
				startDay = format.format(new Date(million));
				endDay  = 8;
				result.setStartDay(startDay);
				result.setEndDay(endDay);
			}else if("星期日".equals(week)||"周日".equals(week)){
				million = currentMillion + 1000L*60*60*24;;
				startDay = format.format(new Date(million));
				endDay  = 7;
				result.setStartDay(startDay);
				result.setEndDay(endDay);
			}
			break;
		case 2:	// 第二
			if("星期一".equals(week)||"周一".equals(week)){
				// 星期二到星期日
				million = currentMillion + 1000L*60*60*24;;
				startDay = format.format(new Date(million));
				endDay  = 6;
				result.setStartDay(startDay);
				result.setEndDay(endDay);
			}else if("星期二".equals(week)||"周二".equals(week)){
				// 星期三到星期日
				million = currentMillion + 1000L*60*60*24;;
				startDay = format.format(new Date(million));
				endDay  = 5;
				result.setStartDay(startDay);
				result.setEndDay(endDay);
			}else if("星期三".equals(week)||"周三".equals(week)){
				// 星期四到星期日
				million = currentMillion + 1000L*60*60*24;;
				startDay = format.format(new Date(million));
				endDay  = 4;
				result.setStartDay(startDay);
				result.setEndDay(endDay);
			}else if("星期四".equals(week)||"周四".equals(week)){
				// 星期五到星期日
				million = currentMillion + 1000L*60*60*24;;
				startDay = format.format(new Date(million));
				endDay  = 3;
				result.setStartDay(startDay);
				result.setEndDay(endDay);
			}else if("星期五".equals(week)||"周五".equals(week)){
				// 周五就可以提前安排下周活动
				million = currentMillion + 1000L*60*60*24;;
				startDay = format.format(new Date(million));
				endDay  = 9;
				result.setStartDay(startDay);
				result.setEndDay(endDay);
			}else if("星期六".equals(week)||"周六".equals(week)){
				million = currentMillion + 1000L*60*60*24;;
				startDay = format.format(new Date(million));
				endDay  = 8;
				result.setStartDay(startDay);
				result.setEndDay(endDay);
			}else if("星期日".equals(week)||"周日".equals(week)){
				million = currentMillion + 1000L*60*60*24;;
				startDay = format.format(new Date(million));
				endDay  = 7;
				result.setStartDay(startDay);
				result.setEndDay(endDay);
			}
			break;
		case 3:	// 第三
			if("星期一".equals(week)||"周一".equals(week)){
				// 星期二到星期日
				million = currentMillion + 1000L*60*60*24;;
				startDay = format.format(new Date(million));
				endDay  = 6;
				result.setStartDay(startDay);
				result.setEndDay(endDay);
			}else if("星期二".equals(week)||"周二".equals(week)){
				// 星期三到星期日
				million = currentMillion + 1000L*60*60*24;;
				startDay = format.format(new Date(million));
				endDay  = 5;
				result.setStartDay(startDay);
				result.setEndDay(endDay);
			}else if("星期三".equals(week)||"周三".equals(week)){
				// 星期四到星期日
				million = currentMillion + 1000L*60*60*24;;
				startDay = format.format(new Date(million));
				endDay  = 4;
				result.setStartDay(startDay);
				result.setEndDay(endDay);
			}else if("星期四".equals(week)||"周四".equals(week)){
				// 星期五到星期日
				million = currentMillion + 1000L*60*60*24;;
				startDay = format.format(new Date(million));
				endDay  = 3;
				result.setStartDay(startDay);
				result.setEndDay(endDay);
			}else if("星期五".equals(week)||"周五".equals(week)){
				// 星期六到星期日
				million = currentMillion + 1000L*60*60*24;;
				startDay = format.format(new Date(million));
				endDay  = 2;
				result.setStartDay(startDay);
				result.setEndDay(endDay);
			}else if("星期六".equals(week)||"周六".equals(week)){
				// 第三层级周六可以安排下周活动
				million = currentMillion + 1000L*60*60*24;;
				startDay = format.format(new Date(million));
				endDay  = 8;
				result.setStartDay(startDay);
				result.setEndDay(endDay);
			}else if("星期日".equals(week)||"周日".equals(week)){
				million = currentMillion + 1000L*60*60*24;;
				startDay = format.format(new Date(million));
				endDay  = 7;
				result.setStartDay(startDay);
				result.setEndDay(endDay);
			}
			break;
		}
		
		result.setResult(true);
		result.setMessage("日程安排起止时间计算完成");
		return result;
	}

}

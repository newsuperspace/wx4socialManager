package cc.natapp4.ddaig.json.returnMessage;

import java.io.Serializable;

/**
 * 本domain并非用作向前端回复AJAX消息之用，而是用来作为前端的FullCalendar的数据源之用
 * 用来通过特定字段（属性，这些属性名都是FullCalendar规定好的）封装必要事件数据，然后
 * 向前端反馈，这前端的Fullcalendar就能将这些事件数据显示在日历上了。
 * @author Administrator
 *
 */
public class Json4FullCalendar implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String title;
	private String start;
	private String end;
	private boolean allDay = false;
	private String backgroundColor;
	private String textColor;
	private String url;
	
	// 活动的aid
	public String getId() {
		return id;
	}
	// 活动的aid
	public void setId(String id) {
		this.id = id;
	}
	// 活动的name
	public String getTitle() {
		return title;
	}
	// 活动的name
	public void setTitle(String title) {
		this.title = title;
	}
	// 形如：2018-08-20T11:30:00的活动开始时间
	public String getStart() {
		return start;
	}
	// 形如：2018-08-20T11:30:00的活动开始时间
	public void setStart(String start) {
		this.start = start;
	}
	// 形如：2018-08-20T11:30:00的活动结束时间
	public String getEnd() {
		return end;
	}
	// 形如：2018-08-20T11:30:00的活动结束时间
	public void setEnd(String end) {
		this.end = end;
	}
	// 是否是全天活动（默认为false）
	public boolean isAllDay() {
		return allDay;
	}
	// 是否是全天活动（默认为false）
	public void setAllDay(boolean allDay) {
		this.allDay = allDay;
	}
	// 设置事件图标的背景颜色，例如'red'、'yellow'、'green'、
	public String getBackgroundColor() {
		return backgroundColor;
	}
	// 设置事件图标的背景颜色，例如'red'、'yellow'、'green'、
	public void setBackgroundColor(String backgroundColor) {
		this.backgroundColor = backgroundColor;
	}
	// 设置事件图标的字体颜色，例如'red'、'yellow'、'green'、
	public String getTextColor() {
		return textColor;
	}
	// 设置事件图标的字体颜色，例如'red'、'yellow'、'green'、
	public void setTextColor(String textColor) {
		this.textColor = textColor;
	}
	// 当用户点击事件图标的时候跳转到的url
	public String getUrl() {
		return url;
	}
	// 当用户点击事件图标的时候跳转到的url
	public void setUrl(String url) {
		this.url = url;
	}

}

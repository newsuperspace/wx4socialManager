package cc.natapp4.ddaig.service_interface.health;

import cc.natapp4.ddaig.bean.health.ReturnMessage4CountandCreateFirstPage4Eslist;
import cc.natapp4.ddaig.domain.health.EnclosedScale;
import cc.natapp4.ddaig.service_interface.BaseService;

/**
 * 有关封闭式问卷对象的，服务（service）层操作对象
 * 
 * @author 吉磊
 *
 */
public interface EnclosedScaleService extends BaseService<EnclosedScale> {

	// =================个性化方法===============
	
	// ===============================enclosedScaleList.jsp页面的方法====================================
	/**
	 * 【分页查询】 获取enclosedScaleList.jsp页面上的首页数据
	 * 
	 * @return
	 */
	public ReturnMessage4CountandCreateFirstPage4Eslist getCountandCreateFirstPage4InitLaypage4EslistPage();

	/**
	 * 【分页查询】 在enclosedScaleList.jsp页面上的分页查询
	 * 
	 * @param targetPageNum    等待分页查询的目标页号码（从1开始）
	 * @param pageItemNumLimit 分页查询时，每页的条目数量（初始化页面一般为10条）
	 * @return
	 */
	public ReturnMessage4CountandCreateFirstPage4Eslist getEssByPageLimit(int targetPageNum, int pageItemNumLimit);
}

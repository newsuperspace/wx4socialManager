package cc.natapp4.ddaig.service_interface.health;


import cc.natapp4.ddaig.bean.health.ReturnMessage4CountandCreateFirstPage;
import cc.natapp4.ddaig.bean.health.ReturnMessage4InitSelector;
import cc.natapp4.ddaig.json.returnMessage.ReturnMessage4Common;

/**
 * 直接用于封装HealthAction中所用到的业务逻辑
 * @author 吉磊
 *
 */
public interface HealthService {

	/**
	 * 借助微信渠道，向指定用户发送消息
	 * @param openID
	 * @param content
	 * @return
	 */
	public ReturnMessage4Common sendMessage2One(String openID, String content);

	/**
	 * 分页查询——获取首页数据信息
	 * @param targetTag			目标层级级别（"admin","minus_first","zero","first","second","third","fourth"）
	 * @param targetLid			目标层级的主键ID
	 * @param targetPageNum		目标页数（从1开始）
	 * @param pageItemNumLimit	每页上显示数据条目数
	 * @return
	 */
	public ReturnMessage4CountandCreateFirstPage getCountandCreateFirstPage4InitLaypage(String targetTag, String targetLid, int targetPageNum, int pageItemNumLimit);
	
	/**
	 * 格局前端回传的，符合特定规则的JSON格式字符串，创建封闭式问卷对象
	 * 
	 * @param jsonStr4CreateEnclosedScale
	 * @return
	 */
	public ReturnMessage4Common createEnclosedScale(String jsonStr4CreateEnclosedScale);
	
	
	
	/**
	 * 分页查询——获取指定页码的分页用户数据
	 * @param targetTag			获取哪个层级（标签）的用户
	 * @param targetLid			获取哪个层级（主键ID）的用户
	 * @param targetPageNum		目标页码
	 * @param pageItemNumLimit	每页显示的数据条目数
	 * @return
	 */
	public ReturnMessage4CountandCreateFirstPage getUsersByPageLimit(String targetTag, String targetLid, int targetPageNum, int pageItemNumLimit);
	
	
	/**
	 * 为当前层级操作者，在访问健康管理-被测者目录的时候，初始化用于过滤其不同子层级用户的selector数据，该selector是基于picker-extend.js实现的
	 * @param tag		当前操作者层级的tag（admin、minus_first、zero、first、second、third、fourth）
	 * @param lid		当前操作者层级的lid主键ID
	 * @return
	 */
	public ReturnMessage4InitSelector initSelector(String tag,String lid);
	
	
	
	
}

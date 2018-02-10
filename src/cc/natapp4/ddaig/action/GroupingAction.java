package cc.natapp4.ddaig.action;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import cc.natapp4.ddaig.domain.Grouping;
import cc.natapp4.ddaig.exception.WeixinExceptionWhenCreatingTag;
import cc.natapp4.ddaig.json.returnMessage.ReturnMessage4Common;
import cc.natapp4.ddaig.service_interface.GroupingService;
import cc.natapp4.ddaig.weixin.service_implement.WeixinService4SettingImpl;

@Controller("groupingAction")
@Scope("prototype")
@Lazy(true)
public class GroupingAction extends ActionSupport implements ModelDriven<Grouping> {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	// ================================================DI注入
	@Resource(name = "groupingService")
	private GroupingService groupingService;
	// 主动调用微信API接口，实现对公众号的主动设置 TODO 还没实现呢
	@Resource(name = "weixinService4Setting")
	protected WeixinService4SettingImpl mpService4Setting;

	// 模型驱动——收纳请求参数
	private Grouping grouping;

	@Override
	public Grouping getModel() {
		this.grouping = new Grouping();
		return this.grouping;
	}

	/**
	 * JSP后台设置表情的页面 → 管理员点击"新建TAG按钮" → 填写表单后提交到当前方法处理
	 * 
	 * @return
	 */
	public String createTag() {
		ReturnMessage4Common  result=null;
		// ---------------------------------------验证前端提交过来的参数的合法性-------------------------------------
			// 验证提交的请求参数是否缺失
		if(null==grouping || StringUtils.isBlank(grouping.getGroupName()) || StringUtils.isBlank(grouping.getTag())){
			result = new ReturnMessage4Common("关键参数缺失，请重新添加", false);
			/*
			 *  将包含处理结果的JavaBean放入到对象栈栈顶，并返回json结果索引字符串后，struts-json-plugin.jar插件中定义的json类型结果集
			 *  就会从栈顶中取出该JavaBean对象，然后解析成JSON格式字符串后，写入到Response返回给前端（正在通过Ajax等待这次处理结果）
			 *  然后前端JSP页面上的javascrpit脚本就可以通过jQuery的post()方法的回调函数直接获取到JavaBean中参数的值，从而执行相应逻辑。
			 */
			ActionContext.getContext().getValueStack().push(result);
			// 通过Ajax技术与前段交互
			return "json"; 
		}
			// 验证提交的关键参数标签tag是否在数据库中已有
		List<Grouping> list = groupingService.queryEntities();
		for(Grouping g: list){
			if(g.getTag().equals(grouping.getTag())){
				result = new ReturnMessage4Common("tag标签重复，请确认后重试", false);
				ActionContext.getContext().getValueStack().push(result);
				// 通过Ajax技术与前段交互
				return "json"; 
			}
		}
		// ---------------------------------------验证数据没问题后，调用WeixinService4SettingImpl.createTag() 从公众号处获取tagid-----------------
		try {
			mpService4Setting.createTag(this.grouping);
			result = new ReturnMessage4Common("标签新建成功！", true);
		} catch (WeixinExceptionWhenCreatingTag e) {
			e.printStackTrace();
			logger.info("新建标签时本地服务器与微信服务器交互出现异常，请稍候重试");
			result = new ReturnMessage4Common("新建标签时本地服务器与微信服务器交互出现异常，请稍候重试", true);
		}
		// 通过Ajax技术与前段交互
		return "json"; 
	}



}

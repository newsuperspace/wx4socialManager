package cc.natapp4.ddaig.action;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

import cc.natapp4.ddaig.domain.cengji.MinusFirstLevel;
import cc.natapp4.ddaig.domain.cengji.ZeroLevel;
import cc.natapp4.ddaig.json.returnMessage.ReturnMessage4Common;
import cc.natapp4.ddaig.service_interface.MinusFirstLevelService;
import cc.natapp4.ddaig.service_interface.ZeroLevelService;
import cc.natapp4.ddaig.utils.QRCodeUtils;

@Controller("minusFirstLevelAction")
@Scope("prototype")
@Lazy(true)
public class MinusFirstLevelAction implements ModelDriven<MinusFirstLevel> {
	// =================模型驱动=================
	private MinusFirstLevel minusFirstLevel;

	@Override
	public MinusFirstLevel getModel() {
		minusFirstLevel = new MinusFirstLevel();
		return minusFirstLevel;
	}

	// =================属性驱动=================
	private String sonName;
	private String sonDescription;

	public String getSonName() {
		return sonName;
	}

	public void setSonName(String sonName) {
		this.sonName = sonName;
	}

	public String getSonDescription() {
		return sonDescription;
	}

	public void setSonDescription(String sonDescription) {
		this.sonDescription = sonDescription;
	}

	// =================DI注入=================
	@Resource(name = "minusFirstLevelService")
	private MinusFirstLevelService minusFirstLevelService;
	@Resource(name = "zeroLevelService")
	private ZeroLevelService zeroLevelService;

	// ====================Actions================
	/**
	 * 管理员权限者通过点击MinusFirstLevel.jsp页面右上方的新建按钮，通过Modal+Ajax请求本方法直接创建其次一级层级对象（
	 * 街道对象） 需要自动通过Shiro获取执行当前操作的层级管理者，并获取它绑定的层级对象，然后所新建的次一层级对象默认至于操作者层级之下。
	 * 
	 * @return
	 */
	public String createLevel() {
		// TODO 通过Shiro获取执行新建操作的管理者对象，并进一步获取与其绑定的层级对象

		ReturnMessage4Common r = new ReturnMessage4Common();
		
		if("".equals(minusFirstLevel.getDescription())||"".equals(minusFirstLevel.getName())){
			r.setMessage("关键信息为null，街道创建失败");
			r.setResult(false);
		}else{
			MinusFirstLevel m = new MinusFirstLevel();
			
			StringBuffer sb = new StringBuffer();
			sb.append("level_");
			sb.append(MinusFirstLevel.LEVEL_MINUS_FIRST);
			sb.append("$");
			sb.append("id_");
			String id = UUID.randomUUID().toString();
			sb.append(id);
			
			m.setMflid(id);
			
			String qrcode = QRCodeUtils.createLevelQR(sb.toString());
			m.setQrcode(qrcode);
			
			m.setDescription(minusFirstLevel.getDescription());
			m.setName(minusFirstLevel.getName());
			
			minusFirstLevelService.save(m);
			r.setMessage("创建成功");
			r.setResult(true);
		}

		ActionContext.getContext().getValueStack().push(r);
		return "json";
	}

	/**
	 * 通过点击MinusFirstLevel.jsp页面上每个minusFirst条目后的新建按钮，实现创建制定minusFirst层级对象的子对象，
	 * 也就是zeroLevel的操作
	 * 
	 * @return
	 */
	public String createSonLevel() {
		ReturnMessage4Common r = new ReturnMessage4Common();

		if (getSonDescription().equals("") || "".equals(getSonName()) || minusFirstLevel.getMflid().equals("")) {
			r.setMessage("关键数据为NULL，创建失败");
			r.setResult(false);
		} else {
			// 首先需要分析出“被新建次层级”的层级对象是哪个
			String pid = minusFirstLevel.getMflid();
			MinusFirstLevel parentLevel = minusFirstLevelService.queryEntityById(pid);

			ZeroLevel sonLevel = new ZeroLevel();
			sonLevel.setDescription(getSonDescription());
			sonLevel.setName(getSonName());

			StringBuffer sb = new StringBuffer();
			sb.append("level_");
			sb.append(ZeroLevel.LEVEL_ZERO);
			sb.append("$");
			sb.append("id_");
			String sid = UUID.randomUUID().toString();
			sb.append(sid);

			sonLevel.setZid(sid);

			String qrcode = QRCodeUtils.createLevelQR(sb.toString());
			sonLevel.setQrcode(qrcode);

			sonLevel.setParent(parentLevel);

			zeroLevelService.save(sonLevel);

			r.setMessage("新建成功");
			r.setResult(true);
		}

		ActionContext.getContext().getValueStack().push(r);
		return "json";
	}

	public String getLevelList() {

		List<MinusFirstLevel> list = minusFirstLevelService.queryEntities();

		ActionContext.getContext().put("levels", list);
		return "list";
	}
	
	public String getLevelInfo(){
		
		String id = minusFirstLevel.getMflid();
		MinusFirstLevel m = minusFirstLevelService.queryEntityById(id);
		
		List<MinusFirstLevel> list  =  new  ArrayList<MinusFirstLevel>();
		list.add(m);
		
		ActionContext.getContext().getValueStack().push(list);
		return "list";
	}

}

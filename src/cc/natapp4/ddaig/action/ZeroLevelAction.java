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

import cc.natapp4.ddaig.domain.cengji.FirstLevel;
import cc.natapp4.ddaig.domain.cengji.MinusFirstLevel;
import cc.natapp4.ddaig.domain.cengji.ZeroLevel;
import cc.natapp4.ddaig.json.returnMessage.ReturnMessage4Common;
import cc.natapp4.ddaig.service_interface.FirstLevelService;
import cc.natapp4.ddaig.service_interface.ZeroLevelService;
import cc.natapp4.ddaig.utils.QRCodeUtils;



@Controller("zeroLevelAction")
@Scope("prototype")
@Lazy(true)
public class ZeroLevelAction implements ModelDriven<ZeroLevel> {
	// =================模型驱动=================
	private ZeroLevel  zeroLevel;
	@Override
	public ZeroLevel getModel() {
		zeroLevel  =  new  ZeroLevel();
		return zeroLevel;
	}
	// =================属性驱动=================
	private String parentId;
	private String sonName;
	private String sonDescription;
	public String getParentId(){
		return parentId;
	}
	public void setParentId(String parentId){
		this.parentId = parentId;
	}
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
	@Resource(name="zeroLevelService")
	private ZeroLevelService  zeroLevelService;
	@Resource(name="firstLevelService")
	private FirstLevelService  firstLevelService;
	// ====================Actions================
	/**
	 * 管理员权限者通过点击zero.jsp页面右上方的新建按钮，通过Modal+Ajax请求本方法直接创建其次一级层级对象（街道对象）
	 * 需要自动通过Shiro获取执行当前操作的层级管理者，并获取它绑定的层级对象，然后所新建的次一层级对象默认至于操作者层级之下。
	 * @return
	 */
	public  String  createLevel(){
		// TODO 通过Shiro获取执行新建操作的管理者对象，并进一步获取与其绑定的层级对象
		ReturnMessage4Common  r  =  new  ReturnMessage4Common();
		
		if("".equals(zeroLevel.getDescription())|| "".equals(zeroLevel.getName())){
			r.setMessage("关键数据为null，新建社区失败");
			r.setResult(false);
		}else{
			ZeroLevel l  =  new ZeroLevel();
			
			StringBuffer  sb  =  new  StringBuffer();
			sb.append("level_");
			sb.append(ZeroLevel.LEVEL_ZERO);
			sb.append("$");
			sb.append("id_");
			String id  = UUID.randomUUID().toString();
			sb.append(id);
			
			l.setZid(id);
			
			String qrcode = QRCodeUtils.createLevelQR(sb.toString());
			l.setQrcode(qrcode);
			
			l.setDescription(zeroLevel.getDescription());
			l.setName(zeroLevel.getName());
			
			zeroLevelService.save(l);
			
			r.setMessage("新建成功");
			r.setResult(true);
		}
		
		ActionContext.getContext().getValueStack().push(r);
		return "json";
	}
	
	/**
	 * 通过点击zero.jsp页面上每个zeroLevel条目后的新建按钮，实现创建指定Zero层级对象的子对象，也就是FirstLevel的操作
	 * @return
	 */
	public String createSonLevel(){
		
		ReturnMessage4Common r = new ReturnMessage4Common();

		if (getSonDescription().equals("") || "".equals(getSonName()) || this.getParentId().equals("")) {
			r.setMessage("关键数据为NULL，创建失败");
			r.setResult(false);
		} else {
			// 首先需要分析出“被新建次层级”的层级对象是哪个
			ZeroLevel parentLevel = zeroLevelService.queryEntityById(this.getParentId());

			FirstLevel sonLevel = new FirstLevel();
			sonLevel.setDescription(getSonDescription());
			sonLevel.setName(getSonName());

			StringBuffer sb = new StringBuffer();
			sb.append("level_");
			sb.append(FirstLevel.LEVEL_ONE);
			sb.append("$");
			sb.append("id_");
			String sid = UUID.randomUUID().toString();
			sb.append(sid);

			sonLevel.setFlid(sid);

			String qrcode = QRCodeUtils.createLevelQR(sb.toString());
			sonLevel.setQrcode(qrcode);

			sonLevel.setParent(parentLevel);

			firstLevelService.save(sonLevel);

			r.setMessage("新建成功");
			r.setResult(true);
		}

		ActionContext.getContext().getValueStack().push(r);
		return "json";
	}
	
	public  String  getLevelList(){
		
		List<ZeroLevel> list = zeroLevelService.queryEntities();
		
		ActionContext.getContext().put("levels", list);
		return "list";
	}
	
	/**
	 * managerList.jsp页面中，当点击某个管理者所管理的层级对象的时候
	 * 会触发managerModal.op.jump2LevelPage()方法
	 * 从而根据不同的tag（层级）实现跳转到不同层级Level页面中显示详细信息
	 * 的功能，因此每个层级对象的Action都应该有对应的本方法。
	 * @return
	 */
	public String getLevelInfo() {

		String id = zeroLevel.getZid();
		ZeroLevel l = zeroLevelService.queryEntityById(id);

		List<ZeroLevel> list = new ArrayList<ZeroLevel>();
		list.add(l);

		ActionContext.getContext().put("levels", list);
		return "list";
	}
}

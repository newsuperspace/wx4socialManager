package cc.natapp4.ddaig.action;

import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

import cc.natapp4.ddaig.domain.cengji.FirstLevel;
import cc.natapp4.ddaig.domain.cengji.FourthLevel;
import cc.natapp4.ddaig.domain.cengji.MinusFirstLevel;
import cc.natapp4.ddaig.domain.cengji.SecondLevel;
import cc.natapp4.ddaig.domain.cengji.ThirdLevel;
import cc.natapp4.ddaig.domain.cengji.ZeroLevel;
import cc.natapp4.ddaig.json.returnMessage.ReturnMessage4Common;
import cc.natapp4.ddaig.service_interface.FirstLevelService;
import cc.natapp4.ddaig.service_interface.FourthLevelService;
import cc.natapp4.ddaig.service_interface.SecondLevelService;
import cc.natapp4.ddaig.service_interface.ThirdLevelService;
import cc.natapp4.ddaig.service_interface.ZeroLevelService;
import cc.natapp4.ddaig.utils.QRCodeUtils;

@Controller("thirdLevelAction")  // <!-- ● -->
@Scope("prototype")
@Lazy(true)
public class ThirdLevelAction implements ModelDriven<ThirdLevel> {   // <!-- ● -->
	// =================模型驱动================= <!-- ● -->
	private ThirdLevel  thirdLevel;    
	@Override
	public ThirdLevel getModel() {
		this.thirdLevel  =  new  ThirdLevel();
		return thirdLevel;
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
	// =================DI注入================= <!-- ● -->
	@Resource(name="thirdLevelService")
	private ThirdLevelService  thirdLevelService;
	@Resource(name="fourthLevelService")
	private FourthLevelService fourthLevelService;
	// ====================Actions================
	/**
	 * 管理员权限者通过点击third.jsp页面右上方的新建按钮，通过Modal+Ajax请求本方法直接创建其次一级层级对象（街道对象）
	 * 需要自动通过Shiro获取执行当前操作的层级管理者，并获取它绑定的层级对象，然后所新建的次一层级对象默认至于操作者层级之下。
	 * @return
	 */
	public  String  createLevel(){ // <!-- ● -->
		// TODO 通过Shiro获取执行新建操作的管理者对象，并进一步获取与其绑定的层级对象
		ReturnMessage4Common  r  =  new  ReturnMessage4Common();
		
		if("".equals(thirdLevel.getDescription())||"".equals(thirdLevel.getName())){
			r.setMessage("关键数据为null，新建层级失败");
			r.setResult(false);
		}else{
			ThirdLevel l  =  new ThirdLevel();
			
			StringBuffer  sb  =  new  StringBuffer();
			sb.append("level_");
			sb.append(ThirdLevel.LEVEL_THREE);
			sb.append("$");
			sb.append("id_");
			String id  = UUID.randomUUID().toString();
			sb.append(id);
			
			l.setThid(id);
			
			String qrcode = QRCodeUtils.createLevelQR(sb.toString());
			l.setQrcode(qrcode);
			
			l.setDescription(thirdLevel.getDescription());
			l.setName(thirdLevel.getName());
			
			thirdLevelService.save(l);
			
			r.setMessage("新建成功");
			r.setResult(true);
		}
		
		ActionContext.getContext().getValueStack().push(r);
		return "json";
	}
	
	/**
	 * 通过点击third.jsp页面上每个thirdLevel条目后的新建按钮，实现创建指定third层级对象的子对象，也就是fourthLevel的新建操作
	 * @return
	 */
	public String createSonLevel(){
		
		ReturnMessage4Common r = new ReturnMessage4Common();

		if (getSonDescription().equals("") || "".equals(getSonName()) || this.getParentId().equals("")) {
			r.setMessage("关键数据为NULL，创建失败");
			r.setResult(false);
		} else {
			// 首先需要分析出“被新建次层级”的层级对象是哪个
			ThirdLevel parentLevel = thirdLevelService.queryEntityById(this.getParentId());

			FourthLevel sonLevel = new FourthLevel();
			sonLevel.setDescription(getSonDescription());
			sonLevel.setName(getSonName());

			StringBuffer sb = new StringBuffer();
			sb.append("level_");
			sb.append(FourthLevel.LEVEL_FOUR);
			sb.append("$");
			sb.append("id_");
			String sid = UUID.randomUUID().toString();
			sb.append(sid);

			sonLevel.setFoid(sid);

			String qrcode = QRCodeUtils.createLevelQR(sb.toString());
			sonLevel.setQrcode(qrcode);

			sonLevel.setParent(parentLevel);

			fourthLevelService.save(sonLevel);

			r.setMessage("新建成功");
			r.setResult(true);
		}
		
		ActionContext.getContext().getValueStack().push(r);
		return "json";
	}
	
	
	
	
	public  String  getLevelList(){ // <!-- ● -->
		
		List<ThirdLevel> list = thirdLevelService.queryEntities();
		
		ActionContext.getContext().put("levels", list);
		return "list";
	}
	
	
}

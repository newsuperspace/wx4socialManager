package cc.natapp4.ddaig.action;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import cc.natapp4.ddaig.domain.Exchange;
import cc.natapp4.ddaig.domain.Ware;
import cc.natapp4.ddaig.domain.cengji.ZeroLevel;
import cc.natapp4.ddaig.json.returnMessage.ReturnMessage4Common;
import cc.natapp4.ddaig.service_interface.ExchangeService;
import cc.natapp4.ddaig.service_interface.WareService;
import cc.natapp4.ddaig.service_interface.ZeroLevelService;
import cc.natapp4.ddaig.utils.QRCodeUtils;

@Controller("wareAction")
/*
 * 之所以Struts框架下的Action是多例（prototype）而不是单例（singleton）
 */
@Scope("prototype")
@Lazy(true)
public class WareAction extends ActionSupport implements ModelDriven<Ware> {

	@Autowired
	private WareService wareService;
	@Autowired
	private ZeroLevelService zeroLevelService;

	// ===================================模型方法=========================================
	/**
	 * 这个属性特别重要，下买呢的getModel()方法是供给Struts2的模型驱动使用的，在当前
	 * Action中的各个方法如果想要获取模型（栈顶中的wareModel）不能通过this.getModel()来获取（每次调用就会新建一个新的bean，而不是获取栈顶中的保存有数据的bean）
	 * 而是应该使用当前类数据成员 wareModel， ★★
	 */
	private Ware wareModel = null;

	@Override
	public Ware getModel() {
		wareModel = new Ware();
		return wareModel;
	}

	// ===================================属性方法=========================================
	
	/*
	 * 向error.jsp页面显示错误信息，通过方法返回“errro”结果集索引字符串，然后由base-package中设定的全局结果集执行跳转索引
	 */
	private String errorMessage;
	public String getErrorMessage() {
		return errorMessage;
	}
	
	/*
	 * resources：createWare.jsp →初始化方法中通过AJAX传递过来
	 * user：deletePhoto()
	 * 用途：标记待删除商品的序号（1-3）
	 */
	private int index;
	public void setIndex(int index) {
		this.index = index;
	}
	
	/*
	 * 接收前端上传图片的base64编码字符串
	 */
	private String b64;
	public void setB64(String b64) {
		this.b64 = b64;
	}
	
	private File file;
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}

	// ===================================业务方法=========================================
	public String findAll() {
		// 当前暂时只允许社区查看积分兑换内容
		String tag = (String) ServletActionContext.getRequest().getSession().getAttribute("tag");
		String lid = (String) ServletActionContext.getRequest().getSession().getAttribute("lid");
		if (tag.equals("zero")) {
			ZeroLevel zeroLevel = zeroLevelService.queryEntityById(lid);
			if (null == zeroLevel) {
				this.errorMessage = "查找不到lid为" + lid + "的社区层级";
				return "error";
			}
			ActionContext.getContext().put("zeroLevel", zeroLevel);
		} else {
			this.errorMessage = "您当前所掌管层级不是社区层级，无权查看积分兑换奖品页面";
			return "error";
		}

		List<Ware> list = wareService.queryEntitiesByZid(lid);
		ActionContext.getContext().put("wares", list);
		return "wareList";
	}

	/*
	 * 跳转到创建新商品的页面
	 */
	public String toCreateWarePage() {

		return "createWare";
	}

	/**
	 * 新建商品
	 * @return
	 */
	public String createWare() {

		// 首先判断当前操作者是否是zero层级
		String tag = (String) ServletActionContext.getRequest().getSession().getAttribute("tag");
		String lid = (String) ServletActionContext.getRequest().getSession().getAttribute("lid");
		ZeroLevel zeroLevel = null;
		if (tag.equals("zero")) {
			zeroLevel = zeroLevelService.queryEntityById(lid);
			if (null == zeroLevel) {
				this.errorMessage = "查找不到lid为" + lid + "的社区层级";
				return "error";
			}
			ActionContext.getContext().put("zeroLevel", zeroLevel);
		} else {
			this.errorMessage = "您当前所掌管层级不是社区层级，无权创建兑换品";
			return "error";
		}

		Ware w = new Ware();
		w.setExchanges(new ArrayList<Exchange>());
		w.setScore(wareModel.getScore());
		w.setSurplus(wareModel.getSurplus());
		w.setTotal(0);
		w.setWname(wareModel.getWname());
		w.setCanUse(true);
		w.setCreateDate(System.currentTimeMillis());
		w.setDescription(wareModel.getDescription());
		w.setZeroLevel(zeroLevel);

		String wid = UUID.randomUUID().toString();
		w.setWid(wid);

		// 创建二维码图片，并转换为base64编码用于保存

		// 将前端上传的商品照片（最多三张）转换为base64编码用于保存

//		w.setBase64str4image(base64str4image);
//		w.setBase64str4qrcode(base64str4qrcode);

		return "json";
	}

	/**
	 *  获取商品详细信息，可用于“名片MODAL”也可用于“update操作前的数据回显”
	 * @return
	 */
	public String getWareInfo() {
		System.out.println(this.b64);
		return "json";
	}

	/**
	 *  更新商品信息
	 * @return
	 */
	public String updateWare() {

		return "json";
	}
	
	
	/**
	 * 根据数据驱动index定位待删除图片的序号（1-3），执行删除操作
	 * @return
	 */
	public String deletePhoto() {

		ReturnMessage4Common  message =  new  ReturnMessage4Common();
		
		
		ActionContext.getContext().getValueStack().push(message);
		return "json";
	}
	
	
	/**
	 * 上传商品图片
	 * @return
	 */
	public String upload() {
		System.out.println("====================");
		System.out.println(this.b64);
		return "json";
	}
	
	
	

}

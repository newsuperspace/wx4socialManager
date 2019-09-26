package cc.natapp4.ddaig.action;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import cc.natapp4.ddaig.utils.Image2Base64andBase642ImageUtils;
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
	
	private File file;
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}

	// ===================================业务方法=========================================
	
	/**
	 * wareList.jsp页面上显示商品列表的信息
	 * @return
	 */
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
	 * 跳转到创建新商品的表单页面——createWare.jsp
	 */
	public String toCreateWarePage() {

		Ware w =  new Ware();
		// 必须，设置展视在前端的图片列表<li>
		Map<String,String> map = new HashMap<String,String>();
		w.setPhotos(map);
		// 必须，当前创建日期
		w.setCreateDate(System.currentTimeMillis());

		ActionContext.getContext().getValueStack().push(w);
		return "createWare";
	}
	

	/**
	 * 正式执行“新建商品”的操作
	 * @return
	 */
	public String createWare() {
		
		ReturnMessage4Common result = new  ReturnMessage4Common();
		
		// 首先判断当前操作者是否是zero层级
		String tag = (String) ServletActionContext.getRequest().getSession().getAttribute("tag");
		String lid = (String) ServletActionContext.getRequest().getSession().getAttribute("lid");
		ZeroLevel zeroLevel = null;
		if (tag.equals("zero")) {
			zeroLevel = zeroLevelService.queryEntityById(lid);
			if (null == zeroLevel) {
				result.setMessage("查找不到lid为" + lid + "的社区层级");
				result.setResult(false);
				ActionContext.getContext().getValueStack().push(result);
				return "json";
			}
			ActionContext.getContext().put("zeroLevel", zeroLevel);
		} else {
			result.setMessage("您当前所掌管层级不是社区层级，无权创建兑换品");
			result.setResult(false);
			ActionContext.getContext().getValueStack().push(result);
			return "json";
		}

		// TODO 后端数据校验
		
		// 后端数据校验通过，开始执行新建数据操作
		Ware w = new Ware();
		w.setExchanges(new ArrayList<Exchange>());
		w.setScore(wareModel.getScore());
		w.setSurplus(wareModel.getSurplus());
		w.setTotal(0); // 新建商品的兑换数量一定是0
		w.setWname(wareModel.getWname());
		w.setCanUse(true);
		w.setCreateDate(System.currentTimeMillis());
		w.setDescription(wareModel.getDescription());
		w.setBase64str4image1(wareModel.getBase64str4image1());
		w.setBase64str4image2(wareModel.getBase64str4image2());
		w.setBase64str4image3(wareModel.getBase64str4image3());
		// 新建商品与zero层级建立外键关联
		w.setZeroLevel(zeroLevel);
		List<Ware> wares = zeroLevel.getWares();
		if(null == wares) {
			wares = new ArrayList<Ware>();
			zeroLevel.setWares(wares);
		}
		wares.add(w);
		// 手动创建商品主键（wid），并创建二维码图片，并转换为base64编码用于保存
		String wid = UUID.randomUUID().toString();
		w.setWid(wid);
		
		String realPath = ServletActionContext.getServletContext().getRealPath(File.separator+"qrcode/temp");
		File f = new File(realPath);
		if(!f.exists()) {
			f.mkdirs();
		}
		String qrcodeFilePath = realPath + File.separator + wid + ".jpg";
		if(QRCodeUtils.createQRcode(qrcodeFilePath, wid)) {
			String imgStr = Image2Base64andBase642ImageUtils.getImgStr(qrcodeFilePath);
			System.out.println(imgStr);
			w.setBase64str4qrcode("data:image/jpeg;base64," + imgStr);
			// 删除图片缓存
			f = new File(qrcodeFilePath);
			f.delete();
		}else {
			result.setMessage("创建商品兑换二维码时出现问题，无法创建二维码图片");
			result.setResult(false);
			ActionContext.getContext().getValueStack().push(result);
			return "json";
		}

		List<Exchange> exchanges = new ArrayList<Exchange>();
		w.setExchanges(exchanges);
		
		wareService.save(w);
		zeroLevelService.update(zeroLevel);

		result.setMessage("创建成功！");
		result.setResult(true);
		ActionContext.getContext().getValueStack().push(result);
		return "json";
	}

	
	/**
	 * 跳转到需要更新数据的商品表单页面，需要回显数据
	 * @return
	 */
	public String toUpdateWarePage() {
		
		// 根据前端传递过来的wid，确定需要更新数据的商品是哪一个
		String wid = this.wareModel.getWid();
		Ware ware = wareService.queryEntityById(wid);
		Map<String,String> map = new HashMap<String,String>();
		map.put("1", ware.getBase64str4image1());
		map.put("2", ware.getBase64str4image2());
		map.put("3", ware.getBase64str4image3());
		ware.setPhotos(map);
		
		ActionContext.getContext().getValueStack().push(ware);
		return "createWare";
	}

	/**
	 *  更新商品信息
	 * @return
	 */
	public String updateWare() {
		System.out.println("update the ware");
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
		System.out.println(this.file);
		// 用于响应前端WEUI.js的upload上传功能，即便没有任何数据要处理也要返回json而不是null，否则前端会进入error处理分支
		return "json";
	}
	
	
	

}

package cc.natapp4.ddaig.action;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import cc.natapp4.ddaig.domain.Exchange;
import cc.natapp4.ddaig.domain.Ware;
import cc.natapp4.ddaig.json.returnMessage.ReturnMessage4Common;
import cc.natapp4.ddaig.service_interface.WareService;
import cc.natapp4.ddaig.utils.QRCodeUtils;

@Controller("wareAction")
@Scope("prototype")
@Lazy(true)
public class WareAction extends ActionSupport implements ModelDriven<Ware> {

	@Resource(name="wareService")
	private WareService  service;
	
	
	public String  showAllList(){
		
		List<Ware> list = service.queryEntities();
		ActionContext.getContext().put("wares", list);
		return "wareList";
	}

	/**
	 *  这个属性特别重要，下买呢的getModel()方法是供给Struts2的模型驱动使用的，在当前
	 *  Action中的各个方法如果想要获取模型（栈顶中的wareModel）不能通过this.getModel()来获取（每次调用就会新建一个新的bean，而不是获取栈顶中的保存有数据的bean）
	 *  而是应该使用当前类数据成员 wareModel， ★★
	 */
	private Ware  wareModel =  null;
	@Override
	public Ware getModel() {
		wareModel =  new  Ware();
		return wareModel;
	}
	
	/**
	 * 批量创建商品的QRcode（根据商品的wid）
	 * 该方法用来，当Web应用重新部署导致商品qrcode丢失的时候，批量新建qrcode
	 * @return
	 */
	public String batchCreateWareQR4ajax(){
		
		ReturnMessage4Common  result =  new  ReturnMessage4Common();
		
		List<Ware> list = service.queryEntities();
		for(Ware w: list){
			String qrUri = QRCodeUtils.createWareQR(w.getWid());
			w.setQrcodeUrl(qrUri);
		}
		
		result.setResult(true);
		result.setMessage("生成完毕！");
		ActionContext.getContext().getValueStack().push(result);
		return "json";
	}
	
	/**
	 * 得到指定商品(通过id指定)，并返回给通过ajax等待的前端
	 * @return
	 */
	public String getWare4ajax(){
		
		String wid = wareModel.getWid();
		Ware ware = service.queryEntityById(wid);
		
		// 将商品的qrcode的真实路径拼凑完整
		String url = ServletActionContext.getServletContext().getContextPath() + "/" + ware.getQrcodeUrl();
		ware.setQrcodeUrl(url);
		
		ActionContext.getContext().getValueStack().push(ware);
		return "json";
	}
	
	/**
	 * 后台通过ajax添加新商品
	 * @return
	 */
	public String addWare4ajax(){
		Ware w =  new Ware();
		w.setWname(wareModel.getWname());
		w.setCore(wareModel.getCore());
		w.setSurplus(wareModel.getSurplus());
		
		String wid = UUID.randomUUID().toString();
		String qrcodeUrl = QRCodeUtils.createWareQR(wid);
		
		w.setQrcodeUrl(qrcodeUrl);
		w.setWid(wid);
		w.setTotal("0");
		
		service.save(w);
		
		ReturnMessage4Common  result =  new ReturnMessage4Common();
		result.setMessage("商品添加成功！");
		result.setResult(true);
		ActionContext.getContext().getValueStack().push(result);
		return "json";
	}
	
	/**
	 * 后台通过ajax进行商品信息的更新
	 * @return
	 */
	public String update4Json(){
		
		Ware ware = service.queryEntityById(wareModel.getWid());
		ware.setCore(wareModel.getCore());
		ware.setSurplus(wareModel.getSurplus());
		ware.setWname(wareModel.getWname());
		
		service.update(ware);
		
		return "json";
	}
	
	/**
	 * 获取当前商品的所有交易（兑换）记录，并跳转到exchangeList.jsp页面
	 * @return
	 */
	public String showExchangeList(){
		Ware w = service.queryEntityById(wareModel.getWid());
		
		Set<Exchange> set = w.getExchanges();
		ActionContext.getContext().put("exchanges", set);
		return "exchangeList";
	}
	
	
}

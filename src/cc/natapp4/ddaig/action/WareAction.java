package cc.natapp4.ddaig.action;

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
import cc.natapp4.ddaig.json.returnMessage.ReturnMessage4Common;
import cc.natapp4.ddaig.service_interface.WareService;
import cc.natapp4.ddaig.utils.QRCodeUtils;

@Controller("wareAction")
@Scope("prototype")
@Lazy(true)
public class WareAction extends ActionSupport implements ModelDriven<Ware> {

	@Autowired
	private WareService wareService;

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
	
	// ===================================业务方法=========================================
	public String findAll() {

		List<Ware> list = wareService.queryEntities();
		ActionContext.getContext().put("wares", list);
		return "wareList";
	}
	
	public String createWare() {
		
		Ware w  =  new Ware();
		w.setExchanges(new ArrayList<Exchange>());
		w.setScore(wareModel.getScore());
		w.setSurplus(wareModel.getSurplus());
		w.setTotal(0);
		w.setWname(wareModel.getWname());

		String wid  =  UUID.randomUUID().toString();
		w.setWid(wid);
		
		w.setBase64str4image(base64str4image);
		w.setBase64str4qrcode(base64str4qrcode);
		
		
		
		
		
		return "json";
	}

	public String updateWare() {
		
		
		return "json";
	}
	
	
}

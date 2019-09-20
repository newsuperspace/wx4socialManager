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
import cc.natapp4.ddaig.service_interface.ExchangeService;
import cc.natapp4.ddaig.service_interface.WareService;
import cc.natapp4.ddaig.utils.QRCodeUtils;




@Controller("exchangeAction")
/*
 * 之所以Struts框架下的Action是多例（prototype）而不是单例（singleton）
 */
@Scope("prototype")
@Lazy(true)
public class ExchangeAction extends ActionSupport implements ModelDriven<Exchange> {

	@Autowired
	private ExchangeService exchangeService;

	
	// ==================================模型驱动=================================
	/**
	 * 这个属性特别重要，下买呢的getModel()方法是供给Struts2的模型驱动使用的，在当前
	 * Action中的各个方法如果想要获取模型（栈顶中的exchangeModel）不能通过this.getModel()来获取（每次调用就会新建一个新的bean，而不是获取栈顶中的保存有数据的bean）
	 * 而是应该使用当前类数据成员 exchangeModel， ★★
	 */
	private Exchange exchangeModel = null;

	@Override
	public Exchange getModel() {
		return this.exchangeModel;
	}
	// ==================================属性驱动=================================
	
	
	// ===================================业务方法=========================================
	
	
	
	
	
}

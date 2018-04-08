package cc.natapp4.ddaig.action;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

import cc.natapp4.ddaig.domain.cengji.MinusFirstLevel;
import cc.natapp4.ddaig.json.returnMessage.ReturnMessage4Common;
import cc.natapp4.ddaig.service_interface.MinusFirstLevelService;



@Controller("minusFirstLevelAction")
@Scope("prototype")
@Lazy(true)
public class MinusFirstLevelAction implements ModelDriven<MinusFirstLevel> {
	// =================模型驱动=================
	private MinusFirstLevel  minusFirstLevel;
	@Override
	public MinusFirstLevel getModel() {
		minusFirstLevel  =  new  MinusFirstLevel();
		return minusFirstLevel;
	}
	
	// =================DI注入=================
	@Resource(name="minusFirstLevelService")
	private MinusFirstLevelService  minusFirstLevelService;
	
	
	// ====================Action================
	public  String  createLevel(){
		MinusFirstLevel m  =  new MinusFirstLevel();
		
		m.setDescription(minusFirstLevel.getDescription());
		m.setName(minusFirstLevel.getName());
		
		minusFirstLevelService.save(m);
		
		ReturnMessage4Common  r  =  new  ReturnMessage4Common("新建成功", true);
		ActionContext.getContext().getValueStack().push(r);
		return "json";
	}
	
	public  String  getLevelList(){
		
		List<MinusFirstLevel> list = minusFirstLevelService.queryEntities();
		
		ActionContext.getContext().put("levels", list);
		return "list";
	}
	
	
}

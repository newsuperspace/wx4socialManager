package cc.natapp4.ddaig.action;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import cc.natapp4.ddaig.domain.PermissionLevel;
import cc.natapp4.ddaig.json.returnMessage.ReturnMessage4Common;
import cc.natapp4.ddaig.service_interface.PermissionLevelService;

@Controller("permissionLevelAction")
@Lazy(true)
@Scope("prototype")
public class PermissionLevelAction extends ActionSupport implements ModelDriven<PermissionLevel> {

	// ====================DI注入=====================
	@Resource(name = "permissionLevelService")
	private PermissionLevelService permissionLevelService;

	// ====================模型驱动====================
	private PermissionLevel permissionLevel = new PermissionLevel();

	@Override
	public PermissionLevel getModel() {
		return this.permissionLevel;
	}

	// ====================属性驱动====================
	// 新建权限时，承接从前端提交过来的权限状态（对应数据库中的available字段）
	private String state;

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	// ====================Action方法===================

	/**
	 * 新建权限层级
	 * 
	 * @return
	 */
	public String create() {

		ReturnMessage4Common r = new ReturnMessage4Common();

		if (StringUtils.isEmpty(permissionLevel.getDescription())
				|| StringUtils.isEmpty(permissionLevel.getPermissionLevelName())) {
			r.setMessage("关键信息为NULL，创建失败");
			r.setResult(false);
		} else {

			PermissionLevel p = new PermissionLevel();
			p.setDescription(permissionLevel.getDescription());
			p.setPermissionLevelName(permissionLevel.getPermissionLevelName());
			p.setEnabled(true); // 新建的权限状态默认为true

			switch (p.getPermissionLevelName()) {
			case "minus_first":
				p.setLevel(-1);
				break;
			case "zero":
				p.setLevel(0);
				break;
			case "first":
				p.setLevel(1);
				break;
			case "second":
				p.setLevel(2);
				break;
			case "third":
				p.setLevel(3);
				break;
			case "fourth":
				p.setLevel(4);
				break;
			}

			permissionLevelService.save(p);
			r.setMessage("权限层级" + p.getPermissionLevelName() + "创建成功");
			r.setResult(true);
		}

		ActionContext.getContext().getValueStack().push(r);
		return "json";
	}

	/**
	 * 【完成】 更新权限层级
	 * 
	 * @return
	 */
	public String update() {

		return "json";
	}

	/**
	 * 获取权限层级列表
	 * 
	 * @return
	 */
	public String getList() {
		// 供前端显示的权限层级数据
		List<PermissionLevel> permissionLevels = this.permissionLevelService.queryEntities();

		ActionContext.getContext().put("permissionLevels", permissionLevels);
		return "list";
	}
}

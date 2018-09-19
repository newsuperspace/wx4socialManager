package cc.natapp4.ddaig.action;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import cc.natapp4.ddaig.domain.Geographic;
import cc.natapp4.ddaig.domain.House;
import cc.natapp4.ddaig.domain.User;
import cc.natapp4.ddaig.domain.cengji.FirstLevel;
import cc.natapp4.ddaig.domain.cengji.FourthLevel;
import cc.natapp4.ddaig.domain.cengji.MinusFirstLevel;
import cc.natapp4.ddaig.domain.cengji.SecondLevel;
import cc.natapp4.ddaig.domain.cengji.ThirdLevel;
import cc.natapp4.ddaig.domain.cengji.ZeroLevel;
import cc.natapp4.ddaig.json.returnMessage.ReturnMessage4Common;
import cc.natapp4.ddaig.json.returnMessage.ReturnMessage4ShowUpdateModal4Geo;
import cc.natapp4.ddaig.service_interface.ActivityService;
import cc.natapp4.ddaig.service_interface.DoingProjectService;
import cc.natapp4.ddaig.service_interface.GeographicService;
import cc.natapp4.ddaig.service_interface.UserService;

@Controller("geographicAction")
@Scope("prototype")
@Lazy(true)
public class GeographicAction extends ActionSupport implements ModelDriven<Geographic>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// ==============================DI注入==============================
	@Resource(name = "activityService")
	private ActivityService activityService;
	@Resource(name = "userService")
	private UserService userService;
	@Resource(name = "doingProjectService")
	private DoingProjectService doingProjectService;
	@Resource(name = "geographicService")
	private GeographicService  geographicService;

	// ==============================属性驱动==============================

	// ==============================模型驱动==============================
	private Geographic geographic;
	@Override
	public Geographic getModel() {
		this.geographic  =  new Geographic();
		return this.geographic;
	}
	// ==============================Methods==============================
	
	/**
	 * 获取当前用户所管理的层级对象之下所创建的所有Geographic地理位置坐标
	 * 然后放入到List容器中，并存入到值栈空间，供给geoList.jsp页面显示数据
	 * @return
	 */
	public String getGeoList(){
		
		Subject subject = SecurityUtils.getSubject();
		String principal = (String) subject.getPrincipal();
		// 执行当前新建操作的管理者的User对象
		User doingMan = null;
		// 标记当前执行者是否是admin
		boolean isAdmin = false;
		if (28 == principal.length()) {
			// openID是恒定不变的28个字符，说明本次登陆是通过openID登陆的（微信端自动登陆/login.jsp登陆）
			doingMan = userService.queryByOpenId(principal);
		} else {
			// 用户名登陆（通过signin.jsp页面的表单提交的登陆）
			// 先判断是不是使用admin+admin 的方式登录的测试管理员
			if ("admin".equals(principal)) {
				isAdmin = true;
			} else {
				// 非admin用户登录
				doingMan = userService.getUserByUsername(principal);
			}
		}
		
		List<Geographic> geos = null;
		if(isAdmin){
			geos = geographicService.queryEntities();
		}else{
			String tag = doingMan.getGrouping().getTag();
			switch (tag) {
			case "minus_first":
				Set<MinusFirstLevel> mfls = doingMan.getManager().getMfls();
				for(MinusFirstLevel mfl: mfls){
					List<Geographic> geographics = mfl.getGeographics();
					geos = geographics;
				}
				break;
			case "zero":
				Set<ZeroLevel> zls = doingMan.getManager().getZls();
				for(ZeroLevel zl:zls){
					List<Geographic> geographics = zl.getGeographics();
					geos = geographics;
				}
				break;
			case "first":
				Set<FirstLevel> fls = doingMan.getManager().getFls();
				for(FirstLevel fl:fls){
					List<Geographic> geographics = fl.getGeographics();
					geos = geographics;
				}
				break;
			case "second":
				Set<SecondLevel> scls = doingMan.getManager().getScls();
				for(SecondLevel scl: scls){
					List<Geographic> geographics = scl.getGeographics();
					geos = geographics;
				}
				break;
			case "third":
				Set<ThirdLevel> tls = doingMan.getManager().getTls();
				for(ThirdLevel tl:tls){
					List<Geographic> geographics = tl.getGeographics();
					geos = geographics;
				}
				break;
			case "fourth":
				Set<FourthLevel> fols = doingMan.getManager().getFols();
				for(FourthLevel fol:fols){
					List<Geographic> geographics = fol.getGeographics();
					geos = geographics;
				}
				break;
			}
		}
		
		ActionContext.getContext().put("geos", geos);
		return SUCCESS;
	}
	
	/**
	 * AJAX
	 * 新建位置
	 * @return
	 */
	public String createGeo(){
		
		Subject subject = SecurityUtils.getSubject();
		String principal = (String) subject.getPrincipal();
		// 执行当前新建操作的管理者的User对象
		User doingMan = null;
		// 标记当前执行者是否是admin
		boolean isAdmin = false;
		if (28 == principal.length()) {
			// openID是恒定不变的28个字符，说明本次登陆是通过openID登陆的（微信端自动登陆/login.jsp登陆）
			doingMan = userService.queryByOpenId(principal);
		} else {
			// 用户名登陆（通过signin.jsp页面的表单提交的登陆）
			// 先判断是不是使用admin+admin 的方式登录的测试管理员
			if ("admin".equals(principal)) {
				isAdmin = true;
			} else {
				// 非admin用户登录
				doingMan = userService.getUserByUsername(principal);
			}
		}
		
		// ---------------正式的业务逻辑---------------
		ReturnMessage4Common  result  =  new ReturnMessage4Common();
		
		List<Geographic> geos = null;
		Geographic g  =  null;
		if(isAdmin){
			result.setResult(false);
			result.setMessage("您是管理员，无权创建活动地点");
		}else{
			String tag = doingMan.getGrouping().getTag();
			switch (tag) {
			case "minus_first":
				Set<MinusFirstLevel> mfls = doingMan.getManager().getMfls();
				for(MinusFirstLevel mfl: mfls){
					List<Geographic> geographics = mfl.getGeographics();
					g =  new Geographic();
					g.setName(this.geographic.getName());
					g.setDescription(this.geographic.getDescription());
					g.setLongitude(this.geographic.getLongitude());
					g.setLatitude(this.geographic.getLatitude());
					g.setLevel(-1);
					g.setMinusFirstLevel(mfl);
					geos = mfl.getGeographics();
					if(null==geos){
						geos = new ArrayList<Geographic>();
						geos.add(g);
						mfl.setGeographics(geos);
					}else{
						geos.add(g);
					}
					geographicService.save(g);
				}
				break;
			case "zero":
				Set<ZeroLevel> zls = doingMan.getManager().getZls();
				for(ZeroLevel zl:zls){
					List<Geographic> geographics = zl.getGeographics();
					g =  new Geographic();
					g.setName(this.geographic.getName());
					g.setDescription(this.geographic.getDescription());
					g.setLongitude(this.geographic.getLongitude());
					g.setLatitude(this.geographic.getLatitude());
					g.setLevel(0);
					g.setZeroLevel(zl);;
					geos = zl.getGeographics();
					if(null==geos){
						geos = new ArrayList<Geographic>();
						geos.add(g);
						zl.setGeographics(geos);
					}else{
						geos.add(g);
					}
					geographicService.save(g);
				}
				break;
			case "first":
				Set<FirstLevel> fls = doingMan.getManager().getFls();
				for(FirstLevel fl:fls){
					List<Geographic> geographics = fl.getGeographics();
					g =  new Geographic();
					g.setName(this.geographic.getName());
					g.setDescription(this.geographic.getDescription());
					g.setLongitude(this.geographic.getLongitude());
					g.setLatitude(this.geographic.getLatitude());
					g.setLevel(1);
					g.setFirstLevel(fl);;
					geos = fl.getGeographics();
					if(null==geos){
						geos = new ArrayList<Geographic>();
						geos.add(g);
						fl.setGeographics(geos);
					}else{
						geos.add(g);
					}
					geographicService.save(g);
				}
				break;
			case "second":
				Set<SecondLevel> scls = doingMan.getManager().getScls();
				for(SecondLevel scl: scls){
					List<Geographic> geographics = scl.getGeographics();
					g =  new Geographic();
					g.setName(this.geographic.getName());
					g.setDescription(this.geographic.getDescription());
					g.setLongitude(this.geographic.getLongitude());
					g.setLatitude(this.geographic.getLatitude());
					g.setLevel(2);
					g.setSecondLevel(scl);;
					geos = scl.getGeographics();
					if(null==geos){
						geos = new ArrayList<Geographic>();
						geos.add(g);
						scl.setGeographics(geos);
					}else{
						geos.add(g);
					}
					geographicService.save(g);
				}
				break;
			case "third":
				Set<ThirdLevel> tls = doingMan.getManager().getTls();
				for(ThirdLevel tl:tls){
					List<Geographic> geographics = tl.getGeographics();
					g =  new Geographic();
					g.setName(this.geographic.getName());
					g.setDescription(this.geographic.getDescription());
					g.setLongitude(this.geographic.getLongitude());
					g.setLatitude(this.geographic.getLatitude());
					g.setLevel(3);
					g.setThirdLevel(tl);;
					geos = tl.getGeographics();
					if(null==geos){
						geos = new ArrayList<Geographic>();
						geos.add(g);
						tl.setGeographics(geos);
					}else{
						geos.add(g);
					}
					geographicService.save(g);
				}
				break;
			case "fourth":
				Set<FourthLevel> fols = doingMan.getManager().getFols();
				for(FourthLevel fol:fols){
					List<Geographic> geographics = fol.getGeographics();
					g =  new Geographic();
					g.setName(this.geographic.getName());
					g.setDescription(this.geographic.getDescription());
					g.setLongitude(this.geographic.getLongitude());
					g.setLatitude(this.geographic.getLatitude());
					g.setLevel(4);
					g.setFourthLevel(fol);;
					geos = fol.getGeographics();
					if(null==geos){
						geos = new ArrayList<Geographic>();
						geos.add(g);
						fol.setGeographics(geos);
					}else{
						geos.add(g);
					}
					geographicService.save(g);
				}
				break;
			}
			result.setResult(true);
			result.setMessage("新建成功！");
		}
		
		ActionContext.getContext().getValueStack().push(result);
		return "json";
	}

	/**
	 * AJAX
	 * 更新位置坐标数据的数据回显
	 * @return
	 */
	public String showUpdateModal(){
		
		Geographic g  =  geographicService.queryEntityById(this.geographic.getGeoid());
		ReturnMessage4ShowUpdateModal4Geo  result = new ReturnMessage4ShowUpdateModal4Geo();
		if(null!=g){
			result.setGeographic(g);
			result.setResult(true);
		}else{
			result.setResult(false);
		}
		
		ActionContext.getContext().getValueStack().push(result);
		return "json";
	}

	/**
	 * AJAX
	 * 更新位置信息
	 * @return
	 */
	public String updateGeo(){
		ReturnMessage4Common result = new ReturnMessage4Common();
		Geographic g =  geographicService.queryEntityById(this.geographic.getGeoid());
		if (null == g) {
			result.setMessage("不存在您所要更新的活动地点");
		} else {
			g.setName(this.geographic.getName());
			g.setDescription(this.geographic.getDescription());
			g.setLongitude(this.geographic.getLongitude());
			g.setLatitude(this.geographic.getLatitude());
			g.setRadus(geographic.getRadus());
			geographicService.update(g);
			result.setMessage("更新成功！");
		}

		ActionContext.getContext().getValueStack().push(result);
		return "json";
	}
	
	/**
	 * AJAX
	 * 关闭位置坐标
	 * @return
	 */
	public String closeGeo(){
		Geographic g = geographicService.queryEntityById(this.geographic.getGeoid());

		ReturnMessage4Common result = new ReturnMessage4Common();
		if (null == g) {
			result.setMessage("不存在指定活动地点的数据信息");
		} else {
			g.setEnable(false);
			geographicService.update(g);
			result.setMessage("活动地点已关闭");
		}

		ActionContext.getContext().getValueStack().push(result);
		return "json";
	}
	
	/**
	 * AJAX
	 * 启用位置坐标
	 * @return
	 */
	public String openGeo(){
		Geographic g = geographicService.queryEntityById(this.geographic.getGeoid());

		ReturnMessage4Common result = new ReturnMessage4Common();
		if (null == g) {
			result.setMessage("不存在指定活动地点的数据信息");
		} else {
			g.setEnable(true);
			geographicService.update(g);
			result.setMessage("活动地点已开启");
		}

		ActionContext.getContext().getValueStack().push(result);
		return "json";
	}
	
	/**
	 * AJAX
	 * 删除位置坐标
	 * @return
	 */
	public String deleteGeo(){
		ReturnMessage4Common result = new ReturnMessage4Common();
		Geographic g = geographicService.queryEntityById(this.geographic.getGeoid());
		
		if (null == g) {
			result.setMessage("您所要删除的活动地点不存在");
		} else {
			// 删除活动室
			int level = g.getLevel();
			// 挑选出活动地点所属的层级对象
			switch (level) {
			case -1:
				MinusFirstLevel minusFirstLevel = g.getMinusFirstLevel();
				minusFirstLevel.getGeographics().remove(g);
				g.setMinusFirstLevel(null);
				break;
			case 0:
				ZeroLevel zeroLevel = g.getZeroLevel();
				zeroLevel.getGeographics().remove(g);
				g.setZeroLevel(null);
				break;
			case 1:
				FirstLevel firstLevel = g.getFirstLevel();
				firstLevel.getGeographics().remove(g);
				g.setFirstLevel(null);
				break;
			case 2:
				SecondLevel secondLevel = g.getSecondLevel();
				secondLevel.getGeographics().remove(g);
				g.setSecondLevel(null);
				break;
			case 3:
				ThirdLevel thirdLevel = g.getThirdLevel();
				thirdLevel.getGeographics().remove(g);
				g.setThirdLevel(null);
				break;
			case 4:
				FourthLevel fourthLevel = g.getFourthLevel();
				fourthLevel.getGeographics().remove(g);
				g.setFourthLevel(null);
				break;
			}
			
			geographicService.delete(g);
			result.setMessage("删除成功！");
		}

		ActionContext.getContext().getValueStack().push(result);
		return "json";
	}
	
	/**
	 * AJAX
	 * 获取位置坐标的详细信息到前端显示
	 * @return
	 */
	public String getInfo(){
		
		return "json";
	}
}

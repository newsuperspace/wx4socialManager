package cc.natapp4.ddaig.domain.cengji;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.struts2.json.annotations.JSON;

import cc.natapp4.ddaig.domain.BesureProject;
import cc.natapp4.ddaig.domain.DoingProject;
import cc.natapp4.ddaig.domain.Geographic;
import cc.natapp4.ddaig.domain.Manager;
import cc.natapp4.ddaig.domain.Member;
import cc.natapp4.ddaig.domain.Permission;
import cc.natapp4.ddaig.domain.User;

/**
 * 层级化管理结构对象First层级——大类型
 * 
 * 固定六大类型 党组织 区域化党建单位组织 居民代表 楼门组长 社区社会组织 志愿者
 * 
 * @author Administrator
 *
 */
public class FirstLevel implements LevelInterface {

	// ---------------------基本信息--------------------
	// 主键
	private String flid;
	// 当前类型或团队的名字
	private String name;
	// 当前类型或团队的描述
	private String description;
	// ---------------------层级化结构-----------------
	// 标注当前类所描述的管理层级别是1级（最高级）
	private int level = LEVEL_ONE;
	// 当前层级对象的父层级对象
	private ZeroLevel parent;
	// 存放属于当前级别的下一级别的级别对象（一对多）
	private Set<SecondLevel> children;
	// 当前层级的管理者（一对多）
	private List<Manager> managers;
	// 当前层级对象所管辖的成员（一对多）
	private Set<Member> members;
	// 当前层级对象所能行使的权限（多对多）
	private Set<Permission> permissions;
	// qrcode 的相對路徑
	// ,包含形如："level:-1;id:293jjf8239832jf8j298ufd987sfh28923"的字符串的二維碼被放置在形如"qrcode/1/12/xxxx.gif"之下
	private String qrcode;
	// 层级对象的qrcode是从微信端换取的临时带参数二维码，有效期是30天，这里记录的就是换取二维码时的时间戳（格里高利历偏毫秒值偏移量）
	private long qrcodeTime;
	
	// ---------------------项目管理------------------
	// 当前层级之下正在进行的项目列表（一对多）
	private Set<DoingProject> doingProjects;
	// 当前层级之下等待审核的项目列表
	private Set<BesureProject> besureProjects;
	// 当前层级对象所创建的点位
	private List<Geographic> geographics;
	

	// ==================================SETTERs/GETTERs=====================================
	

	@JSON(serialize = false)
	public List<Geographic> getGeographics() {
		return geographics;
	}
	public void setGeographics(List<Geographic> geographics) {
		this.geographics = geographics;
	}

	
	public long getQrcodeTime() {
		return qrcodeTime;
	}

	public void setQrcodeTime(long qrcodeTime) {
		this.qrcodeTime = qrcodeTime;
	}

	public String getFlid() {
		return flid;
	}

	public void setFlid(String flid) {
		this.flid = flid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public ZeroLevel getParent() {
		return parent;
	}

	public void setParent(ZeroLevel parent) {
		this.parent = parent;
	}

	/*
	 * 不需要随时都跟随当前层级对象显示在页面上，因此加上@JSON提高json解析效率
	 */
	@JSON(serialize = false)
	public Set<SecondLevel> getChildren() {
		return children;
	}

	public void setChildren(Set<SecondLevel> children) {
		this.children = children;
	}

	/*
	 * 前端可能在组织层级化对象表格的时候，需要及时显示该层级对象的当前管理者姓名
	 * 因此manager内容需要经常伴随层级对象一起传给前端，因此不能加@JSON
	 * 而应该在Manager类中对应Set<FirstLevel>获取的GETTER方法上添加@JSON注解
	 * 以防止在struts-json-plugin.jar插件组织JSON字符串时出现死循环
	 */
	@JSON(serialize = false)
	public List<Manager> getManagers() {
		return managers;
	}
	public void setManagers(List<Manager> managers) {
		this.managers = managers;
	}
	
	
	/*
	 * 层级对象的成员，无需随时都跟随着层级对象返回到前端，需要的时候 在临时获取然后将Set<Member>返回到前端即可，因此需要添加@JSON
	 * 注解提高struts-json-plugin.jar的执行效率
	 */
	@JSON(serialize = false)
	public Set<Member> getMembers() {
		return members;
	}


	public void setMembers(Set<Member> members) {
		this.members = members;
	}

	/*
	 * 前端很少会随着获取层级对象的时候获取权限内容，只会单独获取 指定层级对象的权限，因此到那个时候再直接向栈顶返回Set<Permission>
	 * 容器对象即可，无需每时每刻都随着层级对象获取权限数据，因此这里需要加上
	 * 
	 * @JSON 以提高执行效率
	 */
	@JSON(serialize = false)
	public Set<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(Set<Permission> permissions) {
		this.permissions = permissions;
	}

	/*
	 * 层级越高的层级对象，其之下所拥有的“正在进行中”项目的数量就会越多，
	 * 有时候我们前端只是想获取层级对象的基本数据（名称、描述等）但struts-json-plugin.jar
	 * 插件在扫描解析位于栈顶的层级对象的时候如果所执行的GETTER方法上没有@JSON注解，则
	 * 会对GETTER返回的值深入进行遍历解析，这样会浪费很多时间，因此这里需要添加@JSON
	 * 如果前端真的需要获取某个层级对象管辖范围内的项目，大可直接将GETTER方法返回的set容器 放到栈顶扫描成JSON格式字符串传递给前端也不迟。
	 */
	@JSON(serialize = false)
	public Set<DoingProject> getDoingProjects() {
		return doingProjects;
	}

	public void setDoingProjects(Set<DoingProject> doingProjects) {
		this.doingProjects = doingProjects;
	}

	/*
	 * 待审核项目包含审核通过的、每通过的历史项目、正在审核的项目等 会随着时间数据变得越来越多，因此这里应该设置JSON注解，防止
	 * struts-json-plugin.jar插件在扫描解析位于值栈顶端的层级化对象 的时候在这上面花费大多时间，如果前端真的需要获取这部分数据
	 * 大可将这个SET容器直接获取并放置在栈顶即可。
	 */
	@JSON(serialize = false)
	public Set<BesureProject> getBesureProjects() {
		return besureProjects;
	}

	public void setBesureProjects(Set<BesureProject> besureProjects) {
		this.besureProjects = besureProjects;
	}

	public String getQrcode() {
		return qrcode;
	}

	public void setQrcode(String qrcode) {
		this.qrcode = qrcode;
	}

}

package cc.natapp4.ddaig.domain.cengji;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.struts2.json.annotations.JSON;

import cc.natapp4.ddaig.domain.BesureProject;
import cc.natapp4.ddaig.domain.DoingProject;
import cc.natapp4.ddaig.domain.Manager;
import cc.natapp4.ddaig.domain.Member;
import cc.natapp4.ddaig.domain.Permission;

/**
 * 社区分层的第一层级——大类型
 * 
 * 党组织 区域化党建单位组织 居民代表 楼门组长 社区社会组织 志愿者
 * 
 * @author Administrator
 *
 */
public class ZeroLevel implements LevelInterface {

	// ---------------------基本信息--------------------
	// 主键
	private String zid;
	// 当前类型或团队的名字
	private String name;
	// 当前类型或团队的描述
	private String description;
	// ---------------------层级化结构-----------------
	// 标注当前类所描述的管理层级别是1级（最高级）
	private int level = LEVEL_ZERO;
	// 当前层级对象的父层级对象
	private MinusFirstLevel parent;
	// 存放属于当前级别的下一级别的级别对象（一对多）
	private Set<FirstLevel> children;
	// 当前层级的管理者（一对一）
	private Manager manager;
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

	// ------------专供前端通过Ajax获取数据是，必须要获取到子层级对象的有关数据时存在的容器属性，这些属性与数据库没有任何关系-------------
	private List<FirstLevel> children4Ajax;
	private List<FirstLevel> allChildren4Ajax;

	// ==================================SETTERs/GETTERs=====================================
	// AJAX
	public List<FirstLevel> getAllChildren4Ajax() {

		List<FirstLevel> list = new ArrayList<FirstLevel>();

		Set<FirstLevel> ch = this.getChildren();
		Iterator<FirstLevel> iterator = ch.iterator();
		while (iterator.hasNext()) {
			FirstLevel first = iterator.next();
			first.setParent(null); // 切断父子关系，防止@JSON解析的时候出现死循环
			list.add(first);
		}
		return list;
	}

	// AJAX
	public List<FirstLevel> getChildren4Ajax() {

		List<FirstLevel> list = new ArrayList<FirstLevel>();

		Set<FirstLevel> ch = this.getChildren();
		Iterator<FirstLevel> iterator = ch.iterator();
		while (iterator.hasNext()) {
			FirstLevel first = iterator.next();
			if (null == first.getManager()) {
				first.setParent(null); // 切断父子关系，防止@JSON解析的时候出现死循环
				list.add(first);
			}
		}
		return list;
	}

	
	
	public long getQrcodeTime() {
		return qrcodeTime;
	}

	public void setQrcodeTime(long qrcodeTime) {
		this.qrcodeTime = qrcodeTime;
	}

	public String getZid() {
		return zid;
	}

	public void setZid(String zid) {
		this.zid = zid;
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

	public MinusFirstLevel getParent() {
		return parent;
	}

	public void setParent(MinusFirstLevel parent) {
		this.parent = parent;
	}

	@JSON(serialize = false)
	public Set<FirstLevel> getChildren() {
		return children;
	}

	public void setChildren(Set<FirstLevel> children) {
		this.children = children;
	}

	public Manager getManager() {
		return manager;
	}

	public void setManager(Manager manager) {
		this.manager = manager;
	}

	@JSON(serialize = false)
	public Set<Member> getMembers() {
		return members;
	}

	public void setMembers(Set<Member> members) {
		this.members = members;
	}

	@JSON(serialize = false)
	public Set<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(Set<Permission> permissions) {
		this.permissions = permissions;
	}

	@JSON(serialize = false)
	public Set<DoingProject> getDoingProjects() {
		return doingProjects;
	}

	public void setDoingProjects(Set<DoingProject> doingProjects) {
		this.doingProjects = doingProjects;
	}

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

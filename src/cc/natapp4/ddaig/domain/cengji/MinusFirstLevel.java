package cc.natapp4.ddaig.domain.cengji;

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
public class MinusFirstLevel implements LevelInterface {
	
	//---------------------基本信息--------------------
	// 主键
	private String mflid;
	// 当前类型或团队的名字
	private String name;
	// 当前类型或团队的描述
	private String description;
	//---------------------层级化结构-----------------
	// 标注当前类所描述的管理层级别是1级（最高级）
	private int level = LEVEL_MINUS_FIRST;
	// 当前层级对象的父层级对象
//	private MinusFirstLevel parent;
	// 存放属于当前级别的下一级别的级别对象（一对多）
	private Set<ZeroLevel> children;
	// 当前层级的管理者（一对一）
	private Manager manager;
	// 当前层级对象所管辖的成员（一对多）
	private Set<Member> members;
	// 当前层级对象所能行使的权限（多对多）
	private Set<Permission>  permissions;
	// qrcode 的相對路徑  ,包含形如："level:-1;id:293jjf8239832jf8j298ufd987sfh28923"的字符串的二維碼被放置在形如"qrcode/1/12/xxxx.gif"之下
	private String  qrcode;
	//---------------------项目管理------------------
	// 当前层级之下正在进行的项目列表（一对多）
	private Set<DoingProject> doingProjects;
	// 当前层级之下等待审核的项目列表
	private Set<BesureProject>  besureProjects;
	
	// ==================================SETTERs/GETTERs=====================================
	public String getMflid() {
		return mflid;
	}
	public void setMflid(String mflid) {
		this.mflid = mflid;
	}
	public String getQrcode() {
		return qrcode;
	}
	public void setQrcode(String qrcode) {
		this.qrcode = qrcode;
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
	
	@JSON(serialize=false)
	public Set<ZeroLevel> getChildren() {
		return children;
	}
	public void setChildren(Set<ZeroLevel> children) {
		this.children = children;
	}
	
	public Manager getManager() {
		return manager;
	}
	public void setManager(Manager manager) {
		this.manager = manager;
	}
	
	@JSON(serialize=false)
	public Set<Member> getMembers() {
		return members;
	}
	public void setMembers(Set<Member> members) {
		this.members = members;
	}
	
	@JSON(serialize=false)
	public Set<Permission> getPermissions() {
		return permissions;
	}
	public void setPermissions(Set<Permission> permissions) {
		this.permissions = permissions;
	}
	
	@JSON(serialize=false)
	public Set<DoingProject> getDoingProjects() {
		return doingProjects;
	}
	public void setDoingProjects(Set<DoingProject> doingProjects) {
		this.doingProjects = doingProjects;
	}
	
	@JSON(serialize=false)
	public Set<BesureProject> getBesureProjects() {
		return besureProjects;
	}
	public void setBesureProjects(Set<BesureProject> besureProjects) {
		this.besureProjects = besureProjects;
	}

	
	
	
}

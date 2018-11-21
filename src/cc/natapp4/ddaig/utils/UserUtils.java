package cc.natapp4.ddaig.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.struts2.ServletActionContext;

import cc.natapp4.ddaig.bean.User4Ajax;
import cc.natapp4.ddaig.domain.Manager;
import cc.natapp4.ddaig.domain.Member;
import cc.natapp4.ddaig.domain.User;

/**
 * 处理有关User、User4Ajax、manager、member之间的常用逻辑功能
 * @author Administrator
 *
 */
public class UserUtils {

	
	/**
	 * 将查询到的user，按照当前操作者层级的情况整理成为user4AJax
	 * 详细的整理过程可以参考UserAction.getUserInfo()  
	 * 本工具方法就是用于批量处理从User到User4Ajax操作的
	 * @param users
	 * @return
	 */
	public static  List<User4Ajax>  userToUser4Ajax(List<User> users, boolean  isAdmin){
		List<User4Ajax>  user4Ajaxs  =  new  ArrayList<User4Ajax>();
		// 获取当前操作者层级的信息
		String lid = (String) ServletActionContext.getRequest().getSession().getAttribute("lid");
		String tag = (String) ServletActionContext.getRequest().getSession().getAttribute("tag");		
		
		User4Ajax  u  =  null;
		// 存放被遍历的user其在当前操作者层级直辖的member对象
		List<Member>  members  =  null;
		for(User user: users){
			u  =  new  User4Ajax();
			// ①现处理两个可能用到的属性
			String qrcodeUrl = ServletActionContext.getServletContext().getContextPath() + "/" + user.getQrcode();
			u.setQrcode(qrcodeUrl);
			// 处理注册时间，根据long类型的格力高丽丽偏移量毫秒值 经过格式转化成前端用户可识别的字符串信息
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			u.setRegistrationTimeStr(format.format(new Date(u.getRegistrationTime())));
			/*
			 *  ②处理members
			 *  ③ 判断被遍历的user是否为当前操作者层级的直辖成员，如果是则添加该member到user4AJax.member
			 */
			members = new ArrayList<Member>();
			for (Member m : user.getMembers()) {
				switch (tag) {
				case "minus_first":
					// minus_first层级我们就直接遍历这些member的minusFirstLevel字段，查看lid是否相同，相同则该member记录的就是本层级及其子层级的信息
					if (null != m.getMinusFirstLevel() && m.getMinusFirstLevel().getMflid().equals(lid)) {
						members.add(m);
					}
					/*
					 * 判断标准解读： 如果便利出的member存在MinusFirstLevel层级对象，且该层级对象与操作者对象（
					 * 同样也是MinusFirstLevel层级）的lid相同
					 * 并且MinusFirst次一级，也就是Zero级对象zeroLevel为null，
					 * 则说明当前遍历出来的member代表的是用户所加入的一个
					 * MinusFirst层级且这个MinusFirst层级就是当前操作者层级（因为lid相同）
					 */
					if (null != m.getMinusFirstLevel() && m.getMinusFirstLevel().getMflid().equals(lid)
							&& null == m.getZeroLevel()) {
						u.setMember(m);
					}
					break;
				case "zero":
					// zero层级我们就直接遍历这些member的zeroLevel字段，查看lid是否相同，相同则该member记录的就是本层级及其子层级的信息
					if (null != m.getZeroLevel() && m.getZeroLevel().getZid().equals(lid)) {
						members.add(m);
					}
					if (null != m.getZeroLevel() && m.getZeroLevel().getZid().equals(lid)
							&& null == m.getFirstLevel()) {
						u.setMember(m);
					}
					break;
				case "first":
					// first层级我们就直接遍历这些member的FirstLevel字段，查看lid是否相同，相同则该member记录的就是本层级及其子层级的信息
					if (null != m.getFirstLevel() && m.getFirstLevel().getFlid().equals(lid)) {
						members.add(m);
					}
					if (null != m.getFirstLevel() && m.getFirstLevel().getFlid().equals(lid)
							&& null == m.getSecondLevel()) {
						u.setMember(m);
					}
					break;
				case "second":
					// second层级我们就直接遍历这些member的secondLevel字段，查看lid是否相同，相同则该member记录的就是本层级及其子层级的信息
					if (null != m.getSecondLevel() && m.getSecondLevel().getScid().equals(lid)) {
						members.add(m);
					}
					if (null != m.getSecondLevel() && m.getSecondLevel().getScid().equals(lid)
							&& null == m.getThirdLevel()) {
						u.setMember(m);
					}
					break;
				case "third":
					// third层级我们就直接遍历这些member的thirdLevel字段，查看lid是否相同，相同则该member记录的就是本层级及其子层级的信息
					if (null != m.getThirdLevel() && m.getThirdLevel().getThid().equals(lid)) {
						members.add(m);
					}
					if (null != m.getThirdLevel() && m.getThirdLevel().getThid().equals(lid)
							&& null == m.getFourthLevel()) {
						u.setMember(m);
					}
					break;
				case "fourth":
					// fourth层级我们就直接遍历这些member的fourthLevel字段，查看lid是否相同，相同则该member记录的就是本层级及其子层级的信息
					if (null != m.getFourthLevel() && m.getFourthLevel().getFoid().equals(lid)) {
						members.add(m);
					}
					if (null != m.getFourthLevel() && m.getFourthLevel().getFoid().equals(lid)) {
						u.setMember(m);
					}
					break;
					
				default: // admin
					// 如果当前查看该用户的操作者是Admin，那么就将它的所有member都放进去
					members = user.getMembers();
					// 对于admin来说，由于所有用户必定永远存在默认member，因此都是admin直辖对象
					if (null == m.getMinusFirstLevel() && null == m.getZeroLevel() && null == m.getFirstLevel()
							&& null == m.getSecondLevel() && null == m.getThirdLevel()&& null == m.getFourthLevel()) {
						u.setMember(m);
					}
					break;
				}
			}
			u.setMembers(members);
			// ④如果当前人员是当前操作者层级的直辖人员，则user4AJax.managers中因该存放其在所管理的所有操作者层级的直接子层级的manager情况
			/**
			 * managers中存放的是用户作为操作者层级的直辖人员对于其直接自层级的管理信息
			 * 如果用户是当前操作者层级的直辖人员（u.member!=null），则可以从中找到该用户的所有任职情况
			 */
			if (null != u.getMember()) {
				List<Manager> managers = u.getMember().getManagers();
				u.setManagers(managers);
			}
			// ⑤ 处理遍历到的该用户可被设置的grouping.tag
			ArrayList<String> tagsList = null;
			// 注意只有该用户是当前操作者层级的直辖人员，才有权利更改其member.grouping.tag 否则不可以
			if(null!=u.getMember()){
				tagsList = new ArrayList<String>();
				if (isAdmin) {
					/*
					 * adming管理员用户有权分配街道层级用户，而不能越级分配，防止出现人员在层级结构中的混乱（明明是某个第四层及的member，
					 * 却被分配了社区层级的tag，这是不合理的） 因此一切涉及有关权限/层级/安全的事务，都遵循“就近原则”——不在其位，不谋其政。
					 */
					tagsList.add("unreal");
					tagsList.add("common");
					tagsList.add("minus_first");
				} else {
					/*
					 * 同理 非admin，则根据实际情况来设置tags（只能设置低于当前操作者层次的tag）
					 * ，而不能越级分配，防止出现人员在层级结构中的混乱（明明是某个第四层及的member，却被分配了社区层级的tag，这是不合理的）
					 * 因此一切涉及有关权限/层级/安全的事务，都遵循“就近原则”——不在其位，不谋其政。
					 */
					switch (tag) {
					case "minus_first":
						tagsList.add("zero");
						tagsList.add("common");
						break;
					case "zero":
						tagsList.add("first");
						tagsList.add("common");
						break;
					case "first":
						tagsList.add("second");
						tagsList.add("common");
						break;
					case "second":
						tagsList.add("third");
						tagsList.add("common");
						break;
					case "third":
						tagsList.add("fourth");
						tagsList.add("common");
						break;
					case "fourth":
						tagsList.add("common");
						break;
					}
				}
			}
			/*
			 * 当需要将List转化成数组Array的时候是需要像如下方式实现的， 给ArrayList.toArray()传递一个数组实例作为参数。★
			 */
			String[] tags = (String[]) tagsList.toArray(new String[0]);
			u.setTags(tags);
			
			// 最后就是将user中的常规属性设置到user4ajax上即可
			u.setAddress(user.getAddress());
			u.setAge(user.getAge());
			u.setBirth(user.getBirth());
			u.setCardid(user.getCardid());
			u.setEmail(user.getEmail());
			u.setIshere(user.isIshere());
			u.setLocked(user.isLocked());
			u.setOpenid(user.getOpenid());
			u.setPhone(user.getPhone());
			u.setRegistrationTime(user.getRegistrationTime());
			u.setScore(user.getScore());
			u.setServeTime(user.getServeTime());
			u.setSex(user.getSex());
			u.setSickname(user.getSickname());
			u.setUid(user.getUid());
			u.setUsername(user.getUsername());
			// 将存放着User数据的user4Ajax类型的u变量放入到容器中，完成这一次for循环
			user4Ajaxs.add(u);
		}
		// for循环完成，返回装着user数据的容器
		return  user4Ajaxs;
	}
	
}

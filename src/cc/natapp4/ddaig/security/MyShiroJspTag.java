package cc.natapp4.ddaig.security;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.tags.PermissionTag;

/**
 * 自定义shiro的JSP标签，该功能仅限于JSP页面上产生显示/隐藏作用，与Shiro的Filter在底层决定是否准许通过请求是不一样的。
 * 
 * 由于Shiro的自定义JSP标签缺少类似hasAnyRoles的hasAnyPermissions的标签（权限列表是or的关系即可通过认证）
 * 因此这里我们自己定义了一个JSP标签，而且我们的myShiro.tld已经保存在了/WEB-INF/tlds中了，只需要在JSP页面上像
 * menu.jsp那样通过JSP处理指令引入即可使用产生效果。
 * 
 * @author Administrator
 *
 */
public class MyShiroJspTag extends PermissionTag {

	private static final long serialVersionUID = 1L;

	@Override
	protected boolean showTagBody(String permissions) {

		boolean hasAnyPermissions = false;

		Subject subject = getSubject();

		if (subject != null) {
			for (String role : permissions.split(",")) {

				if (subject.isPermitted(role.trim())) {
					hasAnyPermissions = true;
					break;
				}
			}
		}
		return hasAnyPermissions;
	}

}

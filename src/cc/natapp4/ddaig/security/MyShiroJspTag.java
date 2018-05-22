package cc.natapp4.ddaig.security;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.tags.PermissionTag;

public class MyShiroJspTag extends PermissionTag {

	/**
	 * 
	 */
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

package cc.natapp4.ddaig.domain.cengji;

import java.io.Serializable;
import java.util.Set;

import cc.natapp4.ddaig.domain.Permission;
import cc.natapp4.ddaig.domain.Role;
import cc.natapp4.ddaig.domain.User;


public interface LevelInterface extends Serializable {

	/**
	 * 标注当前类所描述的管理层级别是多少
	 * 从高到低，分别是1~5级
	 */
	public static final Integer LEVEL_ONE = 1;   // 社区六大类（固有）
	public static final Integer LEVEL_TWO = 2;   // 每个大类下的次级类型（各个主管大类的主任自建后分配给社工）
	public static final Integer LEVEL_THREE = 3;  // 每个次级类型下的团队（社工根据所管理的团队自建后分配给队总队长）
	public static final Integer LEVEL_FOUR = 4;   // 每个团队下的支队（总队长根据团队支队情况自建并分配给支队长）
//	public static final Integer LEVEL_FIVE = 5;
	
	/**
	 * 获取当前类型或团队的层级级别
	 * 1 大类型
	 * 2 次级类型
	 * @return
	 */
	public int getLevel();
	
	/**
	 * 获取当前层级的管理者所拥有的权限集合（也就是角色）
	 * @return
	 */
	public Role getRole();
	
	/**
	 * 获取团队或类型名称
	 * @return
	 */
	public String  getName();
	
	/**
	 * 获取团队或类型的描述说明
	 * @return
	 */
	public String getDescription();

	/**
	 * 获取当前类型或团队的管理者
	 * @return
	 */
	public User getManager();

}

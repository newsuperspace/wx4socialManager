package cc.natapp4.ddaig.domain.cengji;

import java.io.Serializable;
import java.util.Set;

import cc.natapp4.ddaig.domain.Manager;
import cc.natapp4.ddaig.domain.Permission;
import cc.natapp4.ddaig.domain.User;


public interface LevelInterface extends Serializable {

	/**
	 * 标注当前类所描述的管理层级别是多少
	 * 从高到低，分别是-1~4级
	 */
	public static final Integer LEVEL_MINUS_FIRST = -1;   // 街道层级
	public static final Integer LEVEL_ZERO = 0;   // 社区层级（呼家楼固有的10个社区）
	public static final Integer LEVEL_ONE = 1;   // 社区六大类（固有的六大类）
	public static final Integer LEVEL_TWO = 2;   // 每个大类下的次级类型（各个主管大类的主任自建后分配给社工）
	public static final Integer LEVEL_THREE = 3;  // 每个次级类型下的团队（社工根据所管理的团队自建后分配给队总队长）
	public static final Integer LEVEL_FOUR = 4;   // 每个团队下的支队（总队长根据团队支队情况自建并分配给支队长）
//	public static final Integer LEVEL_FIVE = 5;
	
	/**
	 * 获取当前类型或团队的层级级别
	 * @return   -1 到 4  的数值
	 */
	public int getLevel();
	
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
	public Manager getManager();

	
	
	
}

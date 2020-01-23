package cc.natapp4.ddaig.dao_interface.health;

import java.util.List;

import cc.natapp4.ddaig.dao_interface.BaseDao;
import cc.natapp4.ddaig.domain.health.EnclosedScale;

/**
 * 用于操作封闭量表对象——EnclosedScale的持久层
 * @author 吉磊
 *
 */
public interface EnclosedScaleDao extends BaseDao<EnclosedScale> {
	
	// ===============个性化方法===============
	
	
	// ----------------------分页查询---------------------
	/**
	 * 获取当前系统中所有问卷的数量
	 * @return
	 */
	public int getCount4EnclosedScale();
	
	/**
	 * 分页查询，根据目标页码和每页的条目数量进行有关封闭式问卷的分页查询
	 * @param targetPageNum			目标页码
	 * @param pageItemNumLimit		每页的条目数量
	 * @return
	 */
	public List<EnclosedScale> getEssByPageLimit(int targetPageNum, int pageItemNumLimit);
	
	
	
}

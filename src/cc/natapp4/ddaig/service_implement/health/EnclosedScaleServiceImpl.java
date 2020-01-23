package cc.natapp4.ddaig.service_implement.health;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cc.natapp4.ddaig.bean.health.ReturnMessage4CountandCreateFirstPage4Eslist;
import cc.natapp4.ddaig.dao_interface.BaseDao;
import cc.natapp4.ddaig.dao_interface.health.EnclosedScaleDao;
import cc.natapp4.ddaig.domain.health.EnclosedScale;
import cc.natapp4.ddaig.service_implement.BaseServiceImpl;
import cc.natapp4.ddaig.service_interface.health.EnclosedScaleService;

@Service("enclosedScaleService")
public class EnclosedScaleServiceImpl extends BaseServiceImpl<EnclosedScale> implements EnclosedScaleService {

	@Autowired
	private EnclosedScaleDao  enclosedScaleDao;
	
	@Override
	protected BaseDao<EnclosedScale> getBaseDao() {
		return this.enclosedScaleDao;
	}

	
	
	@Override
	public ReturnMessage4CountandCreateFirstPage4Eslist getCountandCreateFirstPage4InitLaypage4EslistPage() {
		ReturnMessage4CountandCreateFirstPage4Eslist result = new ReturnMessage4CountandCreateFirstPage4Eslist();
		// 首先，获取量表总条目数量
		int total = enclosedScaleDao.getCount4EnclosedScale();
		// 随后，获取用于组织首页数据（前10条）的数据
		List<EnclosedScale> ess = enclosedScaleDao.getEssByPageLimit(1, 10);
		// 放入到结果中
		result.setCount(total);
		result.setEnclosedScales(ess);
		// 返回到action层，用于AJAX交互
		return result;
	}

	@Override
	public ReturnMessage4CountandCreateFirstPage4Eslist getEssByPageLimit(int targetPageNum, int pageItemNumLimit) {
		ReturnMessage4CountandCreateFirstPage4Eslist result = new ReturnMessage4CountandCreateFirstPage4Eslist();
		// 基于分页查询，实现对指定页码的数据查找
		List<EnclosedScale> ess = enclosedScaleDao.getEssByPageLimit(targetPageNum, pageItemNumLimit);
		// 封装到结果中
		result.setEnclosedScales(ess);
		// 返回给action层，用于AJAX交互
		return result;
	}


}

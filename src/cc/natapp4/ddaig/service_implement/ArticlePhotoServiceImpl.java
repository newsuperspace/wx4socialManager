package cc.natapp4.ddaig.service_implement;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import cc.natapp4.ddaig.dao_interface.ArticlePhotoDao;
import cc.natapp4.ddaig.dao_interface.BaseDao;
import cc.natapp4.ddaig.domain.ArticlePhoto;
import cc.natapp4.ddaig.service_interface.ArticlePhotoService;

@Service("articlePhotoService")
public class ArticlePhotoServiceImpl extends BaseServiceImpl<ArticlePhoto> implements ArticlePhotoService {

	@Resource(name="articlePhotoDao")
	private ArticlePhotoDao  dao;
	@Override
	protected BaseDao<ArticlePhoto> getBaseDao() {
		return dao;
	}


	
}

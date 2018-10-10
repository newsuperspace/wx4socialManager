package cc.natapp4.ddaig.service_implement;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;


import cc.natapp4.ddaig.dao_interface.ArticleDao;
import cc.natapp4.ddaig.dao_interface.BaseDao;
import cc.natapp4.ddaig.domain.Article;
import cc.natapp4.ddaig.service_interface.ArticleService;

@Service("articleService")
public class ArticleServiceImpl extends BaseServiceImpl<Article> implements ArticleService {

	@Resource(name="activityDao")
	private ArticleDao  dao;
	@Override
	protected BaseDao<Article> getBaseDao() {
		return dao;
	}


	
}

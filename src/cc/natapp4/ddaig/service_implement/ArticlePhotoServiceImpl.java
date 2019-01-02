package cc.natapp4.ddaig.service_implement;

import java.io.File;
import java.io.Serializable;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
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
	
	/**
	 * 根据持久化状态对象，从本地磁盘删除文章图片文件，从数据库中删除图片数据
	 */
	@Override
	public void delete(ArticlePhoto t) {
		String realPath = ServletActionContext.getServletContext().getRealPath("/"+t.getPath());
		File file  =  new  File(realPath);
		file.delete();
		
		super.delete(t);
	}
	
	/**
	 * 根据图片ID，从本地磁盘删除文章图片文件，从数据库中删除图片数据
	 */
	@Override
	public void deleteById(Serializable id) {
		ArticlePhoto queryEntityById = dao.queryEntityById(id);
		String realPath = ServletActionContext.getServletContext().getRealPath("/"+queryEntityById.getPath());
		File file  =  new File(realPath);
		
		super.deleteById(id);
	}

	
	
}

package cc.natapp4.ddaig.action;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import cc.natapp4.ddaig.domain.Activity;
import cc.natapp4.ddaig.domain.Article;
import cc.natapp4.ddaig.domain.ArticlePhoto;
import cc.natapp4.ddaig.json.returnMessage.ReturnMessage4Common;
import cc.natapp4.ddaig.json.returnMessage.ReturnMessage4UploadPhoto;
import cc.natapp4.ddaig.service_interface.ActivityService;
import cc.natapp4.ddaig.service_interface.ArticlePhotoService;
import cc.natapp4.ddaig.service_interface.ArticleService;
import cc.natapp4.ddaig.service_interface.DoingProjectService;
import cc.natapp4.ddaig.service_interface.GeographicService;
import cc.natapp4.ddaig.service_interface.HouseService;
import cc.natapp4.ddaig.service_interface.UserService;

@Controller("articleAction")
@Scope("prototype")
@Lazy(true)
public class ArticleAction extends ActionSupport implements ModelDriven<Article> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 10122314547L;
	// ==============================DI注入==============================
	@Resource(name = "activityService")
	private ActivityService activityService;
	@Resource(name = "userService")
	private UserService userService;
	@Resource(name = "doingProjectService")
	private DoingProjectService doingProjectService;
	@Resource(name = "houseService")
	private HouseService houseService;
	@Resource(name = "geographicService")
	private GeographicService geographicService;
	@Resource(name = "articlePhotoService")
	private ArticlePhotoService articlePhotoService;
	@Resource(name = "articleService")
	private ArticleService articleService;

	// ==============================属性驱动==============================
	// 待删除的ArticlePhoto的apid
	private String apid;
	public String getApid() {
		return apid;
	}
	public void setApid(String apid) {
		this.apid = apid;
	}
	
	// 活动的aid
	private String aid;
	public String getAid() {
		return aid;
	}
	public void setAid(String aid) {
		this.aid = aid;
	}

	private String fileName;
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	private File  file;
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}

	// ==============================模型驱动==============================
	private Article article;
	@Override
	public Article getModel() {
		this.article  =  new  Article();
		return this.article;
	}
	// ==============================Methods==============================
	/**
	 * 【完成】
	 * 获取某一篇文章
	 * @return
	 */
	public String getArticle(){
		Activity activity = activityService.queryEntityById(this.aid);
		Article  article  =  null;
		if(null==activity.getArticle()){
			article  =  new  Article();
			// 还没有文章，新建一个文章对象
			article.setActivity(activity);
			article.setContent("");
			article.setForwardingNum(0);
			article.setReadingNum(0);
			article.setTitle(activity.getName()+"活动新闻稿");
			// 向数据库中新建
			articleService.save(article);
		}else{
			article = activity.getArticle();
		}
		ActionContext.getContext().getValueStack().push(article);
		return SUCCESS;
	}
	
	/**
	 * 【完成】
	 * 进入某个活动的编写文章的表单页面，
	 * 如果从数据中发现该活动已经存在文章数据，
	 * 则向表单页面中回显这些数据
	 * @return
	 */
	public String showInputPage(){
		String artid  =  this.article.getArtid();
		Article a = articleService.queryEntityById(artid);
		
		ActionContext.getContext().getValueStack().push(artid);
		return "inputPage";
	}
	
	/**
	 * 【完成】
	 * 在articleInput页面点击“保存”按钮时调用本方法
	 * 检索被创建文章的活动是否已经有文章，
	 * 如果有文章则执行dao的update()
	 * 如果没有文章则执行dao的save()
	 * @return
	 */
	public String  saveActicle(){
		ReturnMessage4Common  result  =  new  ReturnMessage4Common();
		Article a  =  articleService.queryEntityById(this.article.getArtid());
		a.setContent(this.article.getContent());
		a.setTitle(this.article.getTitle());
		articleService.update(a);
		
		result.setResult(true);
		result.setMessage("更新成功！");
		ActionContext.getContext().getValueStack().push(result);
		return "json";
	}

	/**
	 * 【完成】
	 * AJAX
	 * 删除图片
	 * @return
	 */
	public String deletePhoto(){
		
		ReturnMessage4Common  result  =  new  ReturnMessage4Common();
		articlePhotoService.deleteById(this.apid);
		result.setResult(true);
		result.setMessage("删除成功！");
		
		ActionContext.getContext().getValueStack().push(result);
		return "json";
	}
	
	/**
	 * 【完成】
	 * 上传活动照片
	 * @return
	 */
	public String uploadPhoto(){
		System.out.println("fileName=" + this.fileName);
		System.out.println("aid="+this.aid);
		ReturnMessage4UploadPhoto  result   =  new ReturnMessage4UploadPhoto();
		
		// 检查用于存放上传图片的/upload路径是否存在，不存在则创建出来
		String uuid  =  UUID.randomUUID().toString();
		int  hashCode  =  uuid.hashCode();
		int  first = hashCode & 0xf;
		int second = (hashCode & 0xf0) >>4;
		String dir  =  ServletActionContext.getServletContext().getRealPath("/upload/"+first+"/"+second);
		// 这里的targetFile是一个路径不是文件
		File targetFile = new File(dir);
		if(!targetFile.exists()){
			targetFile.mkdirs();
		}
		// 正式创建用来存放上传图片的路径和file
		String target = ServletActionContext.getServletContext().getRealPath("/upload/"+first+"/"+second +"/"+ this.fileName);
		// 而这里的targetFile才是真正的文件
		targetFile  =  new  File(target);
		// 通过struts2提供的FileUtils类拷贝
		try {
			FileUtils.copyFile(file, targetFile);
			ArticlePhoto  photo  =  new ArticlePhoto();
			Activity a =  activityService.queryEntityById(this.aid);
			Article art = a.getArticle();
			List<ArticlePhoto> photos = art.getPhotos();
			if(null==photos){
				photos =  new ArrayList<ArticlePhoto>();
				art.setPhotos(photos);
			}
			photo.setApid(uuid);
			photo.setArticle(art);
			photo.setDescription("");
			photo.setPath("upload/"+first+"/"+second +"/"+ this.fileName);
			photos.add(photo);
			articlePhotoService.save(photo);
			
			result.setApid(photo.getApid());
			result.setMessage("上传成功！");
			result.setResult(true);
		} catch (IOException e) {
			e.printStackTrace();
			result.setMessage("上传图片失败");
			result.setResult(false);
		}
		ActionContext.getContext().getValueStack().push(result);
		return "json";
	}
	
	
}

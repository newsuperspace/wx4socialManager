package cc.natapp4.ddaig.action;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

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
import cc.natapp4.ddaig.domain.User;
import cc.natapp4.ddaig.json.returnMessage.ReturnMessage4Common;
import cc.natapp4.ddaig.json.returnMessage.ReturnMessage4UploadPhoto;
import cc.natapp4.ddaig.service_interface.ActivityService;
import cc.natapp4.ddaig.service_interface.ArticlePhotoService;
import cc.natapp4.ddaig.service_interface.ArticleService;
import cc.natapp4.ddaig.service_interface.DoingProjectService;
import cc.natapp4.ddaig.service_interface.GeographicService;
import cc.natapp4.ddaig.service_interface.HouseService;
import cc.natapp4.ddaig.service_interface.UserService;
import cc.natapp4.ddaig.utils.FileController;
import cn.com.obj.freemarker.ExportDoc;

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
	// 用作error全局结果集指定的页面——error.jsp中显示错误的信息内容
	private String errorMessage;
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

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
		
		ActionContext.getContext().getValueStack().push(activity.getArticle());
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
		System.out.println("文章所属活动的aid是："+a.getActivity().getAid());
		
		ActionContext.getContext().getValueStack().push(a);
		return "inputPage";
	}
	
	/**
	 * 【完成】
	 * 在articleInput页面点击“保存”按钮时调用本方法
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
		ArticlePhoto ap = articlePhotoService.queryEntityById(this.apid);
		Article article2 = ap.getArticle();
		article2.getPhotos().remove(ap);
		articleService.update(article2);
		
		ap.setArticle(null);
		articlePhotoService.update(ap);
		articlePhotoService.delete(ap);
		
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
		
		// 如果fileName文件名中存在空格，则应该主动替换成为"_"
		fileName = fileName.replace(" ", "_");
		
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
	
	
	// =================================基于Struts2的下载功能================================
	// 该inputStream属性将作为在struts-article.xml中，名为download的结果集中所使用的，用来向前端提供下载
	private InputStream inputStream;
	public InputStream getInputStream() {
		return inputStream;
	}
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}
	/**
	 *  负责下载活动记录表
	 * @return
	 * @throws Exception 
	 */
	public String  downloadArticle() throws Exception{
		
//		 ServletContext context = ServletActionContext.getServletContext();
		
		// 先找到文章
		Article art = articleService.queryEntityById(this.article.getArtid());
		
		/*
		 * downloadMap 是Map<String,Map<String,String>>类型的容器
		 * 其中外层String是形如"article"（文章）、“signin”（签到）、“minus_first”（组织人员名单）、“zero”（组织人员名单）等
		 * 内层Map的键名可以是“aid”（活动的主键ID值）、“lid”（层级对象的主键ID值）等
		 */
//		Map<String,Map<String,String>> map = (Map<String, Map<String, String>>) context.getAttribute("downloadMap");
//		Map<String, String> articleMap = map.get("articleMap");
//		String fullPath = articleMap.get(art.getArtid());
//		// 如果找不到待下载文件的磁盘URL，则
//		if(null==fullPath){
//			// 开始创建DOC
//			ExportDoc maker = new ExportDoc("UTF-8");
//			String fileName = "download"+File.separator+"article";
//			FileController.makeDirs(ServletActionContext.getServletContext().getRealPath(fileName));
//			fileName += File.separator+art.getActivity().getName()+".doc";
//			fullPath = ServletActionContext.getServletContext().getRealPath(fileName);
//	        maker.exportDoc(art, fullPath, "test.ftl"); 
//	        articleMap.put(art.getArtid(), fullPath);
//	        System.out.println("创建成功！");
//		}

		String fullPath;
		ExportDoc maker = new ExportDoc("UTF-8");
		fullPath = "download"+File.separator+"article";
		FileController.makeDirs(ServletActionContext.getServletContext().getRealPath(fullPath));
		fullPath += File.separator+art.getActivity().getName()+".docx";
		fullPath = ServletActionContext.getServletContext().getRealPath(fullPath);
        maker.exportDoc4Article(art, fullPath, "article.ftl"); 
        /*
         * ★★★★★基于Struts2的文件下载★★★★★
         * 以下内容需要特别注意，下方的inputStream和fileName字段必须像下方
         * this.inputStream = ServletActionContext.getServletContext().getResourceAsStream(File.separator+fileName);
         * String s  =  art.getActivity().getName()+".doc";
         * this.fileName = new String(s.getBytes(),"ISO8859-1");
         * 这样编写，要十分注意的是
         * 1、在通过servletActionContext获取资源的输入流时，此处传进去的一定是以当前web应用部署到
         * 服务器后的文件名（此处为weixin）为根目录的，以“\”为起始的相对路径，否则会报出
         * Can not find a java.io.InputStream with the name [inputStream] in the invocation stack错误
         * 2、在设置fileName为下载时的文件名，不能直接使用汉字作为文件名，否则会报出
         * Can not find a java.io.InputStream with the name [inputStream] in the invocation stack错误
         * 可行的方式就像这样，将汉字文件名字符串（包含后缀）编码为字节码，然后重新以“ISO8859-1”解码为
         * 字符串，然后就可用了。
         *
         */
        // -------------------独立准备Struts2用的输入流--------------------
        File file = new File(fullPath);
        if(!file.exists()){
        	// 如果指定路径中不存在文件，则直接返回，引导到在struts.xml配置文件中配置的名为error的全局结果集指定的错误页面反应问题
        	this.errorMessage = "路径："+fullPath+"下，不存在指定文件，请稍后再试！";
        	// error结果集索引字符串不是定义在当前Action对应的子配置文件中的，而是定义在struts.xml中配置文件中的全局结果集索引
        	return "error";
        }
        // 如果文件存在，则创建等待下载文件的文件输入流fis，该流失唯一与磁盘临时文件链接的流
        FileInputStream fis = new FileInputStream (file);
        // 准备字节数组（字节缓冲区）输出流备用
		ByteArrayOutputStream swapStream = new ByteArrayOutputStream();  
        // 准备字节缓冲区（也就是字节数组，1024字节就够用了）用作输入流和输出流的流对接
		byte[] buff = new byte[1024];  
		int rc = 0;  
		while ((rc = fis.read(buff, 0, 1024)) > 0) {  
			swapStream.write(buff, 0, rc);  
		}
		// 将输出流转变为用作下载的输入流，完成数据从待下载磁盘文件转入到下载输入流
		this.inputStream=new ByteArrayInputStream(swapStream.toByteArray());
        // 关闭待下载临时文件的输入流，从而该临时文件可以随时删除而又不会影响下载流
        fis.close();
        swapStream.close();
        file.delete();
        // ---------------准备下载用的文件名（形如：“测试文件.doc”）----------------
        String s  =  art.getActivity().getName()+".docx";
        this.fileName = new String(s.getBytes(),"ISO8859-1");
        
		return "download";
	}
	
	
	
}

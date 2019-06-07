package cn.com.obj.freemarker;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;

import cc.natapp4.ddaig.domain.Activity;
import cc.natapp4.ddaig.domain.Article;
import cc.natapp4.ddaig.domain.ArticlePhoto;
import cc.natapp4.ddaig.domain.DoingProject;
import cc.natapp4.ddaig.domain.Visitor;
import cc.natapp4.ddaig.domain.cengji.FirstLevel;
import cc.natapp4.ddaig.domain.cengji.MinusFirstLevel;
import cc.natapp4.ddaig.domain.cengji.SecondLevel;
import cc.natapp4.ddaig.domain.cengji.ThirdLevel;
import cc.natapp4.ddaig.domain.cengji.ZeroLevel;
import cc.natapp4.ddaig.utils.Image2Base64andBase642ImageUtils;
import cn.com.obj.freemarker.domain.Picture4FreeMarker;
import cn.com.obj.freemarker.domain.Signin4FreeMarker;
import cn.com.obj.freemarker.domain.Visitor4FreeMarker;
import freemarker.template.Configuration;
import freemarker.template.Template;

public class ExportDoc {

	/*
	 * =========================================================================
	 * = =============================初始化对象及筹备工作===============================
	 * =========================================================================
	 * =
	 */

	private Configuration configuration;
	private String encoding; // UTF-8

	public ExportDoc(String encoding) {
		// 通常为UTF-8
		this.encoding = encoding;
		// 设置使用的FreeMarker的版本
		configuration = new Configuration(Configuration.VERSION_2_3_28);
		// 设置字符集编码
		configuration.setDefaultEncoding(encoding);
		// 设定去哪里读取相应的ftl模板文件
		configuration.setClassForTemplateLoading(this.getClass(), "/templates4freemarker");
	}

	/**
	 * 在模板文件目录中找到指定名称为name的flt文件
	 * 
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public Template getTemplate(String name) throws Exception {
		return configuration.getTemplate(name);
	}

	/*
	 * =========================================================================
	 * = =============================业务方法，都在这下面===============================
	 * =========================================================================
	 * =
	 */

	/**
	 * 【封装--活动记录表数据】 创建 FreeMarker所需要的位于*.flt模板文件中的键值对儿数据信息
	 * 
	 * @return
	 */
	public Map<String, Object> getDataMap4Article(Article a) {
		// 我们使用一个哈希Map容器存放键值对儿
		Map<String, Object> dataMap = new HashMap<String, Object>();
		// 需要注意的是，必须将flt模板中存在的所有“占位符”都配置好对应的值，不能漏掉一个
		dataMap.put("purchase","创享计划");
		dataMap.put("projectName", a.getActivity().getProject().getBesureProject().getName());
		dataMap.put("activityName", a.getTitle());
		dataMap.put("date", a.getActivity().getEndTimeStr());
		if (a.getActivity().getGeographic() == null) {
			// 室内活动
			dataMap.put("local", a.getActivity().getHouse().getName());
		} else {
			// 室外活动
			dataMap.put("local", a.getActivity().getGeographic().getName());
		}
		dataMap.put("number", a.getActivity().getVisitors().size());
		dataMap.put("content", a.getContent());
		// 由于目标doc文件中存在图片，因此这里需要获取图片的B64编码字符串 【试用阶段，仅仅展示第一张图片】
		String imgRealPath = "";   // 存放图片在服务器磁盘上的真实路径
		String base64Str = "";   // 存放图片的base64编码字符串
		Picture4FreeMarker  picture = null;
		List<Picture4FreeMarker> pictures = new ArrayList<Picture4FreeMarker>();
		int index = 1;
		for(ArticlePhoto ap:a.getPhotos()) {
			picture = new Picture4FreeMarker();
			imgRealPath = ServletActionContext.getServletContext().getRealPath(a.getPhotos().get(0).getPath());
			base64Str = Image2Base64andBase642ImageUtils.getImgStr(imgRealPath);
			picture.setBase64Str(base64Str);
			picture.setDescription("这张图片是当前文章的第"+index+"张图片");
			picture.setName("图片"+index);
			
			pictures.add(picture);
			index++;
		}
		dataMap.put("pictures", pictures);

		return dataMap;
	}

	/**
	 * 【生成--活动记录表】 通过FreeMarker在本地磁盘中输出Doc
	 * 
	 * @param a
	 *            要下载文章的Article对象（数据都在这里面）
	 * @param fullDocPath
	 *            形如："c:\\aaa\\bbb\\ccc\\1.docx"
	 *            的目标生成doc文档的完整路径，无需预先进行路径是否存在的校验，FreeMarker创建时自动创建目录结构
	 * @param tempName
	 *            形如:"test.ftl" 模板文件名
	 * @throws Exception
	 */
	public void exportDoc4Article(Article a, String fullDocPath, String tempName) throws Exception {
		FileOutputStream out = new FileOutputStream(fullDocPath);
		OutputStreamWriter writer = new OutputStreamWriter(out, this.encoding);
		BufferedWriter bufferWriter = new BufferedWriter(writer);
		getTemplate(tempName).process(getDataMap4Article(a), bufferWriter);
		// 一定要关闭流啊，不然创建的临时数据doc文件下载完毕后不能立刻删除
		bufferWriter.close();
		writer.close();
		out.close();
		// 打印信息
		System.out.println("位于" + fullDocPath + "的DOC文件生成完毕！");
	}

	/**
	 * 【封装--签到表数据】
	 * 
	 * @param activity
	 *            待下载活动签到表的活动对象（关键数据都在这里面）
	 */
	public Map<String, Object> getDataMap4SigninList(Activity activity) throws IOException {
		// ----------准备用作组织模板数据的map容器------------
		Map<String, Object> map = new HashMap<String, Object>();
		// ----------获取并存入标题数据----------
		DoingProject doingProject = activity.getProject();
		if (null != doingProject.getThirdLevel()) {
			ThirdLevel thirdLevel = doingProject.getThirdLevel();
			map.put("teamName", thirdLevel.getName());
		} else if (null != doingProject.getSecondLevel()) {
			SecondLevel secondLevel = doingProject.getSecondLevel();
			map.put("teamName", secondLevel.getName());
		} else if (null != doingProject.getFirstLevel()) {
			FirstLevel firstLevel = doingProject.getFirstLevel();
			map.put("teamName", firstLevel.getName());
		} else if (null != doingProject.getZeroLevel()) {
			ZeroLevel zeroLevel = doingProject.getZeroLevel();
			map.put("teamName", zeroLevel.getName());
		} else if (null != doingProject.getMinusFirstLevel()) {
			MinusFirstLevel minusFirstLevel = doingProject.getMinusFirstLevel();
			map.put("teamName", minusFirstLevel.getName());
		}
		map.put("projectName", doingProject.getBesureProject().getName());
		map.put("activityName", activity.getName());
		map.put("startTimeStr", activity.getBeginTimeStr());
		if (null != activity.getHouse()) {
			map.put("place", activity.getHouse().getName());
		} else {
			map.put("place", activity.getGeographic().getName());
		}
		// ----------存入活动参与者数据----------
		List<Visitor4FreeMarker> visitors = new ArrayList<Visitor4FreeMarker>();
		map.put("visitors", visitors);
		Visitor4FreeMarker v4m = null;
		for (Visitor v : activity.getVisitors()) {
			v4m = new Visitor4FreeMarker();
			v4m.setStartTimeStr(v.getStartTimeStr());
			v4m.setEndTimeStr(v.getEndTimeStr());
			v4m.setScore(v.getScore());
			v4m.setSex(v.getUser().getSex());
			v4m.setUsername(v.getUser().getUsername());
			v4m.setWorkTime(v.getWorkTime() / 1000L / 60);
			if (null != v.getSignin()) {
				v4m.setSignin(new Signin4FreeMarker(v.getSignin().getName(), v.getSignin().getBase64Str()));
			}
			visitors.add(v4m);
		}

		return map;
	}

	/**
	 * 【生成--签到表】
	 * 
	 * @param activity
	 * @param fullDocPath
	 *            形如："c:\\aaa\\bbb\\ccc\\1.docx"
	 *            的目标生成doc文档的完整路径，无需预先进行路径是否存在的校验，FreeMarker创建时自动创建目录结构
	 * @param tempName
	 *            形如:"signinList.ftl" 模板文件名
	 * @throws Exception
	 */
	public void exportDoc4SigninList(Activity activity, String fullDocPath, String tempName) throws Exception {
		FileOutputStream out = new FileOutputStream(fullDocPath);
		OutputStreamWriter writer = new OutputStreamWriter(out, this.encoding);
		BufferedWriter bufferWriter = new BufferedWriter(writer);
		getTemplate(tempName).process(getDataMap4SigninList(activity), bufferWriter);
		// 一定要关闭流啊，不然创建的临时数据doc文件下载完毕后不能立刻删除
		bufferWriter.close();
		writer.close();
		out.close();
		// 打印信息
		System.out.println("位于" + fullDocPath + "的DOC文件生成完毕！");
	}
}

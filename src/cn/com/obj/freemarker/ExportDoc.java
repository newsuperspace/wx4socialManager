package cn.com.obj.freemarker;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.ServletActionContext;

import cc.natapp4.ddaig.domain.Article;
import cc.natapp4.ddaig.utils.Image2Base64andBase642ImageUtils;
import freemarker.template.Configuration;
import freemarker.template.Template;

public class ExportDoc {

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

	/**
	 * 创建 FreeMarker所需要的位于*.flt模板文件中的键值对儿数据信息
	 * 
	 * @return
	 */
	public Map<String, Object> getDataMap4Article(Article a) {
		// 我们使用一个哈希Map容器存放键值对儿
		Map<String, Object> dataMap = new HashMap<String, Object>();
		// 需要注意的是，必须将flt模板中存在的所有“占位符”都配置好对应的值，不能漏掉一个
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
		String img = ServletActionContext.getServletContext().getRealPath(a.getPhotos().get(0).getPath());
		dataMap.put("image", Image2Base64andBase642ImageUtils.getImgStr(img));

		return dataMap;
	}

	/**
	 * 通过FreeMarker在本地磁盘中输出Doc文件
	 * 
	 * @param doc  本地磁盘的保存路径
	 * @param name 使用的*.flt模板的文件名
	 * @throws Exception
	 */
	public void exportDoc(Article a, String doc, String name) throws Exception {
		FileOutputStream  out = new FileOutputStream(doc);
		OutputStreamWriter  writer =  new OutputStreamWriter(out,this.encoding);
		BufferedWriter bufferWriter = new BufferedWriter(writer);
		getTemplate(name).process(getDataMap4Article(a), bufferWriter);
		// 一定要关闭流啊，不然创建的临时数据doc文件下载完毕后不能立刻删除
		bufferWriter.close();
		writer.close();
		out.close();
	}

}

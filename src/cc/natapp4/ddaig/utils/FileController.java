package cc.natapp4.ddaig.utils;

import java.io.File;

/**
 * 用来对服务器端磁盘上的文件进行操作的工具类
 * @author Administrator
 *
 */
public class FileController {

	/**
	 * 删除指定磁盘真实路径下的某一个文件
	 * @param realPath  形如:"E:\apache-tomcat-9.0.8\webapps\weixin\qrcode\6\7\ofjsdof3292r.jpg"的真实路径
	 * @return
	 */
	public static boolean deleteFile(String realPath){
		boolean result = false;
		
		/*
		 *  由于参数realPath都是形如"E:\apache-tomcat-9.0.8\webapps\weixin\qrcode\15\13\a4ec164c-f7ba-49c1-98dc-1ae5e9fc3612.jpg"
		 *  而\是转移字符，因此必须转化成\\
		 *  
		 */
		realPath.replace("\\", "\\\\");
		File file = new  File(realPath);
		result = file.delete();
		if(result){
			System.out.println("路径为："+realPath+"已经成功删除!");
		}
		return result;
	}
}

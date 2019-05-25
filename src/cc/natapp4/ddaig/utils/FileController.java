package cc.natapp4.ddaig.utils;

import java.io.File;

/**
 * 用来对服务器端磁盘上的文件进行操作的工具类
 * 
 * @author Administrator
 *
 */
public class FileController {

	/**
	 * 删除指定磁盘真实路径下的某一个文件（不是文件夹）
	 * 
	 * @param realPath
	 *            形如:
	 *            "E:\apache-tomcat-9.0.8\webapps\weixin\qrcode\6\7\ofjsdof3292r.jpg"的真实路径
	 * @return
	 */
	public static boolean deleteFile(String realPath) {
		boolean result = false;

		/*
		 * 由于参数realPath都是形如"E:\apache-tomcat-9.0.8\webapps\weixin\qrcode\15\13\a4ec164c-f7ba-49c1-98dc-1ae5e9fc3612.jpg"
		 * 而\是转移字符，因此必须转化成\\
		 * 
		 */
		realPath.replace("\\", "\\\\");
		File file = new File(realPath);
		result = file.delete();
		if (result) {
			System.out.println("路径为：" + realPath + "已经成功删除!");
		}
		return result;
	}

	/**
	 * 迭代删除文件夹
	 * 
	 * @param dirPath
	 *            文件夹路径
	 */
	public static boolean deleteDir(String dirPath) {
		boolean  result = false;
		File file = new File(dirPath);
		if (file.isFile()) {
			// 如果该路径指定的是文件，则直接删除
			file.delete();
			result = true;
		} else {
			// 如果是文件夹
			File[] files = file.listFiles();
			if (files == null) {
				// 如果文件夹中不包含子目录，则直接删除文件夹
				file.delete();
				result = true;
			} else {
				// 如果文件夹包含子目录则遍历
				for (int i = 0; i < files.length; i++) {
					deleteDir(files[i].getAbsolutePath());
				}
				file.delete();
				result = true;
			}
		}
		return result;
	}

	
	/**
	 * 查看路径是否存在，不存在则逐层创建目录结构
	 * 
	 * @param realPath
	 *            形如:"E:\apache-tomcat-9.0.8\webapps\weixin\qrcode\6\7 的文件夹路径
	 */
	public static void makeDirs(String realPath) {
		File file = new File(realPath);
		if (!file.exists()) {
			// 如果该文件路径不存在，则逐层创建路径中的所有文件夹
			file.mkdirs();
		}
	}

}

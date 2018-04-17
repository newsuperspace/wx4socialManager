package cc.natapp4.ddaig.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * 收纳了专门用于读写配置file的工具类
 * @author Administrator
 *
 */
public class ConfigUtils {

	/**
	 * 用于加载Classpath根目录下的properties
	 * @param classpathFileName   相对于classpath的相对路径，如“wxConfig/weixin.properties”
	 * @return  Properties对象
	 */
	public static Properties  getProperties(String classpathFileName){
		
		Properties p  =  new Properties();
		/*
		 *	
		 * 	 以ClassLoader类加载器的getResourceAsStream()加载的磁盘file的路径根目录默认是classpath
		 *  而如果你是以this.getClass.getResourceAsStream()加载的磁盘file的路径根目录默认是this类同级目录
		 *  这一点需要特别注意 ★★★
		 */
		InputStream is = ConfigUtils.class.getClassLoader().getResourceAsStream(classpathFileName);
		
		try {
			p.load(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return p;
	}
	
	/**
	 * 获取记录在/config/permissionConfig/PermissionLevel_PermissionType_Permission.json文件中的
	 * 以JSON格式字符串记录的系统基础权限信息的字符串（带有格式字符，例如\t等）
	 * @return
	 * @throws IOException
	 */
	public static String getPermissionConfig() throws IOException{
		StringBuffer sb  = new StringBuffer();
		
		InputStream is  = null;
		/*
		 * ★★★
		 * class.getClassLoader().getResourceAsStream() 是用于加载项目中资源的最佳方式
		 * 给他传递的参数是基于classpath类路径根目录的相对路径字符串，
		 * 当前项目由于是基于Maven的项目，因此工程目录中的/src、/test、/config以及所有jar包的根目录
		 * 都相当于是一个共同的classpath集合，因此如果我们想要加载
		 * /config/permissionConfig/PermissionLevel_PermissionType_Permission.
		 * json 这个路径下的文件，只需要以permissionConfig/
		 * PermissionLevel_PermissionType_Permission.json为相对路径名作为参数即可
		 */
		is  =  ConfigUtils.class.getClassLoader().getResourceAsStream("permissionConfig/PermissionLevel_PermissionType_Permission.json");
		BufferedReader reader = new  BufferedReader(new InputStreamReader(is));
		String  temp  = "";
		while((temp = reader.readLine())!=null){
			sb.append(temp);
		}
		
		String json  =  new  String(sb.toString().getBytes(), "utf-8");
		System.out.println("ConfigUtils获取到的JSON字符串是："+json);
		return json;
	}
}
